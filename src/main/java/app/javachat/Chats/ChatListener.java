package app.javachat.Chats;

import app.javachat.Calls.CallRequest;
import app.javachat.Controllers.CustomControllers.ChatItem;
import app.javachat.Controllers.CustomControllers.LeftChatItem;
import app.javachat.Controllers.ViewControllers.MainController;
import app.javachat.Logger.Log;
import app.javachat.Models.ChatInfo;
import app.javachat.Utilities.Info;
import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatListener {
    private final MainController mainController;
    private VBox lateralChatMenu;

    private ServerSocket localServer;
    private ChatRequest selfChatRequest;

    public ChatListener(MainController mainController) {

        this.mainController = mainController;
        this.lateralChatMenu = mainController.getLateralMenu();

        try {
            localServer = new ServerSocket(Info.CHAT_LISTENER_PORT);
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }


    public void startThreads() {
        Log.show("Listening for new incoming chats and calls");
        Thread incomingCallsThread = new Thread(this::listenForNewCalls);
        incomingCallsThread.setDaemon(true);
        Thread incomingChatsThread = new Thread(this::listenForNewChats);
        incomingChatsThread.setDaemon(true);

        incomingCallsThread.start();
        incomingChatsThread.start();
    }

    private void listenForNewCalls() {
        while (true) {
            Socket inputServer = null;
            try {
                inputServer = localServer.accept();

                Log.show("New call came!");
                ObjectOutputStream outputStream = new ObjectOutputStream(inputServer.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(inputServer.getInputStream());
                Object objectRead = inputStream.readObject();
                inputStream.close();
                if (objectRead instanceof CallRequest othersCallRequest) {
                    CallRequest selfCallRequest = new CallRequest(Info.Call.isUserFree());

                    outputStream.writeObject(selfCallRequest);
                    outputStream.close();

                }
            } catch (IOException | ClassNotFoundException e) {
                Log.error(e.getMessage());
            }
        }


    }

    private void listenForNewChats() {
        while (true) {
            int indexOfChatPort;

            try {
                //Escuchar mensajes
                Socket inputServer = localServer.accept();
                Log.show("New chat came!");
                ObjectInputStream inputStream = new ObjectInputStream(inputServer.getInputStream());
                Object objectRead = inputStream.readObject();
                inputStream.close();

                if (objectRead instanceof ChatRequest othersChatRequest) {
                    //Si no es una respuesta, simplemente mandales una respuesta
                    if (!othersChatRequest.isAccept()) {
                        // Get info from other user.
                        String otherIp = othersChatRequest.getSender().getIP();
                        int otherChatListenerPort = othersChatRequest.getCallListenerPort();

                        //Create new request
                        selfChatRequest = new ChatRequest(Info.localUser, true);
                        indexOfChatPort = othersChatRequest.getIndexOfChatPort();
                        // When we receive a new chat request, we need to tell the other user which index is he using for his chats.
                        selfChatRequest.setIndexOfChatPort(indexOfChatPort);

                        sendChatRequest(otherIp, otherChatListenerPort, selfChatRequest);

                        //creamos nuevo chat pasandole el request del otro, nuestro puerto de chat y nuestro puerto de llamada
                        createNewChat(othersChatRequest, selfChatRequest.getChatPort());

                    } else {
                        // Si es una respuesta de chat(isAccept es true) creamos el chat pasandole el chat request,
                        indexOfChatPort = othersChatRequest.getIndexOfChatPort();
                        createNewChat(othersChatRequest, Info.getPort(indexOfChatPort));
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                Log.error(e.getMessage());
            }
        }
    }

    private void createNewChat(ChatRequest othersChatRequest, int selfPort) {
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setUser(othersChatRequest.getSender());
        chatInfo.setLocalChatPort(selfPort);
        chatInfo.setOtherChatPort(othersChatRequest.getChatPort());
        chatInfo.setOtherCallListenerPort(othersChatRequest.getCallListenerPort());
        if (!Info.checkIfChatExist(chatInfo)) {

            Chat chat = new SimpleChat(othersChatRequest, selfPort);
            ChatItem chatItem = new ChatItem(chat, othersChatRequest.getSender(), othersChatRequest.getCallListenerPort());

            LeftChatItem leftChatItem = new LeftChatItem(chatItem);
            leftChatItem.setMainController(mainController);

            //Add chat to left side
            Platform.runLater(() -> lateralChatMenu.getChildren().add(leftChatItem));
            Info.addChat(chatInfo);

        } else Log.error("El chat ya existe. Prueba uno distinto");

    }

    public static void sendChatRequest(String ip, int chatListenerPort, Object request) {
        Socket socketEnviador = null;
        ObjectOutputStream outputStream = null;

        try {
            socketEnviador = new Socket(ip, chatListenerPort);
            outputStream = new ObjectOutputStream(socketEnviador.getOutputStream());
            outputStream.writeObject(request);

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

    public static CallRequest sendCallRequest(String ip, int chatListenerPort, CallRequest request) {
        CallRequest callRequest = null;
        Socket socketEnviador = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            socketEnviador = new Socket(ip, chatListenerPort);
            outputStream = new ObjectOutputStream(socketEnviador.getOutputStream());
            outputStream.writeObject(request);

            inputStream = new ObjectInputStream(socketEnviador.getInputStream());
            callRequest = (CallRequest) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
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
        return callRequest;
    }

}
