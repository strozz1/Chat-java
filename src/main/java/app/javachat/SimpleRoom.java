package app.javachat;

import app.javachat.Models.Room;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleRoom implements Serializable, Room {
    private String username;
    private List<HashMap<String, Object>> messages;

    public SimpleRoom(String username) {
        this.username = username;
        this.messages = new ArrayList<>();

    }

    @Override
    public void addMessage(HashMap<String, Object> msg) {
        messages.add(msg);
    }

    @Override
    public String getId() {
        return username;
    }
    @Override
    public List<HashMap<String, Object>> getMessages() {
        return messages;
    }

    @Override
    public void addMessage(JSONObject jsonObject) throws JsonProcessingException {
        HashMap<String,Object> result =
                new ObjectMapper().readValue(jsonObject.toString(), HashMap.class);
        messages.add(result);
    }
}
