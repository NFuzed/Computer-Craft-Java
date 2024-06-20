package computercraft.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Optional;

public class MessageWrapper {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private MessageWrapper() {
    }

    public static Optional<Message> unwrapMessage(String message) {
        try {
            Message msg = OBJECT_MAPPER.readValue(message, Message.class);
            return Optional.of(msg);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> wrapMessage(Map<String, Object> message) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonMessage = mapper.writeValueAsString(message);
            return Optional.of(jsonMessage);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
