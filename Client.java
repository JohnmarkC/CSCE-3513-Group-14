public class Client {
    player_entry_view playerscreen = new player_entry_view();
    Splash splash = new Splash();
    Database playerbase = new Database();
    UDP upd = new UDP();

    public void screen(){
        playerscreen.create();
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.screen();
    }
    
}
