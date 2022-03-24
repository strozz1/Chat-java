package app.javachat.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleType implements LoggerType {
    public String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date= LocalDateTime.now().format(formatter);
        return date;
    }

    @Override
    public void showMessage(String msg) {
        System.out.println("|" + getTime() + "| -> " + msg);
    }

    @Override
    public void showError(String msg) {
        System.err.println("|" + getTime() + "| -> " + msg);
    }

    @Override
    public void showMessage(String msg, String sender) {
        System.out.println("|" + getTime() + "| |"+sender+"| -> " + msg);
    }

    @Override
    public void showError(String msg, String sender) {
        System.err.println("|" + getTime() + "| |"+sender+"| -> " + msg);
    }
}
