package telran.persistence;

public enum LineType {
    LINE_STARTING_AND_ENDING_WITH_BLOCK_COMMENT {
        @Override
        public boolean matches(String line, boolean insideBlockComment) {
            return line.startsWith("/*") && line.endsWith("*/");
        }
    },
    LINE_STARTING_WITH_BLOCK_COMMENT {
        @Override
        public boolean matches(String line, boolean insideBlockComment) {
            return line.startsWith("/*");
        }
    },
    LINE_ENDING_WITH_BLOCK_COMMENT {
        @Override
        public boolean matches(String line, boolean insideBlockComment) {
            return line.endsWith("*/");
        }
    },
    LINE_INSIDE_OF_BLOCK_COMMENT {
        @Override
        public boolean matches(String line, boolean insideBlockComment) {
            return insideBlockComment;
        }
    },
    COMMENT_LINE {
        @Override
        public boolean matches(String line, boolean insideBlockComment) {
            return line.startsWith("//");
        }
    },
    CODE_LINE {
        @Override
        public boolean matches(String line, boolean insideBlockComment) {
            return true;
        }
    };

    public abstract boolean matches(String line, boolean insideBlockComment);
}
