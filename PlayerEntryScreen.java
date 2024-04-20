import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Map.Entry;
import java.util.List;


class player_entry_view extends JPanel
{
    Model model;
    Controller controller;
    UDP udp = new UDP();
    
    Vector<String> game;
    String [] Red_team;
    String [] Green_team;
    boolean players_loaded;
    HashMap<String, Integer> GreenScores, RedScores;
    HashMap<Integer,String> Eq2nameGreen, Eq2nameRed;
    HashMap<String, Integer> Bkeep;
    ArrayList<String> redNames = new ArrayList<String>();
    ArrayList<String> greenNames = new ArrayList<String>();
	Boolean[] nameChange;

    JTextField RedTeam[], RedTeamEqid[];
    JTextField GreenTeam[], GreenTeamEqid[];
    JCheckBox RedNameChange[], GreenNameChange[];

    
    JFrame frame = new JFrame();
    int width = 1250;
    int height = 1250;
    JPanel actionScreen;
    JTextArea eventTextArea;
    List<String> events;
    JPanel warning, timer;
    JPanel actionRed[], actionGreen[], actiondisplay[];
    JLabel timeRemaining;
    JLabel warningLabel;
    JLabel actionCountdownLabel;
    JLabel warningCountdownLabel;
    JPanel game_over;
    JLabel[] RedPlayers, GreenPlayers;


    //Contructor
    player_entry_view(Model m)
    {
        //Link up the controller
        GreenScores = new HashMap<String, Integer>();
        RedScores = new HashMap<String, Integer>();
        Eq2nameGreen= new HashMap<Integer, String>();
        Eq2nameRed = new HashMap<Integer, String>();
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
        Bkeep = new HashMap<String, Integer>();
        nameChange = new Boolean[30];
        for(int i =0; i<nameChange.length;i++){
            boolean c = false;
            nameChange[i]= c;
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

    private void startActionCountdownTimer(int m, int s) {
        actionCountdownLabel.setBounds(1250, 80, 1000, 1000);
        AtomicInteger minutes = new AtomicInteger(m);
        AtomicInteger sec = new AtomicInteger(s);
        // Start countdown timer
        Timer timer = new Timer(1000, e -> {
            if (minutes.get() == 0 && sec.get() == 0) {
                ((Timer) e.getSource()).stop(); // Stop the timer when countdown reaches 0
		        UDP.gameState(221);
                gameOver();
                model.actiondisplay = false;

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
                    actionScreen.setVisible(true);
                }
                else
                {
                    String formattedTime = String.format("%02d:%02d", minutes.get(), sec.get());
                    actionCountdownLabel.setText(formattedTime);
                    actionScreen.setVisible(true);
                }
            }
        });

        timer.start(); // Start the timer
    }

    public void create()
    {   
       //create a panel for the green team and red team
       this.frame.repaint();
       this.frame.getContentPane().removeAll();
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

        //create the label to tell how to change name
        JLabel b = new JLabel("Glide mouse of left side of ID boxes to show Name Change check boxes. Checked = Name changing, Blank = Search.");
        b.setForeground(Color.WHITE);
        b.setHorizontalAlignment(JLabel.CENTER);
        b.setVerticalAlignment(JLabel.BOTTOM);
        b.setFont(new Font("calibri", Font.BOLD, 18));
        
        frame.setLayout(new BorderLayout());
        frame.add(j, BorderLayout.NORTH);
        frame.add(b, BorderLayout.SOUTH);

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
    RedTeam = new JTextField[30];
    GreenTeam = new JTextField[30];
    RedTeamEqid = new JTextField[15];
    GreenTeamEqid = new JTextField[15];
    GreenNameChange = new JCheckBox[15];
    RedNameChange = new JCheckBox[15];
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
            RedNameChange[i]= new JCheckBox();
            final int check = i;
            RedNameChange[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cb = (JCheckBox) e.getSource();
                    boolean isSelected = cb.isSelected();  
                    nameChange[check]=isSelected;
                }
            });
            RedNameChange[i].setBounds(Ix-25, y,25, Nheight);
            RedNameChange[i].setBackground(Color.RED);
            RedNameChange[i].setForeground((Color.WHITE));
            Redpanel.add(RedNameChange[i]);
            
            GreenTeam[i] = new JTextField(10);
            TextPrompt tp2 = new TextPrompt("Enter your name", GreenTeam[i], TextPrompt.Show.FOCUS_GAINED);
            GreenTeam[i].setBackground(Color.WHITE);
            Greenpanel.add(GreenTeam[i]);
            GreenTeam[i].addKeyListener(controller);
            tp2.changeAlpha(0.5f);
            GreenTeam[i].setBounds(Nx,y,Nwidth,Nheight);
            GreenNameChange[i]= new JCheckBox();
            GreenNameChange[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cb = (JCheckBox) e.getSource();
                    boolean isSelected = cb.isSelected(); 
                    nameChange[check+15]=isSelected;
                }
            });
            GreenNameChange[i].setBounds(Ix-25, y,25, Nheight);
        
            GreenNameChange[i].setBackground(Color.GREEN);
            GreenNameChange[i].setForeground((Color.WHITE));
            Greenpanel.add(GreenNameChange[i]);
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
        for(int i = 0; i<15; i++){
            RedTeamEqid[i] = new JTextField(10);
            TextPrompt tp1 = new TextPrompt("Equipment ID", RedTeamEqid[i], TextPrompt.Show.FOCUS_GAINED);
            RedTeamEqid[i].setBackground(Color.WHITE);
            Redpanel.add(RedTeamEqid[i]);
            RedTeamEqid[i].addKeyListener(controller);
            tp1.changeAlpha(0.5f);
            RedTeamEqid[i].setBounds(Eidx,y,Nwidth/2,Nheight);

            GreenTeamEqid[i] = new JTextField(10);
            TextPrompt tp2 = new TextPrompt("Equipment ID", GreenTeamEqid[i], TextPrompt.Show.FOCUS_GAINED);
            GreenTeamEqid[i].setBackground(Color.WHITE);
            Greenpanel.add(GreenTeamEqid[i]);
            GreenTeamEqid[i].addKeyListener(controller);
            tp2.changeAlpha(0.5f);
            GreenTeamEqid[i].setBounds(Eidx,y,Nwidth/2,Nheight);
            y += 35;
        }


        // Countdown Timer Label

        this.frame.setVisible(true);       
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
        warning = new JPanel();
        warning.setBounds(250, 0, 700, 50);
        warning.setBackground(Color.BLACK);
        warning.setVisible(false);
        frame.add(warning);
        timer = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.YELLOW);
                g.fillRect(-10,-5, width, 10);
             
            }
        };
        timer.setBounds(900, 550, 500, 50);
        timer.setBackground(null);
        timer.setVisible(false);
        frame.add(timer);
        actionScreen.setVisible(true);
       
        eventDisplayPanel();
       
        load_players();
        
        create_timer_actionScreen();
        for(int i =0; i<actionRed.length; i++){
            frame.add(actionRed[i]);
        }
        for(int i =0; i<actionGreen.length; i++){
            frame.add(actionGreen[i]);
        }
       
        frame.add(actionScreen);
        frame.repaint();

    }
    
    public void load_players()
    {
    	for(int i = 0; i < 15; i++)
    	{
		if(!RedTeam[i].getText().isBlank())
		{
			redNames.add(RedTeam[i].getText());
            RedScores.put(RedTeam[i].getText(),0);
            Eq2nameRed.put(Integer.parseInt(RedTeamEqid[i].getText()),RedTeam[i].getText());
            Bkeep.put(RedTeam[i].getText(),0);
		}
		if(!GreenTeam[i].getText().isBlank())
		{
			greenNames.add(GreenTeam[i].getText());
            GreenScores.put(GreenTeam[i].getText(),0);
            Eq2nameGreen.put(Integer.parseInt(GreenTeamEqid[i].getText()),GreenTeam[i].getText());
            Bkeep.put(GreenTeam[i].getText(),0);
		}
	}
        actionRed = new JPanel[redNames.size()];
        actionGreen = new JPanel[greenNames.size()];
    	this.RedPlayers = new JLabel[redNames.size()];
    	this.GreenPlayers = new JLabel[greenNames.size()];
    	int rx = 250+20;
    	int gx = 1000+20;
    	int y = 100;
    	int offset = 20;
    	
	for(int i = 0; i < RedPlayers.length; i++)
	{
        actionRed[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionRed[i].setBounds(rx,y,200,30);
        actionRed[i].setBackground(Color.BLACK);
		if(i>7){
            if(i==8){
                y=100;
            }
		    RedPlayers[i] = new JLabel();
		    RedPlayers[i].setText(redNames.get(i)+"  "+ RedScores.get(redNames.get(i)));
		    RedPlayers[i].setForeground(Color.WHITE);
		    RedPlayers[i].setFont(new Font("TimesRoman", Font.BOLD, 15));
		    RedPlayers[i].setBounds(0, 0, 200, 25);
            RedPlayers[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            actionRed[i].setBounds(rx+200,y,200,30);
        }
        else{
            RedPlayers[i] = new JLabel();
		    RedPlayers[i].setText(redNames.get(i)+"  "+ RedScores.get(redNames.get(i)));
		    RedPlayers[i].setForeground(Color.WHITE);
		    RedPlayers[i].setFont(new Font("TimesRoman", Font.BOLD, 15));
            RedPlayers[i].setAlignmentX(Component.LEFT_ALIGNMENT);
		    RedPlayers[i].setBounds(0, 0, 200, 25);
        }
		actionRed[i].add(RedPlayers[i]);
        actionRed[i].setVisible(true);
        actionRed[i].repaint();
        y=y+offset;
	}
    y =100;
	for(int i = 0; i < GreenPlayers.length; i++)
	{
        actionGreen[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionGreen[i].setBounds(gx,y,200,30);
        actionGreen[i].setBackground(Color.BLACK);
        if(i>7){
            if(i==8){
                y=100;
            }
		    GreenPlayers[i] = new JLabel();
		    GreenPlayers[i].setText(greenNames.get(i)+"  "+ GreenScores.get(greenNames.get(i)));
		    GreenPlayers[i].setForeground(Color.WHITE);
		    GreenPlayers[i].setFont(new Font("TimesRoman", Font.BOLD, 15));
		    GreenPlayers[i].setBounds(0, 0, 50, 25);
            GreenPlayers[i].setAlignmentX(Component.LEFT_ALIGNMENT);
            actionGreen[i].setBounds(gx+200,y,200,30);

        }
        else{
            GreenPlayers[i] = new JLabel();
		    GreenPlayers[i].setText(greenNames.get(i)+"  "+ GreenScores.get(greenNames.get(i)));
            GreenPlayers[i].setForeground(Color.WHITE);
		    GreenPlayers[i].setFont(new Font("TimesRoman", Font.BOLD, 15));
            GreenPlayers[i].setAlignmentX(Component.LEFT_ALIGNMENT);
		    GreenPlayers[i].setBounds(0, 0, 200, 25);
        }
        actionGreen[i].add(GreenPlayers[i]);
        actionGreen[i].setVisible(true);
        actionGreen[i].repaint();
        y=y+offset;

	}
    players_loaded = true;


	}
	
    public void sort_players()
    {
        for(int i = 0; i < redNames.size() -1; i++)
        {
            for(int j = 0; j < redNames.size() -i -1; j++)
            {
                if (RedScores.get(redNames.get(j)) < RedScores.get(redNames.get(j + 1)))
                {
                    String temp = redNames.get(j);
                    redNames.set(j, redNames.get(j + 1));
                    redNames.set(j + 1, temp);
                }
            }
        }
        for(int i = 0; i < greenNames.size() -1; i++)
        {
            for(int j = 0; j < greenNames.size() -i -1; j++)
            {
                if (GreenScores.get(greenNames.get(j)) > GreenScores.get(greenNames.get(j + 1)))
                {
                    String temp = greenNames.get(j);
                    greenNames.set(j, greenNames.get(j + 1));
                    greenNames.set(j + 1, temp);
                }
            }
        }
        draw_players();

    }

	
    public void draw_players()
    {
      
        for(int i = 0; i < RedPlayers.length; i++)
        {
            RedPlayers[i].setText(redNames.get(i)+"  "+ RedScores.get(redNames.get(i)));
		    RedPlayers[i].setForeground(Color.WHITE);
		    RedPlayers[i].setFont(new Font("TimesRoman", Font.BOLD, 15));
            RedPlayers[i].setAlignmentX(Component.LEFT_ALIGNMENT);
		    RedPlayers[i].setBounds(0, 0, 200, 25);
            
        }
      
        for(int i = 0; i < GreenPlayers.length; i++)
        {
            GreenPlayers[i].setText(greenNames.get(i)+"  "+ GreenScores.get(greenNames.get(i)));
		    GreenPlayers[i].setForeground(Color.WHITE);
		    GreenPlayers[i].setFont(new Font("TimesRoman", Font.BOLD, 15));
            GreenPlayers[i].setAlignmentX(Component.LEFT_ALIGNMENT);
		    GreenPlayers[i].setBounds(0, 0, 200, 25);
              
        }
    }
	public void create_timer_actionScreen() {

        //30s warning before the 6 minute game
        warningLabel = new JLabel("Get ready! Game starting in: ");
        warningLabel.setFont(new Font("TimesRoman", Font.BOLD, 35));
        warningLabel.setBounds(0, 0, 700, 100);
        warningLabel.setForeground(Color.WHITE);
        warning.add(warningLabel);
    
        warningCountdownLabel = new JLabel("30");
        warningCountdownLabel.setFont(new Font("TimesRoman", Font.BOLD, 35));
        warningCountdownLabel.setBounds(365, 0, 500, 100);
        warningCountdownLabel.setForeground(Color.WHITE);
        warning.add(warningCountdownLabel);
        warning.setVisible(true);
        actionScreen.setVisible(true);
        actionScreen.repaint();
        
    
        // start countdown for 30 seconds
          startCountdownTimer( 0, 30, () -> {
            // After the 30-second warning, start the main game countdown
            warning.remove(warningLabel);
            warning.remove(warningCountdownLabel);
            warning.repaint();
            frame.repaint();
            
            timer.setVisible(true);
            
            // start 6 minute game countdown
            timeRemaining = new JLabel("Time Remaining:");
            timeRemaining.setFont(new Font("TimesRoman", Font.BOLD, 40));
            timeRemaining.setBounds(0, -20, 960, 1000);
            timeRemaining.setForeground(Color.WHITE);
            timer.add(timeRemaining);

            actionCountdownLabel = new JLabel("6:00");
            actionCountdownLabel.setFont(new Font("TimesRoman", Font.BOLD, 35));
            actionCountdownLabel.setBounds(0, -20, 1000, 1000);
            actionCountdownLabel.setForeground(Color.WHITE);
            timer.add(actionCountdownLabel);
            timer.revalidate();
            timer.repaint();

            startActionCountdownTimer( 6, 0);
            actionScreen.setVisible(true);
        });
    }

    private void startCountdownTimer( int m, int s, Runnable callback) {
        AtomicInteger minutes = new AtomicInteger(m);
        AtomicInteger sec = new AtomicInteger(s);
    
        Timer timer = new Timer(1000, e -> {
            if (minutes.get() == 0 && sec.get() == 0) {
                ((Timer) e.getSource()).stop();
		UDP.gameState(202);
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
                    warningCountdownLabel.setText(formattedTime);
                    actionScreen.setVisible(true);
                    
                } else {
                    String formattedTime = String.format("%02d:%02d", minutes.get(), sec.get());
                    warningCountdownLabel.setText(formattedTime);
                    actionScreen.setVisible(true);
                }
            }
        });
    
        timer.start();
    }

    private void collect_entries(){
        game.clear();
        for(int i=0; i<RedTeam.length; i++){
        if(!RedTeam[i].getText().isBlank()){
            Red_team[i]=RedTeam[i].getText();
        }
        else{
            Red_team[i]= " ";
        }
        if(!GreenTeam[i].getText().isBlank()){
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
                 game.add(RedTeamEqid[i].getText());
                
                 }
                 else{
                 game.add(GreenTeamEqid[i-15].getText());
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

    public void gameOver(){
        game_over = new JPanel();

        game_over.setOpaque(false);
        game_over.setBounds(frame.getWidth()/4,frame.getHeight()/4,frame.getWidth()/2, frame.getHeight()/2);
        
        JLabel endgame = new JLabel("Game Over", SwingConstants.CENTER);
        endgame.setFont(new Font("TimesRoman", Font.BOLD, 100));
        endgame.setForeground(Color.WHITE);
       
        JLabel instructions = new JLabel("Press Enter to return", SwingConstants.CENTER);
        instructions.setFont(new Font("TimesRoman", Font.BOLD, 50));
        instructions.setForeground(Color.WHITE);
    

        game_over.setLayout(new GridLayout(2,1));
        game_over.add(endgame);
        game_over.add(instructions);
        game_over.setVisible(true);

        frame.add(game_over);
        frame.setComponentZOrder(game_over, 0);

        frame.repaint();
        frame.revalidate();

    }

    ArrayList<JPanel> bMarkers = new ArrayList<>();
    public void StylizedB(String Name){
        Bkeep.put(Name, 1);
        int y = 110;
        int x = 0;
        int offset = 20;
        Color teamColor = Color.RED;
        for(int i =0; i<RedPlayers.length;i++){
            if(RedPlayers[i].getText().contains(Name)){
                y = (i>7)? y=110+offset*(i-8):y+offset*i;
                x=(i>7)? 450: 250;
                break;
            }
        }
        for(int i =0; i<GreenPlayers.length;i++){
            if(GreenPlayers[i].getText().contains(Name)){
                y = (i>7)? y=100+offset*(i-8):y+offset*i;
                x = (i>7)? 1200: 1000;
                teamColor = Color.GREEN;
                break;
            }
        }

        final Color finalTeamColor = teamColor;
        final int xbase = x;
        final int ybase = y;
       
    
        JPanel styledB = new JPanel(){
            protected void paintComponent(Graphics action) {
                action.setColor(finalTeamColor);
                action.drawRect(xbase, ybase, 12, 12);
                action.drawString("B", xbase+2, ybase+10);
                
            }
        };
        styledB.setBounds(x,y,20,20);
        styledB.repaint();
        styledB.setVisible(true);
        if(model.entryscreen){
            styledB.setVisible(false);
        }
        frame.add(styledB);
        frame.setComponentZOrder(styledB, 0);
        frame.repaint();
        frame.validate();
        bMarkers.add(styledB);
    }
    public void redrawB(String key){
        if(!Bkeep.isEmpty() && Bkeep.get(key)>0){
            StylizedB(key);
        }
    }

    public void removeB(){
        for (JPanel bMarker : bMarkers) {
            frame.remove(bMarker); 
        }
        bMarkers.clear(); 
        frame.repaint();
    }
    
    public void updateScoreForTag(String playerName, boolean isOpponentTagged, boolean isSameTeamTagged) {
        int points = 0;
        if (isOpponentTagged) {
            points += 10;
        }
        if (isSameTeamTagged) {
            points -= 10;
        }

        if (RedScores.containsKey(playerName)) {
            RedScores.put(playerName, RedScores.get(playerName) + points);
            System.out.println("adding to red player: "+playerName);
        }
         else if (GreenScores.containsKey(playerName)) {
            GreenScores.put(playerName, GreenScores.get(playerName) + points);
            System.out.println("adding to green player: "+playerName);
        }
    }

    // Method to handle red base being scored by green team (code 53)
    public void handleRedBaseScore(String Name) {
        GreenScores.put(Name,GreenScores.get(Name)+100);
    }

    // Method to handle green base being scored by red team (code 43)
    public void handleGreenBaseScore(String Name) {
        RedScores.put(Name,RedScores.get(Name)+100);
    }

    public void eventDisplayPanel() {
        events = new ArrayList<>();

        JPanel eventBox = new JPanel(){
            protected void paintComponent(Graphics action) {
                Font timeNewRomanFont = new Font("Times New Roman", Font.PLAIN, 40);
                action.setFont(timeNewRomanFont);
                action.setColor(Color.RED);
                int yOffset = 530; // Initial y-offset for the first event
                for (String event : events) {
                action.drawString(event, 150, yOffset);
                yOffset -= 40; // Increase y-offset for the next event
                }
            }
        };
        frame.add(eventBox);
        frame.repaint();
        frame.validate();
    }
    public void addEvent(String event) {
        events.add(0, event);
        
        // If the list size exceeds 5, remove the oldest event
        if (events.size() > 5) {
            events.remove(5);
        }

        frame.repaint();
    }
}
