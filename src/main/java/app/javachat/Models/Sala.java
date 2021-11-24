package app.javachat.Models;


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
        System.out.println("METHOD IN: crearConnexionConServer() of class Sala.java" +
                "> Entrando al método...");
        Socket server = null;
        try {
            System.out.println("Creando connexion con el server " + serverIp + ":" + PORT);
            server = new Socket(serverIp, PORT);
            System.out.println("Servidor creado correctamente");
        } catch (IOException e) {
            System.out.println("ERROR: Ha habido un fallo al crear la conexion.");
            e.printStackTrace();
        }
        System.out.println("METHOD OUT: crearConnexionConServer() of class Sala.java" +
                "> Saliendo del método...");
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
            System.out.println("Abirendo server en local, puerto " + PORT);
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            System.err.println(e.getMessage());
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
            System.out.println("Conexion cerrada.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    static void cerrarConnexionSocket(ServerSocket server) {
        try {
            //Si el server no es nulo, cierra la connexion.
            if (server != null)
                server.close();

            System.out.println("Server cerrado.");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
