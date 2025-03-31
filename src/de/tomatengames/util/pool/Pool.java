package de.tomatengames.util.pool;

/**
 * The {@code Pool} interface allows for pooling of objects.
 * 
 * <p>
 * Objects can be obtained from a pool and can later be resupplied to the pool
 * to enable the pool to provide that object again in the future, which can
 * reduce the overhead associated with object creation and garbage collection.
 * </p>
 *
 * @param <E> The type of object managed by this pool.
 *
 * @version 2025-03-26 created
 * @since 1.8
 */
public interface Pool<E> {
	
	/**
	 * Claims an instance of {@code Pooled} from the pool.
	 * 
	 * <p>
	 * When done with the object, it may be returned to the pool using either the
	 * {@link Pooled#free()} method or the {@link Pooled#close()} method. Do
	 * not call both, that would be a double-free.
	 * </p>
	 * 
	 * <p>
	 * It is valid to not return ("steal") a claimed object to the pool. That
	 * prevents the pool from providing that object ever again.
	 * </p>
	 *
	 * @return A {@code Pooled} instance containing an object of type {@code E}.
	 */
	Pooled<E> claim();
	
}
