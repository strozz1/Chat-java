package app.javachat.Models;

import app.javachat.Logger.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleChat implements Chat {
    private static int LOCAL_PORT, OTHER_PORT;
    private final User otherUser;
    private ServerSocket serverLocal;


    public SimpleChat(ChatRequest chatRequest, int localPort) {
        this.OTHER_PORT = chatRequest.getChatPort();
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
    public void sendMessage(Message message) {
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        try {
            socket = new Socket(otherUser.getIP(), OTHER_PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(message);

        } catch (IOException e) {
            Log.error(e.getMessage());
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                if (socket != null)
                    socket.close();

            } catch (IOException e) {
                Log.error(e.getMessage());
            }

        }

    }

    @Override
    public Message receiveMessage() {
        ObjectInputStream inputStream = null;
        Socket otherUserSocket = null;
        Object inputObject;

        try {
            //Socket del otro user.
            Log.show("escuhando a chat");
            otherUserSocket = serverLocal.accept();
            inputStream = new ObjectInputStream(otherUserSocket.getInputStream());

            Log.show("Leyendo objeto recibido.");
            inputObject = inputStream.readObject();
            Log.show(inputObject.toString());
            if (inputObject instanceof Message) {
                return (Message) inputObject;
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
        return null;
    }


}
