import java.util.*;
import utils.InputOutput;

public class Main {

    public static void main(String[] args) {

        InputOutput io = new InputOutput();
        List<String> lines = io.readFileFromString("tes.txt");
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
