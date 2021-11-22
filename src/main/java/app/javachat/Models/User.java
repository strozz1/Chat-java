package app.javachat.Models;


import app.javachat.Log.LogInfo;

import java.io.Serializable;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Objects;

public class User implements Serializable {
    private String username;
    private final String IP;

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(IP, user.IP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, IP);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIP() {
        return IP;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", IP='" + IP + '\'' +
                '}';
    }

    public User(String username, String IP) {
        this.username = username;
        this.IP = IP;


    }
    public User(String username){
        this.username= username;
        this.IP = getIpHost();
    }
    private static String getIpHost() {
        String ipHost = null;
        try {
            //Busca en todas las interfaces del sistema la ip local del ordenador.
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                for (InterfaceAddress interfaceAddress : networkInterfaceEnumeration.nextElement().getInterfaceAddresses())
                    if (interfaceAddress.getAddress().isSiteLocalAddress())
                        ipHost = interfaceAddress.getAddress().getHostAddress().toString();
            }
        } catch (SocketException e) {
            LogInfo.log().severe(e.getMessage());
        }
        return ipHost;
    }
}
