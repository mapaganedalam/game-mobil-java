package gamemobil;

import javax.sound.sampled.*;
import java.io.IOException;

public class SoundPlayer {

    private Clip clip;

    public void play(String path) {
        stop();
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(path));

            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop(String path) {
        stop();
        try {
            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(
                            getClass().getResource(path));

            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
