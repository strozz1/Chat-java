package app.javachat.Calls;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class IncomeSoundCall extends Thread {
    private static final int BUFFER_SIZE = 1000;
    private AudioInputStream audioInputStream;
    private InputStream inputStream;
    private ServerSocket serverSocket;
    private Socket socket;
    private boolean listening;

    @Override
    public void run() {
        System.out.println("get audio running");
        try {
            getAudio();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopCall() {
    }

    public IncomeSoundCall(int localPort) {
        try {
            serverSocket = new ServerSocket(localPort);
        } catch (IOException e) {
            e.printStackTrace();
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

        while (listening) {
            socket = serverSocket.accept();
            inputStream = socket.getInputStream();
            audioInputStream = new AudioInputStream(inputStream, audioFormat, BUFFER_SIZE / audioFormat.getFrameSize());

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

