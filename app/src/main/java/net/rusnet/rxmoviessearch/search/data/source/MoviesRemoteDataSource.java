package net.rusnet.rxmoviessearch.search.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.data.model.OMDbMovie;
import net.rusnet.rxmoviessearch.search.data.model.OMDbSearchResponse;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;
import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
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
    public Observable<SearchResult> performSearch(@NonNull String query) {
        return mOmdbApi.getResults(query, API_KEY)
                .map(getMapper());
    }

    @NonNull
    @Override
    public Observable<SearchResult> getPage(@NonNull String query, int pageToLoad) {
        return mOmdbApi.getPageResults(query, pageToLoad, API_KEY)
                .map(getMapper());
    }

    @NotNull
    private Function<OMDbSearchResponse, SearchResult> getMapper() {
        return new Function<OMDbSearchResponse, SearchResult>() {
            @Override
            public SearchResult apply(OMDbSearchResponse response) throws Exception {
                if (response.getOMDbMovies() == null) throw new OmdbApiException();

                List<Movie> movieList = new ArrayList<>();
                for (OMDbMovie omDbMovie : response.getOMDbMovies()) {
                    Movie movie = new Movie(
                            omDbMovie.getTitle(),
                            omDbMovie.getYear(),
                            (omDbMovie.getPoster().equals(NO_POSTER) ? EMPTY_STRING : omDbMovie.getPoster()),
                            omDbMovie.getImdbID(),
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
