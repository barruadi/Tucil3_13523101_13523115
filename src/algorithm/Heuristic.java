package algorithm;
import java.util.*;
import block.Block;
import board.Board;

public class Heuristic{

    // Heuristic 1: cari jarak dari primary block ke exit
    public static int calculateHeuristic(Board board) {
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
}