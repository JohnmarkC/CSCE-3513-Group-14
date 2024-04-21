import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;

import java.awt.event.MouseEvent;
import static java.lang.System.*;
import java.util.Timer;



class Controller implements KeyListener, MouseListener
{
    player_entry_view view;
    Model model;
    SupaBaseIntegration data;
    private Clip audioClip;
    private Timer audioTimer;
    

    //Constructor
    Controller(Model m, player_entry_view v)
    {
        model = m;
        model.view = v;
        data = new SupaBaseIntegration();
        this.view = v;
        v.controller = this;
    }

    void setView(player_entry_view v)
    {
        view = v;
    }

    // Load the audio file
    private void loadAudio() {
        Random rand = new Random();
        int track = rand.nextInt(9); //random integer between 0 and 8
        if(track == 0)
        {
            try {
                File audioFile = new File("music_tracks/Track01.wav"); // Replace with your audio file path
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        else if (track == 1)
        {
            try {
                File audioFile = new File("music_tracks/Track02.wav"); // Replace with your audio file path
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        else if (track == 2)
        {
            try {
                File audioFile = new File("music_tracks/Track03.wav"); // Replace with your audio file path
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        else if (track == 3)
        {
            try {
                File audioFile = new File("music_tracks/Track04.wav"); // Replace with your audio file path
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        else if (track == 4)
        {
            try {
                File audioFile = new File("music_tracks/Track05.wav"); // Replace with your audio file path
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        else if (track == 5)
        {
            try {
                File audioFile = new File("music_tracks/Track06.wav"); // Replace with your audio file path
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        else if (track == 6)
        {
            try {
                File audioFile = new File("music_tracks/Track07.wav"); // Replace with your audio file path
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        else if (track == 7)
        {
            try {
                File audioFile = new File("music_tracks/Track08.wav"); // Replace with your audio file path
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
    }

    // Start playing the audio
    private void playAudio() {
        if (audioClip != null) {
            audioClip.setFramePosition(0); // Start from the beginning
            audioClip.start();
        }
    }

    private void loadAndStartAudioAfterDelay() {
        if (audioTimer != null) {
            audioTimer.cancel();
        }
        audioTimer = new Timer();
        audioTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                loadAudio();
                playAudio();
            }
        }, 14000); // 14 seconds in milliseconds
    }

    public void keyPressed(KeyEvent e)
    {
      if(model.entryscreen){
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ENTER: // Enter key is pressed
                model.search();
                break;
            case KeyEvent.VK_F5: //F5 key pressed
                model.start();
                loadAndStartAudioAfterDelay();
                /*if(model.equipmentID && model.players){
                    model.start();
                
                //create the game
                //start the audio when F5 is pressed
                    loadAndStartAudioAfterDelay(); 
                }*/
                break;
            case KeyEvent.VK_F12: // F12 key pressed
                model.clear();
                break;
        }
     }
     if(!model.actiondisplay && !model.entryscreen && !model.splash){
         if(e.getKeyCode()==KeyEvent.VK_ENTER){
            model.resetgame();
         }
     }
    }

    public void keyReleased(KeyEvent e)
    {   }

    public void keyTyped(KeyEvent e)
    {   }

    public void mousePressed(MouseEvent e)
    {   }

    public void mouseExited(MouseEvent e)
    {   }

    public void mouseEntered(MouseEvent e)
    {   }

    public void mouseReleased(MouseEvent e)
    {   }

    public void mouseClicked(MouseEvent e)
    {
        view.frame.requestFocusInWindow();
    }

    void update()
    {

    }
}
