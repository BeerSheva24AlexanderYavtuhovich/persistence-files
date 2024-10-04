package telran.persistence;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class SplitCodeAndCommentsHandler {
    private boolean insideBlockComment;
    private Map<LineType, Consumer<String>> actionMap;

    public SplitCodeAndCommentsHandler(BufferedWriter codeWriter, BufferedWriter commentsWriter) {
        initActionMap(codeWriter, commentsWriter);
        this.insideBlockComment = false;
    }

    private void initActionMap(BufferedWriter codeWriter, BufferedWriter commentsWriter) {
        actionMap = new HashMap<>();

        actionMap.put(LineType.LINE_STARTING_WITH_BLOCK_COMMENT, line -> {
            writeNewLineWithWriter(commentsWriter, line);
            insideBlockComment = true;
        });

        actionMap.put(LineType.LINE_INSIDE_OF_BLOCK_COMMENT,
                line -> writeNewLineWithWriter(commentsWriter, line));

        actionMap.put(LineType.LINE_ENDING_WITH_BLOCK_COMMENT, line -> {
            writeNewLineWithWriter(commentsWriter, line);
            insideBlockComment = false;
        });

        actionMap.put(LineType.LINE_STARTING_AND_ENDING_WITH_BLOCK_COMMENT, line -> {
            writeNewLineWithWriter(commentsWriter, line);
        });

        actionMap.put(LineType.COMMENT_LINE, line -> {

            String[] parts = line.split("//");

            for (String part : parts) {
                String trimmedPart = part.trim();
                if (!trimmedPart.isEmpty()) {
                    writeNewLineWithWriter(commentsWriter, "// " + trimmedPart);
                }
            }
        });

        actionMap.put(LineType.CODE_LINE, line -> {
            int commentIndex = line.indexOf("//");
            String codePart = commentIndex != -1 ? line.substring(0, commentIndex).trim() : line.trim();
            String commentPart = commentIndex != -1 ? line.substring(commentIndex).trim() : null;

            if (!codePart.isEmpty()) {
                writeNewLineWithWriter(codeWriter, codePart);
            }

            if (commentPart != null) {
                String[] commentParts = commentPart.split("//");
                for (String part : commentParts) {
                    String trimmedPart = part.trim();
                    if (!trimmedPart.isEmpty()) {
                        writeNewLineWithWriter(commentsWriter, "// " + trimmedPart);
                    }
                }
            }
        });
    }

    public void handleLine(String line, BufferedWriter codeWriter, BufferedWriter commentsWriter) {
        String trimmedLine = line.trim();
        LineType lineType = getLineType(trimmedLine);
        actionMap.get(lineType).accept(trimmedLine);
    }

    private LineType getLineType(String trimmedLine) {
        return Arrays.stream(LineType.values())
                .filter(type -> type.matches(trimmedLine, insideBlockComment))
                .findFirst()
                .orElse(LineType.CODE_LINE);
    }

    private void writeNewLineWithWriter(BufferedWriter writer, String line) {
        try {
            writer.write(line + "\n");
        } catch (IOException e) {
            e.getMessage();
        }
    }

}