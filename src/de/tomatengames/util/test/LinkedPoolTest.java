package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.pool.LinkedPool;
import de.tomatengames.util.pool.Pool;
import de.tomatengames.util.pool.Pooled;

class LinkedPoolTest {
	
	private static class LargeClaim {
		private final List<Pooled<?>> claims;
		public LargeClaim(Pool<?> pool, int amount) {
			this.claims = new ArrayList<>();
			for (int i = 0; i < amount; i++)
				this.claims.add(pool.claim());
		}
		public void free() {
			this.claims.forEach(Pooled::free);
		}
	}
	
	private static LargeClaim claimAmount(Pool<?> pool, int amount) {
		return new LargeClaim(pool, amount);
	}
	
	private static int peekId(Pool<Element> pool) {
		try (Pooled<Element> pooled = pool.claim()) {
			return pooled.get().id;
		}
	}
	
	@Test
	void testClaimFree() {
		LinkedPool<Element> pool = Element.pool();
		assertEquals(0, pool.size());
		assertEquals(1, peekId(pool));
		assertEquals(1, pool.size());
		Pooled<Element> p1 = pool.claim(), p2 = pool.claim();
		assertEquals(1, p1.get().id);
		assertEquals(2, p2.get().id);
		assertEquals(0, pool.size());
		p1.free();
		p2.free();
		assertEquals(2, pool.size());
		// 1 and 2 swap because 2 is now on top
		p1 = pool.claim();
		p2 = pool.claim();
		assertEquals(2, p1.get().id);
		assertEquals(1, p2.get().id);
		assertEquals(0, pool.size());
	}
	@Test
	void testDoubleFree() {
		LinkedPool<?> pool = new LinkedPool<>(() -> null);
		Pooled<?> pooled = pool.claim();
		pooled.free();
		assertThrows(IllegalStateException.class, () -> pooled.free());
	}
	
	@Test
	void testClean() {
		LinkedPool<Element> pool = Element.pool();
		claimAmount(pool, 100).free();
		assertEquals(100, pool.size());
		pool.clean();
		assertEquals(100, pool.size());
		claimAmount(pool, 30).free();
		pool.clean();
		assertEquals(30, pool.size());
		assertEquals(30, peekId(pool));
		pool.clean();
		assertEquals(1, pool.size());
		pool.clean();
		assertEquals(0, pool.size());
		pool.clean();
		assertEquals(0, pool.size());
	}
	
	static class Element {
		public final int id;
		public Element(int id) {
			this.id = id;
		}
		public static Supplier<Element> factory() {
			AtomicInteger nextId = new AtomicInteger(1);
			return () -> new Element(nextId.getAndIncrement());
		}
		public static LinkedPool<Element> pool() {
			return new LinkedPool<>(factory());
		}
	}
	
}
