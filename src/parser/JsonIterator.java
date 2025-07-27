package parser;

import java.util.NoSuchElementException;

public class JsonIterator {
    private final char[] chars;
    private int index;
    private final StringBuilder builder;

    public JsonIterator(String json) {
        if (json == null || json == "") throw new NoSuchElementException("Empty JSON");
        builder = new StringBuilder();
        chars = json.toCharArray();
        index = 0;
    }

    public String extractStringValue() {
        builder.setLength(0);

        boolean escaping = false;

        while (index < chars.length) {
            char c = chars[index++];

            if (escaping) {
                switch (c) {
                    case 'n' : builder.append('\n'); break;
                    case 'r' : builder.append('\r'); break;
                    case 't' : builder.append('\t'); break;
                    case 'f' : builder.append('\f'); break;
                    case 'b' : builder.append('\b'); break;
                    case '"' : builder.append('\"'); break;
                    case '\\' : builder.append('\\'); break;
                    case '/' : builder.append('/'); break;
                    default : builder.append(c);
                }
                escaping = false;
            } else if (c == '\\') {
                escaping = true;
            } else if (c == '\"') {
                return builder.toString();
            } else {
                builder.append(c);
            }
        }

        throw new NoSuchElementException("Bad JSON");
    }

    public String extractValue() {
        builder.setLength(0);

        while (index < chars.length) {
            char c = chars[index];
            if (c == ',' || c == '}' || c == ']') return builder.toString();

            builder.append(c);
            index++;
        }

        throw new NoSuchElementException("Bad JSON ");
    }

    public char nextChar() {
        if (index >= chars.length) throw new NoSuchElementException("End of JSON");
        return chars[index++];
    }

    public char peekChar() {
        if (index >= chars.length) throw new NoSuchElementException("End of JSON");
        return chars[index];
    }

    public char backChar() {
        if (index == 0) throw new NoSuchElementException("Cannot move back before start of JSON");
        return chars[--index];
    }

    public char nextCharSkipWhitespace() {
        skipWhitespace();
        return chars[index++];
    }

    public char peekCharSkipWhitespace() {
        skipWhitespace();
        return chars[index];
    }

    public void skipWhitespace() {
        while (index < chars.length) {
            char c = chars[index];
            if (c != ' ' && c != '\t' && c != '\n' && c != '\r') break;
            else index++;
        }
    }

    public boolean hasNextChar() {
        return index < chars.length;
    }
}