package app.javachat;

import app.javachat.Controllers.CustomControllers.UserChatItemControl;
import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.Logger.Log;
import app.javachat.Models.ChatRequest;
import app.javachat.Models.SimpleChat;
import app.javachat.Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatListener extends Thread {
    private static int LOCAL_PORT = Info.NEW_CHAT_LISTENER_PORT;
    private static MainController controller;
    private static VBox lateralChatMenu;

    private ServerSocket localServer;
    private ChatRequest chatRequest;

    public ChatListener() {
        try {
            localServer = new ServerSocket(LOCAL_PORT);
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }


    @Override
    public void run() {
        while (true) {
            Log.show("Escuchando a nuevos posibles chats");
            escucharMensajes();
        }
    }

    private void escucharMensajes() {

        try {
            Socket inputServer = localServer.accept();
            ObjectInputStream inputStream = new ObjectInputStream(inputServer.getInputStream());
            Object objectRead = inputStream.readObject();

            inputStream.close();

            if (objectRead instanceof ChatRequest) {
                this.chatRequest = (ChatRequest) objectRead;
                crearNuevoChat();
                //devuelve nueva info al sender sobre tu puerto y nombre
                enviarChatRequest();

            }


        } catch (IOException | ClassNotFoundException e) {
            Log.error(e.getMessage());
        }


    }

    private void crearNuevoChat() {
        getChatListVBox();
        UserChatItemControl chatItem= new UserChatItemControl();
        //Todo
        SimpleChat simpleChat= new SimpleChat(chatRequest,LOCAL_PORT);
        simpleChat.start();
        lateralChatMenu.getChildren().add(chatItem);

    }

    private void enviarChatRequest() {
        Socket socketEnviador = null;
        ObjectOutputStream outputStream = null;
        //Creo el objeto a enviar, con el puerto.
        ChatRequest chatRequestSelf = new ChatRequest(Info.localUser);
        User user = chatRequest.getSender();
        int port = chatRequest.getSenderPort();

        try {
            socketEnviador = new Socket(user.getIP(), port);
            outputStream = new ObjectOutputStream(socketEnviador.getOutputStream());
            outputStream.writeObject(chatRequestSelf);

        } catch (IOException e) {
            Log.error(e.getMessage());
        } finally {
            try {
                if (socketEnviador != null)
                    socketEnviador.close();
                if (outputStream != null)
                    socketEnviador.close();
            } catch (IOException e) {
                Log.error(e.getMessage());
            }


        }

    }

    public static void getChatListVBox() {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("main-view.fxml"));
        controller = loader.getController();
        lateralChatMenu= controller.getLateralMenu();

    }

}
