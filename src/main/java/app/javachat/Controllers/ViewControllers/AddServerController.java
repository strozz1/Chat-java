package app.javachat.Controllers.ViewControllers;

import app.javachat.Chats.ChatListener;

import app.javachat.Utilities.Info;
import app.javachat.Chats.ChatRequest;
import app.javachat.Garage.SalaModel;
import app.javachat.Models.User;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class AddServerController{
    String serverIp,userText;
    int port;
    @FXML
    private TextField inputTextAddress,inputTextPort;
    private SalaModel sala;
    private User user;



    public void onCreateServer(MouseEvent event){

    }


    public SalaModel getSalaModel(){
        return this.sala;
    }

    public User getUser() {
        return this.user;
    }


    public void onAddNewChat(MouseEvent mouseEvent) {
        String addressText = inputTextAddress.getText();
        String portText = inputTextPort.getText();
        ChatRequest selfChatRequest= new ChatRequest(Info.localUser);
        ChatListener.enviarChatRequest(addressText, Integer.parseInt(portText),selfChatRequest);

        closeWindow(mouseEvent);

    }

    private void closeWindow(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }


}
