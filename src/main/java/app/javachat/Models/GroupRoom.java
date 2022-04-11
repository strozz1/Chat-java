package app.javachat.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupRoom implements Room {
    private String name, id;
    private List<HashMap<String, Object>> messages;

    public GroupRoom(String id, String name) {
        this.id = id;
        this.name = name;
        this.messages = new ArrayList<>();

    }

    @Override
    public void addMessage(HashMap<String, Object> msg) {
        messages.add(msg);
    }

    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<HashMap<String, Object>> getMessages() {
        return messages;
    }
}
