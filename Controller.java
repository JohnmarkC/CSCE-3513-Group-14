import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.System.*;


class Controller implements KeyListener
{
    player_entry_view view;
    Model model;

    //Constructor
    Controller(Model m)
    {
        model = m;
    }

    void setView(player_entry_view v)
    {
        view = v;
    }

    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ENTER: // Enter key is pressed
                model.search();
                //Send user names and IDs to the database then prompt for equipment id
                break;
            case KeyEvent.VK_F5: //F5 key pressed
                model.start();            
                //begin the games
                break;
            case KeyEvent.VK_F12: // F12 key pressed
                model.clear();
                //clear all entries on the player entry screen
                break;
        }
    }

    public void keyReleased(KeyEvent e)
    {   }

    public void keyTyped(KeyEvent e)
    {   }

    void update()
    {

    }
}