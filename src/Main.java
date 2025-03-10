import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    private static final String apikey = System.getenv("TMDB_API_KEY");
    private static final String URL = "https://api.themoviedb.org/3/movie/top_rated";
    private static final int topMovies = 25;

    public static void main(String[] args) {

        HttpClient client = HttpClient.newHttpClient();
        int page = 1;

        while (page <= topMovies) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "?language=pt-BR&page="+page))
                    .GET()
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + apikey)
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println)
                    .join();
            page++;
        }
    }
}
