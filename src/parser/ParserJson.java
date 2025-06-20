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
        boolean isValue = false;
        String key = "";
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
                        if(!isValue){
                            key = readValue(json);
                        }
                        else{
                            objects.put(key, readValue(json));
                        }
                    }
                    else if (ch == ':') {
                        isValue = true;
                    }
                    else if (ch == ','){
                        isValue = false;
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
}