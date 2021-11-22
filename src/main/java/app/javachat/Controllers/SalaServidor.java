package app.javachat.Controllers;

import app.javachat.Excepciones.ExceptionConnexion;
import app.javachat.Models.Mensaje;
import app.javachat.Models.PackageInfo;
import app.javachat.Models.SalaModel;
import app.javachat.Models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class SalaServidor{
    private int PORT_INTERNO = 9656;
    private final SalaModel salaModel;

    public SalaServidor(User userHost, int PORT) {
        salaModel = new SalaModel(userHost.getIP(), PORT, userHost);
    }

    /**
     * Busca la ip local del sistema.
     *
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
            System.out.println(e.getMessage());
        }
        return ipHost;
    }

    /**
     * Metodo que envia mensaje a todos los usuarios conectados.
     *
     * @param object el objeto a enviar
     */
    public void enviarMensaje(Object object) {
        System.out.println("METHOD IN: enviarMensaje() of class SalaServidor.java" +
                "> Entrando al método...");
        // creamos las instancias necesarias para enviar mensajes a los clientes
        Socket socket = null;
        ObjectOutputStream objectWriter = null;

        System.out.println("Preparando para enviar mensaje." + object.toString());
        try {
            // Si el objeto pasado es null o no es un Mensaje o User, tiramos una exception
            if ((object instanceof User)) {
                System.out.println("Preparándose para enviar el objeto de tipo SalaModel.");
                socket = Sala.crearConexionConServer(((User) object).getIP(), PORT_INTERNO); // Creamos la conexion
                objectWriter = new ObjectOutputStream(socket.getOutputStream());
                objectWriter.writeObject(salaModel);  //Escribimos el objeto.
            } else {
                System.out.println("Preparándose para enviar el objet de tipo Mensaje.");
                for (User user : salaModel.getListUsuarios()) {
                    try {
                        socket = Sala.crearConexionConServer(user.getIP(), PORT_INTERNO); // Creamos la conexion
                        if (socket == null) throw new ExceptionConnexion("El socket es nulo por errores internos.");

                        objectWriter = new ObjectOutputStream(socket.getOutputStream());
                        objectWriter.writeObject(object);  //Escribimos el objeto.
                        System.out.println("Mensaje enviado a " + user.getUsername() + " con IP " + user.getIP() + ".");
                    }catch (Exception e){
                        System.out.println("ERROR: Mensaje no enviado a "+((Mensaje) object).getSender().getUsername() +", es posible que se halla desconectado.");
                    }
                }
            }
        } catch (IOException  e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                //Cerrar y liberar recursos;
                System.out.println("Liberando Recursos y cerrando conexiones...");
                Sala.cerrarConexionSocket(socket);
                if (objectWriter != null)
                    objectWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("METHOD OUT: enviarMensaje() of class SalaServidor.java" +
                "> Saliendo del método...");
    }


    public void recibirMensaje() {
        {
            System.out.println("METHOD IN: recibirMensaje() of class SalaServidor.java" +
                    "> Entrando al método...");
            ServerSocket socketServer = null;
            Socket socketEntrante = null;
            //objectRec es el que te envía el user, objectSender el objeto que envías.
            Object objectRecibido;
            ObjectInputStream objectReader = null;

            try {
                System.out.println("Creando servidor para recibir el mensaje.");
                //Creamos una instancia de nuestro server para poder recibir mensajes de los usuarios.
                socketServer = Sala.crearConexionPropia(salaModel.getPORT());
                if (socketServer == null)
                    throw new ExceptionConnexion("El socket servidor es nulo por errores internos.");

                System.out.println("Escuchando en el servidor " + salaModel.getServerIp() + ":" + salaModel.getPORT() + ".");
                // Escuchamos mensajes entrantes y creamos el objeto socketServerEntrante, siendo este el usuario que envía datos.
                socketEntrante = socketServer.accept();
                if (socketEntrante == null)
                    throw new ExceptionConnexion("El socket saliente es nulo por errores internos.");

                objectReader = new ObjectInputStream(socketEntrante.getInputStream());

                System.out.println("Leyendo el objeto recibido.");
                objectRecibido = objectReader.readObject();
                // Al recibir el mensaje del cliente, comprobamos de que objeto se trata
                if (objectRecibido instanceof User) {
                    System.out.println("El objeto es de tipo User."+objectRecibido);
                    addUsuario((User) objectRecibido);
                } else {
                    System.out.println("El objeto es de tipo Mensaje."+objectRecibido);
                    addMensaje((Mensaje) objectRecibido);
                    if(!salaModel.getListUsuarios().contains(((Mensaje) objectRecibido).getSender()))
                        addUsuario(((Mensaje) objectRecibido).getSender());
                }
                //Enviamos el mensaje
                enviarMensaje(objectRecibido);
            } catch (IOException | ClassNotFoundException | ExceptionConnexion e) {
                System.out.println("ERROR: "+e.getMessage());
            } finally {
                try {
                    //Cerrar y liberar recursos
                    Sala.cerrarConexionSocket(socketEntrante);
                    Sala.cerrarConexionSocket(socketServer);
                    if (objectReader != null)
                        objectReader.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        System.out.println("METHOD OUT: recibirMensaje() of class SalaServidor.java" +
                "> Saliendo del método...");
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
