package de.tomatengames.util.test;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.ArrayUtil;

class ArrayUtilTest {
	
	@Test
	void testConcat() {
		B[] array1 = new B[] { new B(1,1) };
		C[] array2 = new C[] { new C(2,"hello") };
		A[] arr = ArrayUtil.concat(array1, array2, A[]::new);
		System.out.println(arr + " " + Arrays.toString(arr));
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
	}
	
}
