package net.rusnet.rxmoviessearch.commons.injection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context mApplicationContext;

    public AppModule(Context applicationContext) {
        mApplicationContext = applicationContext.getApplicationContext();
    }

    @Provides
    Context provideApplicationContext() {
        return mApplicationContext;
    }
}
