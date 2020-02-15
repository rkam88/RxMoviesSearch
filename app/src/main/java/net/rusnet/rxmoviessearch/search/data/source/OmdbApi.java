package net.rusnet.rxmoviessearch.search.data.source;

import net.rusnet.rxmoviessearch.search.data.model.OMDbSearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApi {

    @GET(".")
    Observable<OMDbSearchResponse> getResults(@Query("s") String searchQuery,
                                              @Query("apikey") String apiKey);

    @GET(".")
    Observable<OMDbSearchResponse> getPageResults(@Query("s") String searchQuery,
                                                  @Query("page") int pageToLoad,
                                                  @Query("apikey") String apiKey);
}
