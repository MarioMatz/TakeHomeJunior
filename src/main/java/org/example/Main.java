package org.example;
import java.io.*;
import java.util.List;
import java.util.OptionalDouble;
import MyPackage.MovieUtils;
import MyPackage.Movie;

/**
* The program asks you to enter a movie id, from which it will search a movie name and then 
* calculate an average rating
 */
public class Main {
    public static void main(String[] args) throws NullPointerException, RuntimeException, FileNotFoundException, IOException {

        String COMMA_DELIMITER = ",";
        String MOVIEPATH = "Dateien/movies.csv";
        String RATINGPATH = "Dateien/ratings.csv";

        try {
            List<List<String>> movieData  = MovieUtils.ReadFile(COMMA_DELIMITER, MOVIEPATH);
            List<List<String>> ratingData = MovieUtils.ReadFile(COMMA_DELIMITER, RATINGPATH);

            System.out.println("Bitte geben Sie die Filenummer ein, zu der Sie eine Bewertung wünschen");
            
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String searchMovieId = userInput.readLine();
            Movie movieObject = new Movie(movieData, ratingData, searchMovieId);
            
            String movieName = movieObject.searchAttributeOnMovie(0, 1);
            movieObject.setMovieName(movieName);
            
            if (movieName.isEmpty()) {
                System.out.println("Es wurde der Film: keiner ausgewählt."); 
            }
            else {
                System.out.println("Es wurde der Film: " + movieName + " ausgewählt."); 
                OptionalDouble ratingMean = movieObject.searchMeanRating(1, 2);
                movieObject.setMovieRating(ratingMean);

                System.out.println("Die Bewertung für den Film ist: " + movieObject.getMovieRating());
            }
        } 
        catch (FileNotFoundException e) {
            System.err.println("Error finding/parsing one of the files: " + MOVIEPATH + "; " + RATINGPATH);
        }
        catch (IOException e) {
            System.err.println("Error reading user input.");
        }
    }
}