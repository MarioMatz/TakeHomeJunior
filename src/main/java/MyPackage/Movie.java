package MyPackage;

import java.text.DecimalFormat;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Movie class consists of basic attributes for UseCase of searching for a specific 
 * movieId and then setting the movieName and movieRating for that Instance. 
 * For this, the twosearch methods are used.
 * 
 *  */


public class Movie {

    private List<List<String>> movieRecords;
    private List<List<String>> ratingRecords;    
    private String movieId;
    private String movieName;
    private String movieRating;


    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");

    public Movie(List<List<String>> movieRecords, List<List<String>> ratingRecords, String movieId) {
        this.movieRecords = movieRecords;
        this.ratingRecords = ratingRecords;
        this.movieId = movieId;
        this.movieName = "";
        this.movieRating = "";
    }
    public String getMovieId() {
        return movieId;
    }
    public String getMovieName() {
        return movieName;
    }
    public String getMovieRating() {
        return movieRating;
    }    
    public void setMovieId(String movieId) {
            this.movieId = movieId;
    }   
    public void setMovieName(String movieName) {
            this.movieName = movieName;
    }    
    public void setMovieRating(OptionalDouble movieRating) {
        if (movieRating.isPresent()) {
            this.movieRating = DECIMAL_FORMAT.format(movieRating.getAsDouble());  
        }
        else {
            this.movieRating = "";
        }
    }
    public String searchAttributeOnMovie(int index_column, int attribute_column) {

        List<String> record = MovieUtils.searchDataOnIndex(this.movieRecords, movieId, index_column, attribute_column, true);
        if (record.isEmpty()) {
            return "";
            }
        else {
            return record.get(0);
            }
    }
    public OptionalDouble searchMeanRating(int search_index, int attribute_index) {

        List<String> ratings = MovieUtils.searchDataOnIndex(this.ratingRecords, this.movieId, search_index, attribute_index, false);

        if (ratings.isEmpty()) {
            return OptionalDouble.empty();
        }
        else {
            double sum = 0.0;
            for (String value : ratings) {
                sum += Double.parseDouble(value);
            }
        return OptionalDouble.of(sum / ratings.size());
        }

    }
}