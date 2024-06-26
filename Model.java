import javax.swing.RowFilter.Entry;
import javax.swing.SwingUtilities;

import java.lang.String;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

class Model
{
    SupaBaseIntegration data;
    player_entry_view view;
    boolean entryscreen, actiondisplay, splash, equipmentID, players;
    ArrayList<String> rednames; 
    ArrayList<String> greennames;
    int members = 1;
    String [] ID;

    
    Model()
    {
       data = new SupaBaseIntegration(); 
       equipmentID = false;
       players = false;
       rednames = new ArrayList<String>(); 
       greennames = new ArrayList<String>(); 
       ID = new String[30];
       for(int i =0; i<30; i++){
        ID[i]= "";
       }
       
    }

    public void search()
    {
        if(entryscreen){
          int id = 0;
          String name = "";
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
                result = data.playerData(id, name, view.nameChange[i]);
                
                if(i<15){
                     view.RedTeam[i].setText(result);
                     members++;
             }
              else if(i>=15 && i<30){
                  view.GreenTeam[i-15].setText(result);
                  members++;
               }
            }
         }
        }
        
        
        //sending signal to equipment
        String[] Eqid = new String[30];
        
        for(int i =0; i<30; i++){
           if(i<15){
                Eqid[i]=view.RedTeamEqid[i].getText();
           }
           else{
                Eqid[i]=view.GreenTeamEqid[i-15].getText();
           }
        }
        int i =0;
            for(String Eq: Eqid){

                if(!Eq.isBlank()){
                    equipmentID = true;
                    if(ID[i].isEmpty()){
                        ID[i]=Eq;
                        UDP.sendData(Eqid[i]);
                    }
                }
                i++;
            }


    }

    public void recievedHit(String[] player){
        String name;
        String event = "BLANK";
        switch(player[1])
        {
            case "53":
                //green player shot red base
                System.out.println("53 works");
                UDP.sendData("53");
                name= view.Eq2nameGreen.get(Integer.parseInt(player[0]));
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        view.StylizedB(name);
                        view.frame.repaint();
                        view.frame.revalidate();
                    }
                });
                view.handleRedBaseScore(name);
                event = ("Player "+name+ " has hit Red base");
                view.addEvent(event);
                break;
            case "43":
                //red player shot green base
                System.out.println("43 works");
                UDP.sendData("43");
                name = view.Eq2nameRed.get(Integer.parseInt(player[0]));
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        view.StylizedB(name);
                        view.frame.repaint();
                        view.frame.revalidate();
                    }
                });
                view.handleGreenBaseScore(name);
                event = ("Player "+name+ " has hit Green base");
                view.addEvent(event);
                break;
            default:
                //red/green player has shot green/red player
                System.out.println(player[0]+" has shot "+player[1]);
                if((view.Eq2nameRed.containsKey(Integer.parseInt(player[0])) && view.Eq2nameRed.containsKey(Integer.parseInt(player[1]))) ||
                    (view.Eq2nameGreen.containsKey(Integer.parseInt(player[0])) && view.Eq2nameGreen.containsKey(Integer.parseInt(player[1])))){
                     UDP.sendData(player[0]);
                     if(view.Eq2nameRed.containsKey(Integer.parseInt(player[0]))){
                        name = view.Eq2nameRed.get(Integer.parseInt(player[0]));
                        event = ("Player " + view.Eq2nameRed.get(Integer.parseInt(player[0])) +" has shot player "+ view.Eq2nameRed.get(Integer.parseInt(player[1])));
                    }
                    else{
                        name = view.Eq2nameGreen.get(Integer.parseInt(player[0]));
                        event = ("Player " + view.Eq2nameGreen.get(Integer.parseInt(player[0])) +" has shot player "+ view.Eq2nameGreen.get(Integer.parseInt(player[1])));
                    }
                     view.updateScoreForTag(name, false, true);
                     view.addEvent(event);
                }
                else{
                    if(view.Eq2nameRed.containsKey(Integer.parseInt(player[0]))){
                        name = view.Eq2nameRed.get(Integer.parseInt(player[0]));
                        event = ("Player " + view.Eq2nameRed.get(Integer.parseInt(player[0])) +" has shot player "+ view.Eq2nameGreen.get(Integer.parseInt(player[1])));
                    }
                    else{
                        name = view.Eq2nameGreen.get(Integer.parseInt(player[0]));
                        event = ("Player " + view.Eq2nameGreen.get(Integer.parseInt(player[0])) +" has shot player "+ view.Eq2nameRed.get(Integer.parseInt(player[1])));
                    }
                    view.updateScoreForTag(name, true, false);
                    view.addEvent(event);
                    UDP.sendData(player[1]);
                }
                break;
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
       
        view.actionScreen.removeAll();
        view.actionCountdownLabel.setVisible(false);
       view.frame.remove(view.actionScreen);
       view.frame.remove(view.actionCountdownLabel);
        for(int i=0; i<view.actionGreen.length;i++){
            view.frame.remove(view.actionGreen[i]);
        }
        for(int i=0; i<view.actionRed.length;i++){
            view.frame.remove(view.actionRed[i]);
        }
        view.frame.remove(view.timeRemaining);
        view.frame.remove(view.warningLabel);
        view.frame.remove(view.warning);
        view.redNames.clear();
        view.greenNames.clear();
        view.GreenScores.clear();
        view.RedScores.clear();
        view.frame.repaint();
        view.frame.revalidate();
        view.create();
        entryscreen = true;
        clear();

    }

    public void start()
    {   
        entryscreen = false;
        actiondisplay = true;
        System.out.println("F5 Key Pressed");
        view.frame.repaint();
        view.create_action_screen();
    }

    public void clear()
    {

        System.out.println("F12 Key Pressed");
        for(int i = 0; i <30 ; i++)
        {
             view.RedTeam[i].setText("");
             view.GreenTeam[i].setText("");
             view.Red_team[i] = "";
             view.Green_team[i] = "";
             if(i<15){
                view.GreenNameChange[i].setSelected(false);
                view.RedNameChange[i].setSelected(false);

             }
        }
        for(int i =0; i<15;i++){
            view.GreenTeamEqid[i].setText("");
            view.RedTeamEqid[i].setText("");
        }
        System.out.println("cleared entry");

        view.game.clear();
        view.Eq2nameGreen.clear();
        view.Eq2nameRed.clear();
        view.Bkeep.clear();
        System.out.println("cleared action");
        if(ID.length>0){
            for(int i =0; i<30; i++)
            {
                if(!(ID[i]==null)){
                    ID[i]="";
                }
            }
        }
    }


}
