import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SupabaseClient {

    public static void main(String[] args) {
        // Supabase URL and API key
        String supabaseUrl = "https://aqlqduwzdzfsbiiaqtgc.supabase.co";
        String supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFxbHFkdXd6ZHpmc2JpaWFxdGdjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDc0MTU3NjcsImV4cCI6MjAyMjk5MTc2N30.BAq7EErRELTrKFkCAsWQrfy975RgUxfT71xOnzvvQpM";

        // Create a Supabase client
        HttpClient httpClient = HttpClient.newHttpClient();
        
        // Prepare the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(supabaseUrl))
                .header("apikey", supabaseKey)
                .build();
        
        // Send the request and process the response
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            // Print the response body
            System.out.println(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
