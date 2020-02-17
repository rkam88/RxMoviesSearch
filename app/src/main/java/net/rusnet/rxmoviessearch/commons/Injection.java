package net.rusnet.rxmoviessearch.commons;

import android.content.Context;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.data.source.MoviesDatabase;
import net.rusnet.rxmoviessearch.commons.data.source.MoviesLocalDataSource;
import net.rusnet.rxmoviessearch.commons.domain.usecase.ChangeMovieFavoriteStatus;
import net.rusnet.rxmoviessearch.commons.domain.usecase.LoadFavorites;
import net.rusnet.rxmoviessearch.commons.domain.usecase.UseCaseHandler;
import net.rusnet.rxmoviessearch.favorites.presentation.FavoritesContract;
import net.rusnet.rxmoviessearch.favorites.presentation.FavoritesPresenter;
import net.rusnet.rxmoviessearch.search.data.source.MoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.data.source.OmdbApi;
import net.rusnet.rxmoviessearch.search.domain.usecase.LoadResultsPage;
import net.rusnet.rxmoviessearch.search.domain.usecase.PerformSearch;
import net.rusnet.rxmoviessearch.search.presentation.SearchContract;
import net.rusnet.rxmoviessearch.search.presentation.SearchPresenter;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class Injection {

    private static final String BASE_URL = "http://omdbapi.com/";

    private static Scheduler MAIN_THREAD_SCHEDULER_INSTANCE;
    private static Scheduler WORKER_THREAD_SCHEDULER_INSTANCE;
    private static MoviesLocalDataSource MOVIES_LOCAL_DATA_SOURCE_INSTANCE;
    private static OmdbApi OMDB_API_INSTANCE;
    private static MoviesRemoteDataSource MOVIES_REMOTE_DATA_SOURCE_INSTANCE;
    private static UseCaseHandler USE_CASE_HANDLER_INSTANCE;
    private static ChangeMovieFavoriteStatus CHANGE_MOVIE_FAVORITE_STATUS_INSTANCE;
    private static LoadFavorites LOAD_FAVORITES_INSTANCE;
    private static PerformSearch PERFORM_SEARCH_INSTANCE;
    private static LoadResultsPage LOAD_RESULTS_PAGE_INSTANCE;
    private static SearchContract.Presenter SEARCH_PRESENTER_INSTANCE;
    private static FavoritesContract.Presenter FAVORITES_PRESENTER_INSTANCE;

    public static Scheduler provideMainThreadScheduler() {
        if (MAIN_THREAD_SCHEDULER_INSTANCE == null) {
            MAIN_THREAD_SCHEDULER_INSTANCE = AndroidSchedulers.mainThread();
        }
        return MAIN_THREAD_SCHEDULER_INSTANCE;
    }

    public static Scheduler provideWorkerThreadScheduler() {
        if (WORKER_THREAD_SCHEDULER_INSTANCE == null) {
            WORKER_THREAD_SCHEDULER_INSTANCE = Schedulers.io();
        }
        return WORKER_THREAD_SCHEDULER_INSTANCE;
    }

    public static MoviesLocalDataSource provideMoviesLocalDataSource(@NonNull Context context) {
        if (MOVIES_LOCAL_DATA_SOURCE_INSTANCE == null) {
            MoviesDatabase database = MoviesDatabase.getDatabase(context.getApplicationContext());
            MOVIES_LOCAL_DATA_SOURCE_INSTANCE = new MoviesLocalDataSource(database.movieDao());
        }
        return MOVIES_LOCAL_DATA_SOURCE_INSTANCE;
    }

    public static OmdbApi provideOmdbApi() {
        if (OMDB_API_INSTANCE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            OMDB_API_INSTANCE = retrofit.create(OmdbApi.class);
        }
        return OMDB_API_INSTANCE;
    }

    public static MoviesRemoteDataSource provideMoviesRemoteDataSource() {
        if (MOVIES_REMOTE_DATA_SOURCE_INSTANCE == null) {
            MOVIES_REMOTE_DATA_SOURCE_INSTANCE = new MoviesRemoteDataSource(
                    provideOmdbApi()
            );
        }
        return MOVIES_REMOTE_DATA_SOURCE_INSTANCE;
    }

    public static UseCaseHandler provideUseCaseHandler() {
        if (USE_CASE_HANDLER_INSTANCE == null) {
            USE_CASE_HANDLER_INSTANCE = new UseCaseHandler(
                    provideMainThreadScheduler(),
                    provideWorkerThreadScheduler()
            );
        }
        return USE_CASE_HANDLER_INSTANCE;
    }

    public static ChangeMovieFavoriteStatus provideChangeMovieFavoriteStatusUseCase(@NonNull Context context) {
        if (CHANGE_MOVIE_FAVORITE_STATUS_INSTANCE == null) {
            CHANGE_MOVIE_FAVORITE_STATUS_INSTANCE = new ChangeMovieFavoriteStatus(
                    provideMoviesLocalDataSource(context.getApplicationContext()));
        }
        return CHANGE_MOVIE_FAVORITE_STATUS_INSTANCE;
    }

    public static LoadFavorites provideLoadFavoritesUseCase(@NonNull Context context) {
        if (LOAD_FAVORITES_INSTANCE == null) {
            LOAD_FAVORITES_INSTANCE = new LoadFavorites(
                    provideMoviesLocalDataSource(context.getApplicationContext()));
        }
        return LOAD_FAVORITES_INSTANCE;
    }

    public static PerformSearch providePerformSearchUseCase() {
        if (PERFORM_SEARCH_INSTANCE == null) {
            PERFORM_SEARCH_INSTANCE = new PerformSearch(
                    provideMoviesRemoteDataSource()
            );
        }
        return PERFORM_SEARCH_INSTANCE;
    }

    public static LoadResultsPage provideLoadResultsPageUseCase() {
        if (LOAD_RESULTS_PAGE_INSTANCE == null) {
            LOAD_RESULTS_PAGE_INSTANCE = new LoadResultsPage(
                    provideMoviesRemoteDataSource()
            );
        }
        return LOAD_RESULTS_PAGE_INSTANCE;
    }

    public static SearchContract.Presenter provideSearchPresenter(@NonNull Context context) {
        if (SEARCH_PRESENTER_INSTANCE == null) {
            SEARCH_PRESENTER_INSTANCE = new SearchPresenter(
                    provideUseCaseHandler(),
                    providePerformSearchUseCase(),
                    provideLoadResultsPageUseCase(),
                    provideChangeMovieFavoriteStatusUseCase(context.getApplicationContext()),
                    provideLoadFavoritesUseCase(context.getApplicationContext()));
        }
        return SEARCH_PRESENTER_INSTANCE;
    }

    public static FavoritesContract.Presenter provideFavoritesPresenter(@NonNull Context context) {
        if (FAVORITES_PRESENTER_INSTANCE == null) {
            FAVORITES_PRESENTER_INSTANCE = new FavoritesPresenter(
                    provideUseCaseHandler(),
                    provideChangeMovieFavoriteStatusUseCase(context.getApplicationContext())
            );
        }
        return FAVORITES_PRESENTER_INSTANCE;
    }

}
