package de.tomatengames.util.test;

import de.tomatengames.util.io.CountingInputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CountingStreamTest {
	private static final byte[] bytes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	
	@Test
	void testInputStream() throws IOException {
		try (CountingInputStream in = new CountingInputStream(new ByteArrayInputStream(bytes))) {
			assertEquals(0, in.getByteCount());
			assertEquals(0, in.read());
			assertEquals(1, in.getByteCount());
			assertArrayEquals(new byte[] {1,2,3}, readBytes(in, 3));
			assertEquals(4, in.getByteCount());
			assertArrayEquals(new byte[] {4,5,6,7,8,9}, readBytes(in, 100));
			assertEquals(10, in.getByteCount());
		}
	}
	
	@Test
	void testSkip() throws IOException {
		try (CountingInputStream in = new CountingInputStream(new ByteArrayInputStream(bytes))) {
			assertEquals(0, in.read());
			assertEquals(1, in.getByteCount());
			assertEquals(5, in.skip(5));
			assertEquals(6, in.getByteCount());
			assertArrayEquals(new byte[] {6, 7}, readBytes(in, 2));
			assertEquals(8, in.getByteCount());
			assertEquals(2, in.skip(10));
			assertEquals(10, in.getByteCount());
		}
	}
	
	@Test
	void testMark() throws IOException {
		try (CountingInputStream in = new CountingInputStream(new ByteArrayInputStream(bytes))) {
			assertEquals(0, in.read());
			assertEquals(1, in.getByteCount());
			in.mark(100);
			assertArrayEquals(new byte[] {1,2,3,4}, readBytes(in, 4));
			assertEquals(5, in.getByteCount());
			assertEquals(5, in.read());
			assertEquals(6, in.getByteCount());
			in.reset();
			assertEquals(1, in.read());
			assertEquals(2, in.getByteCount());
			in.mark(100);
			assertArrayEquals(new byte[] {2,3,4,5,6,7,8,9}, readBytes(in, 100));
			assertEquals(10, in.getByteCount());
			assertEquals(-1, in.read());
			assertEquals(10, in.getByteCount());
			in.reset();
			assertEquals(3, in.skip(3));
			assertEquals(5, in.getByteCount());
			assertArrayEquals(new byte[] {5,6,7,8,9}, readBytes(in, 5));
			assertEquals(10, in.getByteCount());
		}
	}
	
	private static byte[] readBytes(InputStream in, int n) throws IOException {
		byte[] arr = new byte[n];
		int len = in.read(arr);
		if (len == n) {
			return arr;
		}
		return Arrays.copyOf(arr, len);
	}
	
}
