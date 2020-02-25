package net.rusnet.rxmoviessearch.commons.injection;

import android.content.Context;

import net.rusnet.rxmoviessearch.commons.data.source.IMoviesLocalDataSource;
import net.rusnet.rxmoviessearch.commons.data.source.MovieDao;
import net.rusnet.rxmoviessearch.commons.data.source.MoviesDatabase;
import net.rusnet.rxmoviessearch.commons.data.source.MoviesLocalDataSource;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module(includes = AppModule.AppAbstractModule.class)
public class AppModule {

    private final Context mApplicationContext;
    public static final String MAIN_THREAD = "MAIN_THREAD";
    public static final String WORKER_THREAD = "WORKER_THREAD";

    public AppModule(Context applicationContext) {
        mApplicationContext = applicationContext.getApplicationContext();
    }

    @Provides
    Context provideApplicationContext() {
        return mApplicationContext;
    }

    @Singleton
    @Provides
    @Named(MAIN_THREAD)
    Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Singleton
    @Provides
    @Named(WORKER_THREAD)
    Scheduler provideWorkerThreadScheduler() {
        return Schedulers.io();
    }

    @Singleton
    @Provides
    MoviesDatabase provideMoviesDatabase(Context context) {
        return MoviesDatabase.getDatabase(context.getApplicationContext());
    }

    @Singleton
    @Provides
    MovieDao provideMovieDao(MoviesDatabase moviesDatabase) {
        return moviesDatabase.movieDao();
    }

    @Module
    interface AppAbstractModule {
        @Binds
        IMoviesLocalDataSource bindMoviesLocalDataSource(
                MoviesLocalDataSource moviesLocalDataSource
        );
    }
}
