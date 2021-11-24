package app.javachat.Controllers;

import app.javachat.Excepciones.ExceptionConnexion;
import app.javachat.Models.Sala;
import app.javachat.Models.SalaModel;
import app.javachat.Models.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Esta clase se usa cada vez que un usuario quiera unirse a una sala ya creada.
 */
public class SalaCliente {
    private final User user;
    private final SalaModel salaModel;

    /**
     * Creamos una SalaCliente, pasándole un objeto SalaModel, el cual contiene la ip,
     * el puerto y lista users y mensajes. También contiene el host.
     * Además le pasamos nuestro usuario para que lo guarde
     *
     * @param salaModel el modelo de sala del servidor
     * @param user      el usuario que accede al servidor
     */
    public SalaCliente(SalaModel salaModel, User user) {
        this.salaModel = salaModel;
        this.user = user;

    }

    public Object recibirMensaje() {
        int PORT_INTERNO = 9656;

        System.out.println("METHOD IN: recibirMensaje() of class SalaCliente.java" +
                "> Entrando al método...");
        ServerSocket socketPropio = null;
        Socket socketSaliente = null;
        Object objetoRecibido = null;
        ObjectInputStream objectReader = null;

        try {
            System.out.println("> Creando servidor interno para recibir mensajes del servidor.");
            //Creamos una instancia de nuestro server interno para poder recibir mensajes del server.
            if ((socketPropio = Sala.crearConnexionPropia(PORT_INTERNO)) == null)
                throw new ExceptionConnexion("El socket es nulo.");
            // Escuchamos mensajes entrantes y creamos el objeto socketServerEntrante, siendo este el usuario que envía datos.
            System.out.println("> Escuchando en el servidor." + user.getIP() + ":" + PORT_INTERNO);
            socketSaliente = socketPropio.accept();
            objectReader = new ObjectInputStream(socketSaliente.getInputStream());

            System.out.println("> Leyendo objeto recibido.");
            // Recaudamos el mensaje del cliente, comprobamos de que objeto se trata
            objetoRecibido = objectReader.readObject();


        } catch (IOException | ClassNotFoundException | ExceptionConnexion e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                //Cerrar y liberar recursos
                Sala.cerrarConnexionSocket(socketSaliente);
                Sala.cerrarConnexionSocket(socketPropio);
                if (objectReader != null)
                    objectReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        //Devolvemos el mensaje
        System.out.println("METHOD OUT: recibirMensaje() of class SalaCliente.java" +
                "> Saliendo del método...");
        return objetoRecibido;
    }


    public void enviarMensaje(Object mensaje) {

        System.out.println("METHOD IN: enviarMensaje() of class SalaCliente.java" +
                "> Entrando al método...");
        // creamos las instancias necesarias para enviar mensajes al servidor
        Socket socket = null;
        ObjectOutputStream objectWriter = null;
        try {
            // Si el objeto pasado es null o no es un Mensaje.java, tiramos una exception
            if (mensaje == null) throw new ExceptionConnexion("El objeto a enviar es nulo");
            // Creamos la connexion
            socket = Sala.crearConnexionConServer(salaModel.getServerIp(), salaModel.getPORT());
            if (socket == null) throw new ExceptionConnexion("El socket es nulo por errores internos.");

            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectWriter.writeObject(mensaje);  //Escribimos el objeto.
            System.out.println("Mensaje enviado." + mensaje);

        } catch (IOException | ExceptionConnexion e) {
            System.out.println("ERROR, mensaje: " + e.getMessage());
        } finally {
            try {
                //Cerrar y liberar recursos;
                Sala.cerrarConnexionSocket(socket);
                if (objectWriter != null)
                    objectWriter.close();
            } catch (IOException e) {
                System.out.println("ERROR, mensaje: " + e.getMessage());
            }
        }
        System.out.println("METHOD OUT: enviarMensaje() of class SalaCliente.java" +
                "> Saliendo del método...");
    }


//    public List<User> getListUsuarios() {
//        return salaModel.getListUsuarios();
//    }
//
//    public List<Mensaje> getListMensajes() {
//        return salaModel.getListMensajes();
//    }
}
