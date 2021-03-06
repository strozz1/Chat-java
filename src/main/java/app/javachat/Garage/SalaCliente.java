package app.javachat.Garage;

import app.javachat.Logger.Log;
import app.javachat.Models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Esta clase se usa cada vez que un usuario quiera unirse a una sala ya creada.
 */
public record SalaCliente(SalaModel salaModel, User user) {

    /**
     * Creamos una SalaCliente, pasándole un objeto SalaModel, el cual contiene la ip,
     * el puerto y lista users y mensajes. También contiene el host.
     * Además le pasamos nuestro usuario para que lo guarde
     *
     * @param salaModel el modelo de sala del servidor
     * @param user      el usuario que accede al servidor
     */
    public SalaCliente {
    }

    public Object recibirMensaje() {
        int PORT_INTERNO = 9656;
        ServerSocket socketPropio = null;
        Socket socketSaliente = null;
        Object objetoRecibido = null;
        ObjectInputStream objectReader = null;

        try {
            Log.show("Creando servidor interno para recibir mensajes del servidor.", "CLIENT");
            //Creamos una instancia de nuestro server interno para poder recibir mensajes del server.
            if ((socketPropio = Sala.crearConnexionPropia(PORT_INTERNO)) == null)
                // Escuchamos mensajes entrantes y creamos el objeto socketServerEntrante, siendo este el usuario que envía datos.
                Log.show("Escuchando en el servidor." + user.getIP() + ":" + PORT_INTERNO, "CLIENT");
            socketSaliente = socketPropio.accept();
            objectReader = new ObjectInputStream(socketSaliente.getInputStream());

            Log.show("Leyendo objeto recibido.", "CLIENT");
            // Recaudamos el mensaje del cliente, comprobamos de que objeto se trata
            objetoRecibido = objectReader.readObject();


        } catch (IOException | ClassNotFoundException e) {
            Log.error(e.getMessage(), "CLIENT");
        } finally {
            try {
                //Cerrar y liberar recursos
                Sala.cerrarConnexionSocket(socketSaliente);
                Sala.cerrarConnexionSocket(socketPropio);
                if (objectReader != null)
                    objectReader.close();
            } catch (IOException e) {
                Log.error(e.getMessage(), "CLIENT");
            }
        }
        //Devolvemos el mensaje
        return objetoRecibido;
    }


    public void enviarMensaje(Object mensaje) {

        // creamos las instancias necesarias para enviar mensajes al servidor
        Socket socket = null;
        ObjectOutputStream objectWriter = null;
        try {
            // Si el objeto pasado es null o no es un Mensaje.java, tiramos una exception
            if (mensaje == null)
                // Creamos la connexion
                socket = Sala.crearConnexionConServer(salaModel.getServerIp(), salaModel.getPORT());
            if (socket == null)

                objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectWriter.writeObject(mensaje);  //Escribimos el objeto.
            Log.show("Mensaje enviado." + mensaje, "CLIENT");

        } catch (IOException e) {
            Log.error(e.getMessage(), "CLIENT");
        } finally {
            try {
                //Cerrar y liberar recursos;
                Sala.cerrarConnexionSocket(socket);
                if (objectWriter != null)
                    objectWriter.close();
            } catch (IOException e) {
                Log.error(e.getMessage(), "CLIENT");
            }
        }
    }


//    public List<User> getListUsuarios() {
//        return salaModel.getListUsuarios();
//    }
//
//    public List<Mensaje> getListMensajes() {
//        return salaModel.getListMensajes();
//    }

}
