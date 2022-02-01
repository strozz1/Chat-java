package app.javachat.Calls;

import app.javachat.Logger.Log;
import app.javachat.Utilities.Info;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import static app.javachat.Utilities.Info.Call.BUFFER_SIZE;

public class IncomeSoundCall extends Thread  implements Serializable {
    private final Call call;
    private boolean listening;

    private DatagramPacket datagramPacket;
    private DatagramSocket socket;
    private SourceDataLine sourceDataLine;

    @Override
    public void run() {
        try {
            Log.show("Started receiving audio.", "In-CALL");
            getAudio();
        } catch (IOException | LineUnavailableException e) {
            call.endCall();
        }
    }

    public void stopCall() {
        listening = false;
        socket.close();
        sourceDataLine.close();
        Log.show("Stopped receiving audio.", "IN-CALL");

    }

    public IncomeSoundCall(int localPort, Call call) {
        this.call=call;
        try {
            socket = new DatagramSocket(localPort);
            socket.setSoTimeout(1500);
        } catch (IOException e) {
            Log.error(e.getMessage(), "IncomeSoundCall");
        }

    }

    public void getAudio() throws IOException, LineUnavailableException {
        byte[] buffer = new byte[BUFFER_SIZE];
        listening = true;

        AudioFormat audioFormat = Info.Call.getAudioFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        while (listening) {
            datagramPacket = new DatagramPacket(buffer, BUFFER_SIZE);
            socket.receive(datagramPacket);

            //Escribimos la informacion en el buffer de la linea
            sourceDataLine.write(buffer, 0, BUFFER_SIZE);

        }
    }

}


