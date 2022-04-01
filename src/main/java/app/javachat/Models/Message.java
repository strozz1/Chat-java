package app.javachat.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private final String content;
    private final String sender;
    private final String horaEnvio;

    public Message(String content, String sender, String horaEnvio) {
        this.content = content;
        this.sender = sender;
        this.horaEnvio = horaEnvio;
    }

    public String getContent() {
        return content;
    }


    public String getSender() {
        return sender;
    }


    public String getHoraEnvio() {
        return horaEnvio;
    }


    @Override
    public String toString() {
        return sender +
                ":  " + content;
    }
}
