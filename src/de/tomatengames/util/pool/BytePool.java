package de.tomatengames.util.pool;

/**
 * The {@code BytePool} class provides a pool of byte arrays, allowing efficient
 * management and reuse of byte arrays with varying lengths. It uses multiple
 * {@link LinkedPool} instances to manage arrays of different sizes.
 *
 * @version 2025-03-26 created
 * @since 1.8
 */
public class BytePool {
	
	private static final int LEFT_OUT_EXPONENTS = 5;
	private static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
	
	private final LinkedPool<byte[]>[] pools;
	
	/**
	 * Constructs a new {@code BytePool} instance with pre-configured pools for byte
	 * arrays of different sizes.
	 */
	@SuppressWarnings("unchecked")
	public BytePool() {
		this.pools = new LinkedPool[32 - LEFT_OUT_EXPONENTS];
		for (int i = 0; i < this.pools.length; i++) {
			int exp = i + LEFT_OUT_EXPONENTS;
			int poolArrayLength = exp >= 31 ? MAX_ARRAY_LENGTH : 1 << exp;
			this.pools[i] = new LinkedPool<>(() -> new byte[poolArrayLength]);
		}
	}
	
	/**
	 * Cleans all pools by reducing their sizes to the minimum size recorded since
	 * the last clean operation.
	 */
	public void clean() {
		for (LinkedPool<byte[]> pool : this.pools)
			pool.clean();
	}
	
	/**
	 * Retrieves a pool that provides byte arrays that are at least as long as the
	 * specified minimum length.
	 *
	 * @param minLength The minimum required length of the byte arrays.
	 * @return A {@link Pool} instance for byte arrays of at least the specified
	 *         minimum length.
	 * @throws IllegalArgumentException If the requested array size exceeds the
	 *                                  maximum supported size.
	 */
	public Pool<byte[]> ofLength(int minLength) {
		if (minLength > MAX_ARRAY_LENGTH)
			throw new IllegalArgumentException("Unsupported array size " + minLength);
		int poolIndex;
		if (minLength <= 0) {
			poolIndex = 0;
		} else {
			int log2 = log2(minLength);
			poolIndex = log2 - LEFT_OUT_EXPONENTS;
			if (poolIndex < 0)
				poolIndex = 0;
		}
		return this.pools[poolIndex];
	}
	
	/**
	 * Claims a byte array that is at least as long as the specified minimum length.
	 *
	 * @param minLength The minimum required length of the byte array.
	 * @return A {@link Pooled} instance wrapping a byte array of at least the
	 *         specified minimum length.
	 */
	public Pooled<byte[]> claim(int minLength) {
		return ofLength(minLength).claim();
	}
	
	private static int log2(int i) {
		return 32 - Integer.numberOfLeadingZeros(i - 1);
	}
	
}
