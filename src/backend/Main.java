import algorithm.*;
import board.Board;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*; 
import utils.InputOutput;
public class Main {

    public static void main(String[] args) {

        InputOutput io = new InputOutput();
        List<String> lines = io.readFileFromString("input.txt");

        Board board = io.makeBoard(lines);
        boolean cek = io.makeBlocks(lines,board);
        if (!cek) {
            System.out.println("Data yang dimasukkan tidak valid");
            return;
        }

        String algorithm = args[0];

        List<Board> allPossibleMoves = board.getAllPossibleMove();

        List<Board> boards = new ArrayList<>();
        if (algorithm.equals("AStar")) {
            boards = AStar.AStarAlgorithm(board);
        } else if (algorithm.equals("UCS")) {
            boards = UCS.UCSAlgorithm(board);
        } else if (algorithm.equals("GBFS")) {
            boards = GreedyBestFirstSearch.GreedyAlgorithm(board);
        } else if (algorithm.equals("bidirectional")) {
            boards = BidirectionalSolver.solve(board);
        }
        // Convert solution to JSON
        String jsonOutput = BoardToJsonConverter.convertToJson(boards);
        
        // // Print JSON to console
        // System.out.println(jsonOutput);
        
        // Save JSON to file
        try (FileWriter file = new FileWriter("../src/frontend/public/solution.json")) {
            file.write(jsonOutput);
            System.out.println("Solution saved to solution.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // BidirectionalSolver al = new BidirectionalSolver();
        // List<Board> boards = al.solve(board);
        int count = 0;
        if (boards != null) {
            System.out.println("Found solution:" + boards.size());
            for (Board b : boards) {
                System.out.println("Step " + count++);
                b.printBoard();
            }}
        // } else {
        //     System.out.println("No solution found.");
        // }
    }
}
