package de.tomatengames.util.test;

import static de.tomatengames.util.ArrayUtil.concat;
import static de.tomatengames.util.ArrayUtil.contains;
import static de.tomatengames.util.ArrayUtil.containsEqual;
import static de.tomatengames.util.ArrayUtil.indexOf;
import static de.tomatengames.util.ArrayUtil.lastIndexOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.ArrayUtil;

class ArrayUtilTest {
	private static final A a7 = new A(7);
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
