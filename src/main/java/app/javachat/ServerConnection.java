package app.javachat;

import app.javachat.Logger.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static app.javachat.Utilities.Info.getMapFromJson;

public class ServerConnection {
    private final String serverURI = Properties.getProperty("server-uri");
    private Socket socket;
    private final Manager manager;

    /**
     * Creates a connection from a properties file
     *
     * @param manager Manager in charge of perfom actions when a message arrives
     */
    public ServerConnection(Manager manager) {
        this.manager = manager;
    }

    public void connect() {
        Log.show("Starting connection with the server","Server connection");

        URI uri = URI.create(serverURI);
        IO.Options options = IO.Options.builder().build();

        this.socket = IO.socket(uri, options).connect();
        socket.on("connect_error", err -> {
            try {
                throw  new ConnectionException();
            } catch (ConnectionException e) {
                Log.error(e.getMessage(),"ServerConnection");
            }
        });
    }

    public boolean login(String username, String password) throws SocketNotInitializedException {
        Log.show("Trying to login","Server connection");

        if (socket == null) throw new SocketNotInitializedException();
        AtomicInteger code = new AtomicInteger(0);
        if (!username.equals("") && !password.equals("")) {
            socket.emit("login", new String[]{username, password}, objects -> {
                HashMap<String, Object> mapFromJson = getMapFromJson(objects[0].toString());
                int res = Integer.parseInt((String) mapFromJson.get("code"));
                code.set(res);
            });
            while (true) if (code.get() != 0) break;
            return code.get() == 200;

        }
        return false;
    }

    /**
     * Start listening for incoming messages
     */
    public void listen() {
        Log.show("listening for messages","Server connection");
        socket.on("message", (msg) -> {
            manager.manage(String.valueOf(msg[0]));
        });
    }


    public static void main(String[] args) throws SocketNotInitializedException {

//        ServerConnection server = new ServerConnection(new MessageManager());
//        server.connect();
//        if (server.login("Juan", "123")) {
//            server.listen();
//        }
    }
}
