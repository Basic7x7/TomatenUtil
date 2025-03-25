package de.tomatengames.util.pool;

/**
 * The {@code Pooled} interface represents a wrapper around an object that can
 * be returned to a pool after use.
 * <p>
 * {@code Pooled} objects are {@link AutoCloseable} for convenience, e.g. usage
 * in try-with-resources statements.
 * </p>
 *
 * @param <E> The type of the wrapped object.
 *
 * @version 2025-03-26 created
 * @since 1.8
 */
public interface Pooled<E> extends AutoCloseable {
	
	/**
	 * Retrieves the underlying object managed by this {@code Pooled} instance.
	 *
	 * @return The object of type {@code E}.
	 */
	E get();
	
	/**
	 * Returns the object back to the pool, making it available for reuse.
	 * <p>
	 * Either this method or, equivalently, {@link #close()} should be called when
	 * the object is no longer needed by the caller.
	 * </p>
	 * <p>
	 * Do not call this method multiple times. That also means you should call
	 * either free or close, never both. Implementations may detect this and throw
	 * some exception, but can typically not do that reliably.
	 * </p>
	 */
	void free();
	
	/**
	 * Equivalent to {@link #free()} for convenience.
	 */
	@Override
	default void close() {
		free();
	}
}
