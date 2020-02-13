package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class UseCase<Q, R> {

    public abstract void execute(@Nullable final Q requestValues, @NonNull Callback<R> callback);

    public interface Callback<R> {
        void onResult(@NonNull R result);
    }
}
