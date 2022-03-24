package app.javachat.Utilities;

import app.javachat.Logger.Log;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static app.javachat.Utilities.Info.Call.getAudioFormat;

public class AudioManager {
    private TargetDataLine line;
    private File wavFile;

    public byte[] record(String username, OutputStream out) throws IOException {
        wavFile = new File(LocalDataManager.userFolder + "/audio/" + username + ".wav");
        wavFile.createNewFile();
        int length = (int) wavFile.length();
        byte[] buffer = new byte[length];

        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                //Todo
                Log.error("linea no suporteada", "AudioManager");
                return buffer;
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            AudioInputStream audioIn = new AudioInputStream(line);
            AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
            AudioSystem.write(audioIn, fileType, wavFile);

            FileInputStream input = new FileInputStream(wavFile);
            input.transferTo(out);

            input.read(buffer);


        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

        return buffer;

    }

    public void stopRecording() {
        line.stop();
        line.close();
    }

    public static void main(String[] args) throws IOException {
        AudioManager manager = new AudioManager();

        new Thread(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            manager.stopRecording();
        }).start();
    }
}
