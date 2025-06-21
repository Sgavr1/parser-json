package parser;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Stack;

public class ParserJson {
    private final static String bracketOpen = "{[";
    private final static String bracketClose = "}]";
    private final static String numbers = "1234567890.e";

    private final Stack<Character> brackets;

    private final StringBuilder builder;

    private int index;

    public ParserJson() {
        brackets = new Stack<>();
        builder = new StringBuilder();
        index = 0;
    }

    public Object parse(String json) throws Exception {
        char ch = json.charAt(index);

        if(ch == '{') {
            brackets.push('{');
            readObject(json);
        } else if(ch == '[') {
            brackets.push('[');
            readArray(json);
        }

        return null;
    }

    public String readObject(String json) throws Exception {
        String key = readKey(json);
        moveToChar(json, ':');
        String value = readValue(json);

        System.out.println(key + " : " + value);

        return "";
    }

    public String readArray(String json) {

        return "";
    }

    private String readKey(String json) throws Exception {
        moveToChar(json ,'"');

        return readStringValue(json);
    }

    private String readValue(String json) {

        return "";
    }

    public String readStringValue(String json){
        builder.setLength(0);

        index++;
        char ch = json.charAt(index);
        char pastChar = '"';
        while ((ch != '"' || pastChar == '\\') && index < json.length()) {
            if (ch != '"' && pastChar != '\\' && ch != '\\') {
                builder.append(ch);
                pastChar = ch;
            }
            else if (ch == '\\' && pastChar != '\\') {
                pastChar = ch;
            }
            else if (ch == '\\' && pastChar == '\\') {
                pastChar = ' ';
                builder.append('\\');
            }
            else if (ch == '"' && pastChar == '\\') {
                pastChar = ' ';
                builder.append('"');
            }
            else if (ch == 'n' && pastChar == '\\') {
                pastChar = ' ';
                builder.append('\n');
            }
            else if (ch == 'f' && pastChar == '\\') {
                pastChar = ' ';
                builder.append('\f');
            }
            else if(ch == 'b' && pastChar == '\\') {
                pastChar = ' ';
                builder.append('\b');
            }
            else if(ch == 'r' && pastChar == '\\') {
                pastChar = ' ';
                builder.append('\r');
            }
            else if(ch == 't' && pastChar == '\\') {
                pastChar = ' ';
                builder.append('\t');
            }
            else if(ch == '/' && pastChar == '\\') {
                pastChar = ' ';
                builder.append('/');
            }
            index++;
            ch = json.charAt(index);
        }
        return builder.toString();
    }

    public String readNumberValue(String json) throws Exception {
        builder.setLength(0);
        char ch = json.charAt(index);
        while (ch != ',' && index < json.length()){
            if(numbers.indexOf(ch) != -1){
                builder.append(ch);
            }
            else {
                throw new Exception("Error parsing: index char " + index);
            }
            index++;
            ch = json.charAt(index);
        }

        return builder.toString();
    }

    private void moveToChar(String json, char c) throws Exception {
        while (index < json.length()) {
            if(json.charAt(index) == c) {
                return;
            }
            index++;
        }

        throw new Exception("Error json");
    }
}