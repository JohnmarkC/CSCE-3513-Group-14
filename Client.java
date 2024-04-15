public class Client {
    Model model = new Model();
    player_entry_view playerscreen = new player_entry_view(model);
    Controller controller = new Controller(model, playerscreen);
    SupaBaseIntegration playerbase = new SupaBaseIntegration();
    UDP upd = new UDP();

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
        while(true)
        {
            playerscreen.sort_players();
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.screen();
    }
    
}
