package telran.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class SplitCodeAndComments {

    /**
     * @param args <inputFile> <outputCodeFile> <outputCommentsFile>
     * 
     *             used args testInput.txt testOutputCode.txt testOutputComments.txt
     *             this files located in the root
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            throw new IllegalArgumentException(
                    "Usage: SplitCodeAndComments <inputFile> <outputCodeFile> <outputCommentsFile>");
        }

        try (BufferedReader inputReader = new BufferedReader(new FileReader(args[0]));
                BufferedWriter codeWriter = new BufferedWriter(new FileWriter(args[1]));
                BufferedWriter commentsWriter = new BufferedWriter(new FileWriter(args[2]))) {
            SplitCodeAndCommentsHandler splitCodeAndCommentsHandler = new SplitCodeAndCommentsHandler(codeWriter,
                    commentsWriter);
            String line;
            while ((line = inputReader.readLine()) != null) {
                splitCodeAndCommentsHandler.handleLine(line, codeWriter, commentsWriter);
            }
        }
    }
}