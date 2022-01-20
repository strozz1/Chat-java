package app.javachat.Controllers.CustomControllers;

import app.javachat.MainApplication;
import app.javachat.Models.Chat;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LeftChatItem extends AnchorPane {
    private LeftChatItemController controller;

    public LeftChatItem() {
        super();
        loadXML();
    }

    public LeftChatItem(Chat chat) {
        super();
        loadXML();
        controller.setChat(chat);
    }

    private void loadXML() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("user-chat-item.fxml"));
            //Create new controller
            controller = new LeftChatItemController();
            loader.setController(controller);

            Node node = loader.load();
            this.getChildren().add(node);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setUsernameText(String username){
        controller.getLeftmenuUserLabel().setText(username);
    }
}
