package algorithm;

import java.util.*;
import block.Block;
import board.Board;

public class BoardNode implements Comparable<BoardNode> {
    Board board;
    BoardNode parent;
    int gScore;
    int hScore; 
    int fScore;
    
    public BoardNode(Board board, BoardNode parent, int gScore, int hScore) {
        this.board = board;
        this.parent = parent;
        this.gScore = gScore;
        this.hScore = hScore;
        this.fScore = gScore + hScore;
    }
    
    @Override
    public int compareTo(BoardNode other) {
        return Integer.compare(this.fScore, other.fScore);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        BoardNode other = (BoardNode) obj;
        
        return board.isSameBoard(other.board);
    }

}