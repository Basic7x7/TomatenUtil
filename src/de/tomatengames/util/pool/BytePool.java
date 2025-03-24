package de.tomatengames.util.pool;

public class BytePool {
	
	private static final int LEFT_OUT_EXPONENTS = 5;
	private static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
	
	private final LinkedPool<byte[]>[] pools;
	
	@SuppressWarnings("unchecked")
	public BytePool() {
		this.pools = new LinkedPool[32 - LEFT_OUT_EXPONENTS];
		for (int i = 0; i < this.pools.length; i++) {
			int exp = i + LEFT_OUT_EXPONENTS;
			int poolArrayLength = exp >= 31 ? MAX_ARRAY_LENGTH : 1 << exp;
			this.pools[i] = new LinkedPool<>(() -> new byte[poolArrayLength]);
		}
	}
	
	public void clean() {
		for (LinkedPool<byte[]> pool : this.pools)
			pool.clean();
	}
	
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
	public Pooled<byte[]> claim(int minLength) {
		return ofLength(minLength).claim();
	}
	
	private static int log2(int i) {
		return 32 - Integer.numberOfLeadingZeros(i - 1);
	}
	
}
