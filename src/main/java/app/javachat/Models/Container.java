package app.javachat.Models;

import app.javachat.Controllers.CustomControllers.LeftChatItem;

import java.io.Serializable;
import java.util.HashMap;

public class Container implements Serializable {
    private HashMap<String, LeftChatItem> rooms;

    public Container(HashMap<String, LeftChatItem> rooms) {
        this.rooms = rooms;
    }

    public HashMap<String, LeftChatItem> getRooms() {
        return rooms;
    }

    public void setRooms(HashMap<String, LeftChatItem> rooms) {
        this.rooms = rooms;
    }
}
