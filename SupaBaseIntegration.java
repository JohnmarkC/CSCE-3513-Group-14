import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
//import java.util.concurrent.CompletableFuture;
import java.util.Vector;


public class SupaBaseIntegration {
    //Variable calling
    private String supabaseUrl;
    private String supabaseKey;
    private HttpClient client;
    private HttpRequest request;

    /*-----------------------------------------------------------------------------------------------------------
            Constructor   
                    Intialize variables 
    -----------------------------------------------------------------------------------------------------------*/
    SupaBaseIntegration(){
        //intializing variables
        this.supabaseUrl = "https://aqlqduwzdzfsbiiaqtgc.supabase.co/rest/v1/player";
        this.supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFxbHFkdXd6ZHpmc2JpaWFxdGdjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDc0MTU3NjcsImV4cCI6MjAyMjk5MTc2N30.BAq7EErRELTrKFkCAsWQrfy975RgUxfT71xOnzvvQpM";
        this.client = HttpClient.newHttpClient();
        this.request = HttpRequest.newBuilder()
        .uri(URI.create(supabaseUrl))
        .header("apikey", supabaseKey)
        .header("Authroization", "Bearer " + supabaseKey)
        .build();
    }



     /*-----------------------------------------------------------------------------------------------------------
            Private Function for Database interactions    
                    The purpose of this is to add players that are not currently in the database
                Variables: integer id - player id to add to data base
                         : String name - code name of player 
     -----------------------------------------------------------------------------------------------------------*/
    private void addplayer(int id, String name){
        //setting request for database to add player
        String requestBody = "{\"id\": " + id + ", \"codename\": \"" + name + "\"}";
        
        request = HttpRequest.newBuilder()
        .uri(URI.create(supabaseUrl))
        .header("apikey", supabaseKey)
        .header("Authorization", "Bearer " + supabaseKey)
        .header("Content-Type","application/json")
        .header("Prefer","return=minimal")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        .build();

        try {
            //sending request #return doesn't matter since it is an edit
            client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    /*-----------------------------------------------------------------------------------------------------------
        Private Function for Database interactions
             The purpose of this function is to check if the player is already in database through ID and 
                 return the code name of the player
            Variables: integer id - player id to check data base
    -----------------------------------------------------------------------------------------------------------*/
    private String checkDatabase(int id, String Name){
        //setting variable to determine placement of name
        String idString = String.valueOf(id);

        request = HttpRequest.newBuilder()
        .uri(URI.create(supabaseUrl))
        .header("apikey", supabaseKey)
        .header("Authorization", "Bearer " + supabaseKey)
        .build();
       
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //checking if id is in the database
             idString = "\"id\":"+idString+",\"codename\":";
             String name ="";
             int codename_spot = response.body().indexOf(idString);
             if(codename_spot!=-1){
                codename_spot += idString.length()+1;
             }
             if(codename_spot!=-1){
                name = response.body().substring(codename_spot);
                int remove = name.indexOf("\"");
                name = name.substring(0, remove);
             }
                return name;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }  
        
    }

    /*-----------------------------------------------------------------------------------------------------------
        Private Funtion for Editing Table
             The function interacts with the database and changes names associated with certain ID
            Variables: integer id - player who is changing name
                     : String name - new name
    -----------------------------------------------------------------------------------------------------------*/
    private void changename(int id, String name){
        //setting up variables to change name
        String requestBody = "{ \"codename\": \"" + name + "\"}";
        String update = supabaseUrl+"?id=eq."+String.valueOf(id);
        request = HttpRequest.newBuilder()
        .uri(URI.create(update))
        .header("apikey", supabaseKey)
        .header("Authorization", "Bearer " + supabaseKey)
        .method("PATCH",HttpRequest.BodyPublishers.ofString(requestBody))
        .build();
       
        try{
            //sending name change request
            client.send(request,HttpResponse.BodyHandlers.ofString());
            
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /*-----------------------------------------------------------------------------------------------------------
            Public Function for Client use  (NEEDS TO BE FIXED to ASYNC) 
                This is the only callable that checks database, adds, and changes name depending 
                on input
            Variables: integer id - player's id
                     : String name - code name of player
                     : boolean changename - State whether they wish to change their code name
    -----------------------------------------------------------------------------------------------------------*/
    public String playerData(int id, String name,boolean changename){
        //checking if player wished for name change
        if(changename){
            changename(id,name);
            //checking if player is in data base
            if(checkDatabase(id, name)== "" && !name.isBlank()){
                addplayer(id, name);
                return name;
            }
            else{
                changename(id, name);
                return checkDatabase(id,name);
            }
        }
        else{ 
            if(checkDatabase(id, name)== "" && !name.isBlank()){
                addplayer(id, name);
                return name;
            }
            else{
            return checkDatabase(id, name);
            }
        }
        
    }
    
}
