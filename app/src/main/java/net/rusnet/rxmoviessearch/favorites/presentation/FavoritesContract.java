package net.rusnet.rxmoviessearch.favorites.presentation;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.domain.model.Movie;

public interface FavoritesContract {
    interface View {

    }

    interface Presenter {
        void deleteFromFavorites(@NonNull Movie movie);
    }
}
