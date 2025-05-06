package org.utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sounds {

    public static void soundGo(){ //suono generato quando viene cliccato il pulsante avanti
        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/go.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void soundBack(){ //suono generato quando viene cliccato il pulsante indietro
        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/back.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void soundInfo(){ //suono generato quando compare la scena info
        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/info.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void soundOpen(){ //suono generato quando si apre il programma
        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/open.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void soundClose(){ //suono generato quando si chiude il programma
        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/close1.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/close2.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void soundWelcome(){ //suono generato quando si accede per la prima volta il menu
        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/welcome.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void soundLogout(){ //suono generato quando si accede per la prima volta il menu
        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/logout.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void soundLoading(){ //suono generato quando compare la scena del caricamento
        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/loading.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void soundPositiveAndQuestionNotify(){ //suono generato quando compare la scena della risposta positiva e della domanda
        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/positiveAndQuestionNotify.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void soundNegativeNotify(){ //suono generato quando compare la scena della risposta negativa
        try {
            File fileAudio = new File("C:/Users/giorg/OneDrive/Desktop/App/G&P/Programming/Java/GeostoreFX/src/main/resources/org/sounds/negativeNotify.wav"); // Inserisci il tuo file WAV
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Riproduce il suono
            //Thread.sleep(clip.getMicrosecondLength() / 1000); // Attendi la fine
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
