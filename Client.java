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
        startUpdates();    
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
            packetData = new byte[256];
            playerscreen.sort_players();
            playerscreen.TeamScoreDisplay(null, null);
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
            try{
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
