package algorithm;

import block.Block;
import board.Board;
import java.util.*;

public class BidirectionalSolver {

    public List<Board> solve(Board startBoard) {
        Queue<Board> forwardQueue = new LinkedList<>();
        Queue<Board> backwardQueue = new LinkedList<>();

        Map<String, BoardNode> forwardVisited = new HashMap<>();
        Map<String, BoardNode> backwardVisited = new HashMap<>();

        forwardQueue.add(startBoard);
        forwardVisited.put(BoardNode.boardToString(startBoard), new BoardNode(startBoard, null, 0, 0));

        Board goalBoard = generateGoalBoard(startBoard);
        if (goalBoard == null) return null;

        backwardQueue.add(goalBoard);
        backwardVisited.put(BoardNode.boardToString(goalBoard), new BoardNode(goalBoard, null, 0, 0));

        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
            Board fCurrent = forwardQueue.poll();
            String fKey = BoardNode.boardToString(fCurrent);

            for (Board next : fCurrent.getAllPossibleMove()) {
                String nextKey = BoardNode.boardToString(next);
                if (forwardVisited.containsKey(nextKey)) continue;

                BoardNode nextNode = new BoardNode(next, forwardVisited.get(fKey), 0, 0);
                forwardVisited.put(nextKey, nextNode);
                forwardQueue.add(next);

                if (backwardVisited.containsKey(nextKey)) {
                    return mergePath(nextNode, backwardVisited.get(nextKey));
                }
            }

            Board bCurrent = backwardQueue.poll();
            String bKey = BoardNode.boardToString(bCurrent);

            for (Board next : bCurrent.getAllPossibleMove()) {
                String nextKey = BoardNode.boardToString(next);
                if (backwardVisited.containsKey(nextKey)) continue;

                BoardNode nextNode = new BoardNode(next, backwardVisited.get(bKey), 0, 0);
                backwardVisited.put(nextKey, nextNode);
                backwardQueue.add(next);

                if (forwardVisited.containsKey(nextKey)) {
                    return mergePath(forwardVisited.get(nextKey), nextNode);
                }
            }
        }

        return null;
    }

    private List<Board> mergePath(BoardNode forwardNode, BoardNode backwardNode) {
        LinkedList<Board> path = new LinkedList<>();
        BoardNode curr = forwardNode;
        while (curr != null) {
            path.addFirst(curr.board);
            curr = curr.parent;
        }

        curr = backwardNode.parent;
        while (curr != null) {
            path.addLast(curr.board);
            curr = curr.parent;
        }

        return path;
    }

    private Board generateGoalBoard(Board from) {
        Board copy = from.clone();
        copy.setKiriAtas(from.isKiriAtas());
        Block primary = copy.getPrimaryBlock();

        if (primary == null) return null;

        Block goalPrimary;
        if (primary.isBlockVertical()) {
            int goalRow = from.isKiriAtas() ? 0 : from.getHeight() - primary.getBlockSize();
            goalPrimary = primary.makeBlockNewPosition(goalRow, primary.getBlockColIndex());
        } else {
            int goalCol = from.isKiriAtas() ? 0 : from.getWidth() - primary.getBlockSize();
            goalPrimary = primary.makeBlockNewPosition(primary.getBlockRowIndex(), goalCol);
        }

        copy.updateBlock(goalPrimary);
        return copy;
    }
}
