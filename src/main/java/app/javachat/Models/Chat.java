package app.javachat.Models;

import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.ViewControllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public interface Chat {

    /**
     * This opens a connection to the server and sends a message to the server
     * @param message the messsage to send
     */
    void sendMessage(Message message);

    /**
     * Returns a message instance from another server. If the object received is not a Message, it will return null
     * @return Message object
     */
    Message receiveMessage();

    /**
     * Return the VBox objects that holds all the messages from the Chat
     * @return lateral ChatList Box
     */

}
