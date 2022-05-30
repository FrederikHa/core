package resourceLoading;

import com.badlogic.gdx.utils.Queue;
import java.util.LinkedList;
import java.util.logging.Logger;

public class ResourceController {
    private static final Logger LOG = Logger.getLogger(ResourceController.class.getName());
    private final Queue<UIThreadTask> taskQueue = new Queue<>();
    private final LinkedList<ResourceLoader<?>> allLoaders = new LinkedList<>();

    /**
     * Factory for a new ResourceLoader controlled by this controller
     *
     * @param <T> Type of the resource
     * @param code function returning the new resource
     * @return the resource loader loading the resource
     */
    public <T> ResourceLoader<T> newLoader(final NewResource<T> code) {
        ResourceLoader<T> loader = new ResourceLoader<T>(code, this);
        allLoaders.add(loader);
        loader.start();
        return loader;
    }

    void remove(ResourceLoader<?> loader) {
        allLoaders.remove(loader);
    }

    /*
     * Helper class to synchronise the threads and wait for the result
     */
    private class UIThreadTask {
        private final Runnable task;
        private boolean finished = false;

        UIThreadTask(Runnable task) {
            this.task = task;
        }

        void execute() {
            task.run();
            finished = true;
            synchronized (this) {
                notify();
            }
        }

        boolean isRunning() {
            return !finished;
        }
    }

    /**
     * Runs a task on the GUI Thread
     *
     * @param task runnable which should be run on the GUI Thread
     */
    public void runOnUIThread(final Runnable task) {
        if (Thread.currentThread().getName().equals("main")) {
            task.run();
            return;
        }

        final UIThreadTask uiTask = new UIThreadTask(task);
        synchronized (this) {
            taskQueue.addLast(uiTask);
        }

        synchronized (uiTask) {
            while (uiTask.isRunning()) {
                try {
                    uiTask.wait();
                } catch (final InterruptedException e) {
                    LOG.severe("thread interrupted while waiting for hudController");
                    LOG.finest(e.toString());
                    throw new RuntimeException("application closed / interrupted");
                }
            }
        }
    }

    /**
     * runs GUI Task from LibGdx that can only be performed by the UI. This tasks can come from any
     * Thread by invoking <code>runOnUIThread()</code>. This method should be invoked regularly from
     * the UI / main Thread
     *
     * @return true if a task was run
     */
    public boolean runUIThreadTask() {
        if (taskQueue.isEmpty()) {
            return false;
        }
        UIThreadTask r;
        synchronized (this) {
            r = taskQueue.removeFirst();
        }
        r.execute();
        return true;
    }

    /** */
    public void interruptAll() {
        for (final ResourceLoader<?> loader : allLoaders) {
            loader.interrupt();
        }
    }
}
