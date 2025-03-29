package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.pool.BytePool;
import de.tomatengames.util.pool.LevelBytePool;
import de.tomatengames.util.pool.Pooled;
import de.tomatengames.util.pool.ZeroBytePool;

class ZeroBytePoolTest {
	
	private static void assertZero(byte[] b) {
		for (byte el : b)
			assertEquals(0, el);
	}
	private static void check(BytePool pool, int minLength) {
		try (Pooled<byte[]> pooled = pool.claim(10)) {
			assertZero(pooled.get());
			Arrays.fill(pooled.get(), (byte)1);
		}
	}
	
	@Test
	void testZeroPool() {
		BytePool pool = new ZeroBytePool(new LevelBytePool());
		check(pool, 10); // initially zero
		check(pool, 10); // zero in next cycle
		check(pool, 5000); // also for larger arrays
		check(pool, 5000);
	}
	
	@Test
	void testZero() {
		byte[] b = new byte[5000];
		Arrays.fill(b, (byte)1);
		ZeroBytePool.zero(b);
		assertZero(b);
	}
	
}
