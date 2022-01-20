package app.javachat.Models;

import app.javachat.Logger.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleChat implements Chat {
    private static int LOCAL_PORT, OTHER_PORT;
    private final User otherUser;
    private ServerSocket serverLocal;


    public SimpleChat(ChatRequest chatRequest, int localPort) {
        this.OTHER_PORT = chatRequest.getSenderPort();
        this.otherUser = chatRequest.getSender();
        this.LOCAL_PORT = localPort;
        try {
            //Creamos el server local para escuchar mensajes
            serverLocal = new ServerSocket(LOCAL_PORT);
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }

    @Override
    public void start() {
        Thread threadListener = new Thread(this::receiveMessage);
        threadListener.start();
    }

    @Override
    public void sendMessage() {
    }

    @Override
    public void receiveMessage() {
        ObjectInputStream inputStream = null;
        Socket otherUserSocket = null;
        Object inputObject = null;

        try {
            //Socket del otro user.
            otherUserSocket = serverLocal.accept();
            inputStream = new ObjectInputStream(otherUserSocket.getInputStream());

            Log.show("Leyendo objeto recibido.");
            inputObject = inputStream.readObject();
            if(inputObject instanceof Message){
                printMessage((Message) inputObject);
            }


        } catch (IOException | ClassNotFoundException e) {
            Log.error(e.getMessage());
        } finally {
            try {
                //Cerrar y liberar recursos
                if (otherUserSocket != null)
                    otherUserSocket.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                Log.error(e.getMessage());
            }
        }
    }

    private void printMessage(Message message) {
        System.out.println(message);
    }
}
