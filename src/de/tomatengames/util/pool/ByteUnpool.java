package de.tomatengames.util.pool;

/**
 * Represents an implementation of {@link BytePool} that does not reuse byte
 * arrays. Instead, it simply creates a new byte array each time {@code claim}
 * is called, with exactly the requested length.
 *
 * @version 2025-03-29 created
 * @since 1.8
 */
public class ByteUnpool implements BytePool {
	
	/**
	 * Creates a new {@link ByteUnpool}.
	 */
	public ByteUnpool() {
	}
	
	@Override
	public Pooled<byte[]> claim(int minLength) {
		return new Unpool.Unpooled<>(new byte[minLength]);
	}
	
}
