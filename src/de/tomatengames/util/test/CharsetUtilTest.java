package de.tomatengames.util.test;

import static de.tomatengames.util.CharsetUtil.encodeUTF8;
import static de.tomatengames.util.TestUtil.assertOutputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class CharsetUtilTest {
	
	@Test
	void testEncodeUTF8ToInt() {
		assertEquals(0x41, encodeUTF8('A'));
		assertEquals(0xC3B6, encodeUTF8('ö'));
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(-1));
		assertThrows(IllegalArgumentException.class, () -> encodeUTF8(0xFFFFFF));
		
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
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertOutputStream("24", out -> encodeUTF8('$', out));
		assertOutputStream("C2A3", out -> encodeUTF8('£', out)); // U+00A3
		assertOutputStream("D098", out -> encodeUTF8('И', out)); // U+0418
		assertOutputStream("E0A4B9", out -> encodeUTF8('ह', out)); // U+0939
		assertOutputStream("E282AC", out -> encodeUTF8('€', out)); // U+20AC
		assertOutputStream("ED959C", out -> encodeUTF8('한', out)); // U+D55C
		assertOutputStream("F0908D88", out -> encodeUTF8(0x10348, out));
	}
}
