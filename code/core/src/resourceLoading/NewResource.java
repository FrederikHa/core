package resourceLoading;

/**
 * defines the lambda function
 *
 * @param <T> Type of loaded state
 */
public interface NewResource<T> {
    /**
     * method creates new instance of T
     *
     * @return new instance of T
     */
    T initializeInstance();
}
