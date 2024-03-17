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

        System.out.println("Enter Key Pressed");
        
    //send all ids in the textfields to the database to be searched
    //if id is found fill codename textfield with found codename
    //if id is not found update the database with codename
    //prompt for the equipment id
    
   
    String[] arrString = view.Entry();
         
         for(int i = 0; i < view.game[i].length(); i++)
         {
            data.playerData(id, name, changename);
            String ID_string = String.valueOf(id);

            if( ID_string == view.game[i]) //if id is found
            {
                name = view.game[i];
                System.out.println("ID found, filling textfield with found codename");
            }
            else
            {
                System.out.println("ID not found, updating database with codename");
                update();
            }



         }

            
        

        /////equipment id prompt
    }

    public void start()
    {
        System.out.println("F5 Key Pressed");
        //start the game
        //view.frame.getContentPane().removeAll();
        //view.frame.repaint();
        //JLabel new_game = "Here is the game";
        //new_game.setVerticalAlignment(JLabel.CENTER);
        //new_game.setHorizontalAlignment(JLabel.CENTER);
    }

    public void clear()
    {

        System.out.println("F12 Key Pressed");
        // reset all of the textfields on the player entry screen
        /*for(int i = 0; i < 45; i++)
         {
            view.RedTeam[i] = null;
            view.GreenTeam[i] = null;
         }*/
    }

    void update()
    {
        //data.add(data.playerData(id, name, changename));
         System.out.println("Player data updated to database");
    }


}
