import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


public class Main {

    private static final String apikey = System.getenv("TMDB_API_KEY");

    public static void main(String[] args) {
        String json = "";
        int page = 1;
        int topMovies = 250;
        List<Movies> movies = new ArrayList<>();

        while (movies.size() < topMovies) {
            json = new TmdbApiClient(apikey, page).getBody();


            JSONArray jsonArray = new JSONObject(json).getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String poster = jsonObject.getString("poster_path");
                Double voteAverage = jsonObject.getDouble("vote_average");
                String date = jsonObject.getString("release_date");
                movies.add(new Movies(title, poster, date, voteAverage));
                if(movies.size() >= topMovies) break;
            }
                page++;
            }
                System.out.println(movies.get(200));
                System.out.println(movies.size());
    }
}