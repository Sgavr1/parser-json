package parser.value;

public interface ValueJson {
    <T> T getParseValue(Class<T> type) throws Exception;
}