package bg.sofia.uni.fmi.mjt.movies;

import bg.sofia.uni.fmi.mjt.movies.model.Actor;
import bg.sofia.uni.fmi.mjt.movies.model.Movie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MoviesExplorer {

    private List<Movie> allMovies;

    /**
     * Loads the dataset from the given {@code dataInput} stream.
     */
    public MoviesExplorer(InputStream dataInput) {
        BufferedReader input = new BufferedReader(new InputStreamReader(dataInput));
        allMovies = input.lines().map(Movie::createMovie).collect(Collectors.toList());
    }

    /**
     * Returs all the movies loaded from the dataset.
     **/
    public Collection<Movie> getMovies() {
        return allMovies;
    }

    public int countMoviesReleasedIn(int year) {
        return (int) allMovies.stream().filter(t -> t.getYear() == year).count();
    }

    public Movie findFirstMovieWithTitle(String title) {
        return allMovies.stream().filter(movie -> movie.getTitle().contains(title)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such title.."));
    }

    public Collection<Actor> getAllActors() {
        return allMovies.stream().map(movie -> movie.getActors())
                .flatMap(x -> x.stream()).collect(Collectors.toSet());
    }

    public int getFirstYear() {
        return allMovies.stream().min((movie1, movie2) -> (movie1.getYear() - movie2.getYear())).get().getYear();
    }

    public Collection<Movie> getAllMoviesBy(Actor actor) {
        return allMovies.stream().filter(movie -> movie.getActors().contains(actor)).collect(Collectors.toSet());
    }

    public Collection<Movie> getMoviesSortedByReleaseYear() {
        return allMovies.stream().sorted((movie1, movie2) -> (movie1.getYear() - movie2.getYear()))
                .collect(Collectors.toList());
    }


    public int findYearWithLeastNumberOfReleasedMovies() {
        //Set<Integer> years=allMovies.stream().map(Movie::getYear).collect(Collectors.toSet());
        return allMovies.stream().map(Movie::getYear)
                .min((year1, year2) -> (countMoviesReleasedIn(year1) - countMoviesReleasedIn(year2))).get();
    }

    public Movie findMovieWithGreatestNumberOfActors() {
        return allMovies.stream().max((movie1, movie2) ->
                (movie1.getActors().size() - movie2.getActors().size())).get();
    }
    // Other methods ...
}
