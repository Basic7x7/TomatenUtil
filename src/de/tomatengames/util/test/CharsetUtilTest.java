package de.tomatengames.util.test;

import static de.tomatengames.util.CharsetUtil.encodeUTF8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CharsetUtilTest {
	
	@Test
	void testEncodeUTF8ToInt() {
		assertEquals(0x41, encodeUTF8('A'));
		assertEquals(0xC3B6, encodeUTF8('ö'));
		
		// Examples from https://en.wikipedia.org/wiki/UTF-8
		assertEquals(0x24, encodeUTF8('$'));
		assertEquals(0xC2A3, encodeUTF8('£')); // U+00A3
		assertEquals(0xD098, encodeUTF8('И')); // U+0418
		assertEquals(0xE0A4B9, encodeUTF8('ह')); // U+0939
		assertEquals(0xE282AC, encodeUTF8('€')); // U+20AC
		assertEquals(0xED959C, encodeUTF8('한')); // U+D55C
		assertEquals(0xF0908D88, encodeUTF8(0x10348));
	}
	
}
