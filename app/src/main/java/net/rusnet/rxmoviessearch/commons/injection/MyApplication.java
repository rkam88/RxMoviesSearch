package net.rusnet.rxmoviessearch.commons.injection;

import android.app.Application;

import net.rusnet.rxmoviessearch.search.data.source.MoviesRemoteDataSource;

public class MyApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .searchModule(new SearchModule(MoviesRemoteDataSource.BASE_URL))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
