public class Client {
    Model model = new Model();
    player_entry_view playerscreen = new player_entry_view(model);
    Controller controller = new Controller(model, playerscreen);
    SupaBaseIntegration playerbase = new SupaBaseIntegration();
    UDP upd = new UDP();

    public void screen(){
        model.launch();
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.screen();
    }
    
}
