package app.javachat.Controllers.CustomControllers;

import app.javachat.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UserChatItemControl extends AnchorPane {
    UserChatItemController controller;

    public UserChatItemControl() {
        super();
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("user-chat-item.fxml"));

            //Create new controller
            controller = new UserChatItemController();
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
