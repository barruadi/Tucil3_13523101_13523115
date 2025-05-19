package block;

import java.util.ArrayList;
import java.util.List;

public class Block {

    private final char id;
    private final int row;                          // posisi i di board
    private final int col;                          // posisi j di board
    private final int size;                         // panjang dari block
    private final boolean isVertical;               // kalau bentuknya | berarti true
    private final String color = "";

    public Block(char id, int row, int col, int size, boolean verti){
        this.id = id;
        this.row = row;
        this.col = col;
        this.size = size;
        this.isVertical = verti;
    }
    public char getBlockId(){
        return this.id;
    }
    public int getBlockRowIndex(){
        return this.row;
    }
    public int getBlockColIndex(){
        return this.col;
    }
    public int getBlockSize(){
        return this.size;
    }
    public boolean isBlockVertical(){
        return this.isVertical;
    }
    public boolean isBlockPrimary(){
        return this.id == 'P';
    }

    public String getColor(){
        return this.color;
    }

    public Block makeBlockNewPosition(int row, int col){
        return new Block(this.id, row, col, this.size, this.isVertical);
    }
    
    public void printBlock(){
        if (isVertical) {
            for (int i =0; i < size; i++){
                System.out.println(this.id);
            }
        } else {
            for (int i =0; i < size; i++){
                System.out.print(this.id);
            }
            System.out.println();
        }
    }

    public List<int[]> getAllBlockCordinateInBoard(){
        List<int[]> coordinate = new ArrayList<>();
        if (this.isVertical){
            for (int i = 0; i < this.size; i++){
                coordinate.add(new int[]{this.row + i, this.col});
            }
        } else {
            for (int i = 0; i < this.size; i++){
                coordinate.add(new int[]{this.row, this.col + i});
            }
        }
        return coordinate;

    }
    @Override
    public String toString() {
        return "Piece(" + id + ", " + (this.isBlockPrimary() ? "Primary" : "Regular") + ", " +
                (isVertical ? "Vertical" : "Horizontal") + ", Length:" + this.size +
                ", Pos:(" + row + "," + col + "))";
    }

    public Block clone() {
        return new Block(id, row, col, size, isVertical);
    }

}