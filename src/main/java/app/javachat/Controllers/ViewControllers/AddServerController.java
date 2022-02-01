package app.javachat.Controllers.ViewControllers;

import app.javachat.Chats.ChatListener;
import app.javachat.Chats.ChatRequest;
import app.javachat.Garage.SalaModel;
import app.javachat.Models.User;
import app.javachat.Utilities.Info;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class AddServerController {
    String serverIp, userText;
    int port;
    @FXML
    private TextField inputTextAddress, inputTextPort;
    private SalaModel sala;
    private User user;


    public void onCreateServer(MouseEvent event) {

    }


    public SalaModel getSalaModel() {
        return this.sala;
    }

    public User getUser() {
        return this.user;
    }


    public void onAddNewChat(MouseEvent mouseEvent) {
        createChat(mouseEvent);
    }


    public void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            createChat(event);


    }

    private void closeWindow(Event event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }
    private void createChat(Event mouseEvent) {
        String addressText = inputTextAddress.getText();
        String portText = inputTextPort.getText();

        ChatRequest selfChatRequest = new ChatRequest(Info.localUser);
        ChatListener.enviarChatRequest(addressText, Integer.parseInt(portText), selfChatRequest);
        closeWindow(mouseEvent);
    }



}
