package de.tomatengames.util.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.HexUtil;
import de.tomatengames.util.exception.CharacterDecodeException;
import de.tomatengames.util.io.UTF8Reader;

class UTF8ReaderTest {
	
	@Test
	void testSimple() throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(hex("414243"));
		try (UTF8Reader reader = new UTF8Reader(in)) {
			assertEquals('A', reader.read());
			assertEquals('B', reader.read());
			assertEquals('C', reader.read());
			assertEquals(-1, reader.read());
		}
	}
	
	@Test
	void testDecode() throws IOException {
		try (ByteArrayInputStream in = new ByteArrayInputStream(hex("C3B6 41 F09D849E 42"));
				UTF8Reader reader = new UTF8Reader(in)) {
			assertEquals('ö', reader.read());
			assertEquals('A', reader.read());
			assertEquals(0xD834, reader.read()); // High surrogate
			assertEquals(0xDD1E, reader.read()); // Low surrogate
			assertEquals('B', reader.read());
			assertEquals(-1, reader.read());
			assertEquals(-1, reader.read());
		}
		
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		
		try (ByteArrayInputStream in = new ByteArrayInputStream(hex("4D C3AC 6E 68 20 6E C3B3 69 20 74 69 E1BABF 6E 67 20 56 69 E1BB87 74"));
				UTF8Reader reader = new UTF8Reader(in)) {
			assertArrayEquals(chars("Mình nói tiếng Việt"), readN(19, reader));
			assertEquals(-1, reader.read());
		}
		
		try (ByteArrayInputStream in = new ByteArrayInputStream(hex("F0A8899F E59190 E39782 E8B68A"));
				UTF8Reader reader = new UTF8Reader(in)) {
			assertArrayEquals(chars("𨉟呐㗂越"), readN(5, reader));
			assertEquals(-1, reader.read());
		}
	}
	
	@Test
	void testReadArray() throws IOException {
		try (ByteArrayInputStream in = new ByteArrayInputStream(hex("C3B6 41 F09D849E 42"));
				UTF8Reader reader = new UTF8Reader(in)) {
			char[] buf = new char[4];
			assertEquals(3, reader.read(buf, 0, 3));
			assertArrayEquals(new char[] {'ö', 'A', 0xD834, 0}, buf);
			assertEquals(2, reader.read(buf, 1, 3));
			assertArrayEquals(new char[] {'ö', 0xDD1E, 'B', 0}, buf);
			assertEquals(-1, reader.read(buf, 0, 3));
		}
		
		try (ByteArrayInputStream in = new ByteArrayInputStream(hex("C3B6 41 F09D849E 42"));
				UTF8Reader reader = new UTF8Reader(in)) {
			char[] buf = new char[7];
			assertThrows(IndexOutOfBoundsException.class, () -> reader.read(buf, 2, 7));
			assertEquals(5, reader.read(buf, 2, 5));
			assertArrayEquals(new char[] {0, 0, 'ö', 'A', 0xD834, 0xDD1E, 'B'}, buf);
			assertEquals(-1, reader.read(buf, 0, 7));
		}
	}
	
	@Test
	void testInvalidInput() throws IOException {
		try (ByteArrayInputStream in = new ByteArrayInputStream(hex("41 EDA0B4")); // Encoded high surrogate
				UTF8Reader reader = new UTF8Reader(in)) {
			assertEquals('A', reader.read());
			assertThrows(CharacterDecodeException.class, () -> reader.read());
		}
		
		try (ByteArrayInputStream in = new ByteArrayInputStream(hex("EDB49E 42")); // Encoded low surrogate
				UTF8Reader reader = new UTF8Reader(in)) {
			assertThrows(CharacterDecodeException.class, () -> reader.read());
		}
		
		try (ByteArrayInputStream in = new ByteArrayInputStream(hex("44 FFFF")); // Invalid input
				UTF8Reader reader = new UTF8Reader(in)) {
			assertEquals('D', reader.read());
			assertThrows(CharacterDecodeException.class, () -> reader.read());
		}
		
		try (ByteArrayInputStream in = new ByteArrayInputStream(hex("42 F09D84")); // Incomplete 4-byte character
				UTF8Reader reader = new UTF8Reader(in)) {
			assertEquals('B', reader.read());
			assertThrows(CharacterDecodeException.class, () -> reader.read());
		}
	}
	
	@Test
	void testMark() throws IOException {
		try (ByteArrayInputStream in = new ByteArrayInputStream(hex("C3B6 41 F09D849E 42"));
				UTF8Reader reader = new UTF8Reader(in)) {
			assertEquals('ö', reader.read());
			reader.mark(100);
			assertEquals('A', reader.read());
			assertEquals(0xD834, reader.read());
			assertEquals(0xDD1E, reader.read());
			reader.reset();
			assertEquals('A', reader.read());
			assertEquals(0xD834, reader.read());
			assertEquals(0xDD1E, reader.read());
			assertEquals('B', reader.read());
			assertEquals(-1, reader.read());
			reader.reset();
			assertEquals('A', reader.read());
			assertEquals(0xD834, reader.read());
			reader.mark(100); // Between surrogates
			assertEquals(0xDD1E, reader.read());
			assertEquals('B', reader.read());
			reader.reset();
			assertEquals(0xDD1E, reader.read());
			assertEquals('B', reader.read());
			assertEquals(-1, reader.read());
		}
	}
	
	
	private static int[] chars(char... chars) {
		int[] ints = new int[chars.length];
		for (int i = 0; i < chars.length; i++) {
			ints[i] = chars[i];
		}
		return ints;
	}
	
	private static int[] chars(String str) {
		return chars(str.toCharArray());
	}
	
	private static int[] readN(int n, Reader reader) throws IOException {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = reader.read();
		}
		return arr;
	}
	
	private static byte[] hex(String hexStr) {
		return HexUtil.hexToBytes(hexStr.replaceAll("\\s", ""));
	}
}
