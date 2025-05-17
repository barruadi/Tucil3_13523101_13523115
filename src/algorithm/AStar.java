package algorithm;

import java.util.*;
import block.Block;
import board.Board;
import algorithm.Heuristic;
import algorithm.BoardNode;

public class AStar {
    
    public static List<Board> AStarAlgorithm(Board initialBoard) {
        PriorityQueue<BoardNode> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();
        Map<String, BoardNode> allNodes = new HashMap<>();
        
        
        // inisialisasi awal
        BoardNode startNode = new BoardNode(initialBoard, null, 0, Heuristic.calculateHeuristic(initialBoard));
        
        openSet.add(startNode);
        allNodes.put(boardToString(initialBoard), startNode);
        
        while (!openSet.isEmpty()) {
            BoardNode current = openSet.poll();
            
            if (current.board.isFinish()) {
                System.out.println("Found a solution!");
                return reconstructPath(current);
            }
            
            String currentBoardString = boardToString(current.board);
            closedSet.add(currentBoardString);
            
            List<Board> possibleMoves = current.board.getAllPossibleMove();
            
            for (Board nextBoard : possibleMoves) {
                nextBoard.updateBoardData();
                String nextBoardString = boardToString(nextBoard);
                
                if (closedSet.contains(nextBoardString)) {
                    continue;
                }
                int tentativeGScore = current.gScore + 1;
                
                BoardNode nextNode = allNodes.get(nextBoardString);
                boolean isNewNode = (nextNode == null);

                
                if (isNewNode || tentativeGScore < nextNode.gScore) {
                    if (isNewNode) {
                        nextNode = new BoardNode(
                            nextBoard,
                            current,
                            tentativeGScore,
                            Heuristic.calculateHeuristic(nextBoard)
                        );
                        allNodes.put(nextBoardString, nextNode);
                        // System.out.println("Adding new node: " + nextBoardString);
                    } else {
                        nextNode.parent = current;
                        nextNode.gScore = tentativeGScore;
                        nextNode.fScore = tentativeGScore + nextNode.hScore;
                        
                        openSet.remove(nextNode);
                    }
                    
                    openSet.add(nextNode);
                }
            }
        }
        
        return new ArrayList<>();
    }
    
    // convert board ke string untuk memudahkan perbandingan
    private static String boardToString(Board board) {
        StringBuilder sb = new StringBuilder();
        char[][] boardData = board.getBoardData();
        
        for (int i = 0; i < board.getHeight(); i++) {
            for (int j = 0; j < board.getWidth(); j++) {
                sb.append(boardData[i][j]);
            }
        }
        
        return sb.toString();
    }
    
    private static List<Board> reconstructPath(BoardNode goalNode) {
        List<Board> path = new ArrayList<>();
        BoardNode current = goalNode;
        
        while (current != null) {
            path.add(current.board);
            current = current.parent;
        }
        
        Collections.reverse(path);
        return path;
    }
    
}