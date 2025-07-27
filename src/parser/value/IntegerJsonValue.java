package parser.value;

public class IntegerJsonValue extends NativeJsonValue implements ValueJson {
    public IntegerJsonValue() {

    }

    public IntegerJsonValue(String value) {
        super(value);
    }

    @Override
    public <T> T getParseValue(Class<T> type) throws Exception {
        Integer value = Integer.parseInt(getValue());
        return type.cast(value);
    }
}
