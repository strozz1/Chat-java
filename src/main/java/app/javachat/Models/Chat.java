package app.javachat.Models;

import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.ViewControllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public interface Chat {
    /**
     * Starts listening for messages
     */
    void start();
    void sendMessage();

    void receiveMessage();

    /**
     * Return the VBox objects that holds all the messages from the Chat
     * @return lateral ChatList Box
     */
    static VBox getChatListVBox() {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main-view.fxml"));
        MainController controller = loader.getController();
        return controller.getLateralMenu();

    }


    ChatItem getChatItem();

    void setChatItem(ChatItem chatItem);

}
