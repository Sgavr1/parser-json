package parser.value;

public class StringJsonValue extends NativeJsonValue implements ValueJson {

    public StringJsonValue() {

    }

    public StringJsonValue(String value) {
        super(value);
    }

    @Override
    public <T> T getParseValue(Class<T> type) throws Exception {
        return type.cast(this.getValue());
    }
}