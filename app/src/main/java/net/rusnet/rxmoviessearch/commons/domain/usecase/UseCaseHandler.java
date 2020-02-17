package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class UseCaseHandler {

    private Scheduler mMainThreadScheduler;
    private Scheduler mWorkerThreadScheduler;

    public UseCaseHandler(
            @NonNull Scheduler mainThreadScheduler,
            @NonNull Scheduler workerThreadScheduler) {
        mMainThreadScheduler = mainThreadScheduler;
        mWorkerThreadScheduler = workerThreadScheduler;
    }

    @NonNull
    public <V, T> Disposable execute(
            @NonNull UseCase<V, Observable<T>> useCase,
            @Nullable V requestValues,
            @NonNull DisposableObserver<T> observer) {
        return useCase.buildUseCaseObservable(requestValues)
                .subscribeOn(mWorkerThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribeWith(observer);
    }

    @NonNull
    public <V, T> Disposable execute(
            @NonNull UseCase<V, Single<T>> useCase,
            @Nullable V requestValues,
            @NonNull DisposableSingleObserver<T> observer) {
        return useCase.buildUseCaseObservable(requestValues)
                .subscribeOn(mWorkerThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribeWith(observer);
    }

    @NonNull
    public <V, T> Disposable execute(
            @NonNull UseCase<V, Maybe<T>> useCase,
            @Nullable V requestValues,
            @NonNull DisposableMaybeObserver<T> observer) {
        return useCase.buildUseCaseObservable(requestValues)
                .subscribeOn(mWorkerThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribeWith(observer);
    }

    @NonNull
    public <V> Disposable execute(
            @NonNull UseCase<V, Completable> useCase,
            @Nullable V requestValues,
            @NonNull DisposableCompletableObserver observer) {
        return useCase.buildUseCaseObservable(requestValues)
                .subscribeOn(mWorkerThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribeWith(observer);
    }

}
