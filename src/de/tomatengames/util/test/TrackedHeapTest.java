package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.OptionalInt;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.tomatengames.util.data.TrackedHeap;

class TrackedHeapTest {
	private TrackedHeap<A> heap;
	private A[] a;
	
	@BeforeEach
	void prepare() {
		this.heap = new TrackedHeap<>((a1, a2) -> Integer.compare(a1.value, a2.value));
		this.a = insertElements(this.heap, 5, 3, 8, 12, 7, 1, 10);
	}
	
	@Test
	void testBase() {
		TrackedHeap<A> heap = new TrackedHeap<>((a1, a2) -> Integer.compare(a1.value, a2.value));
		assertEquals(0, heap.size());
		assertEquals(true, heap.isEmpty());
		
		A[] a = createElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		
		// Insert a2 -> [a2]
		assertEquals(true, heap.insert(a[2]));
		assertEquals(1, heap.size());
		assertEquals(false, heap.isEmpty());
		assertEquals(false, heap.contains(a[1]));
		assertEquals(true, heap.contains(a[2]));
		assertEquals(false, heap.contains(a[3]));
		assertEquals(a[2], heap.getFirst());
		assertEquals(a[2], heap.getFirst());
		
		// RemoveFirst -> []
		assertEquals(a[2], heap.removeFirst());
		assertEquals(0, heap.size());
		assertEquals(true, heap.isEmpty());
		assertEquals(false, heap.contains(a[1]));
		assertEquals(false, heap.contains(a[2]));
		assertEquals(false, heap.contains(a[3]));
		assertEquals(null, heap.getFirst());
		
		// Insert a3, a2, a6, a4 -> [a2, a3, a4, a6]
		assertEquals(true, heap.insert(a[3]));
		assertEquals(true, heap.insert(a[2]));
		assertEquals(false, heap.insert(a[2])); // Double-Insert to the same heap
		assertEquals(true, heap.insert(a[6]));
		assertEquals(true, heap.insert(a[4]));
		
		assertEquals(4, heap.size());
		assertEquals(false, heap.isEmpty());
		assertEquals(false, heap.contains(a[1]));
		assertEquals(true, heap.contains(a[2]));
		assertEquals(true, heap.contains(a[3]));
		assertEquals(true, heap.contains(a[4]));
		assertEquals(false, heap.contains(a[5]));
		assertEquals(true, heap.contains(a[6]));
		assertEquals(false, heap.contains(a[7]));
		assertEquals(a[2], heap.getFirst());
		
		// a7 not in heap -> No change
		assertEquals(false, heap.remove(a[7]));
		assertEquals(4, heap.size());
		assertEquals(false, heap.contains(a[7]));
		
		// Remove a3 -> [a2, a4, a6]
		assertEquals(true, heap.remove(a[3]));
		assertEquals(false, heap.remove(a[3]));
		assertEquals(3, heap.size());
		assertEquals(false, heap.isEmpty());
		assertEquals(false, heap.contains(a[3]));
		assertEquals(a[2], heap.getFirst());
		
		// RemoveFirst -> [a4, a6]
		assertEquals(a[2], heap.removeFirst());
		assertEquals(2, heap.size());
		assertEquals(false, heap.contains(a[2]));
		assertEquals(a[4], heap.getFirst());
		
		// Insert a9, a1 -> [a1, a4, a6, a9]
		assertEquals(true, heap.insert(a[9]));
		assertEquals(true, heap.insert(a[1]));
		assertEquals(4, heap.size());
		assertEquals(false, heap.contains(a[2]));
		assertEquals(true, heap.contains(a[9]));
		assertEquals(true, heap.contains(a[1]));
		assertEquals(a[1], heap.getFirst());
		
		// Remove all
		assertEquals(a[1], heap.removeFirst());
		assertEquals(a[4], heap.removeFirst());
		assertEquals(a[6], heap.removeFirst());
		assertEquals(a[9], heap.removeFirst());
		assertEquals(null, heap.removeFirst());
		assertEquals(0, heap.size());
		assertEquals(true, heap.isEmpty());
	}
	
	@Test
	void testClear() {
		assertEquals(7, heap.size());
		assertEquals(false, heap.isEmpty());
		assertEquals(a[5], heap.getFirst());
		
		heap.clear();
		
		assertEquals(0, heap.size());
		assertEquals(true, heap.isEmpty());
		assertEquals(null, heap.getFirst());
		
		// Test that the heap is still usable.
		heap.insert(a[2]);
		assertEquals(1, heap.size());
		assertEquals(a[2], heap.getFirst());
	}
	
	@Test
	void testRemoveAll() {
		// Sorted [5, 3, 8, 12, 7, 1, 10]
		assertEquals(1, heap.removeFirst().value);
		assertEquals(3, heap.removeFirst().value);
		assertEquals(5, heap.removeFirst().value);
		assertEquals(7, heap.removeFirst().value);
		assertEquals(8, heap.removeFirst().value);
		assertEquals(10, heap.removeFirst().value);
		assertEquals(12, heap.removeFirst().value);
		assertEquals(null, heap.removeFirst());
	}
	
	@Test
	void testTwoHeaps() {
		TrackedHeap<A> heap2 = new TrackedHeap<>((a1, a2) -> Integer.compare(a1.value, a2.value));
		assertThrows(IllegalArgumentException.class, () -> heap2.insert(a[2]));
		
		A[] a2 = insertElements(heap2, 3, 0, 8);
		assertEquals(3, heap2.size());
		
		// Cannot insert element present in this.heap.
		assertThrows(IllegalArgumentException.class, () -> heap2.insert(a[6]));
		
		// Transfer an element.
		heap.insert(heap2.removeFirst());
		assertEquals(8, heap.size());
		assertEquals(2, heap2.size());
		assertEquals(0, heap.getFirst().value);
		assertEquals(3, heap2.getFirst().value);
		
		assertEquals(true, heap.contains(a2[1]));
		assertEquals(false, heap2.contains(a2[1]));
		assertEquals(false, heap.contains(a2[0]));
		assertEquals(true, heap2.contains(a2[0]));
		assertEquals(true, heap.contains(a[5]));
		assertEquals(false, heap2.contains(a[5]));
	}
	
	@Test
	void testManyElements() {
		// +20 elements
		A[] a1 = insertElements(heap, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
		assertEquals(27, heap.size());
		assertEquals(a1[0], heap.getFirst());
		assertEquals(a1[0], heap.removeFirst());
		assertEquals(true, heap.remove(a1[5]));
		assertEquals(25, heap.size());
		assertEquals(1, heap.getFirst().value);
		
		// +10000 elements
		final int n = 10000;
		A[] a2 = insertElements(heap, createCountingArray(n));
		assertEquals(10025, heap.size());
		assertEquals(a2[0], heap.getFirst());
		
		assertEquals(0, heap.removeFirst().value);
		assertEquals(1, heap.removeFirst().value);
		assertEquals(1, heap.removeFirst().value);
		assertEquals(1, heap.removeFirst().value);
		assertEquals(2, heap.removeFirst().value);
		assertEquals(2, heap.removeFirst().value);
		assertEquals(3, heap.removeFirst().value);
		assertEquals(3, heap.removeFirst().value);
		assertEquals(3, heap.removeFirst().value);
		assertEquals(10016, heap.size());
		
		assertEquals(false, heap.remove(a2[0]));
		assertEquals(false, heap.remove(a2[2]));
		for (int i = 4; i < n; i += 2) {
			assertEquals(true, heap.remove(a2[i]));
		}
		assertEquals(5018, heap.size());
		
		assertEquals(4, heap.removeFirst().value);
		assertEquals(5, heap.removeFirst().value);
		assertEquals(5, heap.removeFirst().value);
		assertEquals(6, heap.removeFirst().value);
		assertEquals(7, heap.removeFirst().value);
		assertEquals(7, heap.removeFirst().value);
		assertEquals(7, heap.removeFirst().value);
		assertEquals(8, heap.removeFirst().value);
		assertEquals(8, heap.removeFirst().value);
		assertEquals(9, heap.removeFirst().value);
		assertEquals(5008, heap.size());
		
		assertEquals(true, heap.insert(a2[0]));
		assertEquals(true, heap.insert(a2[6]));
		assertEquals(false, heap.insert(a2[11]));
		assertEquals(true, heap.insert(a2[2]));
		
		assertEquals(a2[0], heap.removeFirst());
		assertEquals(a2[2], heap.removeFirst());
		assertEquals(a2[6], heap.removeFirst());
		assertEquals(9, heap.removeFirst().value);
		assertEquals(10, heap.removeFirst().value);
	}
	
	@Test
	void testNull() {
		assertEquals(7, heap.size());
		assertEquals(false, heap.insert(null));
		assertEquals(7, heap.size());
		assertEquals(false, heap.remove(null));
		assertEquals(7, heap.size());
		assertEquals(false, heap.contains(null));
		heap.increase(null);
		heap.decrease(null);
		heap.move(null);
		assertEquals(7, heap.size());
		assertEquals(1, heap.removeFirst().value);
	}
	
	@Test
	void testMove() {
		assertEquals(1, heap.getFirst().value);
		
		a[4].value = -1;
		assertEquals(1, heap.getFirst().value);
		heap.decrease(a[4]);
		assertEquals(-1, heap.getFirst().value);
		
		a[4].value = 0;
		assertEquals(0, heap.getFirst().value);
		heap.increase(a[4]);
		assertEquals(0, heap.getFirst().value);
		
		a[4].value = 100;
		assertEquals(100, heap.getFirst().value);
		heap.decrease(a[4]);
		assertEquals(100, heap.getFirst().value);
		heap.increase(a[4]);
		assertEquals(1, heap.getFirst().value);
		
		a[4].value = 0;
		assertEquals(1, heap.getFirst().value);
		heap.move(a[4]);
		assertEquals(0, heap.getFirst().value);
		
		a[4].value = 90;
		assertEquals(90, heap.getFirst().value);
		heap.move(a[4]);
		assertEquals(1, heap.getFirst().value);
		
		a[4].value = -10;
		assertEquals(1, heap.getFirst().value);
		heap.insert(a[4]); // reinsert
		assertEquals(-10, heap.getFirst().value);
		
		a[4].value = 70;
		assertEquals(70, heap.getFirst().value);
		heap.insert(a[4]); // reinsert
		assertEquals(1, heap.getFirst().value);
	}
	
	@Test
	void testRemoveMinimal() {
		TrackedHeap<A> heap = new TrackedHeap<>((a1, a2) -> Integer.compare(a1.value, a2.value));
		A[] a = insertElements(heap, 4, 7);
		assertEquals(2, heap.size());
		assertEquals(true, heap.remove(a[0]));
		assertEquals(false, heap.remove(a[0]));
		assertEquals(true, heap.remove(a[1]));
		assertEquals(false, heap.remove(a[1]));
		assertEquals(null, heap.removeFirst());
		assertEquals(0, heap.size());
	}
	
	
	@Test
	void testRandomized() {
		TrackedHeap<A> heap = new TrackedHeap<>((a1, a2) -> Integer.compare(a1.value, a2.value));
		HashSet<A> controlSet = new HashSet<>();
		StringBuilder opScript = new StringBuilder();
		
		// 100 possible elements
		A[] elements = createElements(createCountingArray(100));
		
		try {
			// 10000 random operations
			for (int i = 0; i < 10000; i++) {
				int op = ThreadLocalRandom.current().nextInt(100);
				if (op < 40) { // 40% insert
					int index = ThreadLocalRandom.current().nextInt(elements.length);
					opScript.append("\ninsert " + index);
					A a = elements[index];
					assertEquals(controlSet.add(a), heap.insert(a), () -> opScript.toString());
				}
				else if (op < 60) { // 20% remove random
					int index = ThreadLocalRandom.current().nextInt(elements.length);
					opScript.append("\nremove " + index);
					A a = elements[index];
					assertEquals(controlSet.remove(a), heap.remove(a), () -> opScript.toString());
				}
				else if (op < 75) { // 15% remove first
					opScript.append("\nremoveFirst");
					A removed = heap.removeFirst();
					OptionalInt expectedMin = controlSet.stream().mapToInt(a -> a.value).min();
					if (expectedMin.isPresent()) {
						assertNotNull(removed, () -> opScript.toString());
						assertEquals(expectedMin.getAsInt(), removed.value, () -> opScript.toString());
						assertTrue(controlSet.remove(removed), () -> opScript.toString());
					}
					else {
						assertNull(removed, () -> opScript.toString());
					}
				}
				else { // 25% move
					int index = ThreadLocalRandom.current().nextInt(elements.length);
					A a = elements[index];
					int change = ThreadLocalRandom.current().nextInt(30);
					int subop = ThreadLocalRandom.current().nextInt(100);
					if (subop < 35) { // 35% move
						change = ThreadLocalRandom.current().nextBoolean() ? change : -change;
						a.value += change;
						opScript.append("\nmove " + index + " = " + a.value);
						heap.move(a);
					}
					else if (subop < 50) { // 15% reinsert
						change = ThreadLocalRandom.current().nextBoolean() ? change : -change;
						a.value += change;
						opScript.append("\nreinsert " + index + " = " + a.value);
						assertEquals(controlSet.add(a), heap.insert(a), () -> opScript.toString());
					}
					else if (subop < 75) { // 25% increase
						a.value += change;
						opScript.append("\nincrease " + index + " = " + a.value);
						heap.increase(a);
					}
					else { // 25% decrease
						a.value -= change;
						opScript.append("\ndecrease " + index + " = " + a.value);
						heap.decrease(a);
					}
				}
				
				// -- Check that the heap and the control set are equal --
				
				assertEquals(controlSet.size(), heap.size());
				assertEquals(controlSet.isEmpty(), heap.isEmpty());
				
				// Check getFirst()
				OptionalInt expectedMin = controlSet.stream().mapToInt(a -> a.value).min();
				if (expectedMin.isPresent()) {
					assertEquals(expectedMin.getAsInt(), heap.getFirst().value);
				}
				else {
					assertEquals(null, heap.getFirst());
				}
				
				// Check contains
				for (A a : a) {
					assertEquals(controlSet.contains(a), heap.contains(a));
				}
			}
		} catch (Throwable t) {
			throw new Error(opScript.toString(), t);
		}
	}
	
	private static int[] createCountingArray(int length) {
		int[] array = new int[length];
		for (int i = 0; i < length; i++) {
			array[i] = i;
		}
		return array;
	}
	
	private static A[] createElements(int...values) {
		return Arrays.stream(values).mapToObj(A::new).toArray(A[]::new);
	}
	
	private static A[] insertElements(TrackedHeap<A> heap, int...values) {
		A[] elements = createElements(values);
		for (A a : elements) {
			assertEquals(true, heap.insert(a));
		}
		return elements;
	}
	
	private static class A extends TrackedHeap.Element {
		private int value;
		
		public A(int value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return String.valueOf(this.value);
		}
	}
	
}
