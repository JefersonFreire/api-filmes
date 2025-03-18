import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesParse {

    static int page = 1;
    int topMovies ;
    private String json;

    public MoviesParse(String json, int topMovies) {
        this.json = json;
        this.topMovies = topMovies;
    }

    public static List<Movies> parseMovies(String json, int topMovies){
        List<Movies> movies = new ArrayList<>();

        while (movies.size() < topMovies) {

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
        return movies;
    }

}
