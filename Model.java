import javax.swing.RowFilter.Entry;
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
    HashMap<Integer,String> Eq2nameGreen, Eq2nameRed;
    ArrayList<String> rednames; 
    ArrayList<String> greennames;
    int members = 1;

    
    Model()
    {
       data = new SupaBaseIntegration(); 
       equipmentID = false;
       players = false;
       Eq2nameGreen = new HashMap<Integer,String>();
       Eq2nameRed = new HashMap<Integer, String>();
       rednames = new ArrayList<String>(); 
       greennames = new ArrayList<String>(); 
       
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
        String[] Eqid = view.Entry(true).toArray(new String[0]);
        if(Eqid.length>0){
            equipmentID = true;
            for(int i =0; i<Eqid.length; i++){
             if(!Eqid[i].isBlank()){
                if(!Eq2nameRed.containsKey(Integer.parseInt(Eqid[i]))&& i<Eqid.length/2 && !rednames.isEmpty()){
                    UDP.sendData(Eqid[i]);
                    Eq2nameRed.put(Integer.parseInt(Eqid[i]),rednames.get(i));
                }
                else if(!Eq2nameGreen.containsKey(Integer.parseInt(Eqid[i])) && !greennames.isEmpty()){
                    UDP.sendData(Eqid[i]);
                    Eq2nameGreen.put(Integer.parseInt(Eqid[i]),greennames.get(i));
                }
                i++;
             }
            }
        }
    }

    public void recievedHit(String[] player){
        switch(player[1])
        {
            case "53":
                //green player shot red base
                System.out.println("53 works");
                UDP.sendData("53");
                break;
            case "43":
                //red player shot green base
                System.out.println("43 works");
                UDP.sendData("43");
                break;
            default:
                //red/green player has shot green/red player
                System.out.println(player[0]+" has shot "+player[1]);
                if((Eq2nameRed.containsKey(Integer.parseInt(player[0])) && Eq2nameRed.containsKey(Integer.parseInt(player[1]))) ||
                    (Eq2nameGreen.containsKey(Integer.parseInt(player[0])) && Eq2nameGreen.containsKey(Integer.parseInt(player[1])))){
                     UDP.sendData(player[0]);
                     System.out.println("Sending Shooter");
                }
                else{
                    UDP.sendData(player[1]);
                    System.out.println("Sending Hit "+player[1]);
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
        Eq2nameGreen.clear();
        Eq2nameRed.clear();
    }


}
