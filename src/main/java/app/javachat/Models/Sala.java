package app.javachat.Models;


import app.javachat.Logger.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public interface Sala {


    /**
     * Este método crear una nueva connexion para poder realizar operaciones con el servidor.
     *
     * @return socket creado
     */
    static Socket crearConnexionConServer(String serverIp, int PORT) {
        Socket server = null;
        try {
            Log.show("Creando connexion con el server " + serverIp + ":" + PORT);
            server = new Socket(serverIp, PORT);
            Log.show("Servidor creado correctamente");
        } catch (IOException e) {
            Log.error("Ha habido un fallo al crear la conexion. " + e.getMessage());
        }
        return server;
    }

    /**
     * Este metodo crear una nueva conexion para poder leer msg del server.
     *
     * @return serverSocket
     */
    static ServerSocket crearConnexionPropia(int PORT) {
        ServerSocket server = null;
        try {
            Log.show("Abriendo server en puerto " + PORT);
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        return server;
    }

    /**
     * Este metodo cierra el socket Server atual.
     */
    static void cerrarConnexionSocket(Socket server) {
        try {
            //Si el server no es nulo, cierra la connexion.
            if (server != null)
                server.close();
            Log.show("Conexion cerrada.");
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }

    static void cerrarConnexionSocket(ServerSocket server) {
        try {
            //Si el server no es nulo, cierra la connexion.
            if (server != null)
                server.close();

            Log.show("Server cerrado.");
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
    }

}
