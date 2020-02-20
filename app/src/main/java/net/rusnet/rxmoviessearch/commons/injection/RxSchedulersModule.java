package net.rusnet.rxmoviessearch.commons.injection;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class RxSchedulersModule {

    public static final String MAIN_THREAD = "MAIN_THREAD";
    public static final String WORKER_THREAD = "WORKER_THREAD";

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

}
