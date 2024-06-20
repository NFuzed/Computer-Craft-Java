package util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GenericJsonConverter {

    private GenericJsonConverter() {

    }

    public static Map<String, Object> convertToMap(JsonNode input) {
        return parseJsonNode(input);
    }

    public static boolean convertToBoolean(JsonNode jsonNode) {
        // Check if the JsonNode is an array and has at least one element
        if (jsonNode.isArray() && !jsonNode.isEmpty()) {
            // Get the first element of the array
            JsonNode firstElement = jsonNode.get(0);
            // Return the boolean value of the first element
            return firstElement.asBoolean();
        }
        throw new IllegalArgumentException("JsonNode is not an array or is empty");
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