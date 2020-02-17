package net.rusnet.rxmoviessearch.search.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;

import io.reactivex.Single;

public interface IMoviesRemoteDataSource {

    @NonNull
    Single<SearchResult> performSearch(
            @NonNull String query);

    @NonNull
    Single<SearchResult> getPage(
            @NonNull String query,
            int pageToLoad);

}
