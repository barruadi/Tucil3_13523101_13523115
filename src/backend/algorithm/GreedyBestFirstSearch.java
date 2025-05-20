package algorithm;

import java.util.*;
import board.Board;

public class GreedyBestFirstSearch {
    
    public static List<Board> GreedyAlgorithm(Board initialBoard, String method) {
        PriorityQueue<BoardNode> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();
        Map<String, BoardNode> allNodes = new HashMap<>();
        
        int h = Heuristic.calculateHeuristic(initialBoard, method);
        BoardNode startNode = new BoardNode(initialBoard, null, 0, h);
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
                
                int hScore = Heuristic.calculateHeuristic(nextBoard, method);
                BoardNode nextNode = allNodes.get(nextBoardString);
                boolean isNewNode = (nextNode == null);
                
                if (isNewNode || hScore < nextNode.hScore) {
                    if (isNewNode) {
                        nextNode = new BoardNode(nextBoard, current, 0, hScore);
                        allNodes.put(nextBoardString, nextNode);
                    } else {
                        nextNode.parent = current;
                        nextNode.hScore = hScore;
                        nextNode.fScore = hScore;
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
