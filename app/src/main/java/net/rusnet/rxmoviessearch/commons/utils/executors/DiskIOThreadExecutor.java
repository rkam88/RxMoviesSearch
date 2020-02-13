package net.rusnet.rxmoviessearch.commons.utils.executors;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DiskIOThreadExecutor implements AppExecutor.WorkerThread {

    private final Executor mDiskIOThreadExecutor;

    public DiskIOThreadExecutor() {
        mDiskIOThreadExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mDiskIOThreadExecutor.execute(command);
    }
}
