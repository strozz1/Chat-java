package app.javachat.Models;

import java.util.HashMap;
import java.util.List;

public interface Room {
    void addMessage(HashMap<String, Object> msg);
    String getId();
    List<HashMap<String, Object>> getMessages();
}
