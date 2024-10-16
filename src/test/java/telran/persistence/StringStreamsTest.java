package telran.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class StringStreamsTest {

    private static final String PRINTED_WORD = "HELLO";
    static final String PRINT_STREAM_FILE = "printStreamFile.txt";
    static final String PRINT_WRITER_FILE = "printWriterFile.txt";
    static final String PRINTED_DIRECTORY = "/Users/yavtik/TelRanLessons/";
    static final int DEPTH = 3;
    static final String PRINT_DIRECTORY_CONTENT_FILE = "TelRanLessonsDirectory.txt";
    static final String TAB_SYMBOL = " ";
    static final int NUMBER_OF_SPACES = 4;

    @Test
    @Disabled
    void printStreamTest() throws Exception {
        try (PrintStream printStream = new PrintStream(PRINT_STREAM_FILE)) {
            printStream.println(PRINTED_WORD);
        }
    }

    @Test
    @Disabled
    void printWriterTest() throws Exception {
        try (PrintWriter printWriter = new PrintWriter(PRINT_WRITER_FILE)) {
            printWriter.println(PRINTED_WORD);
        }
    }

    @Test
    @Disabled
    void bufferedReaderTest() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRINT_WRITER_FILE))) {
            assertEquals(PRINTED_WORD, reader.readLine());
        }
    }

    @Test
    void printingDirectoryTest() throws Exception {
        printDirectoryContent(PRINTED_DIRECTORY, DEPTH);
    }

    /**
     * @param path  - path to a directory
     * @param depth - number of been walked levels
     */
    void printDirectoryContent(String path, int depth) throws Exception {
        Path initialPath = Paths.get(path);
        try (PrintWriter writer = new PrintWriter(PRINT_DIRECTORY_CONTENT_FILE)) {
            Files.walkFileTree(initialPath, new SimpleFileVisitor<>() {
                private int currentDepth = 0;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    FileVisitResult result = FileVisitResult.SKIP_SUBTREE;
                    if (currentDepth <= depth) {
                        printLineWithPrintWriter(writer, dir);
                        currentDepth++;
                        result = FileVisitResult.CONTINUE;
                    }
                    return result;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    currentDepth--;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (currentDepth <= depth) {
                        printLineWithPrintWriter(writer, file);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    writer.println(TAB_SYMBOL.repeat(currentDepth * NUMBER_OF_SPACES) + exc);
                    return FileVisitResult.CONTINUE;
                }

                private void printLineWithPrintWriter(PrintWriter writer, Path path) {
                    writer.println(TAB_SYMBOL.repeat(currentDepth * NUMBER_OF_SPACES) + path.getFileName());
                }
            });
        }
    }
}