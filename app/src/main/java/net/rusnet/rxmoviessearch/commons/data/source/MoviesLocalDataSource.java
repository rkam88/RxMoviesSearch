package net.rusnet.rxmoviessearch.commons.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.data.model.RoomMovie;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesLocalDataSource implements IMoviesLocalDataSource {

    private static final String EMPTY_STRING = "";

    private MovieDao mMovieDao;

    public MoviesLocalDataSource(@NonNull MovieDao movieDao) {
        mMovieDao = movieDao;
    }

    @NonNull
    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        for (RoomMovie roomMovie : mMovieDao.getAllMovies()) {
            movieList.add(new Movie(roomMovie.getTitle(),
                    roomMovie.getYear(),
                    roomMovie.getPosterURL(),
                    roomMovie.getImdbID(),
                    true));
        }
        return movieList;
    }

    @Override
    public void addMovie(@NonNull Movie movie) {
        mMovieDao.addMovie(new RoomMovie(movie.getTitle(),
                movie.getYear(),
                movie.getImdbID(),
                movie.getPosterURL()));
    }

    @Override
    public void deleteMovie(@NonNull String imdbId) {
        mMovieDao.deleteMovie(imdbId);
    }
}
