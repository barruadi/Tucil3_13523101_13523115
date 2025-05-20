package algorithm;
import java.util.*;
import block.Block;
import board.Board;

public class Heuristic{
    public static int calculateHeuristic(Board board, String method) {
        if (method.equals("1")) {
            return calculateHeuristicSatu(board);
        } else if (method.equals("2")) {
            return calculateHeuristicDua(board);
        }
        return 0;
    }

    // Heuristic 1: cari jarak dari primary block ke exit
    public static int calculateHeuristicSatu(Board board) {
        Block primaryBlock = board.getPrimaryBlock();
        
        if (primaryBlock.isBlockVertical()) {
            if (board.isKiriAtas()) {
                return primaryBlock.getBlockColIndex();
            } else {
                return board.getWidth() - (primaryBlock.getBlockColIndex() + primaryBlock.getBlockSize());
            }
        } else {
            if (board.isKiriAtas()) {
                return primaryBlock.getBlockRowIndex();
            } else {
                return board.getHeight() - (primaryBlock.getBlockRowIndex() + primaryBlock.getBlockSize());
            }
        }
    }

    // Heuristic 2: Banyaknya mobil yang mengahalangi dari exit
    public static int calculateHeuristicDua(Board board) {
        Block primaryBlock = board.getPrimaryBlock();

        int row = primaryBlock.getBlockRowIndex();
        int col = primaryBlock.getBlockColIndex();
        int carAmount = 0;

        if (primaryBlock.isBlockVertical()) {
            if (board.isKiriAtas()) {
                for (int i = row; i > 0; i--) {
                    if ((board.getBoardData())[i][col] != '.') {
                        carAmount++;
                    }
                }
            } else {
                for (int i = row + primaryBlock.getBlockSize(); i < board.getHeight(); i++) {
                    if ((board.getBoardData())[i][col] != '.') {
                        carAmount++;
                    }
                }
            }
        } else {
            if (board.isKiriAtas()) {
                for (int i = col; i > 0; i--) {
                    if ((board.getBoardData())[row][i] != '.') {
                        carAmount++;
                    }
                }
            } else {
                for (int i = col + primaryBlock.getBlockSize(); i < board.getWidth(); i++) {
                    if ((board.getBoardData())[row][i] != '.') {
                        carAmount++;
                    }
                }
            }
        }
        return carAmount;
    }
}