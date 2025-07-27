package parser.value;

public class CharJsonValue extends NativeJsonValue implements ValueJson {
    public CharJsonValue() {

    }

    public CharJsonValue(String value) {
        super(value);
    }

    @Override
    public <T> T getParseValue(Class<T> type) throws Exception {
        char c = getValue().charAt(0);
        return type.cast(c);
    }
}