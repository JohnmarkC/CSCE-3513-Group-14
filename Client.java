import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    Model model = new Model();
    player_entry_view playerscreen = new player_entry_view(model);
    Controller controller = new Controller(model, playerscreen);
    SupaBaseIntegration playerbase = new SupaBaseIntegration();
    UDP upd = new UDP();
   
    DatagramSocket receiverSocket; 
    byte[] packetData; 
    
    public void screen(){
        model.launch();
        while(!playerscreen.players_loaded)
        {
            try
            {
                Thread.sleep(5000);
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
        Thread screenThread = new Thread() 
        {
            public void run()
            {
                try
                {
                    startUpdates();
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        };
        Thread udpThread = new Thread()
        {
            public void run()
            {
                try
                {
                    receivingUpdates();
                }
                catch (Exception e)
                {
                    System.out.println(e);
                }
            }
        };
        screenThread.start();
        udpThread.start();    
    }

    public void startUpdates()
    {
        try{
            receiverSocket = new DatagramSocket(7501, InetAddress.getLocalHost());
        }catch(Exception e){
            e.printStackTrace();
        }
       
        while(true)
        {
            playerscreen.sort_players();
            if(playerscreen.GreenTeamScore > playerscreen.RedTeamScore)
            {
                playerscreen.green_score_flash();
            }
            if(playerscreen.RedTeamScore > playerscreen.GreenTeamScore)
            {
                playerscreen.red_score_flash();
            }
            if(playerscreen.GreenTeamScore == playerscreen.RedTeamScore)
            {
                playerscreen.scores_visible();
            }
            try
            {
                Thread.sleep(500);
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void receivingUpdates()
    {
        while(true)
        {
            try{
                packetData = new byte[256];
                model.recievedHit(UDP.receiveData(packetData, receiverSocket));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.screen();
    }
    
}
