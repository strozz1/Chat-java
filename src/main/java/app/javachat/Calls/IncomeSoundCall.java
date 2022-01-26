package app.javachat.Calls;

import app.javachat.Logger.Log;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class IncomeSoundCall extends Thread {
    private static final int BUFFER_SIZE = 1000;
    private ServerSocket serverSocket;
    private boolean listening;

    @Override
    public void run() {
        try {
            Log.show("Started receiving audio.", "In-CALL");
            getAudio();

        } catch (IOException | LineUnavailableException e) {
            Log.error(e.getMessage());
        }
    }

    public void stopCall() {
        try {
            listening = false;
            serverSocket.close();
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        Log.show("Stopped receiving audio.", "IN-CALL");

    }

    public IncomeSoundCall(int localPort) {
        try {
            serverSocket = new ServerSocket(localPort);
        } catch (IOException e) {
            Log.error(e.getMessage(), "IncomeSoundCall");
        }

    }

    public void getAudio() throws IOException, LineUnavailableException {
        byte[] buffer = new byte[BUFFER_SIZE];
        listening = true;

        AudioFormat audioFormat = getAudioFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();
        Socket socket;
        while (listening) {
            socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            AudioInputStream audioInputStream = new AudioInputStream(inputStream, audioFormat, BUFFER_SIZE / audioFormat.getFrameSize());

            int read;
            //Leer hasta q devuelva -1, es decir, hasta q este vacio
            while ((read = audioInputStream.read(buffer, 0, BUFFER_SIZE / 2)) != -1) {
                if (read > 0) {
                    //Escribimos la informacion en el buffer de la linea
                    sourceDataLine.write(buffer, 0, read);

                }
            }
            audioInputStream.close();
            inputStream.close();
        }
    }

    private AudioFormat getAudioFormat() {
        return new AudioFormat(16000, 8, 1, true, true);
    }

}

