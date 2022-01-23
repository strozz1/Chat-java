package app.javachat.Controllers.CustomControllers;

import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.MainApplication;
import app.javachat.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LeftChatItem extends AnchorPane {
    private LeftChatItemController controller;

    public LeftChatItem() {
        super();
        controller = new LeftChatItemController();
        loadXML(controller);
    }

    public LeftChatItem(ChatItem chatItem,User otherUser) {
        super();
        controller = new LeftChatItemController(chatItem,otherUser);
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
}
