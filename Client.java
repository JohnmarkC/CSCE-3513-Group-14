public class Client {
    Model model = new Model();
    player_entry_view playerscreen = new player_entry_view(model);
    Controller controller = new Controller(model, playerscreen);
    SupaBaseIntegration playerbase = new SupaBaseIntegration();
    UDP upd = new UDP();

    public void screen(){
        playerscreen.create_splash();
        try {
            // Wait 3 seconds
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playerscreen.create();
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.screen();
    }
    
}
