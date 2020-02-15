package net.rusnet.rxmoviessearch.search.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;

import io.reactivex.Observable;

public interface IMoviesRemoteDataSource {

    @NonNull
    Observable<SearchResult> performSearch(
            @NonNull String query);

    @NonNull
    Observable<SearchResult> getPage(
            @NonNull String query,
            int pageToLoad);

}
