package net.rusnet.rxmoviessearch.commons.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.data.model.RoomMovie;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

public class MoviesLocalDataSource implements IMoviesLocalDataSource {

    private static final String EMPTY_STRING = "";

    private MovieDao mMovieDao;

    public MoviesLocalDataSource(@NonNull MovieDao movieDao) {
        mMovieDao = movieDao;
    }

    @NonNull
    @Override
    public Observable<List<Movie>> getAllMovies() {
        return Observable.create(new ObservableOnSubscribe<List<RoomMovie>>() {
            @Override
            public void subscribe(ObservableEmitter<List<RoomMovie>> emitter) throws Exception {
                emitter.onNext(Arrays.asList(mMovieDao.getAllMovies()));
                emitter.onComplete();
            }
        })
                .map(new Function<List<RoomMovie>, List<Movie>>() {
                    @Override
                    public List<Movie> apply(List<RoomMovie> roomMovies) throws Exception {
                        List<Movie> movieList = new ArrayList<>();
                        for (RoomMovie roomMovie : roomMovies) {
                            movieList.add(new Movie(roomMovie.getTitle(),
                                    roomMovie.getYear(),
                                    roomMovie.getPosterURL(),
                                    roomMovie.getImdbID(),
                                    true));
                        }
                        return movieList;
                    }
                });
    }

    @NonNull
    @Override
    public Completable addMovie(@NonNull Movie movie) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                mMovieDao.addMovie(new RoomMovie(movie.getTitle(),
                        movie.getYear(),
                        movie.getImdbID(),
                        movie.getPosterURL()));
                emitter.onComplete();
            }
        });
    }

    @NonNull
    @Override
    public Completable deleteMovie(@NonNull String imdbId) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                mMovieDao.deleteMovie(imdbId);
                emitter.onComplete();
            }
        });
    }

}
