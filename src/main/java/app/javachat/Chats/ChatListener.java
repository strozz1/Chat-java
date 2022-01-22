package app.javachat.Chats;

import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.Utilities.Info;
import app.javachat.Logger.Log;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatListener extends Thread {
    private final MainController mainController;
    private VBox lateralChatMenu;

    private ServerSocket localServer;
    private ChatRequest selfChatRequest;

    public ChatListener(MainController mainController) {
        this.mainController = mainController;
        this.lateralChatMenu = mainController.getLateralMenu();

        try {
            localServer = new ServerSocket(Info.NEW_CHAT_LISTENER_PORT);
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }


    @Override
    public void run() {
        while (true) {
            Log.show("Listening for new incoming chats");
            listenForNewChats();
        }
    }

    private void listenForNewChats() {

        try {
            Socket inputServer = localServer.accept();
            Log.show("New chat came!");
            ObjectInputStream inputStream = new ObjectInputStream(inputServer.getInputStream());
            Object objectRead = inputStream.readObject();

            inputStream.close();

            if (objectRead instanceof ChatRequest othersChatRequest) {
                selfChatRequest = new ChatRequest(Info.localUser, true);
                //Si no es una respuesta, simplemente mandales una respuesta
                int indexOfPort;
                if (!othersChatRequest.isAccept()) {
                    indexOfPort = othersChatRequest.getIndexOfPort();
                    selfChatRequest.setIndexOfPort(indexOfPort);
                    enviarChatRequest(othersChatRequest.getSender().getIP(),
                            othersChatRequest.getChatListenerPort()
                            , selfChatRequest);
                    createNewChat(othersChatRequest, selfChatRequest.getChatPort());

                } else {
                    indexOfPort = othersChatRequest.getIndexOfPort();
                    createNewChat(othersChatRequest, Info.getPort(indexOfPort));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Log.error(e.getMessage());
        }
    }

    private void createNewChat(ChatRequest othersChatRequest, int selfPort) {
        SimpleChat simpleChat = new SimpleChat(othersChatRequest, selfPort);
        ChatItem chatItem = new ChatItem(simpleChat, othersChatRequest.getSender());

        LeftChatItem leftChatItem = new LeftChatItem(chatItem, othersChatRequest.getSender());
        leftChatItem.setMainController(mainController);

        //Add chat to left side
        Platform.runLater(() -> lateralChatMenu.getChildren().add(leftChatItem));

    }

    public static void enviarChatRequest(String ip, int chatListenerPort, ChatRequest chatRequest) {
        Socket socketEnviador = null;
        ObjectOutputStream outputStream = null;

        try {
            socketEnviador = new Socket(ip, chatListenerPort);
            outputStream = new ObjectOutputStream(socketEnviador.getOutputStream());
            outputStream.writeObject(chatRequest);

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
}
