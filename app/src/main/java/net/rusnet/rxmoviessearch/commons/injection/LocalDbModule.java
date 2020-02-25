package net.rusnet.rxmoviessearch.commons.injection;

import android.content.Context;

import net.rusnet.rxmoviessearch.commons.data.source.IMoviesLocalDataSource;
import net.rusnet.rxmoviessearch.commons.data.source.MovieDao;
import net.rusnet.rxmoviessearch.commons.data.source.MoviesDatabase;
import net.rusnet.rxmoviessearch.commons.data.source.MoviesLocalDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = LocalDbModule.LocalDbAbstractModule.class)
public class LocalDbModule {

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
    interface LocalDbAbstractModule {
        @Binds
        IMoviesLocalDataSource bindMoviesLocalDataSource(
                MoviesLocalDataSource moviesLocalDataSource
        );
    }

}
