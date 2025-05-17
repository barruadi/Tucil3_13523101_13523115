package block;

public class Block {
    
    private final int row;                          // posisi i di board
    private final int col;                          // posisi j di board
    private final int size;                         // panjang dari block
    private final char id;
    private final boolean isVertical;               // kalau bentuknya | berarti true
    private final boolean isPrimary;
    private final String color = "";


    public Block(char id, int row, int col, int size, boolean verti, boolean primary){
        this.id = id;
        this.row = row;
        this.col = col;
        this.size = size;
        this.isVertical = verti;
        this.isPrimary = primary;
    }
    public char getBlockId(){
        return this.id;
    }
    public int getBlockRow(){
        return this.row;
    }
    public int getBlockCol(){
        return this.col;
    }
    public int getBlockSize(){
        return this.size;
    }
    public boolean isBlockVertical(){
        return this.isVertical;
    }
    public boolean isBlockPrimary(){
        return this.isPrimary;
    }

    public String getColor(){
        return this.color;
    }

    public Block makeBlockNewPosition(int row, int col){
        return new Block(this.id, row, col, this.size, this.isVertical, this.isPrimary);
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

    @Override
    public String toString() {
        return "Piece(" + id + ", " + (isPrimary ? "Primary" : "Regular") + ", " +
                (isVertical ? "Vertical" : "Horizontal") + ", Length:" + this.size +
                ", Pos:(" + row + "," + col + "))";
    }
}