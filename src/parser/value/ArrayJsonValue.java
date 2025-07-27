package parser.value;

import java.util.ArrayList;
import java.util.List;

public class ArrayJsonValue implements ValueJson {
    private List<ValueJson> values;

    public ArrayJsonValue() {
        values = new ArrayList<>();
    }

    @Override
    public <T> T getParseValue(Class<T> type) throws Exception {
        return null;
    }

    public List<ValueJson> getValues() {
        return values;
    }

    public void setValues(List<ValueJson> values) {
        this.values = values;
    }
}
