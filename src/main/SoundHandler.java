package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundHandler {

    Clip clip;
    URL[] soundURL = new URL[10];

    public SoundHandler() {
       /* soundURL[0] = getClass().getResource("/sounds/");
        soundURL[1] = getClass().getResource("/sounds/");
        soundURL[2] = getClass().getResource("/sounds/");
        soundURL[3] = getClass().getResource("/sounds/");*/
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
