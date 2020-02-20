package net.rusnet.rxmoviessearch.commons.injection;

import net.rusnet.rxmoviessearch.search.data.source.IMoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.data.source.MoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.data.source.OmdbApi;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.NetworkAbstractModule.class)
public class NetworkModule {

    private final String mBaseUrl;

    public NetworkModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    OmdbApi provideOmdbApi(Retrofit retrofit) {
        return retrofit.create(OmdbApi.class);
    }

    @Module
    interface NetworkAbstractModule {
        @Binds
        IMoviesRemoteDataSource bindMoviesRemoteDataSource(
                MoviesRemoteDataSource moviesRemoteDataSource);
    }

}
