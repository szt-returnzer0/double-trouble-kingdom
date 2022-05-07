package DTK_model;

/**
 * A class that represents a pair of objects.
 *
 * @param <T> The type of the first object.
 * @param <U> The type of the second object.
 */
public class Pair<T, U> {
    /**
     * The first object.
     */
    private T map;
    /**
     * The second object.
     */
    private U file;

    /**
     * Constructs a new pair.
     *
     * @param first  The first object.
     * @param second The second object.
     */
    public Pair(T first, U second) {
        this.map = first;
        this.file = second;
    }

    /**
     * Returns the first object.
     *
     * @return The first object.
     */
    public T getMap() {
        return map;
    }

    /**
     * Sets the first object.
     *
     * @param map The first object.
     */
    public void setMap(T map) {
        this.map = map;
    }

    /**
     * Returns the second object.
     *
     * @return The second object.
     */
    public U getFile() {
        return file;
    }

    /**
     * Sets the second object.
     *
     * @param file The second object.
     */
    public void setFile(U file) {
        this.file = file;
    }

}
