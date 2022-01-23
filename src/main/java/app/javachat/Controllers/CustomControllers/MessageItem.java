package app.javachat.Controllers.CustomControllers;

import app.javachat.MainApplication;
import app.javachat.Models.Message;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;

import javax.swing.*;
import java.io.IOException;

public class MessageItem extends AnchorPane {

    private MessageItemController controller;

    public MessageItem(){
        setMaxWidth(USE_COMPUTED_SIZE);
        setMaxHeight(USE_COMPUTED_SIZE);
        controller= new MessageItemController();
        load(controller);
    }
    public MessageItem(Message message,boolean isMine){
        if(isMine){
            nodeOrientationProperty().set(NodeOrientation.RIGHT_TO_LEFT);
        }
        else{
            nodeOrientationProperty().set(NodeOrientation.LEFT_TO_RIGHT);
        }

        controller= new MessageItemController(message, isMine);
        load(controller);
    }

    private void load(MessageItemController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("message-item.fxml"));
            loader.setController(controller);
            Node node = loader.load();
            getChildren().add(node);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
