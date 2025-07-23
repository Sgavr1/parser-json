package parser;

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
        JsonIterator iterator = new JsonIterator(json);
        skepSpace(json);
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
        while (true) {
            String key = readKey(json);
            index++;
            moveToCharSkepSpace(json, ':');
            index++;
            String value = readValue(json);

            System.out.println(key + " : " + value);
            skepSpace(json);
            char ch = json.charAt(index);
            if(ch == '"'){
                index++;
                skepSpace(json);
                if(json.charAt(index) == '}') {
                    if(json.charAt(index) + 2 == brackets.pop()){
                        break;
                    }
                }
                else if(json.charAt(index) != ',') {
                    throw new Exception();
                }
            }
            else {
                if(json.charAt(index) == '}') {
                    if(json.charAt(index) + 2 == brackets.pop()){
                        break;
                    }
                }
                else if(json.charAt(index) != ',') {
                    throw new Exception();
                }
            }
        }

        return "";
    }

    public String readArray(String json) {

        return "";
    }

    private String readKey(String json) throws Exception {
        index++;
        moveToCharSkepSpace(json ,'"');

        return readStringValue(json);
    }

    private String readValue(String json) throws Exception {
        skepSpace(json);
        char ch = json.charAt(index);

        if (ch == '{') {
            brackets.push('{');
            readObject(json);
        }
        else if (ch == '[') {
            brackets.push('[');
            readArray(json);
        }
        else if(ch == '"'){
            return readStringValue(json);
        }
        else {
            if(ch > 47 && ch < 58){
                if(ch == '0' && json.charAt(index+1) != '.') {
                    throw new Exception();
                }
                return readNumberValue(json);
            }
            else {
                throw new Exception();
            }
        }

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
        while (!(ch == ',' || ch == ' ' || ch == '\n' || ch == '\t' || ch == '}' ) && index < json.length()) {
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

    private void moveToCharSkepSpace(String json, char c) throws Exception {
        while (index < json.length()) {
            char ch = json.charAt(index);
            if (ch == ' ' || ch == '\t' || ch == '\n') {
                index++;
            }
            else if (ch == c) {
                return;
            }
            else {
                throw new Exception("Error json");
            }
        }

        throw new Exception("Error json");
    }

    private void skepSpace(String json) throws Exception {
        while (index < json.length()) {
            if (json.charAt(index) == ' ' || json.charAt(index) == '\t' || json.charAt(index) == '\n') {
                index++;
            }
            else {
                return;
            }
        }

        throw new Exception("Error json");
    }
}