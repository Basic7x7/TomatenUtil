package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.data.OrderedHashSet;

class OrderedHashSetTest {
	
	private static OrderedHashSet<String> orderedSet(String... elements) {
		return new OrderedHashSet<>(Arrays.asList(elements));
	}
	
	@Test
	void testEquals() {
		assertNotEquals(orderedSet("Hello", "World"), orderedSet("World", "Hello"));
		assertNotEquals(orderedSet("Hello", "World"), orderedSet("Hello", "!"));
		assertNotEquals(orderedSet("Hello", "World"), orderedSet("Hello", "World", "!"));
		
		HashMap<OrderedHashSet<String>, String> map = new HashMap<>();
		map.put(orderedSet(), "empty");
		map.put(orderedSet("Hello", "World"), "set1");
		assertNull(map.get(orderedSet("World", "Hello")));
		assertEquals("set1", map.get(orderedSet("Hello", "World")));
	}
	
	@Test
	void testEmptySet() {
		OrderedHashSet<String> set = new OrderedHashSet<>();
		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
		assertEquals(orderedSet(), set);
		assertThrows(NoSuchElementException.class, () -> set.getFirst());
		assertThrows(NoSuchElementException.class, () -> set.getLast());
		assertThrows(NoSuchElementException.class, () -> set.removeFirst());
		assertThrows(NoSuchElementException.class, () -> set.removeLast());
	}
	
	@Test
	void testAddElement() {
		OrderedHashSet<String> set = new OrderedHashSet<>();
		assertTrue(set.add("Hello"));
		assertFalse(set.isEmpty());
		assertEquals(1, set.size());
		assertEquals(orderedSet("Hello"), set);
	}
	
	@Test
	void testAddDuplicateElement() {
		OrderedHashSet<String> set = new OrderedHashSet<>();
		assertTrue(set.add("Hello"));
		assertFalse(set.add("Hello"));
		assertEquals(1, set.size());
		assertEquals(orderedSet("Hello"), set);
	}
	
	@Test
	void testRemoveElement() {
		OrderedHashSet<String> set = new OrderedHashSet<>();
		set.add("Hello");
		assertTrue(set.remove("Hello"));
		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
	}
	
	@Test
	void testRemoveNonExistingElement() {
		OrderedHashSet<String> set = new OrderedHashSet<>();
		assertFalse(set.remove("Hello"));
		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
	}
	
	@Test
	void testContainsElement() {
		assertTrue(orderedSet("Hello").contains("Hello"));
		assertFalse(orderedSet().contains("Hello"));
	}
	
	@Test
	void testClearSet() {
		OrderedHashSet<String> set = orderedSet("Hello", "World");
		assertFalse(set.isEmpty());
		assertEquals(2, set.size());
		assertEquals(orderedSet("Hello", "World"), set);
		set.clear();
		assertTrue(set.isEmpty());
		assertEquals(0, set.size());
		assertEquals(orderedSet(), set);
	}
	
	@Test
	void testGetFirstElement() {
		OrderedHashSet<String> set = orderedSet("Hello", "World");
		assertEquals("Hello", set.getFirst());
		assertEquals("Hello", set.removeFirst());
		assertEquals("World", set.getFirst());
		assertEquals(orderedSet("World"), set);
	}
	
	@Test
	void testGetLastElement() {
		OrderedHashSet<String> set = orderedSet("Hello", "World");
		assertEquals("World", set.getLast());
		assertEquals("World", set.removeLast());
		assertEquals("Hello", set.getLast());
		assertEquals(1, set.size());
		assertEquals(orderedSet("Hello"), set);
	}
	
	@Test
	void testIterator() {
		OrderedHashSet<String> set = orderedSet("Hello", "World");
		
		Iterator<String> it = set.iterator();
		assertTrue(it.hasNext());
		assertEquals("Hello", it.next());
		assertTrue(it.hasNext());
		assertEquals("World", it.next());
		assertFalse(it.hasNext());
		assertThrows(NoSuchElementException.class, () -> it.next());
		
		Iterator<String> it2 = set.iterator();
		assertEquals("Hello", it2.next());
		assertEquals(orderedSet("Hello", "World"), set);
		it2.remove();
		assertEquals(orderedSet("World"), set);
		assertEquals("World", it2.next());
	}
	
	@Test
	void testAddAll() {
		OrderedHashSet<String> set = new OrderedHashSet<>();
		set.addAll(orderedSet("Hello", "World"));
		assertTrue(set.addAll(Arrays.asList("World", "!")));
		assertEquals(orderedSet("Hello", "World", "!"), set);
	}
	
	@Test
	void testContainsAll() {
		OrderedHashSet<String> set = orderedSet("Hello", "World", "!");
		
		Set<String> set2 = new HashSet<>();
		set2.add("Hello");
		set2.add("World");
		
		assertTrue(set.containsAll(set2));
		set2.add("Tomaten");
		assertFalse(set.containsAll(set2));
	}
	
	@Test
	void testRemoveAll() {
		OrderedHashSet<String> set = orderedSet("Hello", "World", "!");
		
		Set<String> set2 = new HashSet<>();
		set2.add("Hello");
		set2.add("World");
		set2.add("Tomaten");
		
		assertTrue(set.removeAll(set2));
		assertEquals(orderedSet("!"), set);
		assertFalse(set.removeAll(set2));
		assertEquals(orderedSet("!"), set);
	}
	
	@Test
	void testRetainAll() {
		OrderedHashSet<String> set = orderedSet("Hello", "World", "!");
		
		Set<String> set2 = new HashSet<>();
		set2.add("Hello");
		set2.add("World");
		set2.add("Tomaten");
		
		assertTrue(set.retainAll(set2));
		assertEquals(orderedSet("Hello", "World"), set);
		assertFalse(set.retainAll(set2));
		assertEquals(orderedSet("Hello", "World"), set);
	}
	
	@Test
	void testToArray() {
		OrderedHashSet<String> set = orderedSet("Hello", "World");
		assertArrayEquals(new Object[] {"Hello", "World"}, set.toArray());
		assertArrayEquals(new String[] {"Hello", "World"}, set.toArray(new String[0]));
		assertArrayEquals(new String[] {"Hello", "World"}, set.toArray(new String[] {"a", "b"}));
		assertArrayEquals(new String[] {"Hello", "World", null, "d"}, set.toArray(new String[] {"a", "b", "c", "d"}));
	}
	
}
