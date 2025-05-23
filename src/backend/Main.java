import algorithm.*;
import board.Board;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*; 
import utils.InputOutput;
public class Main {

    public static void main(String[] args) {

        InputOutput io = new InputOutput();
        List<String> lines = io.readFileFromString("qwertyuiop.txt");

        Board board = io.makeBoard(lines);
        boolean cek = io.makeBlocks(lines,board);
        if (!cek) {
            System.out.println("Data yang dimasukkan tidak valid");
            return;
        }

        String algorithm = args[0];
        String heuristic = args[1];

        long start = System.currentTimeMillis();

        List<Board> boards = new ArrayList<>();
        if (algorithm.equals("AStar")) {
            boards = AStar.AStarAlgorithm(board, heuristic);
        } else if (algorithm.equals("UCS")) {
            boards = UCS.UCSAlgorithm(board, heuristic);
        } else if (algorithm.equals("GBFS")) {
            boards = GreedyBestFirstSearch.GreedyAlgorithm(board, heuristic);
        } else if (algorithm.equals("bidirectional")) {
            boards = BidirectionalSolver.solve(board);
        }

        long end = System.currentTimeMillis();

        // Convert solution to JSON
        String jsonOutput = BoardToJsonConverter.convertToJson(boards, end - start);
        
        // Save JSON to file
        try (FileWriter file = new FileWriter("../src/frontend/test/public/solution.json")) {
            System.out.println("Absolute path: " + new java.io.File("../src/frontend/test/public/solution.json").getAbsolutePath());
            file.write(jsonOutput);
            System.out.println("Solution saved to solution.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
