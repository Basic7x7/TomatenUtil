package de.tomatengames.util.test;

import static de.tomatengames.util.ArrayUtil.concat;
import static de.tomatengames.util.ArrayUtil.contains;
import static de.tomatengames.util.ArrayUtil.containsEqual;
import static de.tomatengames.util.ArrayUtil.indexOf;
import static de.tomatengames.util.ArrayUtil.isEqualCT;
import static de.tomatengames.util.ArrayUtil.lastIndexOf;
import static de.tomatengames.util.ArrayUtil.reverse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.ArrayUtil;

class ArrayUtilTest {
	private static final A a7 = new A(7);
	private static final A a1 = new A(1);
	private static final A a2 = new A(2);
	private static final A a3 = new A(3);
	private static final B[] oa1 = { new B(1, 1) };
	private static final C[] oa2 = { new C(2, "hello") };
	private static final A[] oa3 = { new B(4, 5), new A(0) };
	private static final A[] oa4 = { new A(7), new B(2, 3), new A(3) };
	private static final A[] oa5 = { };
	private static final A[] oa6 = { new A(2), a7, a7, new A(5), new A(3), new A(5) };
	
	private static final int[] ia1 = { 4, 7, 9 };
	private static final int[] ia2 = { 5 };
	private static final int[] ia3 = { 4, 10, 7 };
	private static final int[] ia4 = { };
	private static final int[] ia5 = { 2, 7, 7, 5, 3, 5 };
	
	@Test
	void testContainsIntArray() {
		assertTrue(contains(ia5, 7));
		assertTrue(contains(ia5, 3));
		assertFalse(contains(ia5, 100));
		assertFalse(contains((int[]) null, 100));
		assertFalse(contains(ia2, 7));
	}
	
	@Test
	void testIndexOfIntArray() {
		assertEquals(-1, indexOf(ia4, 7));
		assertEquals(1, indexOf(ia5, 7));
		assertEquals(2, lastIndexOf(ia5, 7));
		assertEquals(2, indexOf(ia5, 7, 2));
		assertEquals(5, indexOf(ia5, 5, 4));
		assertEquals(-1, indexOf(ia5, 5, 42));
		assertEquals(5, lastIndexOf(ia5, 5, 42));
		assertEquals(3, lastIndexOf(ia5, 5, 4));
		assertEquals(-1, lastIndexOf(ia5, 7, -1));
		assertEquals(-1, indexOf((int[]) null, 5));
		assertEquals(-1, lastIndexOf((int[]) null, 5, 42));
		assertEquals(-1, lastIndexOf((int[]) null, 5));
	}
	
	@Test
	void testContainsObjectArray() {
		assertTrue(contains(oa6, a7));
		assertFalse(contains(oa6, new A(2))); // != but equals
		assertFalse(contains(oa6, new A(100)));
		assertFalse(contains(null, a7));
		assertFalse(contains(oa6, null));
		assertFalse(contains(oa4, a7));
	}
	
	@Test
	void testContainsEqualObjectArray() {
		assertTrue(containsEqual(oa6, a7));
		assertTrue(containsEqual(oa6, new A(7)));
		assertTrue(containsEqual(oa6, new A(2)));
		assertFalse(containsEqual(oa6, new A(100)));
		assertFalse(containsEqual(null, a7));
		assertFalse(containsEqual(oa6, null));
		assertTrue(containsEqual(oa4, a7));
		assertFalse(containsEqual(oa5, a7));
	}
	
	@Test
	void testIndexOfObjectArray() {
		assertEquals(-1, indexOf(oa4, a7));
		assertEquals(1, indexOf(oa6, a7));
		assertEquals(2, lastIndexOf(oa6, a7));
		assertEquals(-1, lastIndexOf(oa6, new A(7)));
		assertEquals(2, indexOf(oa6, a7, 2));
		assertEquals(-1, indexOf(oa6, new A(7)));
		assertEquals(-1, indexOf(oa4, null));
		assertEquals(-1, indexOf(oa4, new A(3)));
		assertEquals(-1, lastIndexOf(null, a7));
		assertEquals(-1, lastIndexOf(oa6, null));
		assertEquals(-1, lastIndexOf(null, a7, 2));
		assertEquals(2, lastIndexOf(oa6, a7, 1000));
	}
	
	@Test
	void testConcatObjectArrays() {
		assertArrayEquals(new A[] { new B(1, 1), new C(2, "hello") },
				concat(oa1, oa2, A[]::new));
		assertArrayEquals(new A[] { new C(2, "hello"), new B(1, 1) },
				concat(oa2, oa1, A[]::new));
		
		assertArrayEquals(oa3, concat(oa3, oa5, A[]::new));
		
		assertArrayEquals(new A[] { new B(1, 1), new C(2, "hello"), new B(4, 5), new A(0) },
				concat(A[]::new, oa1, oa2, oa3));
		assertArrayEquals(oa3, concat(A[]::new, oa3));
	}
	
	@Test
	void testConcatObjectArraysNull() {
		assertArrayEquals(new A[0], concat(null, null, A[]::new));
		assertArrayEquals(new A[] { new B(1, 1) }, concat(oa1, null, A[]::new));
		assertArrayEquals(new A[] { new C(2, "hello") }, concat(null, oa2, A[]::new));
		
		assertArrayEquals(new A[] { }, concat(A[]::new));
		assertArrayEquals(oa4, concat(A[]::new, null, oa4, null));
	}
	
	@Test
	void testConcatIntArrays() {
		assertArrayEquals(new int[] { 4, 7, 9, 5 }, ArrayUtil.concat(ia1, ia2));
		assertArrayEquals(new int[] { 5, 4, 7, 9 }, ArrayUtil.concat(ia2, ia1));
		assertArrayEquals(ia3, ArrayUtil.concat(ia3, ia4));
		
		assertArrayEquals(new int[] { 4, 7, 9, 5, 4, 10, 7 }, ArrayUtil.concat(ia1, ia2, ia3, ia4));
		assertArrayEquals(ia3, ArrayUtil.concat(ia3));
		
		assertArrayEquals(new int[0], ArrayUtil.concat((int[]) null, null));
		assertArrayEquals(ia1, ArrayUtil.concat(ia1, null));
		assertArrayEquals(ia2, ArrayUtil.concat(null, ia2));
		
		assertArrayEquals(ia3, ArrayUtil.concat(null, ia3, null));
	}
	
	@Test
	void testReverse() {
		int[] a = { 1, 2, 3, 4, 5 };
		reverse(a);
		assertArrayEquals(new int[] {5,4,3,2,1}, a);
		
		assertArrayEquals(new int[] {1,2,3}, reverse(new int[] {3,2,1}));
		assertArrayEquals(new int[] {7,2,9,9}, reverse(new int[] {9,9,2,7}));
		assertArrayEquals(new int[] {3}, reverse(new int[] {3}));
		assertArrayEquals(new int[] {5,4,3,2,1,9,8}, reverse(new int[] {8,9,1,2,3,4,5}));
		
		assertEquals(null, reverse((int[]) null));
	}
	
	@Test
	void testConcatCollections() {
		ArrayList<Integer> list1 = new ArrayList<>();
		list1.add(5);
		list1.add(8);
		list1.add(3);
		
		ArrayList<Integer> list2 = new ArrayList<>();
		list2.add(7);
		list2.add(8);
		
		assertEquals(Arrays.asList(5,8,3,7,8), concat(ArrayList::new, list1, list2));
		assertEquals(new HashSet<>(Arrays.asList(3,5,7,8)), concat(HashSet::new, list1, list2));
	}
	
	@Test
	void testAddObject() {
		A[] a = null;
		assertArrayEquals(new A[] {a7}, a = ArrayUtil.addFirst(a, a7, A[]::new));
		assertArrayEquals(new A[] {a7, a7}, a = ArrayUtil.addFirst(a, a7, A[]::new));
		assertArrayEquals(new A[] {a1, a7, a7}, a = ArrayUtil.addFirst(a, a1, A[]::new));
		assertArrayEquals(new A[] {a1, a7, a7, a2}, a = ArrayUtil.addLast(a, a2, A[]::new));
		assertArrayEquals(new A[] {a1, a7, a7, a2, a1}, a = ArrayUtil.addLast(a, a1, A[]::new));
		assertArrayEquals(new A[] {a1, a7, a3, a7, a2, a1}, a = ArrayUtil.add(a, a3, 2, A[]::new));
		assertArrayEquals(new A[] {a2, a1, a7, a3, a7, a2, a1}, a = ArrayUtil.add(a, a2, 0, A[]::new));
		assertArrayEquals(new A[] {a2, a1, a7, a3, a7, a2, a1, a2}, a = ArrayUtil.add(a, a2, a.length, A[]::new));
		
		final A[] af = a;
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.add(af, a1, -1, A[]::new));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.add(af, a2, -27, A[]::new));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.add(af, a1, af.length+1, A[]::new));
		
		assertArrayEquals(new A[] {a7}, ArrayUtil.addLast(null, a7, A[]::new));
		assertArrayEquals(new A[] {a7}, ArrayUtil.add(null, a7, 0, A[]::new));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.add(null, a1, 1, A[]::new));
		
		assertArrayEquals(new A[] {a7}, ArrayUtil.add(new A[0], a7, 0, A[]::new));
		assertArrayEquals(new A[] {a7}, ArrayUtil.addFirst(new A[0], a7, A[]::new));
		assertArrayEquals(new A[] {a7}, ArrayUtil.addLast(new A[0], a7, A[]::new));
	}
	
	@Test
	void testRemoveObject() {
		final A[] a = { a1, a2, a3, a7 };
		assertArrayEquals(new A[] { a1, a2, a3 }, ArrayUtil.removeIndex(a, 3, A[]::new));
		assertArrayEquals(new A[] { a1, a3, a7 }, ArrayUtil.removeIndex(a, 1, A[]::new));
		assertArrayEquals(new A[] { a2, a3, a7 }, ArrayUtil.removeIndex(a, 0, A[]::new));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.removeIndex(a, -1, A[]::new));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.removeIndex(a, -28, A[]::new));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.removeIndex(null, 0, A[]::new));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.removeIndex(new A[] {}, 0, A[]::new));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.removeIndex(a, a.length, A[]::new));
		
		final A[] b = new A[] {a1, new A(7), a2, a3, a7};
		assertArrayEquals(new A[] { a1, a7, a2, a3 }, ArrayUtil.removeIndex(b, 4, A[]::new));
		assertArrayEquals(new A[] { a1, a7, a3, a7 }, ArrayUtil.remove(b, a2, A[]::new));
		assertArrayEquals(new A[] { a1, a7, a2, a3 }, ArrayUtil.remove(b, a7, A[]::new));
		assertArrayEquals(new A[] { a1, a2, a3, a7 }, ArrayUtil.removeEqual(b, a7, A[]::new));
		assertArrayEquals(new A[] { a1, a7, a2, a7 }, ArrayUtil.removeEqual(b, a3, A[]::new));
		assertArrayEquals(b, ArrayUtil.remove(b, new A(10), A[]::new));
		assertArrayEquals(b, ArrayUtil.removeEqual(b, new A(10), A[]::new));
	}
	
	@Test
	void testAdd() {
		int[] a = null;
		assertArrayEquals(new int[] { 3 }, a = ArrayUtil.addFirst(a, 3));
		assertArrayEquals(new int[] { 3, 2 }, a = ArrayUtil.addLast(a, 2));
		assertArrayEquals(new int[] { 1, 3, 2 }, a = ArrayUtil.addFirst(a, 1));
		assertArrayEquals(new int[] { 1, 3, 4, 2 }, a = ArrayUtil.add(a, 4, 2));
		assertArrayEquals(new int[] { 5, 1, 3, 4, 2 }, a = ArrayUtil.add(a, 5, 0));
		assertArrayEquals(new int[] { 5, 1, 3, 4, 2, 6 }, a = ArrayUtil.add(a, 6, 5));
		assertArrayEquals(new int[] { 5, 7, 1, 3, 4, 2, 6 }, a = ArrayUtil.add(a, 7, 1));
		assertArrayEquals(new int[] { 5, 7, 1, 3, 4, 2, 6, 8 }, a = ArrayUtil.addLast(a, 8));
		assertArrayEquals(new int[] { 9, 5, 7, 1, 3, 4, 2, 6, 8 }, a = ArrayUtil.addFirst(a, 9));
		
		final int[] af = a;
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.add(af, 20, 20));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.add(af, 21, -1));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.add(af, 22, -30));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.add((int[]) null, 5, 1));
		
		assertArrayEquals(new int[] { 5 }, ArrayUtil.add((int[]) null, 5, 0));
		assertArrayEquals(new int[] { 5 }, ArrayUtil.addFirst((int[]) null, 5));
		assertArrayEquals(new int[] { 5 }, ArrayUtil.addLast((int[]) null, 5));
	}
	
	@Test
	void testRemove() {
		final int[] a = { 1, 2, 3, 4 };
		assertArrayEquals(new int[] { 1, 2, 3 }, ArrayUtil.removeIndex(a, 3));
		assertArrayEquals(new int[] { 1, 3, 4 }, ArrayUtil.removeIndex(a, 1));
		assertArrayEquals(new int[] { 2, 3, 4 }, ArrayUtil.removeIndex(a, 0));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.removeIndex(a, -1));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.removeIndex(a, -28));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.removeIndex((int[]) null, 0));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.removeIndex(new int[] {}, 0));
		assertThrows(IndexOutOfBoundsException.class, () -> ArrayUtil.removeIndex(a, a.length));
		
		assertArrayEquals(new int[] { 1, 3, 4 }, ArrayUtil.remove(a, 2));
		assertArrayEquals(new int[] { 1, 2, 3 }, ArrayUtil.remove(a, 4));
		assertArrayEquals(new int[] { 2, 3, 4 }, ArrayUtil.remove(a, 1));
		assertArrayEquals(a, ArrayUtil.remove(a, 20));
	}
	
	@Test
	void testIsEqualCT() {
		assertEquals(true, isEqualCT(new int[] {1,2,3}, new int[] {1,2,3}));
		assertEquals(false, isEqualCT(new int[] {1,2,4}, new int[] {1,2,3}));
		assertEquals(false, isEqualCT(new int[] {2,2,3}, new int[] {1,2,3}));
		assertEquals(false, isEqualCT(new int[] {1,2,3,4}, new int[] {1,2,3}));
		assertEquals(false, isEqualCT(new int[] {1,2,3}, new int[] {1,2,3,4}));
		assertEquals(true, isEqualCT(new int[] {1,2,3,4}, new int[] {1,2,3,4}));
		assertEquals(true, isEqualCT(new int[] {}, new int[] {}));
		assertEquals(false, isEqualCT(new int[] {1}, new int[] {}));
		assertEquals(false, isEqualCT(new int[] {}, new int[] {1}));
		
		assertEquals(true, isEqualCT(new byte[] {1,2,3}, new byte[] {1,2,3}));
		assertEquals(false, isEqualCT(new byte[] {1,2,4}, new byte[] {1,2,3}));
		assertEquals(false, isEqualCT(new byte[] {2,2,3}, new byte[] {1,2,3}));
		assertEquals(false, isEqualCT(new byte[] {1,2,3,4}, new byte[] {1,2,3}));
		assertEquals(false, isEqualCT(new byte[] {1,2,3}, new byte[] {1,2,3,4}));
		assertEquals(true, isEqualCT(new byte[] {1,2,3,4}, new byte[] {1,2,3,4}));
		assertEquals(true, isEqualCT(new byte[] {}, new byte[] {}));
		assertEquals(false, isEqualCT(new byte[] {1}, new byte[] {}));
		assertEquals(false, isEqualCT(new byte[] {}, new byte[] {1}));
		
		assertEquals(true, isEqualCT("ABC".toCharArray(), "ABC".toCharArray()));
		assertEquals(false, isEqualCT("ABD".toCharArray(), "ABC".toCharArray()));
		assertEquals(true, isEqualCT("Test".toCharArray(), "Test".toCharArray()));
		assertEquals(false, isEqualCT("Test".toCharArray(), "test".toCharArray()));
		assertEquals(false, isEqualCT("Test".toCharArray(), "This is a test".toCharArray()));
		assertEquals(true, isEqualCT(new char[] {}, new char[] {}));
		assertEquals(false, isEqualCT(new char[] {}, "test".toCharArray()));
		assertEquals(false, isEqualCT("test".toCharArray(), new char[] {}));
		assertEquals(true, isEqualCT("Hällo".toCharArray(), "Hällo".toCharArray()));
		assertEquals(false, isEqualCT("Hello".toCharArray(), "Hällo".toCharArray()));
		assertEquals(true, isEqualCT("𨉟呐㗂越".toCharArray(), "𨉟呐㗂越".toCharArray()));
		assertEquals(false, isEqualCT("𨉟呐a越".toCharArray(), "𨉟呐㗂越".toCharArray()));
		assertEquals(false, isEqualCT("𨉟呐㗂越".toCharArray(), "𨉟呐b越".toCharArray()));
	}
	
	
	private static class A {
		protected final int a;
		
		public A(int a) {
			this.a = a;
		}
		
		@Override
		public String toString() {
			return "A(" + this.a + ")";
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			A other = (A) obj;
			return a == other.a;
		}
	}
	
	private static class B extends A {
		protected final int b;
		
		public B(int a, int b) {
			super(a);
			this.b = b;
		}
		
		@Override
		public String toString() {
			return "B(" + this.a + ", " + this.b + ")";
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			B other = (B) obj;
			return b == other.b;
		}
	}
	
	private static class C extends A {
		protected final String c;
		
		public C(int a, String c) {
			super(a);
			this.c = c;
		}
		
		@Override
		public String toString() {
			return "C(" + this.a + ", " + this.c + ")";
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			C other = (C) obj;
			return Objects.equals(c, other.c);
		}
	}
	
}
