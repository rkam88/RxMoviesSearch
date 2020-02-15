package net.rusnet.rxmoviessearch.favorites.presentation;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.domain.usecase.ChangeMovieFavoriteStatus;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import io.reactivex.observers.DisposableObserver;

public class FavoritesPresenter implements FavoritesContract.Presenter {

    private ChangeMovieFavoriteStatus mChangeMovieFavoriteStatus;

    public FavoritesPresenter(@NonNull ChangeMovieFavoriteStatus changeMovieFavoriteStatus) {
        mChangeMovieFavoriteStatus = changeMovieFavoriteStatus;
    }

    @Override
    public void deleteFromFavorites(@NonNull Movie movie) {
        movie.setInFavorites(false);
        mChangeMovieFavoriteStatus.execute(movie, new DisposableObserver<Void>() {
            @Override
            public void onNext(Void aVoid) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                dispose();
            }
        });
    }
}
