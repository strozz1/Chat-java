package app.javachat.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Mensaje implements Serializable {
    private String content;
    private User sender;
    private LocalDateTime horaEnvio;

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
