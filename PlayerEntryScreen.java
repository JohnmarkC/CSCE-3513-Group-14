import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Toolkit;


class player_entry_view extends JPanel
{
    Model model;
    Controller controller;

    //Contructor
    player_entry_view(Controller c, Model m)
    {
        //Link up the controller
        c.setView(this);
        model = m;
        controller = c;

        // send key events to the controller
	this.addKeyListener(c);
    }
    public static void main(String args[])
    {
        Model model = new Model();
        Controller controller = new Controller(model);
        player_entry_view p = new player_entry_view(controller, model);
        p.create();
        while(true)
		{
			controller.update();
			model.update();
			//view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for a brief moment
			try
			{
				Thread.sleep(25);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
    }

    public void create()
    {
	int width, height;
	    width = 1250;
	    height = 1250;
        //create a JFrame on which to create the player entry screen
        JFrame frame = new JFrame();
        frame.setTitle("Entry Terminal");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(width, height);
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        frame.addKeyListener(controller);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

       //create a panel for the green team and red team
        JPanel Redpanel = new JPanel();
        JPanel Greenpanel = new JPanel();
        Redpanel.setBackground(new Color(122, 37, 34));
        Greenpanel.setBackground(new Color(34, 122, 66));
        frame.add(Redpanel);
        frame.add(Greenpanel);
        Redpanel.setBounds(25,35,width/2-75,height/2);
        Greenpanel.setBounds(width/2+25, 35, width/2-75,height/2);
        


        //create the label telling the user to edit the current game
        JLabel j = new JLabel("Edit Current Game");
        j.setForeground(Color.WHITE);
        j.setHorizontalAlignment(JLabel.CENTER);
        j.setVerticalAlignment(JLabel.TOP);
        j.setFont(new Font("calibri", Font.BOLD, 20));
        frame.add(j);

        //create the red team and green team labels
        JLabel r = new JLabel("Red Team");
        JLabel g = new JLabel("Green Team");
        r.setForeground(Color.WHITE);
        g.setForeground(Color.WHITE);
        r.setFont(new Font("calibri", Font.BOLD, 20));
        g.setFont(new Font("calibri", Font.BOLD, 20));
        r.setVerticalAlignment(JLabel.TOP);
        g.setVerticalAlignment(JLabel.TOP);
        r.setHorizontalAlignment(JLabel.CENTER);
        g.setHorizontalAlignment(JLabel.CENTER);
        Redpanel.add(r);
        Greenpanel.add(g);

        //create the text fields for player entry

 	int Nx, y, Nwidth, Nheight, Ix;
    JTextField RedTeam[];
    JTextField GreenTeam[];
    RedTeam = new JTextField[45];
    GreenTeam = new JTextField[45];
        Nx = 25;
        y = 50;
        Nwidth = 225;
        Nheight = 20;
        for(int i = 0; i<15; i++){
            RedTeam[i] = new JTextField("Enter your name");
            RedTeam[i].setBackground(Color.WHITE);
            Redpanel.add(RedTeam[i]);
            RedTeam[i].setBounds(Nx,y,Nwidth,Nheight);
            GreenTeam[i] = new JTextField("Enter your name");
            Greenpanel.add(GreenTeam[i]);
            GreenTeam[i].setBounds(Nx,y,Nwidth,Nheight);
            y += 35;
        }
        y = 50;
        Ix = 275;
        for(int i = 15; i<30; i++){
            RedTeam[i] = new JTextField("Enter your id");
            RedTeam[i].setBackground(Color.WHITE);
            Redpanel.add(RedTeam[i]);
            RedTeam[i].setBounds(Ix,y,Nwidth/2,Nheight);
            GreenTeam[i] = new JTextField("Enter your id");
            Greenpanel.add(GreenTeam[i]);
            GreenTeam[i].setBounds(Ix,y,Nwidth/2,Nheight);
            y += 35;
        }
        y = 50;
        int Eidx = Nwidth/2+Ix+25;
        for(int i = 30; i<45; i++){
            RedTeam[i] = new JTextField("Enter Equipment id");
            RedTeam[i].setBackground(Color.WHITE);
            Redpanel.add(RedTeam[i]);
            RedTeam[i].setBounds(Eidx,y,Nwidth/2,Nheight);
            GreenTeam[i] = new JTextField("Enter Equipment id");
            Greenpanel.add(GreenTeam[i]);
            GreenTeam[i].setBounds(Eidx,y,Nwidth/2,Nheight);
            y += 35;
        }
    }
}
