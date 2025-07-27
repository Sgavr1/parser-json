package parser.value;

import java.util.HashMap;
import java.util.Map;

public class JsonObject implements ValueJson {
    private Map<String, ValueJson> fields;

    public JsonObject() {
        fields = new HashMap<>();
    }

    @Override
    public <T> T getParseValue(Class<T> type) throws Exception {
        return null;
    }

    public Map<String, ValueJson> getFields() {
        return fields;
    }

    public void setFields(Map<String, ValueJson> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        String str = "";
        for (String key : fields.keySet()) {
            str += key + " : " + ((NativeJsonValue)fields.get(key)).getValue() + "\n";
        }
        return str;
    }
}