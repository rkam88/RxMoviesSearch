package net.rusnet.rxmoviessearch.commons.injection;

import net.rusnet.rxmoviessearch.search.data.source.IMoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.data.source.MoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.data.source.OmdbApi;
import net.rusnet.rxmoviessearch.search.presentation.SearchContract;
import net.rusnet.rxmoviessearch.search.presentation.SearchPresenter;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = SearchModule.SearchAbstractModule.class)
public class SearchModule {

    private final String mBaseUrl;

    public SearchModule(String baseUrl) {
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
    interface SearchAbstractModule {
        @Binds
        SearchContract.Presenter bindSearchPresenter(
                SearchPresenter presenter);

        @Binds
        IMoviesRemoteDataSource bindMoviesRemoteDataSource(
                MoviesRemoteDataSource moviesRemoteDataSource);
    }
}
