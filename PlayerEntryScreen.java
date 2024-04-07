import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.Graphics;


class player_entry_view extends JPanel
{
    Model model;
    Controller controller;
    UDP udp = new UDP();
    
    Vector<String> game;
    String [] Red_team;
    String [] Green_team;
    JTextField RedTeam[];
    JTextField GreenTeam[];
    JFrame frame = new JFrame();
    int width = 1250;
    int height = 1250;
    JPanel actionScreen;

    //Contructor
    player_entry_view(Model m)
    {
        //Link up the controller
        controller = new Controller(m, this);
        model = m;
    

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
	this.addKeyListener(controller);
    this.addMouseListener(controller);

	//setting up callable for model interaction of database
	this.Red_team = new String[45];
        this.Green_team = new String[45];
        this.game = new Vector<String>();  
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
    private void startActionCountdownTimer(JLabel actionCountdownLabel, int m, int s) {

        AtomicInteger minutes = new AtomicInteger(m);
        AtomicInteger sec = new AtomicInteger(s);
        // Start countdown timer
        Timer timer = new Timer(1000, e -> {
            if (minutes.get() == 0 && sec.get() == 0) {
                ((Timer) e.getSource()).stop(); // Stop the timer when countdown reaches 0

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
                    actionCountdownLabel.setText(formattedTime);
                }
                else
                {
                    String formattedTime = String.format("%02d:%02d", minutes.get(), sec.get());
                    actionCountdownLabel.setText(formattedTime);
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
        JLabel j = new JLabel("Edit Current Game: Press Enter To Enter Player Information; Press F5 To Start The Game; Press F12 To Clear All Players");
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
            RedTeam[i].setBackground(Color.WHITE);
            Redpanel.add(RedTeam[i]);
            RedTeam[i].addKeyListener(controller);
            tp1.changeAlpha(0.5f);
            RedTeam[i].setBounds(Nx,y,Nwidth,Nheight);
            
            GreenTeam[i] = new JTextField(10);
            TextPrompt tp2 = new TextPrompt("Enter your name", GreenTeam[i], TextPrompt.Show.FOCUS_GAINED);
            GreenTeam[i].setBackground(Color.WHITE);
            Greenpanel.add(GreenTeam[i]);
            GreenTeam[i].addKeyListener(controller);
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
            RedTeam[i].addKeyListener(controller);
            tp1.changeAlpha(0.5f);
            RedTeam[i].setBounds(Ix,y,Nwidth/2,Nheight);

            GreenTeam[i] = new JTextField(10);
            TextPrompt tp2 = new TextPrompt("Enter player ID", GreenTeam[i], TextPrompt.Show.FOCUS_GAINED);
            GreenTeam[i].setBackground(Color.WHITE);
            Greenpanel.add(GreenTeam[i]);
            GreenTeam[i].addKeyListener(controller);
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
            RedTeam[i].addKeyListener(controller);
            tp1.changeAlpha(0.5f);
            RedTeam[i].setBounds(Eidx,y,Nwidth/2,Nheight);

            GreenTeam[i] = new JTextField(10);
            TextPrompt tp2 = new TextPrompt("Equipment ID", GreenTeam[i], TextPrompt.Show.FOCUS_GAINED);
            GreenTeam[i].setBackground(Color.WHITE);
            Greenpanel.add(GreenTeam[i]);
            GreenTeam[i].addKeyListener(controller);
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
    public void create_action_screen() {
        this.frame.getContentPane().removeAll();
        
        actionScreen = new JPanel();
        actionScreen.setLayout(null);
    
        actionScreen = new JPanel() {
            @Override
            protected void paintComponent(Graphics action) {
                super.paintComponent(action);
                action.setColor(new Color(0, 0, 0));
                action.fillRect(0, 0, getWidth(), getHeight());
                // Yellow Box
                action.setColor(Color.YELLOW);
                action.fillRect(100, 50, 10, 550);
                action.fillRect(1435, 50, 10, 550);
                action.fillRect(100, 50, 1335, 10);
                action.fillRect(100, 600, 1345, 5);
                action.fillRect(100, 550, 1345, 5);
                action.fillRect(100, 300, 1345, 5);
    
                // Blue Area
                action.setColor(Color.BLUE);
                action.fillRect(110, 305, 1325, 245);
                // Texts
                action.setColor(Color.WHITE);
                action.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                action.drawString("Red Team", 300, 100);
                action.drawString("Green Team", 1050, 100);
                action.setColor(Color.cyan);
                action.drawString("Current Game Action", 1000, 330);
                action.drawString("Current Game Scores", 1000, 40);
            }
        };
    
        this.frame.add(actionScreen);
        this.frame.repaint();
        this.frame.setVisible(true);
        
        load_players();
        create_timer_actionScreen();
    }
    
    public void load_players()
    {
    	ArrayList<String> redNames = new ArrayList<String>();
    	ArrayList<String> greenNames = new ArrayList<String>();
    	for(int i = 0; i < 15; i++)
    	{
		if(RedTeam[i].getText() != "")
		{
			redNames.add(RedTeam[i].getText());
		}
		if(GreenTeam[i].getText() != "")
		{
			greenNames.add(GreenTeam[i].getText());
		}
	}
    	JLabel[] RedPlayers = new JLabel[redNames.size()];
    	JLabel[] GreenPlayers = new JLabel[greenNames.size()];
    	int rx = 125;
    	int gx = 800;
    	int y = 100;
    	int offset = 20;
    	
	for(int i = 0; i < RedPlayers.length; i++)
	{
		RedPlayers[i] = new JLabel();
		RedPlayers[i].setText(redNames.get(i));
		RedPlayers[i].setForeground(Color.WHITE);
		RedPlayers[i].setFont(new Font("TimesRoman", Font.BOLD, 18));
		RedPlayers[i].setBounds(rx, y + (offset * i), 250, 20);
		actionScreen.add(RedPlayers[i]);
	}
	for(int i = 0; i < GreenPlayers.length; i++)
	{
		GreenPlayers[i] = new JLabel();
		GreenPlayers[i].setText(greenNames.get(i));
		GreenPlayers[i].setForeground(Color.WHITE);
		GreenPlayers[i].setFont(new Font("TimesRoman", Font.BOLD, 18));
		GreenPlayers[i].setBounds(gx, y + (offset * i), 250, 20);
		actionScreen.add(GreenPlayers[i]);
	}
	}
    
    public void create_timer_actionScreen() {

        //30s warning before the 6 minute game
        JLabel warningLabel = new JLabel("Get ready! Game starting in: ");
        warningLabel.setFont(new Font("TimesRoman", Font.BOLD, 35));
        warningLabel.setBounds(735, 525, 700, 100);
        warningLabel.setForeground(Color.WHITE);
        actionScreen.add(warningLabel);
    
        JLabel warningCountdownLabel = new JLabel("30");
        warningCountdownLabel.setFont(new Font("TimesRoman", Font.BOLD, 35));
        warningCountdownLabel.setBounds(1300, 525, 500, 100);
        warningCountdownLabel.setForeground(Color.WHITE);
        actionScreen.add(warningCountdownLabel);
    
        actionScreen.setVisible(true);
    
        // start countdown for 30 seconds
        startCountdownTimer(warningCountdownLabel, 0, 30, () -> {
            // After the 30-second warning, start the main game countdown
            actionScreen.remove(warningLabel);
            actionScreen.remove(warningCountdownLabel);
            actionScreen.repaint();
            // start 6 minute game countdown
            JLabel timeRemaining = new JLabel("Time Remaining:");
            timeRemaining.setFont(new Font("TimesRoman", Font.BOLD, 40));
            timeRemaining.setBounds(900, 80, 960, 1000);
            timeRemaining.setForeground(Color.WHITE);
            actionScreen.add(timeRemaining);

            JLabel actionCountdownLabel = new JLabel("6:00");
            actionCountdownLabel.setFont(new Font("TimesRoman", Font.BOLD, 40));
            actionCountdownLabel.setBounds(1250, 80, 1000, 1000);
            actionCountdownLabel.setForeground(Color.WHITE);
            actionScreen.add(actionCountdownLabel);
            startActionCountdownTimer(actionCountdownLabel, 6, 0);
            actionScreen.setVisible(true);
        });
    }

    private void startCountdownTimer(JLabel countdownLabel, int m, int s, Runnable callback) {
        AtomicInteger minutes = new AtomicInteger(m);
        AtomicInteger sec = new AtomicInteger(s);
    
        Timer timer = new Timer(1000, e -> {
            if (minutes.get() == 0 && sec.get() == 0) {
                ((Timer) e.getSource()).stop();
                callback.run();
            } else {
                if (sec.get() == 0) {
                    minutes.decrementAndGet();
                    sec.set(59);
                } else {
                    sec.decrementAndGet();
                }
    
                if (minutes.get() == 0) {
                    String formattedTime = String.format("%02d", sec.get());
                    countdownLabel.setText(formattedTime);
                } else {
                    String formattedTime = String.format("%02d:%02d", minutes.get(), sec.get());
                    countdownLabel.setText(formattedTime);
                }
            }
        });
    
        timer.start();
    }

    private void collect_entries(){
        game.clear();
        for(int i=0; i<RedTeam.length; i++){
        if(!RedTeam[i].getText().equals("")){
            Red_team[i]=RedTeam[i].getText();
        }
        else{
            Red_team[i]= " ";
        }
        if(!GreenTeam[i].getText().equals("")){
            Green_team[i]=GreenTeam[i].getText();
        }
        else{
            Green_team[i]= " ";
        }
        }
    }	
    //callable for id and codename
    public Vector<String> Entry(boolean EqID){
        collect_entries();
        if(EqID){
             for(int i =0; i<30; i++){
                 if(i<15){
                 game.add(Red_team[i+30]);
                 }
                 else{
                 game.add(Green_team[i+15]);
                 }
             }
             return game;
        }
        else{
         for(int i =0; i<60; i++){
             if(i<15){
                 game.add(Red_team[i+15]);
             }
             else if(i<30){
                 game.add(Green_team[i]);
             }
             else if(i<45){
                 game.add(Red_team[i-30]);
             }
             else{
                  game.add(Green_team[i-45]);
              }
         }
             //first half red, second half green 30 id's followed by 30 names 
             return game;
       }
        
    }
}
