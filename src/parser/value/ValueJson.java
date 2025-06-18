package parser;

public abstract class ValueJson <T> {
    private String value;

    public abstract T getParseValue();

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}