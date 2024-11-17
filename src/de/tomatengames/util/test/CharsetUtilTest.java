package de.tomatengames.util.test;

import static de.tomatengames.util.CharsetUtil.encodeUTF8;
import static de.tomatengames.util.TestUtil.assertOutputStream;
import static de.tomatengames.util.TestUtil.assertOutputStreamThrows;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.CharsetUtil;
import de.tomatengames.util.HexUtil;
import de.tomatengames.util.IOUtil;
import de.tomatengames.util.exception.CharacterDecodeException;
import de.tomatengames.util.exception.LimitException;

class CharsetUtilTest {
	
	@Test
	void testEncodeUTF8ToInt() {
		assertEquals(0x41, encodeUTF8('A'));
		assertEquals(0xC3B6, encodeUTF8('ö'));
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(-1));
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(0xFFFFFF));
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(0xD834)); // High surrogate
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(0xDD1E)); // Low surrogate
		assertEquals(0xF09D849E, encodeUTF8(0x1D11E));
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertEquals(0x24, encodeUTF8('$'));
		assertEquals(0xC2A3, encodeUTF8('£')); // U+00A3
		assertEquals(0xD098, encodeUTF8('И')); // U+0418
		assertEquals(0xE0A4B9, encodeUTF8('ह')); // U+0939
		assertEquals(0xE282AC, encodeUTF8('€')); // U+20AC
		assertEquals(0xED959C, encodeUTF8('한')); // U+D55C
		assertEquals(0xF0908D88, encodeUTF8(0x10348));
	}
	
	@Test
	void testEncodeUTF8Char() throws IOException {
		assertOutputStream("41", out -> encodeUTF8('A', out));
		assertOutputStream("C3B6", out -> encodeUTF8('ö', out));
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(-1, new ByteArrayOutputStream()));
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(0xFFFFFF, new ByteArrayOutputStream()));
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(0xD834, new ByteArrayOutputStream())); // High surrogate
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(0xDD1E, new ByteArrayOutputStream())); // Low surrogate
		assertOutputStream("F09D849E", out -> encodeUTF8(0x1D11E, out));
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertOutputStream("24", out -> encodeUTF8('$', out));
		assertOutputStream("C2A3", out -> encodeUTF8('£', out)); // U+00A3
		assertOutputStream("D098", out -> encodeUTF8('И', out)); // U+0418
		assertOutputStream("E0A4B9", out -> encodeUTF8('ह', out)); // U+0939
		assertOutputStream("E282AC", out -> encodeUTF8('€', out)); // U+20AC
		assertOutputStream("ED959C", out -> encodeUTF8('한', out)); // U+D55C
		assertOutputStream("F0908D88", out -> encodeUTF8(0x10348, out));
	}
	
	@Test
	void testEncodeUTF8String() throws IOException {
		assertOutputStream("41", out -> encodeUTF8("A", out));
		assertOutputStream("C3B6", out -> encodeUTF8("ö", out));
		assertOutputStream("C3B641C3B6", out -> encodeUTF8("öAö", out));
		assertOutputStream("C3B641F09D849E42", out -> encodeUTF8("öA𝄞B", out));
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertOutputStream("4D C3AC 6E 68 20 6E C3B3 69 20 74 69 E1BABF 6E 67 20 56 69 E1BB87 74",
				out -> encodeUTF8("Mình nói tiếng Việt", out));
		assertOutputStream("F0A8899F E59190 E39782 E8B68A",
				out -> encodeUTF8("𨉟呐㗂越", out));
	}
	
	@Test
	void testEncodeUTF8StringWithLimit() throws IOException {
		assertOutputStream("41", out -> encodeUTF8("A", out, 1));
		assertOutputStream("", assertOutputStreamThrows(LimitException.class,
				out -> encodeUTF8("A", out, 0)));
		assertOutputStream("414243", assertOutputStreamThrows(LimitException.class,
				out -> encodeUTF8("ABCD", out, 3)));
		assertOutputStream("41", assertOutputStreamThrows(LimitException.class,
				out -> encodeUTF8("ABCD", out, 1)));
		assertOutputStream("41F09D849E42", out -> encodeUTF8("A𝄞B", out, 6));
		assertOutputStream("41F09D849E", assertOutputStreamThrows(LimitException.class,
				out -> encodeUTF8("A𝄞B", out, 5)));
		
		assertOutputStream("", out -> encodeUTF8("", out, 0));
		
		// High surrogate without low surrogate
		assertOutputStream("", assertOutputStreamThrows(IllegalArgumentException.class,
				out -> encodeUTF8(String.valueOf((char) 0xD834), out, 15)));
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertOutputStream("4D C3AC 6E 68 20 6E C3B3 69 20 74 69 E1BABF 6E 67 20 56 69 E1BB87 74",
				out -> encodeUTF8("Mình nói tiếng Việt", out, 100));
		assertOutputStream("F0A8899F E59190 E39782 E8B68A",
				out -> encodeUTF8("𨉟呐㗂越", out, 13));
		
		assertOutputStream("4D C3AC 6E 68 20 6E C3B3 69 20 74 69 E1BABF", assertOutputStreamThrows(
				LimitException.class, out -> encodeUTF8("Mình nói tiếng Việt", out, 15)));
		assertOutputStream("4D C3AC 6E 68 20 6E C3B3 69 20", assertOutputStreamThrows(
				LimitException.class, out -> encodeUTF8("Mình nói tiếng Việt", out, 11)));
		assertOutputStream("F0A8899F E59190", assertOutputStreamThrows(LimitException.class,
				out -> encodeUTF8("𨉟呐㗂越", out, 6)));
		assertOutputStream("F0A8899F E59190 E39782 E8B68A", assertOutputStreamThrows(LimitException.class,
				out -> encodeUTF8("𨉟呐㗂越", out, 12)));
	}
	
	@Test
	void testEncodeUTF8ToByteArray() throws IOException {
		assertEncToArr("41", 'A');
		assertEncToArr("C3B6", 'ö');
		
		// Test offset
		byte[] out = new byte[5];
		out[0] = 24;
		assertEquals(1, encodeUTF8('B', out, 1));
		assertEquals(1, encodeUTF8('D', out, 2));
		assertEquals(2, encodeUTF8('ö', out, 3));
		assertArrayEquals(new byte[] { 24, 0x42, 0x44, (byte) 0xC3, (byte) 0xB6 }, out);
		
		// Test errors
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(-1, out, 0));
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(0xFFFFFF, out, 0));
		assertThrows(IndexOutOfBoundsException.class, () -> encodeUTF8('A', out, 5));
		assertThrows(IndexOutOfBoundsException.class, () -> encodeUTF8('ö', out, 4)); // 2 bytes
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(0xD834, out, 0)); // High surrogate
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(0xDD1E, out, 0)); // Low surrogate
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertEncToArr("24", '$');
		assertEncToArr("C2A3", '£'); // U+00A3
		assertEncToArr("D098", 'И'); // U+0418
		assertEncToArr("E0A4B9", 'ह'); // U+0939
		assertEncToArr("E282AC", '€'); // U+20AC
		assertEncToArr("ED959C", '한'); // U+D55C
		assertEncToArr("F0908D88", 0x10348);
		
		assertEncToArr("41", "A");
		assertEncToArr("42 44 C3 B6", "BDö");
		assertThrows(IndexOutOfBoundsException.class, () -> encodeUTF8("test", out, -1));
		assertThrows(IndexOutOfBoundsException.class, () -> encodeUTF8("test12", out, 0));
		assertThrows(IndexOutOfBoundsException.class, () -> encodeUTF8("test", out, 2));
		
		assertEncToArr("F09D849E", 0x1D11E);
		assertEncToArr("41F09D849E42", "A𝄞B");
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertEncToArr("4D C3AC 6E 68 20 6E C3B3 69 20 74 69 E1BABF 6E 67 20 56 69 E1BB87 74", "Mình nói tiếng Việt");
		assertEncToArr("F0A8899F E59190 E39782 E8B68A", "𨉟呐㗂越");
	}
	
	@Test
	void testEncodeUTF8WithBuffer() throws IOException {
		byte[] buf = new byte[6];
		assertOutputStream("41", out -> encodeUTF8("A", out, buf));
		assertOutputStream("C3B6", out -> encodeUTF8("ö", out, buf));
		assertOutputStream("C3B641C3B642", out -> encodeUTF8("öAöB", out, buf));
		assertOutputStream("5468697320697320c3a420746578742e20546869732074657874207368c3b6c3bc6c6420626520" +
				"6cc3b66e6720656e6f7567682074c3b62066696c6c2074686520627566666572206d756c7469706c652074696d65732e",
				out -> encodeUTF8("This is ä text. " +
				"This text shöüld be löng enough tö fill the buffer multiple times.", out, new byte[11]));
		
		// High surrogate without low surrogate
		assertOutputStream("", assertOutputStreamThrows(IllegalArgumentException.class,
				out -> encodeUTF8(String.valueOf((char) 0xD834), out, buf)));
		
		assertOutputStream("C3A441C3B6f09d849e42", out -> encodeUTF8("äAö𝄞B", out, new byte[4]));
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertOutputStream("4D C3AC 6E 68 20 6E C3B3 69 20 74 69 E1BABF 6E 67 20 56 69 E1BB87 74",
				out -> encodeUTF8("Mình nói tiếng Việt", out, buf));
		assertOutputStream("F0A8899F E59190 E39782 E8B68A",
				out -> encodeUTF8("𨉟呐㗂越", out, buf));
	}
	
	@Test
	void testEncodeUTF8WithBufferWithLimit() throws IOException {
		byte[] buf = new byte[6];
		assertOutputStream("41", out -> encodeUTF8("A", out, 1, buf));
		assertOutputStream("42", out -> encodeUTF8("B", out, 10, buf));
		assertOutputStream("", assertOutputStreamThrows(LimitException.class, out -> encodeUTF8("C", out, 0, buf)));
		assertOutputStream("C3B6", out -> encodeUTF8("ö", out, 2, buf));
		assertOutputStream("C3B641C3B642C3B643", out -> encodeUTF8("öAöBöC", out, 10, buf));
		assertOutputStream("C3B641C3B642", assertOutputStreamThrows(LimitException.class,
				out -> encodeUTF8("öAöBöC", out, 8, buf))); // 3+3 bytes written
		
		assertOutputStream("54686973206973c3a4 20746578742e2054 6869732074657874", assertOutputStreamThrows(LimitException.class,
				out -> encodeUTF8("This isä text. " +
				"This text shöüld be löng enough tö fill the buffer multiple times.", out, 30, new byte[11]))); // 9+8+8 bytes written
		
		// High surrogate without low surrogate
		assertOutputStream("", assertOutputStreamThrows(IllegalArgumentException.class,
				out -> encodeUTF8(String.valueOf((char) 0xD834), out, 15, buf)));
		
		assertOutputStream("C3A441C3B6f09d849e42", out -> encodeUTF8("äAö𝄞B", out, new byte[4]));
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertOutputStream("4D C3AC 6E 68 20 6E C3B3 69 20 74 69 E1BABF 6E 67 20 56 69 E1BB87 74",
				out -> encodeUTF8("Mình nói tiếng Việt", out, 30, buf));
		assertOutputStream("F0A8899F E59190 E39782 E8B68A",
				out -> encodeUTF8("𨉟呐㗂越", out, 100, buf));
	}
	
	
	private void assertEncToArr(String expectedHex, int codePoint) {
		byte[] expected = HexUtil.hexToBytes(expectedHex);
		byte[] out = new byte[4];
		assertEquals(expected.length, encodeUTF8(codePoint, out, 0));
		assertArrayEquals(Arrays.copyOf(expected, 4), out);
	}
	private void assertEncToArr(String expectedHex, String str) {
		byte[] expected = HexUtil.hexToBytes(expectedHex.replace(" ", ""));
		byte[] out = new byte[4*str.length()];
		assertEquals(expected.length, encodeUTF8(str, out, 0));
		assertArrayEquals(Arrays.copyOf(expected, 4*str.length()), out);
	}
	
	
	@Test
	void testDecodeUTF8ToInt() throws IOException {
		assertUTF8Decode(0x41, null, new byte[] {'A'});
		assertUTF8Decode(0x42, new byte[] {'C', 'D'}, new byte[] {'B', 'C', 'D'});
		assertUTF8Decode(-1, null, new byte[] {});
		assertUTF8Decode(0xF6, null, new byte[] {(byte) 0xC3, (byte) 0xB6}); // 'ö'
		assertUTF8DecodeError(new byte[] {(byte) 0xC3}); // only one byte of 'ö'
		assertUTF8DecodeError(new byte[] { (byte) 0xff });
		assertUTF8Decode(0x1D11E, null, new byte[] {(byte) 0xF0, (byte) 0x9D, (byte) 0x84, (byte) 0x9E});
		assertUTF8DecodeError(new byte[] { (byte) 0xED, (byte) 0xA0, (byte) 0xB4 }); // Encoded high surrogate
		assertUTF8DecodeError(new byte[] { (byte) 0xED, (byte) 0xB4, (byte) 0x9E }); // Encoded low surrogate
		
		
		// Examples from https://www.rfc-editor.org/rfc/rfc3629
		assertUTF8DecodeError(new byte[] { (byte) 0xC0, (byte) 0x80 }); // <=> U+0000
		assertUTF8DecodeError(new byte[] { (byte) 0xC0, (byte) 0xAE }); // <=> '.'
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertUTF8Decode(0x24, null, new byte[] {'$'});
		assertUTF8Decode(0x00A3, null, new byte[] {(byte) 0xC2, (byte) 0xA3}); // '£'
		assertUTF8Decode(0x0418, null, new byte[] {(byte) 0xD0, (byte) 0x98}); // 'И'
		assertUTF8Decode(0x0939, null, new byte[] {(byte) 0xE0, (byte) 0xA4, (byte) 0xB9}); // 'ह'
		assertUTF8Decode(0x20AC, null, new byte[] {(byte) 0xE2, (byte) 0x82, (byte) 0xAC}); // '€'
		assertUTF8Decode(0xD55C, null, new byte[] {(byte) 0xED, (byte) 0x95, (byte) 0x9C}); // '한'
		assertUTF8Decode(0x10348, null, new byte[] {(byte) 0xF0, (byte) 0x90, (byte) 0x8D, (byte) 0x88});
	}
	
	private static void assertUTF8Decode(int expectedCodePoint, byte[] expectedRest, byte[] input) throws IOException {
		try (ByteArrayInputStream in = new ByteArrayInputStream(input)) {
			assertEquals(expectedCodePoint, CharsetUtil.decodeUTF8(in));
			if (expectedRest != null) {
				byte[] rest = new byte[expectedRest.length];
				IOUtil.readFully(in, rest);
				assertArrayEquals(expectedRest, rest);
			}
			assertEquals(-1, in.read());
		}
	}
	
	private static void assertUTF8DecodeError(byte[] input) throws IOException {
		try (ByteArrayInputStream in = new ByteArrayInputStream(input)) {
			assertThrows(CharacterDecodeException.class, () -> CharsetUtil.decodeUTF8(in));
		}
	}
}
