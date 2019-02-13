package bg.sofia.uni.fmi.mjt.movies;

import bg.sofia.uni.fmi.mjt.movies.model.Actor;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;

public class MoviesExplorerTest {

    private final static int MY_YEAR = 1998;
    private final static int MOVIES_PER_MY_YEAR = 3;
    private final static int COUNT_OF_ACTORS = 67;
    private final static int FIRST_YEAR = 1989;
    private final static int SECOND_YEAR = 1990;
    private ByteArrayInputStream data;
    private MoviesExplorer testExplorer;

    @Before
    public void setUp() throws Exception {
        data = new ByteArrayInputStream(("Parent Trap, The " +
                "(1998)/Akey, William/Atterbury, John/Barnes, Joanna/Baron, Roshanna\n" +
                "Home Alone (1990)/Campbell, Ken Hudson/Campeanu, Daiana/Candy," +
                " John/Cernugel, Frank/Cohen, Jedidiah/Cole, Victor" +
                "/Connor, Tracy J./Culkin, Kieran/Culkin, Macaulay\n" +
                "Home Alone 2: Lost in New York (1992)/Cerullo, Al/Civitano, Thomas/Cohen, " +
                "Jedidiah/Cole, James/Columbus, A.M./Columbus, Eleanor/Culkin, Kieran/Culkin, Macaulay\n" +
                "Home Alone 10 (2018)/Barnes, Joanna/Culkin, Macaulay\n" +
                "How the Grinch Stole Christmas (2000)/Tambor, Jeffrey/Thyne, T.J./Troyer, " +
                "Verne/Welker, Frank/White, Lillias/Williams, Caroline/Winfree, Rachel\n" +
                "Mrs. Santa Claus (1996)/Murray, Mick/Norona, David/Perrotta, Tony/Pittman, " +
                "Chachi/Sullivan, Stacy/Torcellini, Jamie/Wheeler, John/Williams, Mitchah/Wiseman, Debra\n" +
                "Boy Who Saved Christmas, The (1998)/Doug/Robinson, Walter Barret/Rose, Lenny/" +
                "Stone, Rutger/Stromer, Amy/Thomas, Patrick/Thompson, Leroy/Turner, Jerry/Woods, Dana\n" +
                "Christmas Vacation (1989)/Bailey, Devin/Burger, Cody/Chase, Chevy/D'Angelo," +
                " Beverly/Doyle-Murray, Brian/Epper, Tony/Flynn, Miriam\n" +
                "Christmas Wish, The (1998)/Archer, Beverly/Barnes, Adilah/Birney, " +
                "Frank/Carlo, Ismael 'East'/Harris, Neil Patrick/Hoag, Jan/Meltzer, Ian/Mitchell, Stuart\n" +
                "Nightmare Before Christmas, The (1993)/Ball, Sherwood/Brown, " +
                "Mia/Callender, L. Peter/Crenshaw, Randy/Durand, Judi M.\n" +
                "Once Upon a Christmas (2000)/Donnelly-Haskell, " +
                "Mary/Dye, John/Gilden, Michael/Ireland, Kathy").getBytes());
        //ByteArrayOutputStream output = new ByteArrayOutputStream();
        testExplorer = new MoviesExplorer(data);

    }

    @Test
    public void countMoviesReleasedIn() {
        int actual = testExplorer.countMoviesReleasedIn(MY_YEAR);
        assertEquals(MOVIES_PER_MY_YEAR, actual);
    }

    @Test
    public void findFirstMovieWithTitle() {
        String actual = testExplorer.findFirstMovieWithTitle("Christmas").getTitle();
        assertEquals("How the Grinch Stole Christmas", actual);
    }

    @Test
    public void getAllActors() {
        int actual = testExplorer.getAllActors().size();
        assertEquals(COUNT_OF_ACTORS, actual);
    }

    @Test
    public void getFirstYear() {
        int actual = testExplorer.getFirstYear();
        assertEquals(FIRST_YEAR, actual);
    }

    @Test
    public void getAllMoviesBy() {
        String actual = String.valueOf(testExplorer.getAllMoviesBy(new Actor("Macaulay", "Culkin")));
        assertEquals("[Movie [title=Home Alone 2: Lost in New York, year=1992], " +
                "Movie [title=Home Alone 10, year=2018], Movie [title=Home Alone, year=1990]]", actual);
    }

    @Test
    public void getMoviesSortedByReleaseYear() {
        String sortedMovies = String.valueOf(testExplorer.getMoviesSortedByReleaseYear());
        assertEquals("[Movie [title=Christmas Vacation, year=1989], " +
                "Movie [title=Home Alone, year=1990], " +
                "Movie [title=Home Alone 2: Lost in New York, year=1992], " +
                "Movie [title=Nightmare Before Christmas, The, year=1993], " +
                "Movie [title=Mrs. Santa Claus, year=1996], " +
                "Movie [title=Parent Trap, The, year=1998], " +
                "Movie [title=Boy Who Saved Christmas, The, year=1998], " +
                "Movie [title=Christmas Wish, The, year=1998], " +
                "Movie [title=How the Grinch Stole Christmas, year=2000], " +
                "Movie [title=Once Upon a Christmas, year=2000], " +
                "Movie [title=Home Alone 10, year=2018]]", sortedMovies);
    }

    @Test
    public void findYearWithLeastNumberOfReleasedMovies() {
        int actual = testExplorer.findYearWithLeastNumberOfReleasedMovies();
        assertEquals(SECOND_YEAR, actual);
    }

    @Test
    public void findMovieWithGreatestNumberOfActors() {
        String actual = testExplorer.findMovieWithGreatestNumberOfActors().getTitle();
        assertEquals("Home Alone", actual);
    }
}