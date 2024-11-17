package de.tomatengames.util.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.map.OrderedHashMap;

class OrderedHashMapTest {
	
	@Test
	void testPutAndGet() {
		OrderedHashMap<Integer, String> map = new OrderedHashMap<>(); assertEquals(0, map.size());
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
		
		Iterator<Entry<Integer, String>> it = map.iterator();
		assertEquals(true, it.hasNext()); assertEntry(0, "n0", it.next());
		assertEquals(true, it.hasNext()); assertEntry(5, "n5", it.next());
		assertEquals(true, it.hasNext()); assertEntry(-100, "100", it.next());
		assertEquals(true, it.hasNext()); assertEntry(1347856348, "1", it.next());
		assertEquals(false, it.hasNext());
		assertThrows(NoSuchElementException.class, () -> it.next());
	}
	
	@Test
	void testPutConflict() {
		OrderedHashMap<String, String> map = new OrderedHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put("0, 0, 0", "0")); assertEquals(1, map.size());
		assertEquals(null, map.put("5, 6, 7", "(5, 6, 7)")); assertEquals(2, map.size());
		assertEquals(null, map.put("5, 7, 7", "test")); assertEquals(3, map.size());
		assertEquals(null, map.put("0, 10045623, -4564398", "ABC")); assertEquals(4, map.size());
		assertEquals("(5, 6, 7)", map.put("5, 6, 7", "xyz")); assertEquals(4, map.size());
		assertEquals("xyz", map.put("5, 6, 7", "XYZ")); assertEquals(4, map.size());
		assertEquals("test", map.put("5, 7, 7", "Test")); assertEquals(4, map.size());
		assertEquals(null, map.put("0, 1, 2", "1")); assertEquals(5, map.size());
		
		assertEquals(null, map.get("-1, 0, 1"));
		assertEquals(null, map.get("0, 0, 1"));
		assertEquals("0", map.get("0, 0, 0"));
		assertEquals("XYZ", map.get("5, 6, 7"));
		assertEquals("Test", map.get("5, 7, 7"));
		assertEquals(null, map.get("5, 8, 7"));
		assertEquals("1", map.get("0, 1, 2"));
		assertEquals(5, map.size());
		
		Iterator<Entry<String, String>> it = map.iterator();
		assertEntry("0, 0, 0", "0", it.next());
		assertEntry("5, 6, 7", "XYZ", it.next());
		assertEntry("5, 7, 7", "Test", it.next());
		assertEntry("0, 10045623, -4564398", "ABC", it.next());
		assertEntry("0, 1, 2", "1", it.next());
		assertThrows(NoSuchElementException.class, () -> it.next());
	}
	
	@Test
	void testManyElements() {
		OrderedHashMap<Long, String> map = new OrderedHashMap<>();
		for (long i = 0; i < 100000L; i++) {
			assertEquals(i, map.size());
			map.put(i, String.valueOf(i));
		}
		
		assertEquals("43524", map.get(43524L));
		assertEquals("42", map.get(42L));
		assertEquals("0", map.get(0L));
		assertEquals(null, map.get(-10L));
		assertEquals(null, map.get(200000L));
		
		Iterator<Entry<Long, String>> it = map.iterator();
		for (long i = 0; i < 100000L; i++) {
			assertEntry(i, String.valueOf(i), it.next());
		}
		assertThrows(NoSuchElementException.class, () -> it.next());
	}
	
	@Test
	void testManyElementsInSameBucket() {
		OrderedHashMap<Integer, String> map = new OrderedHashMap<>();
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
		OrderedHashMap<String, String> map = new OrderedHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.remove("0, 0")); assertEquals(0, map.size());
		assertEquals(null, map.put("0, 0", "00")); assertEquals(1, map.size());
		assertEquals(null, map.put("1, 2", "11")); assertEquals(2, map.size());
		assertEquals("11", map.remove("1, 2")); assertEquals(1, map.size());
		assertEquals(null, map.put("42, 778342985720", "test")); assertEquals(2, map.size());
		assertEquals(null, map.remove("42, 778342985719")); assertEquals(2, map.size());
		
		Iterator<Entry<String, String>> it = map.iterator();
		assertEntry("0, 0", "00", it.next());
		assertEntry("42, 778342985720", "test", it.next());
		assertThrows(NoSuchElementException.class, () -> it.next());
		
		assertEquals("test", map.remove("42, 778342985720")); assertEquals(1, map.size());
		assertEquals("00", map.remove("0, 0")); assertEquals(0, map.size());
	}
	
	@Test
	void testContains() {
		OrderedHashMap<Integer, String> map = new OrderedHashMap<>(); assertEquals(true, map.isEmpty());
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
		OrderedHashMap<Integer, String> map = new OrderedHashMap<>();
		assertEquals(null, map.put(0, "test"));
		assertEquals(null, map.put(1 << 16, "ABC"));
		assertEquals("test", map.put(0, "abc"));
		assertEquals(null, map.put(15, "xyz"));
		assertThrows(IllegalArgumentException.class, () -> map.put(15, null));
		assertEquals(null, map.put(42, "24"));
		assertEquals(null, map.put(-7, "7"));
		assertEquals(5, map.size());
		
		Iterator<Entry<Integer, String>> it = map.iterator();
		assertEquals(true, it.hasNext()); assertEntry(0, "abc", it.next());
		assertEquals(true, it.hasNext()); assertEntry(1 << 16, "ABC", it.next());
		assertEquals(true, it.hasNext()); assertEntry(15, "xyz", it.next());
		assertEquals(true, it.hasNext()); assertEntry(42, "24", it.next());
		assertEquals(true, it.hasNext()); assertEntry(-7, "7", it.next());
		assertEquals(false, it.hasNext());
		assertThrows(NoSuchElementException.class, () -> it.next());
		
		Iterator<Entry<Integer, String>> it2 = map.iterator();
		map.forEach(entry -> assertEquals(it2.next(), entry));
	}
	
	@Test
	void testIteratorEmpty() {
		OrderedHashMap<Integer, String> map = new OrderedHashMap<>();
		
		for (Entry<Integer, String> entry : map) {
			fail("Unexpected key '" + entry.getKey() + "'");
		}
		
		map.forEach(entry -> fail("Unexpected key '" + entry.getKey() + "'"));
	}
	
	@Test
	void testPutIfAbsent() {
		OrderedHashMap<Integer, String> map = new OrderedHashMap<>(); assertEquals(0, map.size());
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
		
		Iterator<Entry<Integer, String>> it = map.iterator();
		assertEntry(7, "ABC", it.next());
		assertEntry(4, "ABC", it.next());
		assertEntry(42, "24", it.next());
		assertEntry(1 << 16 + 42, "42", it.next());
		assertThrows(NoSuchElementException.class, () -> it.next());
	}
	
	@Test
	void testClear() {
		OrderedHashMap<Integer, String> map = new OrderedHashMap<>(); assertEquals(0, map.size());
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
		OrderedHashMap<Integer, String> map = new OrderedHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put(4, "test")); assertEquals(1, map.size());
		assertEquals(null, map.put(2, "abc")); assertEquals(2, map.size());
		assertEquals(null, map.put(42, "xyz")); assertEquals(3, map.size());
		
		OrderedHashMap<Integer, String> clone = new OrderedHashMap<>(map);
		assertEquals(null, clone.put(0, "clone"));
		assertEquals(4, clone.size());
		assertEquals(3, map.size());
		
		assertEquals(null, map.get(0));
		assertEquals("clone", clone.get(0));
		assertEquals("abc", clone.get(2));
		assertEquals("test", clone.get(4));
		assertEquals("xyz", clone.get(42));
		assertEquals(null, clone.get(40));
		
		Iterator<Entry<Integer, String>> it = clone.iterator();
		assertEntry(4, "test", it.next());
		assertEntry(2, "abc", it.next());
		assertEntry(42, "xyz", it.next());
		assertEntry(0, "clone", it.next());
		assertThrows(NoSuchElementException.class, () -> it.next());
	}
	
	@Test
	void testConcurrentModification() {
		OrderedHashMap<Integer, String> map = new OrderedHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put(2, "abc")); assertEquals(1, map.size());
		assertEquals(null, map.put(4, "test")); assertEquals(2, map.size());
		
		assertThrows(ConcurrentModificationException.class, () -> {
			for (Entry<Integer, String> entry : map) {
				map.put(24, entry.getValue());
			}
		});
	}
	
	@Test
	void testFilterIterator() {
		OrderedHashMap<Integer, String> map = new OrderedHashMap<>(); assertEquals(0, map.size());
		assertEquals(null, map.put(1 << 16, "2^16")); assertEquals(1, map.size());
		assertEquals(null, map.put(0, "abc")); assertEquals(2, map.size());
		assertEquals(null, map.put(4, "test")); assertEquals(3, map.size());
		assertEquals(null, map.put(42, "a")); assertEquals(4, map.size());
		assertEquals(null, map.put(7, "b")); assertEquals(5, map.size());
		assertEquals(null, map.put(12, "c")); assertEquals(6, map.size());
		assertEquals(null, map.put(24, "d")); assertEquals(7, map.size());
		assertEquals(null, map.put(-3, "e")); assertEquals(8, map.size());
		assertEquals(null, map.put(100, "f")); assertEquals(9, map.size());
		
		Iterator<Entry<Integer, String>> it = map.iterator();
		while (it.hasNext()) {
			Entry<Integer, String> entry = it.next();
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
		
		Iterator<Entry<Integer, String>> it2 = map.iterator();
		assertEntry(0, "abc", it2.next());
		assertEntry(4, "test", it2.next());
		assertEntry(7, "b", it2.next());
		assertEntry(-3, "e", it2.next());
		assertThrows(NoSuchElementException.class, () -> it.next());
	}
	
	@Test
	void testEquals() {
		OrderedHashMap<Integer, String> map1 = new OrderedHashMap<>();
		assertEquals(null, map1.put(2, "a"));
		assertEquals(null, map1.put(4, "b"));
		assertEquals(null, map1.put(42, "c"));
		assertEquals(3, map1.size());
		
		OrderedHashMap<Integer, String> map2 = new OrderedHashMap<>();
		assertEquals(null, map2.put(2, "a"));
		assertEquals(null, map2.put(4, "b"));
		assertEquals(null, map2.put(42, "c"));
		assertEquals(3, map2.size());
		
		OrderedHashMap<Integer, String> map3 = new OrderedHashMap<>();
		assertEquals(null, map3.put(2, "d"));
		assertEquals(null, map3.put(4, "b"));
		assertEquals(null, map3.put(42, "c"));
		assertEquals(3, map3.size());
		
		OrderedHashMap<Integer, String> map4 = new OrderedHashMap<>();
		assertEquals(null, map4.put(2, "a"));
		assertEquals(null, map4.put(4, "b"));
		assertEquals(2, map4.size());
		
		OrderedHashMap<Integer, String> map5 = new OrderedHashMap<>();
		assertEquals(null, map5.put(3, "d"));
		assertEquals(null, map5.put(5, "b"));
		assertEquals(null, map5.put(43, "c"));
		assertEquals(3, map5.size());
		
		OrderedHashMap<Integer, String> map6 = new OrderedHashMap<>();
		assertEquals(null, map6.put(2, "a"));
		assertEquals(null, map6.put(42, "c"));
		assertEquals(null, map6.put(4, "b"));
		assertEquals(3, map6.size());
		
		assertTrue(map1.equals(map2));
		assertFalse(map1.equals(map3));
		assertFalse(map1.equals(map4));
		assertFalse(map1.equals(map5));
		assertFalse(map1.equals(map6));
		assertTrue(map2.equals(map1));
		assertFalse(map2.equals(map3));
		assertFalse(map2.equals(map4));
		assertFalse(map2.equals(map5));
		assertFalse(map2.equals(map6));
		assertFalse(map3.equals(map1));
		assertFalse(map3.equals(map2));
		assertFalse(map3.equals(map4));
		assertFalse(map3.equals(map5));
		assertFalse(map3.equals(map6));
		
		assertTrue(map1.equals(map1));
		assertTrue(map2.equals(map2));
		assertTrue(map3.equals(map3));
		assertTrue(map4.equals(map4));
		assertTrue(map5.equals(map5));
		assertTrue(map6.equals(map6));
		
		assertFalse(map1.equals(null));
	}
	
	
	private static <K, V> void assertEntry(K expKey, V expValue, Entry<K, V> actualEntry) {
		assertEquals(expKey, actualEntry.getKey());
		assertEquals(expValue, actualEntry.getValue());
	}
}
