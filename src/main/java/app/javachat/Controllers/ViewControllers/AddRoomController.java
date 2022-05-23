package app.javachat.Controllers.ViewControllers;

import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Models.Room;
import app.javachat.ServerConnection;
import app.javachat.SimpleRoom;
import app.javachat.SocketNotInitializedException;
import app.javachat.Utilities.Info;
import app.javachat.Utilities.JSONBuilder;
import app.javachat.Utilities.MessageSenderService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import static app.javachat.Utilities.JSONBuilder.parseMessageToJSON;


public class AddRoomController {

    private ServerConnection serverConnection;
    private MainController mainController;
    @FXML
    private TextField userChat, messageChat, nameGroup, userList, messageGroup;

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
                boolean existingChat = createExistingChat(username);
                if (!existingChat) {
                    createChat(username, message);
                }
                closeWindow(e);
            } else {
                badChatInput();
            }
        });

        createGroup.setOnMouseClicked(e -> {
            String[] list = userList.getText().split(",");
            String message = messageGroup.getText();
            String groupName = nameGroup.getText();
            boolean groupInput = checkGroupInput(list, message, groupName);
            if (groupInput) {

                closeWindow(e);
            } else {
                badGroupInput();
            }

        });
    }

    private void createChat(String username, String message) {
            sendChat(username,message);
    }

    private boolean createExistingChat(String username) {
        return true;
    }

    private void badGroupInput() {

    }

    private void badChatInput() {

    }

    private void sendChat(String message, String username) throws JSONException {
        String type = "message";
        String id = "null";
        String jsonMessage = parseMessageToJSON(message, username, Info.username.getValue(), type, id);
        MessageSenderService.sendMessage(jsonMessage);
        LeftChatItem item = Info.rooms.get(username);
        if (item == null) {
            SimpleRoom simpleRoom= (SimpleRoom) createNewChatItem(username);
            item = Info.rooms.get(username);
            JSONObject jsonObject = new JSONObject(jsonMessage);
            item.addMessage(jsonObject);
        }

    }

    private Room createNewChatItem(String username) {
       return mainController.createNewLateralChatContainer(username);



    }

    private boolean checkChatInput(String username, String message) {
        return false;
    }

    private boolean checkGroupInput(String[] list, String message, String groupName) {
        return false;
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
