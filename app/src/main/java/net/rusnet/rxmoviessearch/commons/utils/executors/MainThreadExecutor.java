package net.rusnet.rxmoviessearch.commons.utils.executors;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

public class MainThreadExecutor implements AppExecutor.MainThread {

    private final Handler mHandler;

    public MainThreadExecutor() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mHandler.post(command);
    }

}
