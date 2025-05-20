import algorithm.AStar;
import algorithm.BoardToJsonConverter;
import board.Board;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*; 
import utils.InputOutput;
public class Main {

    public static void main(String[] args) {

        InputOutput io = new InputOutput();
        List<String> lines = io.readFileFromString("tes.txt");

        Board board = io.makeBoard(lines);
        boolean cek = io.makeBlocks(lines,board);
        if (!cek) {
            System.out.println("Data yang dimasukkan tidak valid");
            return;
        }

        List<Board> allPossibleMoves = board.getAllPossibleMove();
        List<Board> boards = AStar.AStarAlgorithm(board);
        // Convert solution to JSON
        String jsonOutput = BoardToJsonConverter.convertToJson(boards);
        
        // // Print JSON to console
        // System.out.println(jsonOutput);
        
        // Save JSON to file
        try (FileWriter file = new FileWriter("solution.json")) {
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
