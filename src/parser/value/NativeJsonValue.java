package parser.value;

public abstract class NativeJsonValue implements ValueJson {
    private String value;

    public NativeJsonValue() {

    }

    public NativeJsonValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
