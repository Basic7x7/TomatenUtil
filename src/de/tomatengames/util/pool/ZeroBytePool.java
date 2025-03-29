package de.tomatengames.util.pool;

/**
 * A BytePool wrapper that clears (zeros) the provided byte arrays on free.
 *
 * <p>
 * If this is the only access to the underlying BytePool, then all unused byte
 * arrays are clear and are clear when claimed.
 * </p>
 *
 * <p>
 * This may wrap other BytePools to improve
 * <ul>
 * <li>Data protection: Caller data does not remain in memory of the pool but
 * instead gets immediately deleted when no longer used.</li>
 * <li>Fail-safety: Caller data written to and left in the byte array is never
 * given to other callers. Callers should never use the contents of their
 * claimed byte arrays without overwriting them first, but that may happen
 * through bugs in caller code. Clearing the contents can drastically lower the
 * severity of such a bug because it is just zeros then.</li>
 * </ul>
 * Unfortunately, clearing is a significant performance overhead, worsened by
 * the lack of a standard accelerated way to do such clearing in Java. So, the
 * decision to use this wrapper involves weighing security against efficiency.
 * </p>
 *
 * @version 2025-03-29 created
 * @since 1.8
 */
public class ZeroBytePool implements BytePool {
	
	private static final byte[] ZERO = new byte[1024];
	
	/**
	 * Clears all elements in the specified byte array by setting them to zero.
	 *
	 * @param array the byte array to be zeroed out
	 */
	public static void zero(byte[] array) {
		zero(array, 0, array.length);
	}
	
	/**
	 * Clears the specified range in the specified byte array by setting its
	 * elements in that range to zero.
	 *
	 * @param array the byte array to be zeroed out
	 * @param off   the starting index within the array where zeroing begins
	 * @param len   the number of bytes to set to zero
	 */
	public static void zero(byte[] array, int off, int len) {
		while (len > 0) {
			System.arraycopy(ZERO, 0, array, off, Math.min(1024, len));
			off += 1024;
			len -= 1024;
		}
	}
	
	
	private final BytePool source;
	
	/**
	 * Constructs a new ZeroBytePool that wraps the specified source BytePool.
	 *
	 * @param source the underlying BytePool to wrap
	 */
	public ZeroBytePool(BytePool source) {
		this.source = source;
	}
	
	@Override
	public Pooled<byte[]> claim(int minLength) {
		return new ZeroPooled(this.source.claim(minLength));
	}
	
	private static class ZeroPooled implements Pooled<byte[]> {
		private final Pooled<byte[]> base;
		
		public ZeroPooled(Pooled<byte[]> base) {
	        this.base = base;
	    }
		
		@Override
		public byte[] get() {
			return this.base.get();
		}
		@Override
		public void free() {
			zero(this.base.get());
			this.base.free();
		}
	}
	
}
