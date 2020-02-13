package net.rusnet.rxmoviessearch.search.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.data.model.OMDbMovie;
import net.rusnet.rxmoviessearch.search.data.model.OMDbSearchResponse;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;
import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;
import net.rusnet.rxmoviessearch.search.domain.model.SearchResultStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRemoteDataSource implements IMoviesRemoteDataSource {

    private static final String API_KEY = "3b6ee26";
    private static final String NO_POSTER = "N/A";
    private static final String EMPTY_STRING = "";
    private OmdbApi mOmdbApi;

    public MoviesRemoteDataSource(@NonNull OmdbApi omdbApi) {
        mOmdbApi = omdbApi;
    }

    @Override
    public void performSearch(@NonNull String query, @NonNull final onSearchResultCallback callback) {
        Call<OMDbSearchResponse> call = mOmdbApi.getResults(query, API_KEY);
        call.enqueue(new Callback<OMDbSearchResponse>() {
            @Override
            public void onResponse(Call<OMDbSearchResponse> call,
                                   Response<OMDbSearchResponse> response) {
                sendResultOnResponse(response, callback);
            }

            @Override
            public void onFailure(Call<OMDbSearchResponse> call,
                                  Throwable t) {
                sendResultOnFailure(t, callback);
            }
        });
    }

    @Override
    public void getPage(@NonNull String query, int pageToLoad, @NonNull final onSearchResultCallback callback) {
        Call<OMDbSearchResponse> call = mOmdbApi.getPageResults(query, pageToLoad, API_KEY);

        call.enqueue(new Callback<OMDbSearchResponse>() {
            @Override
            public void onResponse(Call<OMDbSearchResponse> call, Response<OMDbSearchResponse> response) {
                sendResultOnResponse(response, callback);
            }

            @Override
            public void onFailure(Call<OMDbSearchResponse> call, Throwable t) {
                sendResultOnFailure(t, callback);
            }
        });
    }

    private void sendResultOnResponse(Response<OMDbSearchResponse> response, @NonNull onSearchResultCallback callback) {
        SearchResult searchResult;

        if (response.isSuccessful() &&
                response.body() != null &&
                response.body().getOMDbMovies() != null) {
            List<OMDbMovie> omDbMovies = response.body().getOMDbMovies();
            List<Movie> movieList = new ArrayList<>();

            for (OMDbMovie omDbMovie : omDbMovies) {
                Movie movie = new Movie(
                        omDbMovie.getTitle(),
                        omDbMovie.getYear(),
                        (omDbMovie.getPoster().equals(NO_POSTER) ? EMPTY_STRING : omDbMovie.getPoster()),
                        omDbMovie.getImdbID(),
                        false
                );
                movieList.add(movie);
            }

            String totalResultsAsString = response.body().getTotalResults();

            searchResult = new SearchResult(
                    SearchResultStatus.SUCCESSFUL,
                    Long.parseLong(totalResultsAsString),
                    movieList);
        } else {
            searchResult = new SearchResult(
                    SearchResultStatus.ERROR_REQUEST,
                    0,
                    null);
        }
        callback.onResult(searchResult);
    }

    private void sendResultOnFailure(Throwable t, @NonNull onSearchResultCallback callback) {
        if (t instanceof IOException) {
            callback.onResult(new SearchResult(
                    SearchResultStatus.ERROR_NETWORK,
                    0,
                    null));
        } else {
            callback.onResult(new SearchResult(
                    SearchResultStatus.ERROR_OTHER,
                    0,
                    null));
        }
    }

}
