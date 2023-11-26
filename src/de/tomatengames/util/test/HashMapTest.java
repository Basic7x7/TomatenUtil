package de.tomatengames.util.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.map.Int3HashMap;
import de.tomatengames.util.map.IntEntry;
import de.tomatengames.util.map.IntHashMap;
import de.tomatengames.util.map.Long2HashMap;
import de.tomatengames.util.map.LongHashMap;
import de.tomatengames.util.map.ReferenceHashMap;

class HashMapTest {
	
	@Test
	void testPutAndGet() {
		IntHashMap<String> map = new IntHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put(0, "n0")); assertEquals(1, map.size());
		assertEquals(null, map.put(5, "n5")); assertEquals(2, map.size());
		assertEquals(null, map.put(-100, "100")); assertEquals(3, map.size());
		assertEquals(null, map.put(1347856348, "1")); assertEquals(4, map.size());
		
		assertEquals(null, map.get(-1));
		assertEquals("n0", map.get(0));
		assertEquals(null, map.get(1347856349));
		assertEquals("1", map.get(1347856348));
		assertEquals("100", map.get(-100));
		assertEquals(null, map.get(6));
		assertEquals("n5", map.get(5));
		assertEquals(null, map.get(4));
		assertEquals(4, map.size());
	}
	
	@Test
	void testPutConflict() {
		Int3HashMap<String> map = new Int3HashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put(0, 0, 0, "0")); assertEquals(1, map.size());
		assertEquals(null, map.put(5, 6, 7, "(5, 6, 7)")); assertEquals(2, map.size());
		assertEquals(null, map.put(5, 7, 7, "test")); assertEquals(3, map.size());
		assertEquals(null, map.put(0, 10045623, -4564398, "ABC")); assertEquals(4, map.size());
		assertEquals("(5, 6, 7)", map.put(5, 6, 7, "xyz")); assertEquals(4, map.size());
		assertEquals("xyz", map.put(5, 6, 7, "XYZ")); assertEquals(4, map.size());
		assertEquals("test", map.put(5, 7, 7, "Test")); assertEquals(4, map.size());
		assertEquals(null, map.put(0, 1, 2, "1")); assertEquals(5, map.size());
		
		assertEquals(null, map.get(-1, 0, 1));
		assertEquals(null, map.get(0, 0, 1));
		assertEquals("0", map.get(0, 0, 0));
		assertEquals("XYZ", map.get(5, 6, 7));
		assertEquals("Test", map.get(5, 7, 7));
		assertEquals(null, map.get(5, 8, 7));
		assertEquals("1", map.get(0, 1, 2));
		assertEquals(5, map.size());
	}
	
	@Test
	void testManyElements() {
		LongHashMap<String> map = new LongHashMap<>();
		for (long i = 0; i < 100000L; i++) {
			assertEquals(i, map.size());
			map.put(i, String.valueOf(i));
		}
		
		assertEquals("43524", map.get(43524L));
		assertEquals("42", map.get(42L));
		assertEquals("0", map.get(0L));
		assertEquals(null, map.get(-10L));
		assertEquals(null, map.get(200000L));
	}
	
	@Test
	void testManyElementsInSameBucket() {
		IntHashMap<String> map = new IntHashMap<>();
		for (int i = 0; i < 10000; i++) {
			assertEquals(i, map.size());
			map.put(i << 16, String.valueOf(i));
		}
		
		assertEquals("4352", map.get(4352 << 16));
		assertEquals("42", map.get(42 << 16));
		assertEquals("0", map.get(0));
		assertEquals(null, map.get(-10));
		assertEquals(null, map.get(10000));
	}
	
	@Test
	void testRemove() {
		Long2HashMap<String> map = new Long2HashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.remove(0, 0)); assertEquals(0, map.size());
		assertEquals(null, map.put(0, 0, "00")); assertEquals(1, map.size());
		assertEquals(null, map.remove(1, 2)); assertEquals(1, map.size());
		assertEquals(null, map.put(42L, 778342985720L, "test")); assertEquals(2, map.size());
		assertEquals(null, map.remove(42L, 778342985719L)); assertEquals(2, map.size());
		assertEquals("test", map.remove(42L, 778342985720L)); assertEquals(1, map.size());
		assertEquals(null, map.put(0, 1 << 16, "abc")); assertEquals(2, map.size());
		assertEquals(null, map.remove(0, 1)); assertEquals(2, map.size());
		assertEquals("00", map.remove(0, 0)); assertEquals(1, map.size());
		assertEquals("abc", map.remove(0, 1 << 16)); assertEquals(0, map.size());
	}
	
	@Test
	void testContains() {
		IntHashMap<String> map = new IntHashMap<>(); assertEquals(true, map.isEmpty());
		assertEquals(false, map.containsKey(0)); assertEquals(true, map.isEmpty());
		assertEquals(null, map.put(0, "0")); assertEquals(false, map.isEmpty());
		assertEquals(true, map.containsKey(0));
		assertEquals(false, map.containsKey(6));
		assertEquals(null, map.put(6, "test")); assertEquals(2, map.size());
		assertEquals(true, map.containsKey(6));
		assertEquals(null, map.put(-7, "abc")); assertEquals(3, map.size());
		assertEquals(true, map.containsKey(-7));
		assertEquals(false, map.containsKey(-6));
	}
	
	@Test
	void testIterator() {
		IntHashMap<String> map = new IntHashMap<>();
		assertEquals(null, map.put(0, "test"));
		assertEquals(null, map.put(1 << 16, "ABC"));
		assertEquals("test", map.put(0, "abc"));
		assertEquals(null, map.put(15, "xyz"));
		assertEquals(null, map.put(42, "24"));
		assertEquals(null, map.put(-7, "7"));
		assertEquals(5, map.size());
		
		// Build a HashMap to compare.
		HashMap<Integer, String> compareMap = new HashMap<>();
		compareMap.put(0, "abc");
		compareMap.put(1 << 16, "ABC");
		compareMap.put(15, "xyz");
		compareMap.put(42, "24");
		compareMap.put(-7, "7");
		
		HashMap<Integer, String> comp1 = new HashMap<>(compareMap);
		for (IntEntry<String> entry : map) {
			String val = comp1.remove(entry.getKey());
			assertEquals(val, entry.getValue(), "Mismatch at key '" + entry.getKey() + "'");
		}
		assertTrue(comp1.isEmpty());
		
		HashMap<Integer, String> comp2 = new HashMap<>(compareMap);
		map.forEach(entry -> {
			String val = comp2.remove(entry.getKey());
			assertEquals(val, entry.getValue(), "Mismatch at key '" + entry.getKey() + "'");
		});
		assertTrue(comp2.isEmpty());
	}
	
	@Test
	void testIteratorEmpty() {
		IntHashMap<String> map = new IntHashMap<>();
		
		for (IntEntry<String> entry : map) {
			fail("Unexpected key '" + entry.getKey() + "'");
		}
		
		map.forEach(entry -> fail("Unexpected key '" + entry.getKey() + "'"));
	}
	
	@Test
	void testPutIfAbsent() {
		IntHashMap<String> map = new IntHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.putIfAbsent(4, "test")); assertEquals(1, map.size());
		assertEquals("test", map.putIfAbsent(4, "abc")); assertEquals(1, map.size());
		assertEquals("test", map.putIfAbsent(4, "ABC")); assertEquals(1, map.size());
		assertEquals(null, map.putIfAbsent(7, "ABC")); assertEquals(2, map.size());
		assertEquals("ABC", map.putIfAbsent(7, "xyz")); assertEquals(2, map.size());
		assertEquals("test", map.remove(4)); assertEquals(1, map.size());
		assertEquals("ABC", map.get(7));
		assertEquals(null, map.putIfAbsent(4, "ABC")); assertEquals(2, map.size());
		assertEquals("ABC", map.get(4));
		
		assertEquals(null, map.putIfAbsent(42, "24")); assertEquals(3, map.size());
		assertEquals(null, map.putIfAbsent(1 << 16 + 42, "42")); assertEquals(4, map.size());
		assertEquals("24", map.putIfAbsent(42, "a")); assertEquals(4, map.size());
		assertEquals("42", map.putIfAbsent(1 << 16 + 42, "b")); assertEquals(4, map.size());
		assertEquals("24", map.get(42));
		assertEquals("42", map.get(1 << 16 + 42));
	}
	
	@Test
	void testClear() {
		IntHashMap<String> map = new IntHashMap<>(); assertEquals(0, map.size());
		map.clear(); assertEquals(0, map.size());
		assertEquals(null, map.put(4, "test")); assertEquals(1, map.size());
		assertEquals(null, map.put(2, "abc")); assertEquals(2, map.size());
		assertEquals("test", map.put(4, "test2")); assertEquals(2, map.size());
		assertEquals(null, map.put(42, "xyz")); assertEquals(3, map.size());
		
		assertEquals("abc", map.get(2));
		assertEquals("test2", map.get(4));
		
		map.clear();
		assertEquals(0, map.size());
		
		assertEquals(null, map.get(2));
		assertEquals(null, map.get(4));
	}
	
	@Test
	void testClone() {
		IntHashMap<String> map = new IntHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put(4, "test")); assertEquals(1, map.size());
		assertEquals(null, map.put(2, "abc")); assertEquals(2, map.size());
		assertEquals(null, map.put(42, "xyz")); assertEquals(3, map.size());
		
		IntHashMap<String> clone = new IntHashMap<>(map);
		assertEquals(null, clone.put(0, "clone"));
		assertEquals(4, clone.size());
		assertEquals(3, map.size());
		
		assertEquals(null, map.get(0));
		assertEquals("clone", clone.get(0));
		assertEquals("abc", clone.get(2));
		assertEquals("test", clone.get(4));
		assertEquals("xyz", clone.get(42));
		assertEquals(null, clone.get(40));
	}
	
	@Test
	void testConcurrentModification() {
		IntHashMap<String> map = new IntHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put(2, "abc")); assertEquals(1, map.size());
		assertEquals(null, map.put(4, "test")); assertEquals(2, map.size());
		
		assertThrows(ConcurrentModificationException.class, () -> {
			for (IntEntry<String> entry : map) {
				map.put(24, entry.getValue());
			}
		});
	}
	
	@Test
	void testFilterIterator() {
		IntHashMap<String> map = new IntHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put(1 << 16, "2^16")); assertEquals(1, map.size());
		assertEquals(null, map.put(0, "abc")); assertEquals(2, map.size());
		assertEquals(null, map.put(4, "test")); assertEquals(3, map.size());
		assertEquals(null, map.put(42, "a")); assertEquals(4, map.size());
		assertEquals(null, map.put(7, "b")); assertEquals(5, map.size());
		assertEquals(null, map.put(12, "c")); assertEquals(6, map.size());
		assertEquals(null, map.put(24, "d")); assertEquals(7, map.size());
		assertEquals(null, map.put(-3, "e")); assertEquals(8, map.size());
		assertEquals(null, map.put(100, "f")); assertEquals(9, map.size());
		
		Iterator<IntEntry<String>> it = map.iterator();
		while (it.hasNext()) {
			IntEntry<String> entry = it.next();
			if (entry.getKey() > 10) {
				it.remove();
			}
		}
		
		assertEquals("abc", map.get(0));
		assertEquals("test", map.get(4));
		assertEquals(null, map.get(42));
		assertEquals("b", map.get(7));
		assertEquals(null, map.get(12));
		assertEquals(null, map.get(24));
		assertEquals("e", map.get(-3));
		assertEquals(null, map.get(100));
		assertEquals(null, map.get(1 << 16));
		
		assertEquals(4, map.size());
	}
	
	@Test
	void testModifyIterator() {
		IntHashMap<Integer> map = new IntHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put(2, 2)); assertEquals(1, map.size());
		assertEquals(null, map.put(4, 4)); assertEquals(2, map.size());
		assertEquals(null, map.put(42, 42)); assertEquals(3, map.size());
		assertEquals(null, map.put(-7, -7)); assertEquals(4, map.size());
		assertEquals(null, map.put(12, 12)); assertEquals(5, map.size());
		
		assertEquals(4, map.get(4));
		assertEquals(42, map.get(42));
		
		for (IntEntry<Integer> entry : map) {
			entry.setValue(entry.getKey()*2);
		}
		
		assertEquals(4, map.get(2));
		assertEquals(8, map.get(4));
		assertEquals(84, map.get(42));
		assertEquals(-14, map.get(-7));
		assertEquals(24, map.get(12));
		assertEquals(null, map.get(5));
		assertEquals(null, map.get(24));
		
		assertEquals(5, map.size());
	}
	
	@Test
	void testEquals() {
		IntHashMap<String> map1 = new IntHashMap<>();
		assertEquals(null, map1.put(2, "a"));
		assertEquals(null, map1.put(4, "b"));
		assertEquals(null, map1.put(42, "c"));
		assertEquals(3, map1.size());
		
		IntHashMap<String> map2 = new IntHashMap<>();
		assertEquals(null, map2.put(2, "a"));
		assertEquals(null, map2.put(4, "b"));
		assertEquals(null, map2.put(42, "c"));
		assertEquals(3, map2.size());
		
		IntHashMap<String> map3 = new IntHashMap<>();
		assertEquals(null, map3.put(2, "d"));
		assertEquals(null, map3.put(4, "b"));
		assertEquals(null, map3.put(42, "c"));
		assertEquals(3, map3.size());
		
		IntHashMap<String> map4 = new IntHashMap<>();
		assertEquals(null, map4.put(2, "a"));
		assertEquals(null, map4.put(4, "b"));
		assertEquals(2, map4.size());
		
		IntHashMap<String> map5 = new IntHashMap<>();
		assertEquals(null, map5.put(3, "d"));
		assertEquals(null, map5.put(5, "b"));
		assertEquals(null, map5.put(43, "c"));
		assertEquals(3, map5.size());
		
		assertTrue(map1.equals(map2));
		assertFalse(map1.equals(map3));
		assertFalse(map1.equals(map4));
		assertFalse(map1.equals(map5));
		assertTrue(map2.equals(map1));
		assertFalse(map2.equals(map3));
		assertFalse(map2.equals(map4));
		assertFalse(map2.equals(map5));
		assertFalse(map3.equals(map1));
		assertFalse(map3.equals(map2));
		assertFalse(map3.equals(map4));
		assertFalse(map3.equals(map5));
		
		assertTrue(map1.equals(map1));
		assertTrue(map2.equals(map2));
		assertTrue(map3.equals(map3));
		assertTrue(map4.equals(map4));
		assertTrue(map5.equals(map5));
		
		assertFalse(map4.equals(new IntHashMap<String>()));
		assertTrue(new IntHashMap<String>().equals(new IntHashMap<String>()));
		assertFalse(map1.equals(null));
	}
	
	
	@Test
	void testReferenceMap() {
		A a1 = new A(1);
		A a2 = new A(2);
		A a3 = new A(1); // equals a1
		A a4 = new A(4);
		
		ReferenceHashMap<A, String> map = new ReferenceHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put(a1, "A")); assertEquals(1, map.size());
		assertEquals(null, map.put(a2, "B")); assertEquals(2, map.size());
		assertEquals("A", map.get(a1));
		assertEquals(null, map.get(a3)); // by reference
		
		assertEquals(null, map.put(a3, "C")); assertEquals(3, map.size());
		assertEquals("A", map.get(a1));
		assertEquals("C", map.get(a3));
		
		assertEquals("A", map.remove(a1)); assertEquals(2, map.size());
		assertEquals("C", map.get(a3));
		assertEquals("B", map.get(a2));
		
		assertEquals(null, map.remove(a4)); assertEquals(2, map.size());
		
		map.clear(); assertEquals(0, map.size());
		assertEquals(null, map.get(a2));
	}
	
	@Test
	void testReferenceMapEquals() {
		A a1 = new A(1);
		A a3 = new A(1); // equals a1
		
		ReferenceHashMap<A, String> map1 = new ReferenceHashMap<>();
		ReferenceHashMap<A, String> map2 = new ReferenceHashMap<>();
		ReferenceHashMap<String, String> map3 = new ReferenceHashMap<>();
		
		assertEquals(true, map1.equals(map2));
		assertEquals(true, map1.equals(map3)); // Both are equal. The generic type cannot be checked.
		
		map1.put(a1, "A");
		map2.put(a3, "A");
		assertEquals(false, map1.equals(map2));
		assertEquals(false, map1.equals(map3));
		
		map3.put("test", "A");
		assertEquals(false, map1.equals(map3));
	}
	
	private static class A {
		private final int x;
		
		public A(int x) {
			this.x = x;
		}
		
		@Override
		public boolean equals(Object obj) {
			return obj.getClass() == this.getClass() && ((A) obj).x == this.x;
		}
		
		@Override
		public int hashCode() {
			return this.x;
		}
	}
}
