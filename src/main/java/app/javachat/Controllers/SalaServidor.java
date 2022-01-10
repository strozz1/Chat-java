package app.javachat.Controllers;

import app.javachat.Excepciones.ExceptionConnexion;
import app.javachat.Logger.Log;
import app.javachat.Models.Mensaje;
import app.javachat.Models.Sala;
import app.javachat.Models.SalaModel;
import app.javachat.Models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class SalaServidor {
    private int PORT_INTERNO = 9656;
    private final SalaModel salaModel;

    public SalaServidor(User userHost, int PORT) {
        salaModel = new SalaModel(userHost.getIP(), PORT, userHost);
    }

    /**
     * Busca la ip local del sistema.
     * @return ipHost, que es la ip del pc del usuario
     */
    private static String getIpHost() {
        String ipHost = null;
        try {
            //Busca en todas las interfaces del sistema la ip local del ordenador.
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                for (InterfaceAddress interfaceAddress : networkInterfaceEnumeration.nextElement().getInterfaceAddresses())
                    if (interfaceAddress.getAddress().isSiteLocalAddress())
                        ipHost = interfaceAddress.getAddress().getHostAddress();
            }
        } catch (SocketException e) {
            Log.error(e.getMessage(),"SERVER");
        }
        return ipHost;
    }

    /**
     * Metodo que envia mensaje a todos los usuarios conectados.
     * @param object el objeto a enviar
     */
    public void enviarMensaje(Object object) {
        // creamos las instancias necesarias para enviar mensajes a los clientes
        Socket socket = null;
        ObjectOutputStream objectWriter = null;

        Log.show("Preparando para enviar mensaje." + object.toString(),"SERVER");
        try {
            // Si el objeto pasado es null o no es un Mensaje o User, tiramos una exception
            if ((object instanceof User)) {
                Log.show("Preparándose para enviar el objeto de tipo SalaModel.","SERVER");
                socket = Sala.crearConnexionConServer(((User) object).getIP(), PORT_INTERNO); // Creamos la conexion
                objectWriter = new ObjectOutputStream(socket.getOutputStream());
                objectWriter.writeObject(salaModel);  //Escribimos el objeto.
            } else {
                Log.show("Preparándose para enviar el objet de tipo Mensaje.","SERVER");
                for (User user : salaModel.getListUsuarios()) {
                    try {
                        socket = Sala.crearConnexionConServer(user.getIP(), PORT_INTERNO); // Creamos la conexion
                        if (socket == null) throw new ExceptionConnexion("El socket es nulo por errores internos.");

                        objectWriter = new ObjectOutputStream(socket.getOutputStream());
                        objectWriter.writeObject(object);  //Escribimos el objeto.
                        Log.show("Mensaje enviado a " + user.getUsername() + " con IP " + user.getIP() + ".","SERVER");
                    } catch (Exception e) {
                        Log.error("Mensaje no enviado a " + ((Mensaje) object).getSender().getUsername() + ", es posible que se halla desconectado.","SERVER");
                    }
                }
            }
        } catch (IOException e) {
            Log.error(e.getMessage());
        } finally {
            try {
                //Cerrar y liberar recursos;
                Log.show("Liberando Recursos y cerrando conexiones...","SERVER");
                Sala.cerrarConnexionSocket(socket);
                if (objectWriter != null)
                    objectWriter.close();
            } catch (IOException e) {
                Log.error(e.getMessage(),"SERVER");
            }
        }
    }


    public void recibirMensaje() {
        {
            ServerSocket socketServer = null;
            Socket socketEntrante = null;
            //objectRec es el que te envía el user, objectSender el objeto que envías.
            Object objectRecibido;
            ObjectInputStream objectReader = null;

            try {
                Log.show("Creando servidor para recibir el mensaje.","SERVER");
                //Creamos una instancia de nuestro server para poder recibir mensajes de los usuarios.
                socketServer = Sala.crearConnexionPropia(salaModel.getPORT());
                if (socketServer == null)
                    throw new ExceptionConnexion("El socket servidor es nulo por errores internos.");

                Log.show("Escuchando en el servidor " + salaModel.getServerIp() + ":" + salaModel.getPORT() + ".","SERVER");
                // Escuchamos mensajes entrantes y creamos el objeto socketServerEntrante, siendo este el usuario que envía datos.
                socketEntrante = socketServer.accept();
                if (socketEntrante == null)
                    throw new ExceptionConnexion("El socket saliente es nulo por errores internos.");

                objectReader = new ObjectInputStream(socketEntrante.getInputStream());

                Log.show("Leyendo el objeto recibido.","SERVER");
                objectRecibido = objectReader.readObject();
                // Al recibir el mensaje del cliente, comprobamos de que objeto se trata
                if (objectRecibido instanceof User) {
                    Log.show("El objeto es de tipo User." + objectRecibido,"SERVER");
                    addUsuario((User) objectRecibido);
                } else {
                    Log.show("El objeto es de tipo Mensaje." + objectRecibido,"SERVER");
                    addMensaje((Mensaje) objectRecibido);
                    if (!salaModel.getListUsuarios().contains(((Mensaje) objectRecibido).getSender()))
                        addUsuario(((Mensaje) objectRecibido).getSender());
                }
                //Enviamos el mensaje
                enviarMensaje(objectRecibido);
            } catch (IOException | ClassNotFoundException | ExceptionConnexion e) {
                Log.error(e.getMessage(),"SERVER");
            } finally {
                try {
                    //Cerrar y liberar recursos
                    Sala.cerrarConnexionSocket(socketEntrante);
                    Sala.cerrarConnexionSocket(socketServer);
                    if (objectReader != null)
                        objectReader.close();
                } catch (IOException e) {
                    Log.error(e.getMessage(),"SERVER");
                }
            }
        }
    }

    public void addMensaje(Mensaje mensaje) {
        salaModel.getListMensajes().add(mensaje);
    }

    public void addUsuario(User user) {
        if (!salaModel.getListUsuarios().contains(user))
            salaModel.getListUsuarios().add(user);
    }

    public List<User> getListUsuarios() {
        return salaModel.getListUsuarios();
    }

    public List<Mensaje> getListMensajes() {
        return salaModel.getListMensajes();
    }


}
