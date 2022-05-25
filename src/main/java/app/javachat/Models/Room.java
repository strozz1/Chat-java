package app.javachat.Models;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public interface Room {
    void addMessage(HashMap<String, Object> msg);
    String getId();
    List<HashMap<String, Object>> getMessages();

    void addMessage(JSONObject jsonObject) throws JsonProcessingException;
}
