package parser;

import java.util.NoSuchElementException;

public class JsonIterator {
    private final char[] chars;
    private int index;
    private final StringBuilder builder;

    public JsonIterator(String json) {
        builder = new StringBuilder();
        chars = json.toCharArray();
        index = 0;
    }

    public String extractStringValue() {
        builder.setLength(0);

        boolean escaping = false;

        while (true) {
            char c = nextChar();

            if (escaping) {
                switch (c) {
                    case 'n' -> builder.append('\n');
                    case 'r' -> builder.append('\r');
                    case 't' -> builder.append('\t');
                    case 'f' -> builder.append('\f');
                    case 'b' -> builder.append('\b');
                    case '"' -> builder.append('\"');
                    case '\\' -> builder.append('\\');
                    case '/' -> builder.append('/');
                    default -> builder.append(c);
                }
                escaping = false;
            } else if (c == '\\') {
                escaping = true;
            } else if (c == '\"') {
                break;
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public char nextChar() {
        if (!hasNextChar()) {
            throw new NoSuchElementException("End of JSON");
        }
        return chars[index++];
    }

    public char peekChar() {
        if (!hasNextChar()) {
            throw new NoSuchElementException("End of JSON");
        }
        return chars[index];
    }

    public char nextCharSkipWhitespace() {
        while (true) {
            char c = nextChar();
            if (c != ' ' && c != '\n' && c != '\t') {
                return c;
            }
        }
    }

    public boolean hasNextChar(){
        return index < chars.length;
    }
}