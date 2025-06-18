package parser;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Stack;

public class ParserJson {
    private final static String bracketOpen = "{[";
    private final static String bracketClose = "}]";
    private StringBuilder builder;

    private int index;

    public Stack<Character> brackets;
    public AbstractMap<String, String> objects;

    public ParserJson(){
        brackets = new Stack<>();
        objects = new HashMap<>();

        builder = new StringBuilder();
        index = 0;
    }

    public Object parse(String json) {
        for ( ; index < json.length(); index++) {
            char ch = json.charAt(index);
            if(bracketOpen.indexOf(ch) != -1) {
                brackets.push(ch);
            }
            else {
                if(bracketClose.indexOf(ch) != -1) {
                    if(ch == brackets.peek() + 2) {
                        brackets.pop();
                    }
                }
                else {
                    if(ch == '"') {

                    }
                    else if(ch == ':') {

                    }
                    else {

                    }
                }
            }
        }

        return null;
    }

    public String readValue(String json){
        builder.setLength(0);

        index++;
        char ch = json.charAt(index);
        char pastChar = '"';
        while (ch != '"' || pastChar == '\\') {
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
            index++;
            ch = json.charAt(index);
        }
        return builder.toString();
    }
}