package net.rusnet.rxmoviessearch.favorites.presentation;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.domain.usecase.ChangeMovieFavoriteStatus;
import net.rusnet.rxmoviessearch.commons.domain.usecase.UseCaseHandler;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.observers.DisposableCompletableObserver;

@Singleton
public class FavoritesPresenter implements FavoritesContract.Presenter {

    private UseCaseHandler mUseCaseHandler;
    private ChangeMovieFavoriteStatus mChangeMovieFavoriteStatus;

    @Inject
    public FavoritesPresenter(
            @NonNull UseCaseHandler useCaseHandler,
            @NonNull ChangeMovieFavoriteStatus changeMovieFavoriteStatus) {
        mUseCaseHandler = useCaseHandler;
        mChangeMovieFavoriteStatus = changeMovieFavoriteStatus;
    }

    @Override
    public void deleteFromFavorites(@NonNull Movie movie) {
        movie.setInFavorites(false);
        mUseCaseHandler.execute(
                mChangeMovieFavoriteStatus,
                movie,
                new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dispose();
                    }
                }
        );
    }
}
