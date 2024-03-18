import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import static java.lang.System.*;


class Controller implements KeyListener, MouseListener
{
    player_entry_view view;
    Model model;
    SupaBaseIntegration data;

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

    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ENTER: // Enter key is pressed
                model.search();
                break;
            case KeyEvent.VK_F5: //F5 key pressed
                model.start();
                //create the game
                break;
            case KeyEvent.VK_F12: // F12 key pressed
                model.clear();
                break;
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
