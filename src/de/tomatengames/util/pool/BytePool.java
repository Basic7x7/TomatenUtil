package de.tomatengames.util.pool;

/**
 * Represents a pool for byte arrays. It allows to claim byte arrays of at least
 * a specified minimum length.
 *
 * @version 2025-03-29 created
 * @since 1.8
 */
public interface BytePool {
	
	/**
	 * Claims a byte array that is at least as long as the specified minimum length.
	 *
	 * @param minLength The minimum required length of the byte array.
	 * @return A {@link Pooled} instance wrapping a byte array of at least the
	 *         specified minimum length.
	 */
	Pooled<byte[]> claim(int minLength);
	
}
