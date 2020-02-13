package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.utils.executors.AppExecutor;

public class AsyncUseCaseExecutor {

    private AppExecutor mMainThreadExecutor;
    private AppExecutor mWorkerThreadExecutor;

    public AsyncUseCaseExecutor(
            @NonNull AppExecutor mainThreadExecutor,
            @NonNull AppExecutor workerThreadExecutor) {
        mMainThreadExecutor = mainThreadExecutor;
        mWorkerThreadExecutor = workerThreadExecutor;
    }

    <Q, R> void execute(
            @NonNull final AsyncUseCase<Q, R> useCase,
            @Nullable final Q requestValues,
            @NonNull final UseCase.Callback<R> callback) {
        mWorkerThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                R result = useCase.doInBackground(requestValues);
                postResult(result, callback);
            }
        });
    }

    private <R> void postResult(
            @NonNull final R result,
            @NonNull final UseCase.Callback<R> callback) {
        mMainThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResult(result);
            }
        });
    }

}
