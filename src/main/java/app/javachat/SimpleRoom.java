package app.javachat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleRoom implements Serializable {
    private String username;
    private List<HashMap<String, Object>> messages;

    public SimpleRoom(String username) {
        this.username=username;
        this.messages= new ArrayList<>();

    }
    public void addMessage(HashMap<String, Object> msg){
        messages.add(msg);
    }

    public String getUsername() {
        return username;
    }

    public List<HashMap<String, Object>> getMessages() {
        return messages;
    }
}
