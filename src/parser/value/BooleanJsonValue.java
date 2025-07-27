package parser.value;

public class BooleanJsonValue extends NativeJsonValue implements ValueJson {
    public BooleanJsonValue() {

    }

    public BooleanJsonValue(String value) {
        super(value);
    }

    @Override
    public <T> T getParseValue(Class<T> type) throws Exception {
        Boolean b = Boolean.parseBoolean(getValue());
        return type.cast(b);
    }
}