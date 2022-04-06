package app.javachat.Controllers.CustomControllers;

import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.MainApplication;
import app.javachat.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class LeftChatItem extends AnchorPane implements Serializable {
    private LeftChatItemController controller;

    public LeftChatItem() {
        super();
        controller = new LeftChatItemController();
        loadXML(controller);
    }

    public LeftChatItem(ChatItem chatItem) {
        super();
        controller = new LeftChatItemController(chatItem);
        loadXML(controller);
    }

    private void loadXML(LeftChatItemController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("user-chat-item.fxml"));
            //Create new controller
            loader.setController(controller);

            Node node = loader.load();
            this.getChildren().add(node);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void setMainController(MainController mainController) {
        controller.setMainController(mainController);

    }
    public ChatItem getChatItem(){
        return controller.getChatItem();
    }

    public void addMessage(HashMap<String, Object> message) {
        controller.addMessage(message);
    }
}
