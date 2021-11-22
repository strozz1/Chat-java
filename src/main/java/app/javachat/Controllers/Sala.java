package app.javachat.Controllers;

import app.javachat.Log.LogInfo;
import app.javachat.Models.Mensaje;
import app.javachat.Models.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public interface Sala {


    /**
     * Este metodo crear una nueva conexion para poder realizar operaciones con el servidor.
     *
     * @return socket creado
     */
    static Socket crearConexionConServer(String serverIp, int PORT) {
        System.out.println("METHOD IN: crearConexionConServer() of class Sala.java" +
                "> Entrando al método...");
        Socket server = null;
        try {
            System.out.println("Creando conexion con el server " + serverIp + ":" + PORT);
            server = new Socket(serverIp, PORT);
            System.out.println("Servidor creado correctamente");
        } catch (IOException e) {
            System.out.println("ERROR: Ha habido un fallo al crear la conexion.");
            e.printStackTrace();
        }
        System.out.println("METHOD OUT: crearConexionConServer() of class Sala.java" +
                "> Saliendo del método...");
        return server;
    }

    /**
     * Este metodo crear una nueva conexion para poder leer msg del server.
     *
     * @return serverSocket
     */
    static ServerSocket crearConexionPropia(int PORT) {
        ServerSocket server = null;
        try {
            System.out.println("Abirendo server en local, puerto " + PORT);
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            LogInfo.log().severe(e.getMessage());
        }
        return server;
    }

    /**
     * Este metodo cierra el socket Server atual.
     */
    static void cerrarConexionSocket(Socket server) {
        try {
            //Si el server no es nulo, cierra la conexion.
            if (server != null)
                server.close();
            System.out.println("Conexion cerrada.");
        } catch (IOException e) {
            LogInfo.log().severe(e.getMessage());
        }
    }

    static void cerrarConexionSocket(ServerSocket server) {
        try {
            //Si el server no es nulo, cierra la conexion.
            if (server != null)
                server.close();

            System.out.println("Server cerrado.");
        } catch (IOException e) {
            LogInfo.log().severe(e.getMessage());
        }
    }

}
