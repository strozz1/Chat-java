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

public class ServerConnection {
    private final String serverURI = Properties.getProperty("server-uri");
    private Socket socket;
    private MessageManager messageManager;

    public ServerConnection() {
        messageManager = new MessageManager();
    }

    public void connect() {
        URI uri = URI.create(serverURI);
        IO.Options options = IO.Options.builder().build();

        this.socket = IO.socket(uri, options).connect();
        Log.show("Connected successfully to the server, waiting for auth", "ServerConnection");
    }

    public boolean login(String username, String password) {
        AtomicInteger code = new AtomicInteger(0);
        if (!username.equals("") && !password.equals("")) {
            socket.emit("login", new String[]{username, password}, objects -> {
                HashMap<String, Object> mapFromJson = getMapFromJson(objects[0].toString());
                int res = Integer.parseInt((String) mapFromJson.get("code"));
                code.set(res);
            });
            while (true) if (code.get() != 0) break;
            if (code.get() == 200) return true;

        }
        return false;
    }

    public void listen() {
        socket.on("message", (msg) -> {
            messageManager.manage((String) msg[0]);
        });
        socket.on("message-list", (msg) -> {
            //todo message list returned
            //messageManager.manage((String) msg[0]);
        });
    }

    public static HashMap<String, Object> getMapFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {
            };
            return mapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        ServerConnection server = new ServerConnection();
        server.connect();
        if (server.login("juan", "123")) {
            server.listen();
        }
    }
}
