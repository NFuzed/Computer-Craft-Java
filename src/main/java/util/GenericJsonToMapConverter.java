package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GenericJsonToMapConverter {

    public static void main(String[] args) {
        String input = "{\"Apples\":3,\"Pears\":2}";

        try {
            Map<String, Object> resultMap = convertToMap(input);
            System.out.println(resultMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> convertToMap(String input) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(input);

        return parseJsonNode(rootNode);
    }

    private static Map<String, Object> parseJsonNode(JsonNode node) {
        Map<String, Object> resultMap = new HashMap<>();

        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                resultMap.put(field.getKey(), parseJsonValue(field.getValue()));
            }
        }

        return resultMap;
    }

    private static Object parseJsonValue(JsonNode value) {
        if (value.isObject()) {
            return parseJsonNode(value);
        } else if (value.isArray()) {
            return parseJsonArray(value);
        } else if (value.isBoolean()) {
            return value.booleanValue();
        } else if (value.isNumber()) {
            return value.numberValue();
        } else if (value.isTextual()) {
            return value.textValue();
        } else {
            return value.toString();
        }
    }

    private static Object parseJsonArray(JsonNode arrayNode) {
        if (arrayNode.size() == 0) {
            return new Object[0];
        } else {
            Object[] array = new Object[arrayNode.size()];
            for (int i = 0; i < arrayNode.size(); i++) {
                array[i] = parseJsonValue(arrayNode.get(i));
            }
            return array;
        }
    }
}
