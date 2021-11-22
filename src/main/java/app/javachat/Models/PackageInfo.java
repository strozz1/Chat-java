package app.javachat.Models;

import java.io.Serializable;
import java.util.List;

public class PackageInfo implements Serializable {

    private List<User> listUsuarios;
    private List<Mensaje> listMensajes;

    public List<User> getListUsuarios() {
        return listUsuarios;
    }

    public void setListUsuarios(List<User> listUsuarios) {
        this.listUsuarios = listUsuarios;
    }

    public List<Mensaje> getListMensajes() {
        return listMensajes;
    }

    public void setListMensajes(List<Mensaje> listMensajes) {
        this.listMensajes = listMensajes;
    }

    @Override
    public String toString() {
        return "PackageInfo -\n" +
                "listUsuarios= " + listUsuarios +
                "\n, listMensajes= " + listMensajes +
                '.';
    }
}
