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
   
    
    Model()
    {
       data = new SupaBaseIntegration(); 
    }

    public void search()
    {
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
        //sending signal to equipment
        String[] Eqid = view.Entry(true).toArray(new String[0]);
        for(String Eq : Eqid){
             UDP.sendData(Eq);
        }
    }

    public void start()
    {
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
