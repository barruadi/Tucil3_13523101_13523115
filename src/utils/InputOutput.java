package utils;

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


}
