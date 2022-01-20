package app.javachat.Controllers.CustomControllers;

import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.MainApplication;
import app.javachat.Models.SimpleChat;
import app.javachat.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LeftChatItem extends AnchorPane {
    private LeftChatItemController controller;
    private ChatItem chatItem;

    public LeftChatItem() {
        super();
        loadXML();
    }

    public LeftChatItem(SimpleChat chat) {
        super();
        loadXML();
        chatItem = chat.getChatItem();
        chatItem.setChat(chat);
        User otherUser = chat.getOtherUser();
        setUsernameText(otherUser.getUsername());
        controller.setChat(chat);

        //Start Listening
        chat.start();

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

    public void setMainController(MainController mainController) {
        controller.setMainController(mainController);

    }
}
