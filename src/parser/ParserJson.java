package parser;

import parser.value.*;

import java.util.NoSuchElementException;

public class ParserJson {

    public ParserJson() {
    }

    public ValueJson parse(String json) throws Exception {
        JsonIterator iterator = new JsonIterator(json);
        char ch = iterator.nextCharSkipWhitespace();

        if (ch == '{') return readObject(iterator);
        else if (ch == '[') return readArray(iterator);
        else throw new NoSuchElementException("Error valid JSON");
    }

    public JsonObject readObject(JsonIterator iterator) throws Exception {
        JsonObject jsonObject = new JsonObject();

        while (iterator.hasNextChar()) {
            String key = readKey(iterator);
            if (iterator.nextCharSkipWhitespace() != ':') throw new NoSuchElementException("Error valid JSON");
            jsonObject.getFields().put(key, readValue(iterator));

            char c = iterator.peekCharSkipWhitespace();

            if (c == ',') iterator.nextChar();
            else if (c == '}') break;
            else if (c == '"') iterator.backChar();
            else throw new NoSuchElementException("Error valid JSON");
        }

        return jsonObject;
    }

    public ArrayJsonValue readArray(JsonIterator iterator) throws Exception {
        ArrayJsonValue arrayJsonValue = new ArrayJsonValue();

        while (iterator.hasNextChar()) {
            arrayJsonValue.getValues().add(readValue(iterator));

            char c = iterator.peekCharSkipWhitespace();

            if (c == ',') iterator.nextChar();
            else if (c == ']') break;
            else if (c == '"') iterator.backChar();
            else throw new NoSuchElementException("Error valid JSON");
        }

        return arrayJsonValue;
    }

    private String readKey(JsonIterator iterator) throws Exception {
        if (iterator.nextCharSkipWhitespace() != '"') throw new NoSuchElementException("Error valid JSON");

        return iterator.extractStringValue();
    }

    private ValueJson readValue(JsonIterator iterator) throws Exception {
        char c = iterator.nextCharSkipWhitespace();

        if (c == '{') return readObject(iterator);
        else if (c == '[') return readArray(iterator);
        else if (c == '"') return new StringJsonValue(iterator.extractStringValue());
        else {
            iterator.backChar();
            return new StringJsonValue(iterator.extractValue());
        }
    }

    private ValueJson readNumber(JsonIterator iterator) {
        NativeJsonValue jsonValue = new IntegerJsonValue();
        return jsonValue;
    }

    private BooleanJsonValue readBoolean(JsonIterator iterator) {
        BooleanJsonValue jsonValue = new BooleanJsonValue();
        return jsonValue;
    }

    private NullJsonValue readNull(JsonIterator iterator) {
        NullJsonValue jsonValue = new NullJsonValue();
        return jsonValue;
    }
}