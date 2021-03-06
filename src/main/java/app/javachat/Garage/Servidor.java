package app.javachat.Garage;


import app.javachat.Logger.Log;
import app.javachat.Models.User;


public class Servidor extends Thread {
    private static User host;
    private static int port;

    public static void startServer(User host, int PORT) {
        SalaServidor server = new SalaServidor(host, PORT);
        while (true) {
            server.recibirMensaje();
            System.out.println(server.getListUsuarios());
        }


    }

    public Servidor(User user, int port) {
        Servidor.port = port;
        host = user;

    }

    @Override
    public void run() {
        Log.show("EL HOST DEL SERVIDOR ES " + host.getUsername() + " CON SERVIDOR " + host.getIP() + ":" + port);
        startServer(host, port);


    }

    public static void main(String[] args) {
        User user = new User("host", "localhost");
        port = 456;
        Log.show("EL HOST DEL SERVIDOR ES " + user.getUsername() + " CON SERVIDOR " + user.getIP() + ":" + port);
        startServer(user, port);
    }
}
