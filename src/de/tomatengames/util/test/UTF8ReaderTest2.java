package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.exception.CharacterDecodeException;
import de.tomatengames.util.io.UTF8Reader;

// from TomatenJSON
class UTF8ReaderTest2 {
	
	static String decode(byte[] bytes) throws IOException {
		try (Reader r = new UTF8Reader(new ByteArrayInputStream(bytes))) {
			StringBuilder b = new StringBuilder();
			int read;
			while ((read = r.read()) >= 0)
				b.append((char)read);
			return b.toString();
		}
	}
	static byte[] bytes(int... ints) {
		byte[] bytes = new byte[ints.length];
		for (int i = 0; i < ints.length; i++)
			bytes[i] = (byte)ints[i];
		return bytes;
	}
	static void checkDecode(String string) throws IOException {
		assertEquals(string, decode(string.getBytes(StandardCharsets.UTF_8)));
	}
	static String codepointString(int... codepoints) {
		StringBuilder b = new StringBuilder();
		for (int c : codepoints)
			b.appendCodePoint(c);
		return b.toString();
	}
	static void checkInvalid(String message, int... bytes) {
		assertEquals(message, assertThrows(CharacterDecodeException.class, () -> decode(bytes(bytes))).getMessage());
	}
	
	@Test
	void testReader() throws IOException {
		checkDecode("Hallo");
		checkDecode("Hällö");
		checkDecode(codepointString(0, 0x10FFFF));
		checkInvalid("Invalid first UTF-8 byte: 80", 0x80);
		checkInvalid("Invalid first UTF-8 byte: ff", 0xFF);
		checkInvalid("Invalid subsequent UTF-8 byte: 00", 0b1100_0000, 0);
		checkInvalid("Invalid subsequent UTF-8 byte: 01", 0b1110_0000, 1);
		checkInvalid("Invalid subsequent UTF-8 byte: 02", 0b1111_0111, 2);
		checkInvalid("Invalid surrogate in UTF-8: d800", 0xED, 0xA0, 0x80);
		checkInvalid("Invalid surrogate in UTF-8: dfff", 0xED, 0xBF, 0xBF);
		checkInvalid("Invalid UTF-8 code point: 110000", 0b1111_0100, 0b1001_0000, 0b1000_0000, 0b1000_0000);
		checkInvalid("Invalid UTF-8 code point: 1c0000", 0b1111_0111, 0b1000_0000, 0b1000_0000, 0b1000_0000);
	}
	
}
