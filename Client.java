public class Client {
    player_entry_view playerscreen = new player_entry_view();
    Splash splashscreen = new Splash();
    SupaBaseIntegration playerbase = new SupaBaseIntegration();
    UDP upd = new UDP();

    public void screen(){
        splashscreen.create();
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
