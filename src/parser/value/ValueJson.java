package parser.value;

public abstract class ValueJson <T> {
    private String value;

    public ValueJson(String value) {
        this.value = value;
    }

    public abstract T getParseValue() throws Exception;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}