package app.javachat.Controllers.ViewControllers;

import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Models.Room;
import app.javachat.ServerConnection;
import app.javachat.SimpleRoom;
import app.javachat.SocketNotInitializedException;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.MessageSenderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static app.javachat.Utilities.JSONBuilder.parseMessageToJSON;


public class AddRoomController {

    private ServerConnection serverConnection;
    private MainController mainController;
    @FXML
    private TextField userChat, messageChat, nameGroup, userList;

    @FXML
    private Button addChat, createGroup;

    public AddRoomController() {
    }



    @FXML
    void initialize() {
        addChat.setOnMouseClicked(e -> {
            String username = userChat.getText();
            String message = messageChat.getText();
            boolean chatInput = checkChatInput(username, message);
            if (chatInput) {
                boolean existingChat = Info.rooms.get(username)!=null;
                if (!existingChat) {
                    try {
                        createChat(username, message);
                    } catch (JSONException ex) {
                        throw new RuntimeException(ex);
                    } catch (JsonProcessingException ex) {
                        ex.printStackTrace();
                    }
                }
                closeWindow(e);
            } else {
                badChatInput();
            }
        });

        createGroup.setOnMouseClicked(e -> {
            String[] list = (userList.getText()+Info.username.getValue()).split(",");
            String groupName = nameGroup.getText();
            boolean groupInput = checkGroupInput(list, groupName);
            if (groupInput) {
                MessageSenderService.sendRoomCreation(groupName,list);
                closeWindow(e);
            } else {
                badGroupInput();
            }

        });
    }

    private void createChat(String username, String message) throws JSONException, JsonProcessingException {
            sendChat(username,message);
    }

    private boolean createExistingChat(String username) {
        return true;
    }

    private void badGroupInput() {
        nameGroup.setStyle("-fx-border-color: red");
        userList.setStyle("-fx-border-color: red");
    }
    private void sendMessageToUsers(List<String> userList, String message, String id) {
        // add user sender to list before this
        userList.remove(Info.username.getValue());
        for (String user : userList) {
            String json = parseMessageToJSON(message, user, Info.username.getValue(), "room-message", id);
            MessageSenderService.sendMessage(json);
        }

    }

    private void badChatInput() {
        userChat.setStyle("-fx-border-color: red");
        messageChat.setStyle("-fx-border-color: red");
    }

    private void sendChat(String username, String message) throws JSONException, JsonProcessingException {
        String type = "message";
        String id = "null";

        String jsonMessage = parseMessageToJSON(message, username, Info.username.getValue(), type, id);
        MessageSenderService.sendMessage(jsonMessage);
        LeftChatItem item = Info.rooms.get(username);
        if (item == null) {
            SimpleRoom simpleRoom= (SimpleRoom) createNewChatItem(username);
            item = Info.rooms.get(username);
            JSONObject jsonObject = new JSONObject(jsonMessage);
            item.addMessage(jsonObject,true);
        }

    }

    private JSONObject createJSONObject(String message, String username, String type, String id) throws JSONException {
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("type", type);
        JSONObject content= new JSONObject();
        jsonObject.put("content",content);
        content.put("username", username);
        content.put("sender",Info.username.getValue());
        content.put("content", message);
        content.put("id", id);
        return jsonObject;
    }

    private Room createNewChatItem(String username) {
       return mainController.createNewLateralChatContainer(username);



    }

    private boolean checkChatInput(String username, String message) {
        String user = userChat.getText();
        String msg = messageChat.getText();
        if(user.isEmpty() || msg.isEmpty()) return false;
        return true;
    }

    private boolean checkGroupInput(String[] list, String groupName) {
        return list.length > 0 && !groupName.isEmpty();
    }

    private void closeWindow(Event event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    public boolean checkRegisterCredentials(String username, String email, String password) throws SocketNotInitializedException {
        return true;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;


    }

    public void setServerConnection(ServerConnection serverConnection) {

        this.serverConnection = serverConnection;
    }
}
