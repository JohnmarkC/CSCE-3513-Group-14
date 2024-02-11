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

class player_entry_view
{
    public static void main(String args[])
    {
        player_entry_view p = new player_entry_view();
        p.create();
    }

    public void create()
    {
        //create a JFrame on which to create the player entry screen
        JFrame frame = new JFrame();
        frame.setTitle("Entry Terminal");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(1250, 1250);
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

        //create a panel for the green team and red team
        JPanel Redpanel = new JPanel();
        JPanel Greenpanel = new JPanel();
        Redpanel.setBackground(new Color(122, 37, 34));
        Greenpanel.setBackground(new Color(34, 122, 66));
        Redpanel.setBounds(50,35,525, 575);
        Greenpanel.setBounds(675, 35, 525, 575);
        frame.add(Redpanel);
        frame.add(Greenpanel);

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
       JTextField RedName1 = new JTextField("Enter your name");
       RedName1.setBackground(Color.WHITE);
       Redpanel.add(RedName1);
       RedName1.setBounds(25,50,225,20);

       JTextField RedName2 = new JTextField("Enter your name");
       RedName2.setBackground(Color.WHITE);
       Redpanel.add(RedName2);
       RedName2.setBounds(25,85,225,20);

       JTextField RedName3 = new JTextField("Enter your name");
       RedName3.setBackground(Color.WHITE);
       Redpanel.add(RedName3);
       RedName3.setBounds(25,120,225,20);

       JTextField RedName4 = new JTextField("Enter your name");
       RedName4.setBackground(Color.WHITE);
       Redpanel.add(RedName4);
       RedName4.setBounds(25,155,225,20);

       JTextField RedName5 = new JTextField("Enter your name");
       RedName5.setBackground(Color.WHITE);
       Redpanel.add(RedName5);
       RedName5.setBounds(25,190,225,20);

       JTextField RedName6 = new JTextField("Enter your name");
       RedName6.setBackground(Color.WHITE);
       Redpanel.add(RedName6);
       RedName6.setBounds(25,225,225,20);

       JTextField RedName7 = new JTextField("Enter your name");
       RedName7.setBackground(Color.WHITE);
       Redpanel.add(RedName7);
       RedName7.setBounds(25,260,225,20);

       JTextField RedName8 = new JTextField("Enter your name");
       RedName8.setBackground(Color.WHITE);
       Redpanel.add(RedName8);
       RedName8.setBounds(25,295,225,20);

       JTextField RedName9 = new JTextField("Enter your name");
       RedName9.setBackground(Color.WHITE);
       Redpanel.add(RedName9);
       RedName9.setBounds(25,330,225,20);

       JTextField RedName10 = new JTextField("Enter your name");
       RedName10.setBackground(Color.WHITE);
       Redpanel.add(RedName10);
       RedName10.setBounds(25,365,225,20);

       JTextField RedName11 = new JTextField("Enter your name");
       RedName11.setBackground(Color.WHITE);
       Redpanel.add(RedName11);
       RedName11.setBounds(25,400,225,20);

       JTextField RedName12 = new JTextField("Enter your name");
       RedName12.setBackground(Color.WHITE);
       Redpanel.add(RedName12);
       RedName12.setBounds(25,435,225,20);

       JTextField RedName13 = new JTextField("Enter your name");
       RedName13.setBackground(Color.WHITE);
       Redpanel.add(RedName13);
       RedName13.setBounds(25,470,225,20);

       JTextField RedName14 = new JTextField("Enter your name");
       RedName14.setBackground(Color.WHITE);
       Redpanel.add(RedName14);
       RedName14.setBounds(25,505,225,20);

       JTextField RedName15 = new JTextField("Enter your name");
       RedName15.setBackground(Color.WHITE);
       Redpanel.add(RedName15);
       RedName15.setBounds(25,540,225,20);

       JTextField Redid1 = new JTextField("Enter your id");
       Redid1.setBackground(Color.WHITE);
       Redpanel.add(Redid1);
       Redid1.setBounds(275,50,225,20);

       JTextField Redid2 = new JTextField("Enter your id");
       Redid2.setBackground(Color.WHITE);
       Redpanel.add(Redid2);
       Redid2.setBounds(275,85,225,20);

       JTextField Redid3 = new JTextField("Enter your id");
       Redid3.setBackground(Color.WHITE);
       Redpanel.add(Redid3);
       Redid3.setBounds(275,120,225,20);

       JTextField Redid4 = new JTextField("Enter your id");
       Redid4.setBackground(Color.WHITE);
       Redpanel.add(Redid4);
       Redid4.setBounds(275,155,225,20);

       JTextField Redid5 = new JTextField("Enter your id");
       Redid5.setBackground(Color.WHITE);
       Redpanel.add(Redid5);
       Redid5.setBounds(275,190,225,20);

       JTextField Redid6 = new JTextField("Enter your id");
       Redid6.setBackground(Color.WHITE);
       Redpanel.add(Redid6);
       Redid6.setBounds(275,225,225,20);

       JTextField Redid7 = new JTextField("Enter your id");
       Redid7.setBackground(Color.WHITE);
       Redpanel.add(Redid7);
       Redid7.setBounds(275,260,225,20);

       JTextField Redid8 = new JTextField("Enter your id");
       Redid8.setBackground(Color.WHITE);
       Redpanel.add(Redid8);
       Redid8.setBounds(275,295,225,20);

       JTextField Redid9 = new JTextField("Enter your id");
       Redid9.setBackground(Color.WHITE);
       Redpanel.add(Redid9);
       Redid9.setBounds(275,330,225,20);

       JTextField Redid10 = new JTextField("Enter your id");
       Redid10.setBackground(Color.WHITE);
       Redpanel.add(Redid10);
       Redid10.setBounds(275,365,225,20);

       JTextField Redid11 = new JTextField("Enter your id");
       Redid11.setBackground(Color.WHITE);
       Redpanel.add(Redid11);
       Redid11.setBounds(275,400,225,20);

       JTextField Redid12 = new JTextField("Enter your id");
       Redid12.setBackground(Color.WHITE);
       Redpanel.add(Redid12);
       Redid12.setBounds(275,435,225,20);

       JTextField Redid13 = new JTextField("Enter your id");
       Redid13.setBackground(Color.WHITE);
       Redpanel.add(Redid13);
       Redid13.setBounds(275,470,225,20);

       JTextField Redid14 = new JTextField("Enter your id");
       Redid14.setBackground(Color.WHITE);
       Redpanel.add(Redid14);
       Redid14.setBounds(275,505,225,20);

       JTextField Redid15 = new JTextField("Enter your id");
       Redid15.setBackground(Color.WHITE);
       Redpanel.add(Redid15);
       Redid15.setBounds(275,540,225,20);
       
       JTextField GreenName1 = new JTextField("Enter your name");
       GreenName1.setBackground(Color.WHITE);
       Greenpanel.add(GreenName1);
       GreenName1.setBounds(25,50,225,20);

       JTextField GreenName2 = new JTextField("Enter your name");
       GreenName2.setBackground(Color.WHITE);
       Greenpanel.add(GreenName2);
       GreenName2.setBounds(25,85,225,20);

       JTextField GreenName3 = new JTextField("Enter your name");
       GreenName3.setBackground(Color.WHITE);
       Greenpanel.add(GreenName3);
       GreenName3.setBounds(25,120,225,20);

       JTextField GreenName4 = new JTextField("Enter your name");
       GreenName4.setBackground(Color.WHITE);
       Greenpanel.add(GreenName4);
       GreenName4.setBounds(25,155,225,20);

       JTextField GreenName5 = new JTextField("Enter your name");
       GreenName5.setBackground(Color.WHITE);
       Greenpanel.add(GreenName5);
       GreenName5.setBounds(25,190,225,20);

       JTextField GreenName6 = new JTextField("Enter your name");
       GreenName6.setBackground(Color.WHITE);
       Greenpanel.add(GreenName6);
       GreenName6.setBounds(25,225,225,20);

       JTextField GreenName7 = new JTextField("Enter your name");
       GreenName7.setBackground(Color.WHITE);
       Greenpanel.add(GreenName7);
       GreenName7.setBounds(25,260,225,20);

       JTextField GreenName8 = new JTextField("Enter your name");
       GreenName8.setBackground(Color.WHITE);
       Greenpanel.add(GreenName8);
       GreenName8.setBounds(25,295,225,20);

       JTextField GreenName9 = new JTextField("Enter your name");
       GreenName9.setBackground(Color.WHITE);
       Greenpanel.add(GreenName9);
       GreenName9.setBounds(25,330,225,20);

       JTextField GreenName10 = new JTextField("Enter your name");
       GreenName10.setBackground(Color.WHITE);
       Greenpanel.add(GreenName10);
       GreenName10.setBounds(25,365,225,20);

       JTextField GreenName11 = new JTextField("Enter your name");
       GreenName11.setBackground(Color.WHITE);
       Greenpanel.add(GreenName11);
       GreenName11.setBounds(25,400,225,20);

       JTextField GreenName12 = new JTextField("Enter your name");
       GreenName12.setBackground(Color.WHITE);
       Greenpanel.add(GreenName12);
       GreenName12.setBounds(25,435,225,20);

       JTextField GreenName13 = new JTextField("Enter your name");
       GreenName13.setBackground(Color.WHITE);
       Greenpanel.add(GreenName13);
       GreenName13.setBounds(25,470,225,20);

       JTextField GreenName14 = new JTextField("Enter your name");
       GreenName14.setBackground(Color.WHITE);
       Greenpanel.add(GreenName14);
       GreenName14.setBounds(25,505,225,20);

       JTextField GreenName15 = new JTextField("Enter your name");
       GreenName15.setBackground(Color.WHITE);
       Greenpanel.add(GreenName15);
       GreenName15.setBounds(25,540,225,20);

       JTextField Greenid1 = new JTextField("Enter your id");
       Greenid1.setBackground(Color.WHITE);
       Greenpanel.add(Greenid1);
       Greenid1.setBounds(275,50,225,20);

       JTextField Greenid2 = new JTextField("Enter your id");
       Greenid2.setBackground(Color.WHITE);
       Greenpanel.add(Greenid2);
       Greenid2.setBounds(275,85,225,20);

       JTextField Greenid3 = new JTextField("Enter your id");
       Greenid3.setBackground(Color.WHITE);
       Greenpanel.add(Greenid3);
       Greenid3.setBounds(275,120,225,20);

       JTextField Greenid4 = new JTextField("Enter your id");
       Greenid4.setBackground(Color.WHITE);
       Greenpanel.add(Greenid4);
       Greenid4.setBounds(275,155,225,20);

       JTextField Greenid5 = new JTextField("Enter your id");
       Greenid5.setBackground(Color.WHITE);
       Greenpanel.add(Greenid5);
       Greenid5.setBounds(275,190,225,20);

       JTextField Greenid6 = new JTextField("Enter your id");
       Greenid6.setBackground(Color.WHITE);
       Greenpanel.add(Greenid6);
       Greenid6.setBounds(275,225,225,20);

       JTextField Greenid7 = new JTextField("Enter your id");
       Greenid7.setBackground(Color.WHITE);
       Greenpanel.add(Greenid7);
       Greenid7.setBounds(275,260,225,20);

       JTextField Greenid8 = new JTextField("Enter your id");
       Greenid8.setBackground(Color.WHITE);
       Greenpanel.add(Greenid8);
       Greenid8.setBounds(275,295,225,20);

       JTextField Greenid9 = new JTextField("Enter your id");
       Greenid9.setBackground(Color.WHITE);
       Greenpanel.add(Greenid9);
       Greenid9.setBounds(275,330,225,20);

       JTextField Greenid10 = new JTextField("Enter your id");
       Greenid10.setBackground(Color.WHITE);
       Greenpanel.add(Greenid10);
       Greenid10.setBounds(275,365,225,20);

       JTextField Greenid11 = new JTextField("Enter your id");
       Greenid11.setBackground(Color.WHITE);
       Greenpanel.add(Greenid11);
       Greenid11.setBounds(275,400,225,20);

       JTextField Greenid12 = new JTextField("Enter your id");
       Greenid12.setBackground(Color.WHITE);
       Greenpanel.add(Greenid12);
       Greenid12.setBounds(275,435,225,20);

       JTextField Greenid13 = new JTextField("Enter your id");
       Greenid13.setBackground(Color.WHITE);
       Greenpanel.add(Greenid13);
       Greenid13.setBounds(275,470,225,20);

       JTextField Greenid14 = new JTextField("Enter your id");
       Greenid14.setBackground(Color.WHITE);
       Greenpanel.add(Greenid14);
       Greenid14.setBounds(275,505,225,20);

       JTextField Greenid15 = new JTextField("Enter your id");
       Greenid15.setBackground(Color.WHITE);
       Greenpanel.add(Greenid15);
       Greenid15.setBounds(275,540,225,20);

    }
}
