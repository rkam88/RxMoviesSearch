package net.rusnet.rxmoviessearch.search.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.domain.usecase.RemoteDataSourceUseCase;
import net.rusnet.rxmoviessearch.search.data.source.IMoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class LoadResultsPage
        extends RemoteDataSourceUseCase<LoadResultsPageRequestValues, SearchResult> {

    public LoadResultsPage(@NonNull Scheduler mainThreadScheduler,
                           @NonNull Scheduler workerThreadScheduler,
                           @NonNull IMoviesRemoteDataSource moviesLocalDataSource) {
        super(mainThreadScheduler, workerThreadScheduler, moviesLocalDataSource);
    }

    @NonNull
    @Override
    protected Observable<SearchResult> buildUseCaseObservable(
            @Nullable LoadResultsPageRequestValues requestValues) {
        if (requestValues == null) throw new IllegalArgumentException();
        return mMoviesRemoteDataSource.getPage
                (requestValues.getSearchQuery(),
                        requestValues.getPageToLoad());
    }

}
