package app.javachat.Models;


import app.javachat.Controllers.SalaServidor;


public class Servidor extends Thread {
    private User host;
    private  int port;
    public void run(User host, int PORT) {
        SalaServidor server = new SalaServidor(host, PORT);
        while (true) {
            server.recibirMensaje();
            System.out.println(server.getListUsuarios());
            System.out.println(server.getListMensajes());

        }


    }

    public Servidor(User user, int port) {
        this.port= port;
        this.host = user;

    }

    @Override
    public void run() {
        System.out.println("EL HOST DEL SERVIDOR ES " + host.getUsername() + " CON SERVIDOR " + host.getIP() + ":" + port);
        run(host, port);


    }
}
