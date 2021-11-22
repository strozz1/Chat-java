package app.javachat.Models;

import app.javachat.Controllers.Sala;
import app.javachat.Controllers.SalaCliente;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

public class Cliente {
    public static void main(String[] args) throws IOException {

//        Socket s = new Socket("192.168.1.53",556);

        User user = new User("juan");
        SalaModel salaModel = new SalaModel("192.168.1.48",556, user);
        SalaCliente cliente = new SalaCliente(salaModel,user);
        cliente.enviarMensaje(new Mensaje("hola", user,LocalDateTime.now()));

    }
}
