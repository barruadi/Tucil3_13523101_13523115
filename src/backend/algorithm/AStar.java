package algorithm;

import java.util.*;
import block.Block;
import board.Board;
import algorithm.Heuristic;
import algorithm.BoardNode;

public class AStar {
    
    public static List<Board> AStarAlgorithm(Board initialBoard, String method) {
        PriorityQueue<BoardNode> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();
        Map<String, BoardNode> allNodes = new HashMap<>();
        
        // inisialisasi awal
        int hInit = Heuristic.calculateHeuristic(initialBoard, method);
        BoardNode startNode = new BoardNode(initialBoard, null, 0, hInit);
        
        openSet.add(startNode);
        allNodes.put(BoardNode.boardToString(initialBoard), startNode);
        
        while (!openSet.isEmpty()) {
            BoardNode current = openSet.poll();
            
            if (current.board.isFinish()) {
                System.out.println("Found a solution!");
                return reconstructPath(current);
            }
            
            String currentBoardString = BoardNode.boardToString(current.board);
            closedSet.add(currentBoardString);
            
            List<Board> possibleMoves = current.board.getAllPossibleMove();
            
            for (Board nextBoard : possibleMoves) {
                nextBoard.updateBoardData();
                String nextBoardString = BoardNode.boardToString(nextBoard);
                
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
                            Heuristic.calculateHeuristic(nextBoard, method)
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