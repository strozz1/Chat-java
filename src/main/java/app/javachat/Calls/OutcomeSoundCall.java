package app.javachat.Calls;

import app.javachat.Controllers.ViewControllers.CallWindowController;
import app.javachat.MainApplication;
import app.javachat.Models.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class OutcomeSoundCall extends Thread {

    private final User otherUser;
    private final int port;
    private final Call call;
    private boolean isCapturing;
    private static final int BUFFER_SIZE = 1000;
    private OutputStream outputStream;
    private Socket socket;
    private Node view;

    public OutcomeSoundCall(User otherUser, int otherPort,Call call) {
        this.call=call;
        this.otherUser = otherUser;
        this.port= otherPort;
    }

    @Override
    public void run() {
        System.out.println("post audio running");
        try {
            captureSound();
        } catch (LineUnavailableException | IOException e) {
              call.endCall();
            Platform.runLater(()->{
                
            });
        }
    }

    public void stopCall() {
    }


    public void captureSound() throws LineUnavailableException, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        isCapturing = true;

        //Definir el formato audio
        AudioFormat audioFormat = getAudioFormat();

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
            int read = targetDataLine.read(buffer, 0, BUFFER_SIZE / 2); // la info se guarda en tempBuffer
            if (read > 0) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.close();
        }
    }

    private AudioFormat getAudioFormat() {
        return new AudioFormat(16000, 8, 1, true, true);
    }

    public void setView(Node node) {
        this.view= node;
    }
}

