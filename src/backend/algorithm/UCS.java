package algorithm;

import java.util.*;
import board.Board;

public class UCS {
    
    public static List<Board> UCSAlgorithm(Board initialBoard, String method) {
        PriorityQueue<BoardNode> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();
        Map<String, BoardNode> allNodes = new HashMap<>();

        int hInit = Heuristic.calculateHeuristic(initialBoard, method);
        BoardNode startNode = new BoardNode(initialBoard, null, 0, hInit);
        
        openSet.add(startNode);
        allNodes.put(BoardNode.boardToString(initialBoard), startNode);
        
        while (!openSet.isEmpty()) {
            BoardNode current = openSet.poll();
            
            if (current.board.isFinish()) {
                return reconstructPath(current);
            }
            
            String currentBoardString = BoardNode.boardToString(current.board);
            closedSet.add(currentBoardString);
            
            for (Board nextBoard : current.board.getAllPossibleMove()) {
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
                            Heuristic.calculateHeuristic(initialBoard, method)
                        );
                        allNodes.put(nextBoardString, nextNode);
                    } else {
                        nextNode.parent = current;
                        nextNode.gScore = tentativeGScore;
                        nextNode.fScore = tentativeGScore;
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
