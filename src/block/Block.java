package block;

public class Block {

    // disusun berdasarkan allignment
    
    private final String color = "\u001B[33m";      // warna block, default yellow
    private final int row;                          // posisi i di board
    private final int col;                          // posisi j di board
    private final int size;                         // panjang dari block
    private final char id;
    private final boolean isVertical;               // kalau bentuknya | berarti true
    private final boolean isPrimary;

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
    
    public void printBlock(){
        System.out.println("Block " + this.id + " :");
        if (isVertical){
            for (int i=0; i < this.size; i++){
                System.out.println("  " + this.color + this.id + "\u001B[0m");
            }
        } else {
            for (int i=0; i < this.size; i++){
                System.out.print("  " + this.color + this.id + "\u001B[0m");
            }
            System.out.println();
        }
    }

}