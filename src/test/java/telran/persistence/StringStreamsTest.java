package telran.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class StringStreamsTest {
    private static final String PRINT_STREAM_FILE = "printStreamFile.txt";
    private static final String PRINT_WRITER_FILE = "printWriterFile.txt";

    @Test
    @Disabled
    void printStreamTest() throws Exception {
        try (PrintStream printStream = new PrintStream(PRINT_STREAM_FILE)) {
            printStream.println("HELLO");
        }
    }

    @Test
    @Disabled
    void printWriterTest() throws Exception {
        try (PrintWriter printWriter = new PrintWriter(PRINT_WRITER_FILE)) {
            printWriter.println("HELLO");
        }
    }

    @Test
    @Disabled
    void bufferedReaderTest() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRINT_WRITER_FILE))) {
            assertEquals("HELLO", reader.readLine());
        }
    }

    @Test
    void printingDirectory() {
        printDirectoryContent("\\", 3);
    }

    private void printDirectoryContent(String path, int depth) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO
        // dir1
        // dir11
        // file
        // 1.txt
        // dir 2
        // consider class Path
        // Consider class Files (path,walk with exceptions)
    }
}
