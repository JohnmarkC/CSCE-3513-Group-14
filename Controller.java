import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

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
        try {
            File audioFile = new File("Track01.wav"); // Replace with your audio file path
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
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
        }, 14000); // 16 seconds in milliseconds
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
                //create the game
                //start the audio when F5 is pressed
                loadAndStartAudioAfterDelay(); 
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
