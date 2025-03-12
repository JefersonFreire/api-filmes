import org.json.JSONArray;
import org.json.JSONObject;

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
        String json = "";
        int page = 1;
        List<Movies> movies = new ArrayList<>();
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
            JSONArray jsonArray = new JSONObject(json).getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String poster = jsonObject.getString("poster_path");
                Double voteAverage = jsonObject.getDouble("vote_average");
                String date = jsonObject.getString("release_date");
                movies.add(new Movies(title, poster, date, voteAverage));
                if (movies.size() >= topMovies) break;
            }
            page++;
        }
    }
}