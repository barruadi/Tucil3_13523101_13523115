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

    public boolean makeBlocks(List<String> lines, Board board) {
        int row = board.getHeight();
        int col = board.getWidth();
        int blockCount = Integer.parseInt(lines.get(1));
        char[][] tempData = new char[row][col];
        List<Character> visited = new ArrayList<>();
        List<Block> blocks = new ArrayList<>();
        boolean checkVertical = false;

        // ini berarti masukan ga valid
        if (lines.size() > row + 3) {
            return false;
        }

        int idx_k = -1;
        if (row + 3 == lines.size()) {
            if (lines.get(2).contains("K")) {
                idx_k = lines.get(2).indexOf("K");
                checkVertical = true;
                board.setKiriAtas(true);
                for (int i = 0; i < row; i++) {
                    String line = lines.get(i + 3);
                    for (int j = 0; j < col; j++) {
                        tempData[i][j] = line.charAt(j);
                    }
                }
            } else if (lines.get(row + 2).contains("K")) {
                idx_k = lines.get(row + 2).indexOf("K");
                checkVertical = true;
                board.setKiriAtas(false);
                for (int i = 0; i < row; i++) {
                    String line = lines.get(i + 2);
                    for (int j = 0; j < col; j++) {
                        tempData[i][j] = line.charAt(j);
                    }
                }
            }
        } else {

            boolean isPossibleKiri = true;
            for (int i = 0; i < row; i++) {
                String line = lines.get(i + 2);
                if (line.length() != col + 1) {
                    isPossibleKiri = false;
                }
            }

            if (isPossibleKiri) {
                for (int i = 0; i < row; i++) {
                    String line = lines.get(i + 2);
                    if (line.charAt(0) == 'K') {
                        idx_k = i;
                        checkVertical = false;
                        board.setKiriAtas(true);
                    }
                    for (int j = 0; j < col; j++) {
                        tempData[i][j] = line.charAt(j + 1);
                    }
                }
            } else {
                for (int i = 0; i < row; i++) {
                    String line = lines.get(i + 2);
                    if (line.length() == col + 1 && line.charAt(col) == 'K') {
                        idx_k = i;
                        checkVertical = false;
                        board.setKiriAtas(false);
                    }
                    for (int j = 0; j < col; j++) {
                        tempData[i][j] = line.charAt(j);
                    }
                }
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
                if (id == 'K') {
                    continue;
                }
                Block block = new Block(id, i, j, size, isVertical);
                blocks.add(block);
                if (id == 'P') {
                    if (isVertical == true && checkVertical == true){
                        if (j != idx_k){
                            System.out.println("gak sesuai posisi");
                            return false;
                        }
                    }
                    else if (isVertical == false && checkVertical == false){
                        if (i != idx_k){
                            System.out.println("gak sesuai posisi");
                            return false;
                        }
                    }
                    else{
                        return false;
                    }
                    board.setPrimaryBlock(block);
                }
            }
        }
        if (!visited.contains('P')) {
            System.out.println("Blok P tidak ditemukan");
            return false;
        }
        if (blockCount != blocks.size()-1 || blocks.size() - 1 > 24 || blockCount > 24) {
            System.out.println("Jumlah block tidak sesuai");
            return false;
        }
        board.setBlocks(blocks);
        board.updateBoardData();
        return true;
    }

}
