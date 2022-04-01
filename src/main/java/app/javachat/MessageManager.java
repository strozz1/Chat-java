package app.javachat;

import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.Utilities.Info;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import static app.javachat.Utilities.Info.getMapFromJson;

public class MessageManager implements Manager {
    private final MainController controller;

    public MessageManager(MainController controller) {
        this.controller= controller;
    }

    @Override
    public void manage(Object msg) {
        HashMap<String, Object> mapFromJson = getMapFromJson((String) msg);
        if(mapFromJson.get("type").equals("message")){
            HashMap<String, Object> message= (HashMap<String, Object>) mapFromJson.get("content");
            String sender = (String) message.get("sender");
            SimpleRoom room = Info.rooms.get(sender);
            if(room ==null){
                room= createNewRoom(sender);
            }
            room.addMessage(message);
            LeftChatItem chat = controller.getChat(sender);
            ChatItem chatItem = chat.getChatItem();
            chatItem.addMessage(message);


        }
    }

    private SimpleRoom createNewRoom(String sender) {
        SimpleRoom room = new SimpleRoom(sender);
        Info.rooms.put(sender,room);
        controller.createNewLateralChatContainer(sender,room);
        return room;
    }


}
