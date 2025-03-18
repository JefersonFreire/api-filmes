import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static final String apikey = System.getenv("TMDB_API_KEY");

    public static void main(String[] args) throws FileNotFoundException {

        String json = "";
        int topMovies = 250;

        json = new TmdbApiClient(apikey, MoviesParse.page).getBody();

        List<Movies> movies = MoviesParse.parseMovies(json, topMovies);
        System.out.println(movies);
        System.out.println(movies.size());
    }
}