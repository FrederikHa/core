package resourceLoading;

import java.util.logging.Logger;

/**
 * loads a new state
 *
 * @param <T> type of loaded state
 */
public class ResourceLoader<T> extends Thread {
    private static final Logger LOG = Logger.getLogger(ResourceLoader.class.getName());

    private final NewResource<T> code;
    private final ResourceController controller;
    private T loadedState;

    /**
     * starts loading the given state
     *
     * <p>therefore it executes the lambda function
     *
     * @param code lambda function which returns the new Thread
     */
    ResourceLoader(final NewResource<T> code, ResourceController controller) {
        this.code = code;
        this.controller = controller;
    }

    @Override
    public void run() {
        loadedState = code.initializeInstance();
        controller.remove(this);
        LOG.info(loadedState.getClass().getSimpleName() + " loaded");
    }

    /**
     * get the state waits for the state to fully load if not already finished
     *
     * @return the state
     */
    public T getLoadedResource() {
        if (loadedState != null) {
            return loadedState;
        }
        if (Thread.currentThread().getName().equals("main")) {
            while (loadedState == null) {
                if (!controller.runUIThreadTask()) {
                    try {
                        Thread.sleep(1);
                    } catch (final InterruptedException e) {
                        LOG.severe("main thread got interrupted while waiting for different task.");
                        e.printStackTrace();
                        throw new RuntimeException("application closed / interrupted");
                    }
                }
            }
        }
        try {
            this.join();
        } catch (final InterruptedException e) {
            LOG.severe("state loading Thread got interrupted");
            LOG.finest(e.toString());
        }
        return loadedState;
    }
}
