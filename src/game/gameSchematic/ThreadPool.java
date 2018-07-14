package game.gameSchematic; /*** In The Name of Allah ***/


import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class holds a global thread-pool for executing our threads.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class ThreadPool {

    private ExecutorService executor;

    /**
     * Initializes a new CachedThreadPool.
     *
     * @see java.util.concurrent.Executors#newCachedThreadPool()
     */
    public void init() {
        executor = Executors.newCachedThreadPool();
    }

    /**
     * {@link java.util.concurrent.ExecutorService#execute(java.lang.Runnable)}
     */
    public void execute(Runnable r) {
        if (executor == null)
            init();
        executor.execute(r);
    }

    /**
     * {@link java.util.concurrent.ExecutorService#shutdown()}
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * {@link java.util.concurrent.ExecutorService#shutdownNow()}
     */
    public void shutdownNow() {
        executor.shutdownNow();
    }
}
