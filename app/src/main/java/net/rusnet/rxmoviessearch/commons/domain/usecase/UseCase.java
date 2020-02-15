package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public abstract class UseCase<R, T> {

    private Scheduler mMainThreadScheduler;
    private Scheduler mWorkerThreadScheduler;
    private CompositeDisposable mDisposables;

    public UseCase(@NonNull Scheduler mainThreadScheduler,
                   @NonNull Scheduler workerThreadScheduler) {
        mMainThreadScheduler = mainThreadScheduler;
        mWorkerThreadScheduler = workerThreadScheduler;
        mDisposables = new CompositeDisposable();
    }

    @NonNull
    protected abstract Observable<T> buildUseCaseObservable(@Nullable R requestValues);

    public void execute(@Nullable R requestValues, @NonNull DisposableObserver<T> observer) {
        mDisposables.add(buildUseCaseObservable(requestValues)
                .subscribeOn(mWorkerThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribeWith(observer));
    }

    public void dispose() {
        mDisposables.dispose();
    }

}
