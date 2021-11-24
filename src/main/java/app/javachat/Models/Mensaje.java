package app.javachat.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Mensaje implements Serializable {
    private final String content;
    private final User sender;
    private final LocalDateTime horaEnvio;

    public Mensaje(String content, User sender, LocalDateTime horaEnvio) {
        this.content = content;
        this.sender = sender;
        this.horaEnvio = horaEnvio;
    }

    public String getContent() {
        return content;
    }


    public User getSender() {
        return sender;
    }


    public LocalDateTime getHoraEnvio() {
        return horaEnvio;
    }


    @Override
    public String toString() {
        return sender.getUsername() +
                ":  " + content;
    }
}
