package app.javachat.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO implementar listMensajes y reemplazar PackageInfo.

public class SalaModel implements Serializable {
    private User host;
    private final int PORT;
    private final String serverIp;
    private List<User> listUsuarios;
    private List<Mensaje> listMensajes;

    public SalaModel(String serverIp, int PORT, User host) {
        this.serverIp = serverIp;
        this.PORT = PORT;
        this.host = host;
        this.listUsuarios = new ArrayList<>();
        this.listMensajes = new ArrayList<>();

    }

    public SalaModel(String serverIp, int PORT, User host, List<User> listUsuarios, List<Mensaje> listMensajes) {
        this.serverIp = serverIp;
        this.listUsuarios = listUsuarios;
        this.listMensajes = listMensajes;
        this.PORT = PORT;
    }


    public String getServerIp() {
        return serverIp;
    }

    public int getPORT() {
        return PORT;
    }

    public void addUser(User user) {
        if (!listUsuarios.contains(user))
            listUsuarios.add(user);
    }

    public void addMensaje(Mensaje mensaje) {
        if (!listMensajes.contains(mensaje))
            listMensajes.add(mensaje);
    }

    public User getHost() {
        return host;
    }

    public List<User> getListUsuarios() {
        return listUsuarios;
    }

    public List<Mensaje> getListMensajes() {
        return listMensajes;
    }

    public void setListUsuarios(List<User> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public void setListMensajes(List<Mensaje> listMensajes) {
        this.listMensajes = listMensajes;
    }
}
