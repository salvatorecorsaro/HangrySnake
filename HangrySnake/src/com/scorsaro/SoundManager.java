package com.scorsaro;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Sound manager
 */
public class SoundManager {

    File soundIntro = new File("sound/ElectronicFantasy.wav");
    File soundMenu = new File("sound/HeroicIntrusion.wav");
    File soundGame = new File("sound/SuTurno.wav");
    File soundImpact = new File("sound/impact.wav");

    Clip intro;
    Clip menu;
    Clip game;
    Clip impact;

    public SoundManager() {


        try {
            intro = AudioSystem.getClip();
            intro.open(AudioSystem.getAudioInputStream(soundIntro));
            intro.start();
            menu = AudioSystem.getClip();
            menu.open(AudioSystem.getAudioInputStream(soundMenu));


            game = AudioSystem.getClip();
            game.open(AudioSystem.getAudioInputStream(soundGame));


            impact = AudioSystem.getClip();
            impact.open(AudioSystem.getAudioInputStream(soundImpact));

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

    }

    public void startSound(Clip clip) {
        clip.start();
    }


    public void stopSound(Clip clip) {
        clip.stop();
    }

    public void loopSound(Clip clip) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}



