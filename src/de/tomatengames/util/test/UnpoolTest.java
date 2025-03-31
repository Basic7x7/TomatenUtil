package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.pool.BytePool;
import de.tomatengames.util.pool.ByteUnpool;
import de.tomatengames.util.pool.Pool;
import de.tomatengames.util.pool.Pooled;
import de.tomatengames.util.pool.Unpool;
import de.tomatengames.util.test.LinkedPoolTest.Element;

class UnpoolTest {
	
	@Test
	void testUnpool() {
		Pool<Element> pool = new Unpool<>(Element.factory());
		try (Pooled<Element> pooled = pool.claim()) {
			assertEquals(1, pooled.get().id);
		}
		try (Pooled<Element> pooled = pool.claim()) {
			assertEquals(2, pooled.get().id);
		}
	}
	
	@Test
	void testByteUnpool() {
		BytePool pool = new ByteUnpool();
		assertArrayEquals(new byte[100], pool.claim(100).get());
	}
	
}
