package computercraft.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class Message {
    @JsonProperty("turtleId")
    private int turtleId;

    @JsonProperty("commandId")
    private int commandId;

    @JsonProperty("action")
    private String action;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("content")
    private JsonNode content;

    @JsonProperty("inspectionData")
    private Map<String, Object> inspectionData;

    public int getTurtleId() {
        return turtleId;
    }

    public void setTurtleId(int turtleId) {
        this.turtleId = turtleId;
    }

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JsonNode getContent() {
        return content;
    }

    public void setContent(JsonNode content) {
        this.content = content;
    }

    public Map<String, Object> getInspectionData() {
        return inspectionData;
    }
}