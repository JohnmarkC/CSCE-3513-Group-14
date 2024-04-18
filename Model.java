import javax.swing.RowFilter.Entry;
import java.lang.String;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

class Model
{
    SupaBaseIntegration data;
    player_entry_view view;
    boolean entryscreen, actiondisplay, splash, equipmentID, players;
    
    Model()
    {
       data = new SupaBaseIntegration(); 
       equipmentID = false;
       players = false;
    }

    public void search()
    {
        if(entryscreen){
          int id = 0;
          String name = "";
          boolean changename = false;
          String result = "";
          System.out.println("Enter Key Pressed");
        
         //sending id search and adding name to teams
         String[] arrString =  view.Entry(false).toArray(new String[0]);
         
         for(int i = 0; i < arrString.length/2; i++)
         {
             if(arrString[i]!=" "){
               players =true;
               id = Integer.parseInt(arrString[i]);
               name = arrString[i+30];
              result = data.playerData(id, name, changename);
                if(i<15){
                     view.RedTeam[i].setText(result);
             }
              else if(i>=15 && i<30){
                  view.GreenTeam[i-15].setText(result);
               }
            }
         }
        }
        //sending signal to equipment
        String[] Eqid = view.Entry(true).toArray(new String[0]);
        if(Eqid.length>0){
            equipmentID = true;
        }
        for(String Eq : Eqid){
             UDP.sendData(Eq);
        }
    }

    public void launch(){
        splash = true;
        view.create_splash();
        try {
            // Wait 3 seconds
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        splash = false;
        entryscreen = true;
        view.create();
    }

    public void resetgame(){
       
        view.actionScreen.setVisible(false);
        view.actionCountdownLabel.setVisible(false);
        for(int i=0; i<view.actionGreen.length;i++){
            view.actionGreen[i].setVisible(false);
        }
        for(int i=0; i<view.actionRed.length;i++){
            view.actionRed[i].setVisible(false);
        }
        view.timeRemaining.setVisible(false);
        view.warningLabel.setVisible(false);
        view.timer.setVisible(false);
        view.warning.setVisible(false);
        view.game_over.setVisible(false);
        view.create();
        entryscreen = true;
        clear();
    }

    public void start()
    {   
        entryscreen = false;
        actiondisplay = true;
        System.out.println("F5 Key Pressed");
        view.frame.getContentPane().removeAll();
        view.frame.repaint();
        view.create_action_screen();
    }

    public void clear()
    {

        System.out.println("F12 Key Pressed");
        for(int i = 0; i <45 ; i++)
        {
             view.RedTeam[i].setText("");
             view.GreenTeam[i].setText("");
             view.Red_team[i] = "";
             view.Green_team[i] = "";
        }
        view.game.clear();
    }

}
