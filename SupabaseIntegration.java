import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;




public class SupaBaseIntegration {
    // Supabase URL and API key
    private String supabaseUrl = "https://aqlqduwzdzfsbiiaqtgc.supabase.co/rest/v1/player";
    private String supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFxbHFkdXd6ZHpmc2JpaWFxdGdjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDc0MTU3NjcsImV4cCI6MjAyMjk5MTc2N30.BAq7EErRELTrKFkCAsWQrfy975RgUxfT71xOnzvvQpM";

    // Create a Supabase client
    private HttpClient client = HttpClient.newHttpClient();
    
    // Prepare the request
    private HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create(supabaseUrl))
    .header("apikey", supabaseKey)
    .header("Authorization", "Bearer " + supabaseKey)
    .build();

     /*The purpose of this is to be able to add players that are not currently in the database*/
    private void addplayer(int id, String name){
        String requestBody = "{\"id\": " + id + ", \"codename\": \"" + name + "\"}";
        request = HttpRequest.newBuilder()
        .uri(URI.create(supabaseUrl))
        .header("apikey", supabaseKey)
        .header("Authorization", "Bearer " + supabaseKey)
        .header("Content-Typea","application/json")
        .header("Prefer","return=minimal")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*The purpose of this function is to check if the player is already in database */
    private boolean checkDatabase(String name){
        String codename = supabaseUrl+"?select=codename";
        request = HttpRequest.newBuilder()
        .uri(URI.create(codename))
        .header("apikey", supabaseKey)
        .header("Authorization", "Bearer " + supabaseKey)
        .build();
       
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
          
            //checking if the codename is in the database
             int codename_spot = response.body().indexOf(name);

             //returning values depending on find
             if(codename_spot !=-1){
                System.out.println("Found\n");
                return true;
             }
             else{
                System.out.println("Not in\n");
                return false;
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return true;   
    }

    /*This is the only callable that checks database and adds (NEEDS TO BE FIXED ASYNC) */
    public void playerData(int id, String name){
       checkDatabase(name);
        if(!checkDatabase(name)){
            addplayer(id, name);
        }
    }
}
