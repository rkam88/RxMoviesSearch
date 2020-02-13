package net.rusnet.rxmoviessearch.commons.utils.executors;

import java.util.concurrent.Executor;

public interface AppExecutor extends Executor {

    interface MainThread extends AppExecutor {
    }

    interface WorkerThread extends AppExecutor {
    }

}
