package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface UseCase<V, T> {

    @NonNull
    T buildUseCaseObservable(@Nullable V requestValues);

}
