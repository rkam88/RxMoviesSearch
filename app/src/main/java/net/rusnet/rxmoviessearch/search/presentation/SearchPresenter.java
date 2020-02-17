package net.rusnet.rxmoviessearch.search.presentation;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.domain.usecase.ChangeMovieFavoriteStatus;
import net.rusnet.rxmoviessearch.commons.domain.usecase.LoadFavorites;
import net.rusnet.rxmoviessearch.commons.domain.usecase.UseCaseHandler;
import net.rusnet.rxmoviessearch.search.data.source.OmdbApiException;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;
import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;
import net.rusnet.rxmoviessearch.search.domain.usecase.LoadResultsPage;
import net.rusnet.rxmoviessearch.search.domain.usecase.LoadResultsPageRequestValues;
import net.rusnet.rxmoviessearch.search.domain.usecase.PerformSearch;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class SearchPresenter implements SearchContract.Presenter {

    private WeakReference<SearchContract.View> mSearchViewWeakReference;
    private UseCaseHandler mUseCaseHandler;
    private PerformSearch mPerformSearch;
    private LoadResultsPage mLoadResultsPage;
    private ChangeMovieFavoriteStatus mChangeMovieFavoriteStatus;
    private LoadFavorites mLoadFavorites;

    public SearchPresenter(
            @NonNull UseCaseHandler useCaseHandler,
            @NonNull PerformSearch performSearch,
            @NonNull LoadResultsPage loadResultsPage,
            @NonNull ChangeMovieFavoriteStatus changeMovieFavoriteStatus,
            @NonNull LoadFavorites loadFavorites) {
        mPerformSearch = performSearch;
        mUseCaseHandler = useCaseHandler;
        mLoadResultsPage = loadResultsPage;
        mChangeMovieFavoriteStatus = changeMovieFavoriteStatus;
        mLoadFavorites = loadFavorites;
    }

    @Override
    public void setView(@NonNull SearchContract.View view) {
        mSearchViewWeakReference = new WeakReference<>(view);
    }

    @Override
    public void performSearch(@NonNull final String searchQuery) {
        showSearchProgressBarInView();
        mUseCaseHandler.execute(
                mPerformSearch,
                searchQuery,
                new DisposableSingleObserver<SearchResult>() {
                    @Override
                    public void onSuccess(SearchResult searchResult) {
                        showMovies(searchResult.getMovieList(),
                                searchQuery,
                                searchResult.getTotalResults());
                        hideSearchProgressBarInView();
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorMessage(e);
                        hideSearchProgressBarInView();
                        dispose();
                    }
                });
    }

    @Override
    public void loadResultsPage(int pageToLoad, @NonNull String searchQuery) {
        mUseCaseHandler.execute(
                mLoadResultsPage,
                new LoadResultsPageRequestValues(pageToLoad, searchQuery),
                new DisposableSingleObserver<SearchResult>() {
                    @Override
                    public void onSuccess(SearchResult searchResult) {
                        updateMovies(searchResult.getMovieList());
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrorMessage(e);
                        hideLoadMoreProgressBarInView();
                        dispose();
                    }
                }
        );
    }

    @Override
    public void changeMovieFavoriteStatus(@NonNull Movie movie) {
        mUseCaseHandler.execute(
                mChangeMovieFavoriteStatus,
                movie,
                new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        dispose();
                        loadFavoriteMovies();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dispose();
                    }
                }
        );
    }

    @Override
    public void loadFavoriteMovies() {
        mUseCaseHandler.execute(
                mLoadFavorites,
                null,
                new DisposableSingleObserver<List<Movie>>() {
                    @Override
                    public void onSuccess(List<Movie> movies) {
                        SearchContract.View view = mSearchViewWeakReference.get();
                        if (view != null) {
                            view.updateFavoriteMovies(movies);
                        }
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dispose();
                    }
                }
        );
    }

    private void showMovies(@NonNull List<Movie> movieList,
                            @NonNull String searchQuery,
                            long totalResults) {
        SearchContract.View view = mSearchViewWeakReference.get();
        if (view != null) {
            view.showMovies(movieList, searchQuery, totalResults);
        }
    }

    private void updateMovies(List<Movie> movieList) {
        SearchContract.View view = mSearchViewWeakReference.get();
        if (view != null) {
            view.updateMovies(movieList);
        }
    }

    private void showErrorMessage(@NonNull Throwable e) {
        SearchContract.View view = mSearchViewWeakReference.get();

        if (e instanceof IOException) {
            view.showNetworkErrorMessage();
        } else if (e instanceof OmdbApiException) {
            view.showRequestErrorMessage();
        } else {
            view.showOtherErrorMessage();
        }
    }

    private void showSearchProgressBarInView() {
        SearchContract.View view = mSearchViewWeakReference.get();
        if (view != null) {
            view.showSearchProgressBar();
        }
    }

    private void hideSearchProgressBarInView() {
        SearchContract.View view = mSearchViewWeakReference.get();
        if (view != null) {
            view.hideSearchProgressBar();
        }
    }

    private void hideLoadMoreProgressBarInView() {
        SearchContract.View view = mSearchViewWeakReference.get();
        if (view != null) {
            view.hideLoadingMoreProgressBar();
        }
    }

}

