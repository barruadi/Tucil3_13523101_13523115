package algorithm;

import block.Block;
import board.Board;
import java.util.*;

public class BoardToJsonConverter {

    public static String convertToJson(List<Board> boardList) {
        if (boardList == null || boardList.isEmpty()) {
            return "{ \"steps\": [] }";
        }
        
        StringBuilder json = new StringBuilder();
        json.append("{\n  \"steps\": [\n");
        
        for (int i = 0; i < boardList.size(); i++) {
            Board currentBoard = boardList.get(i);
            json.append("    {\n");
            
            json.append("      \"boardState\": {\n");
            json.append("        \"height\": ").append(currentBoard.getHeight()).append(",\n");
            json.append("        \"width\": ").append(currentBoard.getWidth()).append(",\n");
            json.append("        \"grid\": [\n");
            
            char[][] boardData = currentBoard.getBoardData();
            for (int row = 0; row < currentBoard.getHeight(); row++) {
                json.append("          [");
                for (int col = 0; col < currentBoard.getWidth(); col++) {
                    json.append("\"").append(boardData[row][col]).append("\"");
                    if (col < currentBoard.getWidth() - 1) {
                        json.append(", ");
                    }
                }
                json.append("]");
                if (row < currentBoard.getHeight() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }
            json.append("        ],\n");
            
            json.append("        \"blocks\": [\n");
            List<Block> blocks = currentBoard.getBlocks();
            for (int b = 0; b < blocks.size(); b++) {
                Block block = blocks.get(b);
                json.append("          {\n");
                json.append("            \"id\": \"").append(block.getBlockId()).append("\",\n");
                json.append("            \"row\": ").append(block.getBlockRowIndex()).append(",\n");
                json.append("            \"col\": ").append(block.getBlockColIndex()).append(",\n");
                json.append("            \"size\": ").append(block.getBlockSize()).append(",\n");
                json.append("            \"isVertical\": ").append(block.isBlockVertical()).append(",\n");
                json.append("            \"isPrimary\": ").append(block.isBlockPrimary()).append("\n");
                json.append("          }");
                if (b < blocks.size() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }
            json.append("        ]\n");
            json.append("      }");
            
            if (i > 0) {
                Board previousBoard = boardList.get(i - 1);
                String moveInfo = getMoveInfo(previousBoard, currentBoard);
                json.append(",\n      \"move\": ").append(moveInfo);
            }
            
            json.append("\n    }");
            if (i < boardList.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        
        json.append("  ]\n}");
        return json.toString();
    }

    private static String getMoveInfo(Board previous, Board current) {
        char movedBlockId = '\0';
        String direction = "";
        int steps = 0;
        
        Map<Character, Block> prevBlockMap = new HashMap<>();
        Map<Character, Block> currBlockMap = new HashMap<>();
        
        for (Block block : previous.getBlocks()) {
            prevBlockMap.put(block.getBlockId(), block);
        }
        
        for (Block block : current.getBlocks()) {
            currBlockMap.put(block.getBlockId(), block);
        }
        
        for (char blockId : prevBlockMap.keySet()) {
            Block prevBlock = prevBlockMap.get(blockId);
            Block currBlock = currBlockMap.get(blockId);
            
            if (prevBlock.getBlockRowIndex() != currBlock.getBlockRowIndex() || 
                prevBlock.getBlockColIndex() != currBlock.getBlockColIndex()) {
                
                movedBlockId = blockId;
                
                if (prevBlock.isBlockVertical()) {
                    int rowDiff = currBlock.getBlockRowIndex() - prevBlock.getBlockRowIndex();
                    if (rowDiff < 0) {
                        direction = "UP";
                        steps = Math.abs(rowDiff);
                    } else {
                        direction = "DOWN";
                        steps = rowDiff;
                    }
                } else {
                    int colDiff = currBlock.getBlockColIndex() - prevBlock.getBlockColIndex();
                    if (colDiff < 0) {
                        direction = "LEFT";
                        steps = Math.abs(colDiff);
                    } else {
                        direction = "RIGHT";
                        steps = colDiff;
                    }
                }
                break;
            }
        }
        
        StringBuilder moveJson = new StringBuilder();
        moveJson.append("{\n");
        moveJson.append("        \"blockId\": \"").append(movedBlockId).append("\",\n");
        moveJson.append("        \"direction\": \"").append(direction).append("\",\n");
        moveJson.append("        \"steps\": ").append(steps).append("\n");
        moveJson.append("      }");
        
        return moveJson.toString();
    }
}