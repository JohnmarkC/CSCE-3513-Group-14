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
import java.awt.Image;

class player_entry_view extends JPanel
{
    Model model;
    Controller controller;
    
    String [] game;
    String [] Red_team;
    String [] Green_team;
    JFrame frame = new JFrame();
    int width = 1250;
    int height = 1250;

    //Contructor
    player_entry_view(Controller c, Model m)
    {
        //Link up the controller
        c.setView(this);
        model = m;
        controller = c;

        //create a JFrame on which to create the player entry screen
        frame.setTitle("Entry Terminal");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        frame.addKeyListener(controller);
        frame.setResizable(false);


        // send key events to the controller
	this.addKeyListener(c);

	//setting up callable for model interaction of database
	this.Red_team = new String[45];
        this.Green_team = new String[45];
        this.game = new String[60];  
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

    public void create_splash()
    {
        ImageIcon imageIcon = new ImageIcon("logo.jpg");
        Image originalImage = imageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(this.frame.getWidth(), this.frame.getHeight(), Image.SCALE_SMOOTH); 
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        this.frame.add(imageLabel);

        this.frame.setVisible(true);
        this.frame.remove(imageLabel);
    }

    public void create()
    {
       //create a panel for the green team and red team
       this.frame.repaint();
        JPanel Redpanel = new JPanel();
        JPanel Greenpanel = new JPanel();
        Redpanel.setBackground(new Color(122, 37, 34));
        Greenpanel.setBackground(new Color(34, 122, 66));
        frame.add(Redpanel);
        frame.add(Greenpanel);
        Redpanel.setBounds(25,35,width/2-75,height/2);
        Greenpanel.setBounds(width/2+25, 35, width/2-75,height/2);


        //create the label telling the user to edit the current game
        JLabel j = new JLabel("Edit Current Game : Press Enter to enter player information; Press F5 to start the game; Press F12 to clear all players");
        j.setForeground(Color.WHITE);
        j.setHorizontalAlignment(JLabel.CENTER);
        j.setVerticalAlignment(JLabel.TOP);
        j.setFont(new Font("calibri", Font.BOLD, 20));
        this.frame.add(j);

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

        //headers for each text field column

        JLabel redHeader = new JLabel("Player Name");
        redHeader.setHorizontalAlignment(JLabel.CENTER);
        Redpanel.add(redHeader);
    
        JLabel redHeader2 = new JLabel("Player ID");
        redHeader2.setHorizontalAlignment(JLabel.CENTER);
        Redpanel.add(redHeader2);
    
        JLabel redHeader3 = new JLabel("Equipment ID");
        redHeader3.setHorizontalAlignment(JLabel.CENTER);
        Redpanel.add(redHeader3);
    
        JLabel greenHeader = new JLabel("Player Name");
        greenHeader.setHorizontalAlignment(JLabel.CENTER);
        Greenpanel.add(greenHeader);
    
        JLabel greenHeader2 = new JLabel("Player ID");
        greenHeader2.setHorizontalAlignment(JLabel.CENTER);
        Greenpanel.add(greenHeader2);
    
        JLabel greenHeader3 = new JLabel("Equipment ID");
        greenHeader3.setHorizontalAlignment(JLabel.CENTER);
        Greenpanel.add(greenHeader3);
    
        this.frame.setVisible(true);

        //create the text fields for player entry

 	int Nx, y, Nwidth, Nheight, Ix;
    JTextField RedTeam[];
    JTextField GreenTeam[];
    RedTeam = new JTextField[45];
    GreenTeam = new JTextField[45];
        Nx = 25;
        y = 50;
        Nwidth = 225;
        Nheight = 25;
        for(int i = 0; i<15; i++){
            RedTeam[i] = new JTextField(10);
            TextPrompt tp1 = new TextPrompt("Enter your name", RedTeam[i], TextPrompt.Show.FOCUS_GAINED);
            RedTeam[i].setBackground(Color.WHITE);
            Redpanel.add(RedTeam[i]);
            tp1.changeAlpha(0.5f);
            RedTeam[i].setBounds(Nx,y,Nwidth,Nheight);
            
            GreenTeam[i] = new JTextField(10);
            TextPrompt tp2 = new TextPrompt("Enter your name", GreenTeam[i], TextPrompt.Show.FOCUS_GAINED);
            GreenTeam[i].setBackground(Color.WHITE);
            Greenpanel.add(GreenTeam[i]);
            tp2.changeAlpha(0.5f);
            GreenTeam[i].setBounds(Nx,y,Nwidth,Nheight);
            
            //old text fields
            //RedTeam[i] = new JTextField("Enter your name");
            //RedTeam[i].setBackground(Color.WHITE);
            //Redpanel.add(RedTeam[i]);
            //RedTeam[i].setBounds(Nx,y,Nwidth,Nheight);
            // GreenTeam[i] = new JTextField("Enter your name");
            // Greenpanel.add(GreenTeam[i]);
            // GreenTeam[i].setBounds(Nx,y,Nwidth,Nheight);
            y += 35;
        }
        y = 50;
        Ix = 275;
        for(int i = 15; i<30; i++){
            RedTeam[i] = new JTextField(10);
            TextPrompt tp1 = new TextPrompt("Enter player ID", RedTeam[i], TextPrompt.Show.FOCUS_GAINED);
            RedTeam[i].setBackground(Color.WHITE);
            Redpanel.add(RedTeam[i]);
            tp1.changeAlpha(0.5f);
            RedTeam[i].setBounds(Ix,y,Nwidth/2,Nheight);

            GreenTeam[i] = new JTextField(10);
            TextPrompt tp2 = new TextPrompt("Enter player ID", GreenTeam[i], TextPrompt.Show.FOCUS_GAINED);
            GreenTeam[i].setBackground(Color.WHITE);
            Greenpanel.add(GreenTeam[i]);
            tp2.changeAlpha(0.5f);
            GreenTeam[i].setBounds(Ix,y,Nwidth/2,Nheight);

            // RedTeam[i] = new JTextField("Enter your id");
            // RedTeam[i].setBackground(Color.WHITE);
            // Redpanel.add(RedTeam[i]);
            // RedTeam[i].setBounds(Ix,y,Nwidth/2,Nheight);
            // GreenTeam[i] = new JTextField("Enter your id");
            // Greenpanel.add(GreenTeam[i]);
            // GreenTeam[i].setBounds(Ix,y,Nwidth/2,Nheight);
            y += 35;
        }
        y = 50;
        int Eidx = Nwidth/2+Ix+25;
        for(int i = 30; i<45; i++){
            RedTeam[i] = new JTextField(10);
            TextPrompt tp1 = new TextPrompt("Equipment ID", RedTeam[i], TextPrompt.Show.FOCUS_GAINED);
            RedTeam[i].setBackground(Color.WHITE);
            Redpanel.add(RedTeam[i]);
            tp1.changeAlpha(0.5f);
            RedTeam[i].setBounds(Eidx,y,Nwidth/2,Nheight);

            GreenTeam[i] = new JTextField(10);
            TextPrompt tp2 = new TextPrompt("Equipment ID", GreenTeam[i], TextPrompt.Show.FOCUS_GAINED);
            GreenTeam[i].setBackground(Color.WHITE);
            Greenpanel.add(GreenTeam[i]);
            tp2.changeAlpha(0.5f);
            GreenTeam[i].setBounds(Eidx,y,Nwidth/2,Nheight);
            
            // RedTeam[i] = new JTextField("Enter Equipment id");
            // RedTeam[i].setBackground(Color.WHITE);
            // Redpanel.add(RedTeam[i]);
            // RedTeam[i].setBounds(Eidx,y,Nwidth/2,Nheight);
            // GreenTeam[i] = new JTextField("Enter Equipment id");
            // Greenpanel.add(GreenTeam[i]);
            // GreenTeam[i].setBounds(Eidx,y,Nwidth/2,Nheight);
            y += 35;
        }
    }

	//callable for id and codename
public String[] Entry(){
       
        for(int i =0; i<60; i++){
            if(i<15){
                game[i]=Red_team[i+15];
            }
            else if(i<30){
                game[i]=Green_team[i];
            }
            else if(i<45){
                game[i]=Red_team[i-30];
            }
            else{
                game[i]=Green_team[i-45];
            }
        }
        return game;
        
    }
}
