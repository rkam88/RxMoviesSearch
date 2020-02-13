package net.rusnet.rxmoviessearch.search.presentation;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.List;

public interface SearchContract {

    interface View {
        void showMovies(@NonNull List<Movie> movieList,
                        @NonNull String searchQuery,
                        long totalResults);

        void updateMovies(@NonNull List<Movie> movieList);

        void showRequestErrorMessage();

        void showNetworkErrorMessage();

        void showOtherErrorMessage();

        void updateFavoriteMovies(@NonNull List<Movie> movieList);

        void showSearchProgressBar();

        void hideSearchProgressBar();

        void hideLoadingMoreProgressBar();
    }

    interface Presenter {
        void setView(@NonNull SearchContract.View view);

        void performSearch(@NonNull String searchQuery);

        void loadResultsPage(int pageToLoad, @NonNull String searchQuery);

        void changeMovieFavoriteStatus(@NonNull Movie movie);

        void loadFavoriteMovies();
    }

}
