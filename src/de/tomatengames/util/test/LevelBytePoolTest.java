package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.tomatengames.util.pool.BytePool;
import de.tomatengames.util.pool.LevelBytePool;

class LevelBytePoolTest {
	
	@Test
	void testClaim() {
		BytePool pool = new LevelBytePool();
		assertEquals(32, pool.claim(Integer.MIN_VALUE).get().length);
		assertEquals(32, pool.claim(-1).get().length);
		assertEquals(32, pool.claim(0).get().length);
		assertEquals(32, pool.claim(32).get().length);
		assertEquals(64, pool.claim(33).get().length);
		assertEquals(128, pool.claim(128).get().length);
		assertEquals(256, pool.claim(129).get().length);
		assertEquals(256, pool.claim(256).get().length);
	}
	
	@Disabled
	@Test
	void testClaimLarge() {
		BytePool pool = new LevelBytePool();
		assertEquals(1 << 30, pool.claim(1000000000).get().length);
		assertEquals(Integer.MAX_VALUE - 8, pool.claim(1500000000).get().length);
		assertEquals(Integer.MAX_VALUE - 8, pool.claim(Integer.MAX_VALUE - 8).get().length);
	}
	
	@Test
	void testClaimTooLarge() {
		BytePool pool = new LevelBytePool();
		assertThrows(IllegalArgumentException.class, () -> pool.claim(Integer.MAX_VALUE - 7));
		assertThrows(IllegalArgumentException.class, () -> pool.claim(Integer.MAX_VALUE));
	}
	
}
