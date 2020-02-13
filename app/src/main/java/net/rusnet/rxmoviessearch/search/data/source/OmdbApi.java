package net.rusnet.rxmoviessearch.search.data.source;

import net.rusnet.rxmoviessearch.search.data.model.OMDbSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApi {

    @GET(".")
    Call<OMDbSearchResponse> getResults(@Query("s") String searchQuery,
                                        @Query("apikey") String apiKey);

    @GET(".")
    Call<OMDbSearchResponse> getPageResults(@Query("s") String searchQuery,
                                            @Query("page") int pageToLoad,
                                            @Query("apikey") String apiKey);
}
