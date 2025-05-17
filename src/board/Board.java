package board;

import block.Block;
import java.util.*;

public class Board {

    private int height;
    private int width;
    private List<Block> blocks;
    private Block primaryBlock;
    private char[][] boardData;
    private boolean kiriAtas = false;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.boardData = new char[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.boardData[i][j] = '.';
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public Block getPrimaryBlock() {
        return primaryBlock;
    }
    public char[][] getBoardData() {
        return boardData;
    }
    public boolean isKiriAtas() {
        return kiriAtas;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public void setPrimaryBlock(Block primaryBlock) {
        this.primaryBlock = primaryBlock;
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void setKiriAtas(boolean kiriAtas) {
        this.kiriAtas = kiriAtas;
    }

    public void updateBoardData() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boardData[i][j] = '.';
            }
        }

        for (Block a : blocks) {
            for (int i = 0; i < a.getBlockSize(); i++) {
                if (a.isBlockVertical()) {
                    boardData[a.getBlockRowIndex() + i][a.getBlockColIndex()] = a.getBlockId();
                } else {
                    boardData[a.getBlockRowIndex()][a.getBlockColIndex() + i] = a.getBlockId();
                }
            }
        }
    }

    public boolean isFinish() {
        if (this.primaryBlock.isBlockVertical()) {
            if (this.kiriAtas) {
                return this.primaryBlock.getBlockColIndex() == 0;
            }
            return this.primaryBlock.getBlockColIndex() + this.primaryBlock.getBlockSize() == this.height;
        }
        // horizontal
        if (this.kiriAtas) {
            return this.primaryBlock.getBlockRowIndex() == 0;
        }
        System.out.println("primary block row index: " + this.primaryBlock.getBlockColIndex() + " block size: " + this.primaryBlock.getBlockSize());
        return this.primaryBlock.getBlockColIndex() + this.primaryBlock.getBlockSize() == this.width;
    }

    public void printBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(boardData[i][j]);
            }
            System.out.println();
        }
    }

    public void updateBlock(Block block) {
        blocks.removeIf(b -> b.getBlockId() == block.getBlockId());
        blocks.add(block);
        
        if (primaryBlock != null && primaryBlock.getBlockId() == block.getBlockId()) {
            primaryBlock = block;
        }

        updateBoardData();
    }


    // fungsi buat deep copy
    @Override
    public Board clone() {
        Board newBoard = new Board(this.height, this.width);
        
        List<Block> copiedBlocks = new ArrayList<>();
        for (Block block : this.blocks) {
            copiedBlocks.add(block.clone());
        }
        newBoard.blocks = copiedBlocks;

        if (this.primaryBlock != null) {
            newBoard.primaryBlock = this.primaryBlock.clone();
        }

        newBoard.boardData = new char[this.height][this.width];
        for (int i = 0; i < this.height; i++) {
            System.arraycopy(this.boardData[i], 0, newBoard.boardData[i], 0, this.width);
        }

        return newBoard;
    }


    // udah handle pindah berurutan
    public List<Board> getAllPossibleMove() {
        List<Board> possibleMoves = new ArrayList<>();

        for (Block block : blocks) {
            int r = block.getBlockRowIndex();
            int c = block.getBlockColIndex();

            if (block.isBlockVertical()) {
                int maxUp = 0;
                while (r - 1 - maxUp >= 0 && boardData[r - 1 - maxUp][c] == '.') {
                    maxUp++;
                }
                if (maxUp > 0) {
                    Block newBlock = block.makeBlockNewPosition(r - maxUp, c);
                    Board newBoard = this.clone();
                    newBoard.updateBlock(newBlock);
                    possibleMoves.add(newBoard);
                }

                int maxDown = 0;
                while (r + block.getBlockSize() + maxDown < height &&
                    boardData[r + block.getBlockSize() + maxDown][c] == '.') {
                    maxDown++;
                }
                if (maxDown > 0) {
                    Block newBlock = block.makeBlockNewPosition(r + maxDown, c);
                    Board newBoard = this.clone();
                    newBoard.updateBlock(newBlock);
                    possibleMoves.add(newBoard);
                }

            } else {
                int maxLeft = 0;
                while (c - 1 - maxLeft >= 0 && boardData[r][c - 1 - maxLeft] == '.') {
                    maxLeft++;
                }
                if (maxLeft > 0) {
                    Block newBlock = block.makeBlockNewPosition(r, c - maxLeft);
                    Board newBoard = this.clone();
                    newBoard.updateBlock(newBlock);
                    possibleMoves.add(newBoard);
                }

                int maxRight = 0;
                while (c + block.getBlockSize() + maxRight < width &&
                    boardData[r][c + block.getBlockSize() + maxRight] == '.') {
                    maxRight++;
                }
                if (maxRight > 0) {
                    Block newBlock = block.makeBlockNewPosition(r, c + maxRight);
                    Board newBoard = this.clone();
                    newBoard.updateBlock(newBlock);
                    possibleMoves.add(newBoard);
                }
            }
        }

        return possibleMoves;

    }

    public boolean isSameBoard(Board other) {
        if (this.height != other.getHeight() || this.width != other.getWidth()) {
            return false;
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (this.boardData[i][j] != other.getBoardData()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
