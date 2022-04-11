package app.javachat;

import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.Utilities.Info;

import java.util.HashMap;

import static app.javachat.Utilities.Info.getMapFromJson;

public class MessageManager implements Manager {
    private final MainController controller;

    public MessageManager(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void manage(Object msg) {
        HashMap<String, Object> mapFromJson = getMapFromJson((String) msg);

        if (mapFromJson.get("type").equals("message")) {
            HashMap<String, Object> message = (HashMap<String, Object>) mapFromJson.get("content");
            String username = (String) message.get("sender");
            LeftChatItem item = Info.rooms.get(username);
            if (item == null) {
                createNewChatItem(username);
                item = Info.rooms.get(username);
            }
            item.addMessage(message);


        }else if(mapFromJson.get("type").equals("room-message")){
            HashMap<String, Object> message = (HashMap<String, Object>) mapFromJson.get("content");
            String roomId= (String) message.get("id");
            LeftChatItem leftChatItemInfo= Info.rooms.get(roomId);
            if(leftChatItemInfo !=null){
                leftChatItemInfo.addMessage(message);
            }else{
                //todo
            }
        }else if(mapFromJson.get("type").equals("room")){
            HashMap<String, Object> room = (HashMap<String, Object>) mapFromJson.get("content");
            String roomId= (String) room.get("id");
            String roomName= (String) room.get("name");
            LeftChatItem leftChatItemInfo= Info.rooms.get(roomId);
            if(leftChatItemInfo ==null) {
                createNewRoomItem(roomId,roomName);
            }
        }
    }

    private void createNewChatItem(String sender) {
        controller.createNewLateralChatContainer(sender);
    }
    private void createNewRoomItem(String id,String name) {
        controller.createNewLateralRoomContainer(id,name);
    }
}
