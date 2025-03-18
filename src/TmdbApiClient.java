import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TmdbApiClient {

    private final String apikey;
    private final int page;

    public TmdbApiClient(String apikey, int page) {
        this.apikey = apikey;
        this.page = page;
    }

    public String getBody(){

        try {
            URI apiTMDB = URI.create("https://api.themoviedb.org/3/movie/top_rated");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()

                    .uri(URI.create(apiTMDB + "?language=pt-BR&page=" + page))
                    .GET()
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + apikey)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
           return response.body();
        }catch (IOException | InterruptedException e){
            throw new IllegalStateException(e);
        }

    }
}

