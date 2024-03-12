import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;


class player_entry_view extends JPanel
{
    Model model;
    Controller controller;
    UDP udp = new UDP();
    
    String [] game;
    String [] Red_team;
    String [] Green_team;
    JTextField RedTeam[];
    JTextField GreenTeam[];
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
        frame.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
        frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
        frame.addKeyListener(controller);
        frame.addMouseListener(controller);
        frame.setResizable(false);

        // send key events to the controller
	this.addKeyListener(c);
    this.addMouseListener(c);

	//setting up callable for model interaction of database
	this.Red_team = new String[45];
        this.Green_team = new String[45];
        this.game = new String[60];  
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

    private void startCountdownTimer(JLabel countdownLabel, int m, int s) {

        AtomicInteger minutes = new AtomicInteger(m);
        AtomicInteger sec = new AtomicInteger(s);
        // Start countdown timer
        Timer timer = new Timer(1000, e -> {
            if (minutes.get() == 0 && sec.get() == 0) {
                ((Timer) e.getSource()).stop(); // Stop the timer when countdown reaches 0
                create_action_screen();
            } else {
                if (sec.get() == 0) {
                    minutes.decrementAndGet();
                    sec.set(59);
                } else {
                    sec.decrementAndGet();
                }

                // Update countdown label text
                if(minutes.get() == 0)
                {
                    String formattedTime = String.format("%02d", sec.get());
                    countdownLabel.setText(formattedTime);
                }
                else
                {
                    String formattedTime = String.format("%02d:%02d", minutes.get(), sec.get());
                    countdownLabel.setText(formattedTime);
                }

                // Show 30-second warning
                if (minutes.get() == 0 && sec.get() == 30) {
                    JOptionPane.showMessageDialog(null, "30-Second Warning!");
                }
            }
        });

        timer.start(); // Start the timer
    }


    public void create()
    {
       //create a panel for the green team and red team
       this.frame.repaint();
        JPanel Redpanel = new JPanel();
        JPanel Greenpanel = new JPanel();
        Redpanel.setLayout(null);
        Greenpanel.setLayout(null);
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
        j.setFont(new Font("calibri", Font.BOLD, 18));
        this.frame.add(j);

        //create the red team and green team labels
        JLabel r = new JLabel("Red Team");
        JLabel g = new JLabel("Green Team");
        r.setForeground(Color.WHITE);
        g.setForeground(Color.WHITE);
        r.setFont(new Font("calibri", Font.BOLD, 25));
        g.setFont(new Font("calibri", Font.BOLD, 25));
        r.setVerticalAlignment(JLabel.TOP);
        g.setVerticalAlignment(JLabel.TOP);
        r.setHorizontalAlignment(JLabel.CENTER);
        g.setHorizontalAlignment(JLabel.CENTER);

        JPanel redTitle = new JPanel();
        redTitle.setOpaque(false);
        redTitle.setBounds(0, 5, width/2 -75, height/2);
        redTitle.add(r);
        Redpanel.add(redTitle);

        JPanel greenTitle = new JPanel();
        greenTitle.setOpaque(false);
        greenTitle.setBounds(0, 5, width/2 -75, height/2);
        greenTitle.add(g);
        Greenpanel.add(greenTitle);
        //create the labels indicating what each column of text fields represents

        JLabel redHeader = new JLabel("Player Name");
        redHeader.setForeground(Color.WHITE);
        redHeader.setFont(new Font("calibri", Font.BOLD, 15));
        redHeader.setHorizontalAlignment(JLabel.CENTER);
        JPanel transparent_panel1 = new JPanel();
        transparent_panel1.setOpaque(false);
        transparent_panel1.setBounds(50+(225/2), 40, 225, 25);
        transparent_panel1.add(redHeader);
        Redpanel.add(transparent_panel1);

        JLabel redHeader2 = new JLabel("Player ID");
        redHeader2.setForeground(Color.WHITE);
        redHeader2.setFont(new Font("calibri", Font.BOLD, 15));
        redHeader2.setHorizontalAlignment(JLabel.CENTER);
        JPanel transparent_panel2 = new JPanel();
        transparent_panel2.setOpaque(false);
        transparent_panel2.setBounds(25, 40, 225/2, 25);
        transparent_panel2.add(redHeader2);
        Redpanel.add(transparent_panel2);
    
        JLabel redHeader3 = new JLabel("Equipment ID");
        redHeader3.setForeground(Color.WHITE);
        redHeader3.setFont(new Font("calibri", Font.BOLD, 15));
        redHeader3.setHorizontalAlignment(JLabel.CENTER);
        JPanel transparent_panel3 = new JPanel();
        transparent_panel3.setOpaque(false);
        transparent_panel3.setBounds((225/2)+300, 40, 225/2, 25);
        transparent_panel3.add(redHeader3);
        Redpanel.add(transparent_panel3);

        JLabel greenHeader = new JLabel("Player Name");
        greenHeader.setForeground(Color.WHITE);
        greenHeader.setFont(new Font("calibri", Font.BOLD, 15));
        greenHeader.setHorizontalAlignment(JLabel.CENTER);
        JPanel transparent_panel4 = new JPanel();
        transparent_panel4.setOpaque(false);
        transparent_panel4.setBounds(50+(225/2), 40, 225, 25);
        transparent_panel4.add(greenHeader);
        Greenpanel.add(transparent_panel4);

        JLabel greenHeader2 = new JLabel("Player ID");
        greenHeader2.setForeground(Color.WHITE);
        greenHeader2.setFont(new Font("calibri", Font.BOLD, 15));
        greenHeader2.setHorizontalAlignment(JLabel.CENTER);
        JPanel transparent_panel5 = new JPanel();
        transparent_panel5.setOpaque(false);
        transparent_panel5.setBounds(25, 40, 225/2, 25);
        transparent_panel5.add(greenHeader2);
        Greenpanel.add(transparent_panel5);
    
        JLabel GreenHeader3 = new JLabel("Equipment ID");
        GreenHeader3.setForeground(Color.WHITE);
        GreenHeader3.setFont(new Font("calibri", Font.BOLD, 15));
        GreenHeader3.setHorizontalAlignment(JLabel.CENTER);
        JPanel transparent_panel6 = new JPanel();
        transparent_panel6.setOpaque(false);
        transparent_panel6.setBounds((225/2)+300, 40, 225/2, 25);
        transparent_panel6.add(GreenHeader3);
        Greenpanel.add(transparent_panel6);
    
        
    
        this.frame.setVisible(true);

        //create the text fields for player entry

 	int Nx, y, Nwidth, Nheight, Ix;
    RedTeam = new JTextField[45];
    GreenTeam = new JTextField[45];
        Ix = 25;
        y = 75;
        Nwidth = 225;
        Nheight = 25;
        Nx = 50 + (Nwidth/2); 
        for(int i = 0; i<15; i++){
            RedTeam[i] = new JTextField(10);
            TextPrompt tp1 = new TextPrompt("Enter your name", RedTeam[i], TextPrompt.Show.FOCUS_GAINED);
            udp.sendData(RedTeam[i].getText());
            RedTeam[i].setBackground(Color.WHITE);
            Redpanel.add(RedTeam[i]);
            tp1.changeAlpha(0.5f);
            RedTeam[i].setBounds(Nx,y,Nwidth,Nheight);
            
            GreenTeam[i] = new JTextField(10);
            TextPrompt tp2 = new TextPrompt("Enter your name", GreenTeam[i], TextPrompt.Show.FOCUS_GAINED);
            udp.sendData(GreenTeam[i].getText());
            GreenTeam[i].setBackground(Color.WHITE);
            Greenpanel.add(GreenTeam[i]);
            tp2.changeAlpha(0.5f);
            GreenTeam[i].setBounds(Nx,y,Nwidth,Nheight);
            y += 35;
        }
        y = 75;
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
            y += 35;
        }
        y = 75;
        int Eidx = Nx + Nwidth + 25;
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
            y += 35;
        }


        // Countdown Timer Label

        this.frame.setVisible(true);       
    }

    public void create_timer()
    {
        //countdown timer label
        JLabel countdownLabel = new JLabel("30");
        countdownLabel.setForeground(Color.WHITE);
        countdownLabel.setFont(new Font("calibri", Font.BOLD, 400));
        countdownLabel.setHorizontalAlignment(JLabel.CENTER);
        countdownLabel.setVerticalAlignment(JLabel.CENTER);
        countdownLabel.setBounds(0, 100, width, 50);
        this.frame.add(countdownLabel);

        this.frame.setVisible(true);

        //start the timer
        startCountdownTimer(countdownLabel, 0, 30);
    }
    public void create_action_screen()
    {
        frame.getContentPane().removeAll();
        frame.repaint();
        JLabel action = new JLabel("Action screen goes here");
        action.setFont(new Font("calibri", Font.BOLD, 25));
        action.setForeground(Color.WHITE);
        action.setHorizontalAlignment(JLabel.CENTER);
        this.frame.add(action);
        frame.setVisible(true);
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
