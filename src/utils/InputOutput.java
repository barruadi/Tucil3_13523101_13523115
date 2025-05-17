package utils;

import block.Block;
import board.Board;
import java.io.*;
import java.util.*;

public class InputOutput {

    public List<String> readFileFromFile(File file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                lines.add(currentLine);
            }
        } catch (IOException e) {
            return null;
        }
        return lines;
    }

    public List<String> readFileFromString(String filename) {
        List<String> lines = new ArrayList<>();
        String filenameWithFolder = "../test/input/" + filename;
        try (BufferedReader reader = new BufferedReader(new FileReader(filenameWithFolder))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                lines.add(currentLine);
            }
        } catch (IOException e) {
            return null;
        }
        return lines;
    }

    public Board makeBoard(List<String> lines) {
        try {
            String firstLine = lines.get(0);
            String[] firstLineArray = firstLine.split(" ");
            if (firstLineArray.length != 2) {
                throw new IllegalArgumentException("Data tidak valid");
            }
            int row = Integer.parseInt(firstLineArray[0]);
            int col = Integer.parseInt(firstLineArray[1]);
            String secondLine = lines.get(1);
            String[] secondLineArray = secondLine.split(" ");
            if (secondLineArray.length != 1) {
                throw new IllegalArgumentException("Data tidak valid");
            }
            return new Board(row, col);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }

    public void makeBlocks(List<String> lines, Board board) {
        int row = board.getHeight();
        int col = board.getWidth();
        char[][] tempData = new char[row][col];
        List<Character> visited = new ArrayList<>();
        List<Block> blocks = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            String line = lines.get(i + 2);

            // K di kiri @ belum bisa
            if (line.length() >= 1 && line.charAt(0) == 'K') {
                // board.setExit(0);
                line = line.substring(1);
            } 

            // K di kanan @ sudah aman

            else if (line.length() == col + 1 && line.charAt(col) == 'K') {
                board.setKiriAtas(false);
                line = line.substring(0, col);
            }

            for (int j = 0; j < col; j++) {
                tempData[i][j] = line.charAt(j);
            }
        }

        // K di atas belum bisa
        String topLine = lines.get(1);
        for (int j = 0; j < topLine.length(); j++) {
            if (topLine.charAt(j) == 'K') {
                // board.setExit(new int[]{-1, j});
                break;
            }
        }

        // K di bawah belum bisa
        String bottomLine = lines.get(lines.size() - 1);
        for (int j = 0; j < bottomLine.length(); j++) {
            if (bottomLine.charAt(j) == 'K') {
                // board.setExit(new int[]{row, j});
                break;
            }
        }

        // Proses pembuatan blocks
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                char id = tempData[i][j];
                if (id == '.' || visited.contains(id)) {
                    continue;
                }

                visited.add(id);
                boolean isVertical = false;
                int size = 1;

                int ni = i + 1;
                while (ni < row && tempData[ni][j] == id) {
                    size++;
                    isVertical = true;
                    ni++;
                }

                if (!isVertical) {
                    int nj = j + 1;
                    while (nj < col && tempData[i][nj] == id) {
                        size++;
                        nj++;
                    }
                }

                Block block = new Block(id, i, j, size, isVertical);
                blocks.add(block);
                if (id == 'P') {
                    board.setPrimaryBlock(block);
                }
            }
        }

        board.setBlocks(blocks);
        board.updateBoardData();
    }

}
