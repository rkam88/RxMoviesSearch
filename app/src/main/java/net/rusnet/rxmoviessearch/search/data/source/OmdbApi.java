package net.rusnet.rxmoviessearch.search.data.source;

import net.rusnet.rxmoviessearch.search.data.model.OMDbSearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApi {

    @GET(".")
    Single<OMDbSearchResponse> getResults(@Query("s") String searchQuery,
                                          @Query("apikey") String apiKey);

    @GET(".")
    Single<OMDbSearchResponse> getPageResults(@Query("s") String searchQuery,
                                              @Query("page") int pageToLoad,
                                              @Query("apikey") String apiKey);
}
