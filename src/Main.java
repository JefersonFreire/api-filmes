import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


public class Main {

    private static final String apikey = System.getenv("TMDB_API_KEY");
    private static final String URL = "https://api.themoviedb.org/3/movie/top_rated";
    private static final int topMovies = 250;

    public static void main(String[] args) {
        List<String> movies = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> urlImages = new ArrayList<>();
        List<String> releaseDate = new ArrayList<>();
        List<Double> ratingMovie = new ArrayList<>();
        String json = "";
        int page = 1;
        while (movies.size() < topMovies) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "?language=pt-BR&page="+page))
                    .GET()
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + apikey)
                    .build();

           json = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                   .thenApply(HttpResponse::body)
                   .join();
            page++;
            JSONArray jsonArray = new JSONObject(json).getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                titles.add(jsonArray.getJSONObject(i).getString("title"));
                urlImages.add(jsonArray.getJSONObject(i).getString("poster_path"));
                releaseDate.add(jsonArray.getJSONObject(i).getString("release_date"));
                ratingMovie.add(jsonArray.getJSONObject(i).getDouble("vote_average"));
                movies.add(jsonArray.toString());
                if(movies.size() >= topMovies) break;
            }
        }
    }
}