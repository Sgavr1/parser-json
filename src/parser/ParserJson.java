package parser;

import java.util.NoSuchElementException;
import java.util.Stack;

public class ParserJson {

    private final Stack<Character> brackets;

    public ParserJson() {
        brackets = new Stack<>();
    }

    public Object parse(String json) throws Exception {
        JsonIterator iterator = new JsonIterator(json);
        char ch = iterator.nextCharSkipWhitespace();

        if (ch == '{') {
            brackets.push('{');
            readObject(iterator);
        } else if (ch == '[') {
            brackets.push('[');
            readArray(iterator);
        }

        return null;
    }

    public String readObject(JsonIterator iterator) throws Exception {
        while (iterator.hasNextChar()) {
            String key = readKey(iterator);
            if (iterator.nextCharSkipWhitespace() != ':') {
                throw new NoSuchElementException("Error valid JSON");
            }
            String value = readValue(iterator);

            System.out.println(key + " : " + value);

            char c = iterator.peekCharSkipWhitespace();
            if (c == ',') {
                iterator.nextChar();
            } else if (c == '}' && brackets.pop() == '{') {
                break;
            } else if (c == '"') {
                iterator.backChar();
            } else {
                throw new NoSuchElementException("Error valid JSON");
            }
        }

        return "";
    }

    public String readArray(JsonIterator iterator) {

        return "";
    }

    private String readKey(JsonIterator iterator) throws Exception {
        if (iterator.nextCharSkipWhitespace() != '"') {
            throw new NoSuchElementException("Error valid JSON");
        }

        return iterator.extractStringValue();
    }

    private String readValue(JsonIterator iterator) throws Exception {
        char c = iterator.nextCharSkipWhitespace();

        if (c == '{') {
            brackets.push('{');
            return readObject(iterator);
        } else if (c == '[') {
            brackets.push('[');
            return readArray(iterator);
        } else if (c == '"') {
            return iterator.extractStringValue();
        } else {
            iterator.backChar();
            return iterator.extractValue();
        }
    }
}