package app.javachat.Calls;

import app.javachat.Logger.Log;
import app.javachat.Models.User;
import app.javachat.Utilities.Info;
import javafx.application.Platform;
import javafx.scene.Node;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class OutcomeSoundCall extends Thread {

    private final User otherUser;
    private final int port;
    private final Call call;
    private boolean isCapturing;

    private OutputStream outputStream;
    private Socket socket;


    public OutcomeSoundCall(User otherUser, int otherPort, Call call) {
        this.call = call;
        this.otherUser = otherUser;
        this.port = otherPort;
    }

    @Override
    public void run() {

        try {
            Log.show("Started sending audio.", "OUT-CALL");
            captureSound();
            Log.show("Stopped sending audio.", "OUT-CALL");
        } catch (LineUnavailableException | IOException e) {
            call.endCall();
            Platform.runLater(() -> {

            });
        }
    }

    public void stopCall() {
        isCapturing=false;
    }


    public void captureSound() throws LineUnavailableException, IOException {
        byte[] buffer = new byte[Info.Call.BUFFER_SIZE];
        isCapturing = true;

        //Definir el formato audio
        AudioFormat audioFormat = Info.Call.getAudioFormat();

        // Info sobre la linea
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

        //Creamos la linea por donde escuchar el audio, pasandole la info del audio q queremos escuchar
        TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);

        //Abrimos la linea y empezamos a caputar el sonido.
        targetDataLine.open(audioFormat);
        targetDataLine.start();

        while (isCapturing) {
            socket = new Socket(otherUser.getIP(), port);
            outputStream = socket.getOutputStream();
            int read = targetDataLine.read(buffer, 0, Info.Call.BUFFER_SIZE); // la info se guarda en tempBuffer
            if (read > 0) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.close();
        }
    }

}

