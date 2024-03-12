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
    Controller(Model m)
    {
        model = m;
        data = new SupaBaseIntegration();
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
                int id = 0;
                String name = "";
                String result = "";
                boolean changename = false;
                for(int i = 0; i < 30; i++)
                {
                    name = view.RedTeam[i].getText();
                    id = Integer.parseInt(view.RedTeam[i+15].getText());
                    result = data.playerData(id, name, false);
                    view.RedTeam[i].setText(result);
                }
                for(int i = 0; i < 30; i++)
                {
                    name = view.GreenTeam[i].getText();
                    id = Integer.parseInt(view.RedTeam[i+15].getText());
                    result = data.playerData(id, name, false);
                    view.RedTeam[i].setText(result);
                }
                break;
            case KeyEvent.VK_F5: //F5 key pressed
                view.frame.getContentPane().removeAll();
                view.frame.repaint();
                view.create_timer();
                //create the game
                break;
            case KeyEvent.VK_F12: // F12 key pressed
                for(int i = 0; i < 45; i++)
                {
                    view.RedTeam[i].setText("");
                    view.GreenTeam[i].setText("");
                }
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
