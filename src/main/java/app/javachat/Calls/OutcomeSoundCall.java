package app.javachat.Calls;

import app.javachat.Logger.Log;
import app.javachat.Models.User;
import app.javachat.Utilities.Info;
import javafx.application.Platform;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import static app.javachat.Utilities.Info.Call.BUFFER_SIZE;

public class OutcomeSoundCall extends Thread  implements Serializable {

    private final User otherUser;
    private final int port;
    private final Call call;
    private boolean isCapturing;

    private DatagramPacket datagramPacket;
    private DatagramSocket socket;
    private TargetDataLine targetDataLine;


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
        isCapturing = false;
        targetDataLine.close();
        socket.close();
    }


    public void captureSound() throws LineUnavailableException, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        isCapturing = true;

        //Definir el formato audio
        AudioFormat audioFormat = Info.Call.getAudioFormat();

        // Info sobre la linea
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

        //Creamos la linea por donde escuchar el audio, pasandole la info del audio q queremos escuchar
        targetDataLine = (TargetDataLine) AudioSystem.getLine(info);

        //Abrimos la linea y empezamos a caputar el sonido.
        targetDataLine.open(audioFormat);
        targetDataLine.start();

        while (isCapturing) {
            InetAddress address = InetAddress.getByName(otherUser.getIP());
            socket = new DatagramSocket();
            int read = targetDataLine.read(buffer, 0, BUFFER_SIZE); // la info se guarda en tempBuffer
            if (read > 0) {
                datagramPacket = new DatagramPacket(buffer, BUFFER_SIZE, address, port);
                socket.send(datagramPacket);
            }
        }
//        socket.close();
    }

}

