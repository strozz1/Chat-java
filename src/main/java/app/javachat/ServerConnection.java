package app.javachat;

import app.javachat.Logger.Log;
import app.javachat.Utilities.Info;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static app.javachat.Utilities.Info.getMapFromJson;

public class ServerConnection {
    private final String serverURI = PropertiesLoader.getProperty("server-uri");
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
        Log.show("Starting connection with the server", this.getClass().getName());

        URI uri = URI.create(serverURI);
        IO.Options options = IO.Options.builder().build();

        this.socket = IO.socket(uri, options).connect();
        socket.on("connect_error", err -> {
            try {
                throw new ConnectionException();
            } catch (ConnectionException e) {
                Log.error(e.getMessage(), this.getClass().getName());
            }
        });
    }

    public boolean login(String username, String password) throws SocketNotInitializedException {


        if (socket == null) throw new SocketNotInitializedException();
        AtomicInteger code = new AtomicInteger(0);
        AtomicReference<String> image = new AtomicReference<>();
        if (!username.equals("") && !password.equals("")) {
            socket.emit("login", new String[]{username, password}, objects -> {
                HashMap<String, Object> mapFromJson = getMapFromJson(objects[0].toString());
                int res = Integer.parseInt((String) mapFromJson.get("code"));
                code.set(res);
                List<Map<String,Object>> user = (List<Map<String, Object>>) mapFromJson.get("user");
                image.set(((String) user.get(0).get("image")));

            });
            while (true) if (code.get() != 0) break;
            boolean valid = code.get() == 200;
            String value = image.get();
            if (valid && value != null) Info.setImage(value);
            return valid;

        }
        return false;
    }

    /**
     * Start listening for incoming messages
     */
    public void listen() {
        Log.show("listening for messages", "Server connection");
        socket.on("message", (msg) -> {
            Log.show("new message");
            manager.manage(String.valueOf(msg[0]));
        });
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean checkLoginCredentials(String username, String password) throws SocketNotInitializedException {
        Log.show("Login attempt for username " + username, this.getClass().getName());
        return login(username, password);
    }

    public boolean checkRegisterCredentials(String username, String email, String password, String photo) throws SocketNotInitializedException {
        AtomicInteger code = new AtomicInteger(0);

        socket.emit("register", new String[]{username, email, password, photo}, (msg) -> {
            HashMap<String, Object> mapFromJson = getMapFromJson(msg[0].toString());
            System.out.println(mapFromJson);
            String code1 = (String) mapFromJson.get("code");
            int res = Integer.parseInt(code1);
            code.set(res);
        });
        while (true) if (code.get() != 0) break;
        return code.get() == 200;
    }

    public void disconnect(){
        Log.show("Disconnected");
        socket.emit("offline",Info.username.getValue());
    }
}
