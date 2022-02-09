package app.javachat.Controllers.CustomControllers;

import app.javachat.Calls.Call;
import app.javachat.Chats.Chat;
import app.javachat.Chats.SimpleChat;
import app.javachat.MainApplication;
import app.javachat.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.Serializable;

public class ChatItem extends BorderPane implements Serializable {
    private User otherUser;
    private ChatItemController controller;

    public ChatItem() {
        super();

        controller = new ChatItemController();
        load(controller);
    }

    public ChatItem(Chat chat, User otherUser,int callListenerPort) {
        super();
        this.otherUser = otherUser;
        controller = new ChatItemController(chat, otherUser,callListenerPort);
        load(controller);
        controller.startListeningForMessages();
//        controller.startListeningForCalls();
    }

    private void load(ChatItemController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("chat-item.fxml"));
            loader.setController(controller);
            Node node = loader.load();

            this.setCenter(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChatItemController getController() {
        return controller;
    }

    public Chat getChat() {
        return controller.getChat();
    }

    public User getOtherUser() {
        return otherUser;
    }
}
