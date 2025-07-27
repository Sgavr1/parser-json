package parser.value;

public class NullJsonValue extends NativeJsonValue implements ValueJson {
    public NullJsonValue() {

    }

    public NullJsonValue(String value) {
        super(value);
    }

    @Override
    public <T> T getParseValue(Class<T> type) throws Exception {
        return null;
    }
}