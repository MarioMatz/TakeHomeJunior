import org.example.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import MyPackage.Movie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
* Test cases of Main. WIP. 
* First UnitTests set up. 
* IntegrationTest (kind of) "successTest" has to be refactored as well.
 */


public class MainTest {
    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void reset() {
        System.setOut(standardOut);
        System.setIn(standardIn);
        outputStreamCaptor.reset();
    }

    @ParameterizedTest
    @MethodSource("testArgumentsSetMovieName")
    void setMovieNameTest(String input, String expectedName) throws IOException {

        List<List<String>> emptyList = new ArrayList<>();
        Movie movieObject = new Movie(emptyList, emptyList, input);  

        movieObject.setMovieName(expectedName);
        assertEquals(expectedName, movieObject.getMovieName());
    }
    @ParameterizedTest
    @MethodSource("testArgumentsSetMovieRating")
    void setMovieRatingTest(OptionalDouble input, String expectedRating) throws IOException {

        List<List<String>> emptyList = new ArrayList<>();
        Movie movieObject = new Movie(emptyList, emptyList, "X");  

        movieObject.setMovieRating(input);
        assertEquals(expectedRating, movieObject.getMovieRating());
    }

    @ParameterizedTest
    @MethodSource("testArguments")
    void successTest(String input, String expectedRating) throws NullPointerException, FileNotFoundException, RuntimeException, IOException {
        final InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        Main.main(new String[0]);

        if (expectedRating != null && !expectedRating.equals("")) {
            assertTrue(outputStreamCaptor.toString().contains(expectedRating));
        }
        else {
            assertFalse(outputStreamCaptor.toString().contains("Die Bewertung für den Film ist:"));
        }
    }

    private static Stream<Arguments> testArgumentsSetMovieName() {
        return Stream.of(
            Arguments.of("5", "Shrek"),
            Arguments.of("5", "Es"),            
            Arguments.of("5", "Buckethead is legend"),
            Arguments.of("5", "Longest movie name I have every seen. So it is great for testing"),            
            Arguments.of("5", "")                        
        ); 
    }
    private static Stream<Arguments> testArgumentsSetMovieRating() {
        return Stream.of(
            Arguments.of(OptionalDouble.of(3.2845985), "3,28"),
            Arguments.of(OptionalDouble.of(4.2855985), "4,29"),
            Arguments.of(OptionalDouble.of(0.1000010), "0,10"), 
            Arguments.of(OptionalDouble.empty(), "")               
        ); 
    }    

    private static Stream<Arguments> testArguments() {
        return Stream.of(
            Arguments.of("1", "3,92"),
            Arguments.of("555", "3,80"),
            Arguments.of("1001", ""),
            Arguments.of("90603", "3,67"),
            Arguments.of("190221", "1,00"),
            Arguments.of("keiner", "")
            
        ); 
    }
}