package app.javachat.Controllers;

import app.javachat.Controllers.SalaCliente;
import java.awt.*;
import java.awt.event.*;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;

import app.javachat.Models.SalaModel;
import app.javachat.Models.Servidor;
import app.javachat.Models.User;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class AddServerController {
    String serverIp,userText;
    int port;
    @FXML
    private TextField inputUserJoin,inputUserCreate,inputServerJoin,inputServerCreate,inputPortJoin,inputPortCreate;
    private SalaModel sala;
    private User user;

    public void onJoinServer(MouseEvent event) {
        serverIp= inputServerJoin.getText();
        userText = inputUserJoin.getText();
        User user = new User(userText);
        port = Integer.parseInt(inputPortJoin.getText());
        SalaCliente sala = new SalaCliente(new SalaModel(serverIp,port,user),user);
        sala.enviarMensaje(user);
        this.sala = (SalaModel) sala.recibirMensaje();
        this.user = user;
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();



    }

    public void onCreateServer(MouseEvent event){
        serverIp= inputServerCreate.getText();
        userText = inputUserCreate.getText();
        User user = new User(userText,serverIp);
        port = Integer.parseInt(inputPortCreate.getText());
        new Servidor(user,port).start();
        this.sala = new SalaModel(serverIp,port,user);
        this.user=user;
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }


    public SalaModel getSalaModel(){
        return this.sala;
    }

    public User getUser() {
        return this.user;
    }
}
