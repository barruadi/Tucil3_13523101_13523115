import algorithm.AStar;
import board.Board;
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
        AStar aStar = new AStar();
        List<Board> boards = aStar.AStarAlgorithm(board);
        int count = 0;
        if (boards != null) {
            System.out.println("Found solution:" + boards.size());
            for (Board b : boards) {
                System.out.println("Step " + count++);
                b.printBoard();
            }
        } else {
            System.out.println("No solution found.");
        }
        
    }
}
