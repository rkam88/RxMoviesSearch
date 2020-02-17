package net.rusnet.rxmoviessearch.search.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.data.model.OmdbMovie;
import net.rusnet.rxmoviessearch.search.data.model.OmdbSearchResponse;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;
import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class MoviesRemoteDataSource implements IMoviesRemoteDataSource {

    private static final String API_KEY = "3b6ee26";
    private static final String NO_POSTER = "N/A";
    private static final String EMPTY_STRING = "";
    private OmdbApi mOmdbApi;

    public MoviesRemoteDataSource(@NonNull OmdbApi omdbApi) {
        mOmdbApi = omdbApi;
    }

    @NonNull
    @Override
    public Single<SearchResult> performSearch(@NonNull String query) {
        return mOmdbApi.getResults(query, API_KEY)
                .map(getMapper());
    }

    @NonNull
    @Override
    public Single<SearchResult> getPage(@NonNull String query, int pageToLoad) {
        return mOmdbApi.getPageResults(query, pageToLoad, API_KEY)
                .map(getMapper());
    }

    @NotNull
    private Function<OmdbSearchResponse, SearchResult> getMapper() {
        return new Function<OmdbSearchResponse, SearchResult>() {
            @Override
            public SearchResult apply(OmdbSearchResponse response) throws Exception {
                if (response.getOmdbMovies() == null) throw new OmdbApiException();

                List<Movie> movieList = new ArrayList<>();
                for (OmdbMovie omdbMovie : response.getOmdbMovies()) {
                    Movie movie = new Movie(
                            omdbMovie.getTitle(),
                            omdbMovie.getYear(),
                            (omdbMovie.getPoster().equals(NO_POSTER) ? EMPTY_STRING : omdbMovie.getPoster()),
                            omdbMovie.getImdbID(),
                            false
                    );
                    movieList.add(movie);
                }

                return new SearchResult(
                        Long.parseLong(response.getTotalResults()),
                        movieList);
            }
        };
    }
}
