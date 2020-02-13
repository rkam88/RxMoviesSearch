package net.rusnet.rxmoviessearch.search.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;

public interface IMoviesRemoteDataSource {

    interface onSearchResultCallback {
        void onResult(@NonNull SearchResult searchResult);
    }

    void performSearch(@NonNull String query,
                       @NonNull onSearchResultCallback callback);

    void getPage(@NonNull String query,
                 int pageToLoad,
                 @NonNull onSearchResultCallback callback);

}
