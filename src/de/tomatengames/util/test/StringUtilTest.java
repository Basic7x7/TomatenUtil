package de.tomatengames.util.test;

import static de.tomatengames.util.StringUtil.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.StringUtil;

class StringUtilTest {
	
	@Test
	void testCount() {
		assertEquals(2, count("Hello", 'l'));
		assertEquals(0, count("Hello", 'x'));
		assertEquals(3, count("This is a test", ' '));
		
		assertEquals(4, count("This Is A Test", Character::isUpperCase));
		assertEquals(3, count("this is a test", c -> c == 'i' || c == 'e'));
		assertEquals(0, count("this is a test", c -> false));
		assertEquals(14, count("this is a test", c -> true));
	}
	
	@Test
	void testCountFront() {
		assertEquals(1, countFront("Hello", 'H'));
		assertEquals(0, countFront("Hello", 'x'));
		assertEquals(3, countFront("   test", ' '));
		assertEquals(2, countFront("AABBCC", 'A'));
		
		assertEquals(3, countFront("ABCdef", Character::isUpperCase));
		assertEquals(4, countFront("ieeix", c -> c == 'i' || c == 'e'));
		assertEquals(0, countFront("this is a test", c -> false));
		assertEquals(14, countFront("this is a test", c -> true));
	}
	
	@Test
	void testTrimFrontChar() {
		assertEquals("test", StringUtil.trimFront("    test", ' '));
		assertEquals("ello", StringUtil.trimFront("hello", 'h'));
		assertEquals("hello", StringUtil.trimFront("hello", 'e'));
		assertEquals("est", StringUtil.trimFront("ttest", 't'));
		
		assertEquals(" test", StringUtil.trimFront("    test", ' ', 3));
		assertEquals("ello", StringUtil.trimFront("hello", 'h', 6));
		assertEquals("ello", StringUtil.trimFront("hello", 'h', 1));
		assertEquals("hello", StringUtil.trimFront("hello", 'h', 0));
	}
	
	@Test
	void testTrimFrontPredicate() {
		assertEquals("test", StringUtil.trimFront("   \t test", Character::isWhitespace));
		assertEquals("ello", StringUtil.trimFront("hello", c -> c == 'h'));
		assertEquals("hello", StringUtil.trimFront("hello", c -> c == 'e'));
		assertEquals("st", StringUtil.trimFront("TTEst", Character::isUpperCase));
		
		assertEquals("\t test", StringUtil.trimFront(" \t \t test", Character::isWhitespace, 3));
		assertEquals("ello", StringUtil.trimFront("hello", c -> c == 'h', 6));
		assertEquals("TEst", StringUtil.trimFront("TTEst", Character::isUpperCase, 1));
		assertEquals("Est", StringUtil.trimFront("TTEst", Character::isUpperCase, 2));
		assertEquals("st", StringUtil.trimFront("TTEst", Character::isUpperCase, 3));
		assertEquals("ttest", StringUtil.trimFront("ttest", Character::isUpperCase, 1));
	}
	
	@Test
	void testStartsWith() {
		assertEquals(true, startsWithIgnoreCase("test", "te"));
		assertEquals(false, startsWithIgnoreCase("test", "es"));
		assertEquals(false, startsWithIgnoreCase("test", "st"));
		assertEquals(true, startsWithIgnoreCase("test", "st", 2));
		assertEquals(false, startsWithIgnoreCase("test", "st", 3));
		assertEquals(true, startsWithIgnoreCase("Test", "te"));
		assertEquals(true, startsWithIgnoreCase("test", "Te"));
		assertEquals(true, startsWithIgnoreCase("HeLlO", "hello"));
		assertEquals(false, startsWithIgnoreCase("HeLlO", "ello"));
		assertEquals(false, startsWithIgnoreCase("HeLlO", "helloa"));
		assertEquals(true, startsWithIgnoreCase("helloa", "HeLlO"));
		assertEquals(true, startsWithIgnoreCase("ABCDEFG", "abc"));
		assertEquals(false, startsWithIgnoreCase("ABCDEFG", "cde"));
		assertEquals(true, startsWithIgnoreCase("ABCDEFG", "cde", 2));
		assertEquals(false, startsWithIgnoreCase("ABCDEFG", "abc", -5));
		assertEquals(true, startsWithIgnoreCase("ABCDEFG", "ABC"));
		assertEquals(true, startsWithIgnoreCase("abcdefg", "EFG", 4));
		assertEquals(false, startsWithIgnoreCase("abcdefg", "EFGH", 4));
		assertEquals(false, startsWithIgnoreCase("abcdefg", "EFGHIJK", 4));
		
		assertEquals(false, startsWithIgnoreCase(null, "a", 4));
		assertEquals(false, startsWithIgnoreCase(null, "a"));
		assertEquals(false, startsWithIgnoreCase(null, null));
		assertEquals(false, startsWithIgnoreCase("a", null));
	}
	
	@Test
	void testEndsWith() {
		assertEquals(false, endsWithIgnoreCase("test", "te"));
		assertEquals(true, endsWithIgnoreCase("test", "st"));
		assertEquals(true, endsWithIgnoreCase("TEST", "st"));
		assertEquals(true, endsWithIgnoreCase("test", "ST"));
		assertEquals(true, endsWithIgnoreCase("ABCDEFG", "g"));
		assertEquals(true, endsWithIgnoreCase("ABCDEFG", "efg"));
		assertEquals(false, endsWithIgnoreCase("ABCDEFG", "efgh"));
		assertEquals(true, endsWithIgnoreCase("abcdefg", "EFG"));
		assertEquals(false, endsWithIgnoreCase("abcdefg", "EFA"));
		assertEquals(false, endsWithIgnoreCase("abcdefg", "EFGH"));
		
		assertEquals(false, endsWithIgnoreCase(null, "a"));
		assertEquals(false, endsWithIgnoreCase(null, null));
		assertEquals(false, endsWithIgnoreCase("a", null));
	}
	
	@Test
	void testContains() {
		assertEquals(true, containsIgnoreCase("test", "t"));
		assertEquals(true, containsIgnoreCase("test", "e"));
		assertEquals(true, containsIgnoreCase("test", "es"));
		assertEquals(true, containsIgnoreCase("test", "st"));
		assertEquals(false, containsIgnoreCase("test", "st2"));
		assertEquals(false, containsIgnoreCase("test", "hello"));
		assertEquals(true, containsIgnoreCase("TEST", "es"));
		assertEquals(true, containsIgnoreCase("HeLlO", "hello"));
		assertEquals(false, containsIgnoreCase("HeLlO", "hello2"));
		assertEquals(true, containsIgnoreCase("HeLlO", "lo"));
		assertEquals(true, containsIgnoreCase("HeLlO", "ell"));
		assertEquals(true, containsIgnoreCase("HeLlO", "elL"));
		assertEquals(false, containsIgnoreCase("HeLlO", "ela"));
		assertEquals(true, containsIgnoreCase("ABCDEF", "def"));
		assertEquals(false, containsIgnoreCase("ABCDEF", "defg"));
		assertEquals(true, containsIgnoreCase("ABCDEF", "bcd"));
		assertEquals(true, containsIgnoreCase("abcdef", "BCD"));
		assertEquals(true, containsIgnoreCase("ABCDEF", "c"));
		assertEquals(true, containsIgnoreCase("ABCDEF", "f"));
		assertEquals(true, containsIgnoreCase("ABCDEF", "a"));
		assertEquals(false, containsIgnoreCase("ABCDEF", "x"));
		
		assertEquals(false, containsIgnoreCase(null, "a"));
		assertEquals(false, containsIgnoreCase(null, null));
		assertEquals(false, containsIgnoreCase("a", null));
	}
	
	@Test
	void testIsEqualCT() {
		assertEquals(true, isEqualCT("test", "test"));
		assertEquals(false, isEqualCT("test", "test1"));
		assertEquals(false, isEqualCT("test", "text"));
		assertEquals(false, isEqualCT("test", "Test"));
		assertEquals(true, isEqualCT("Test", "Test"));
		assertEquals(false, isEqualCT("ABC", "Test"));
		assertEquals(false, isEqualCT("", "Test"));
		assertEquals(true, isEqualCT("", ""));
		assertEquals(false, isEqualCT("Test", ""));
		assertEquals(false, isEqualCT("Test123456789", "Test"));
		assertEquals(false, isEqualCT("Test", "Test123456789"));
		assertEquals(true, isEqualCT("Test123456789", "Test123456789"));
		assertEquals(true, isEqualCT("𨉟呐㗂越", "𨉟呐㗂越"));
		assertEquals(false, isEqualCT("𨉟呐a越", "𨉟呐㗂越"));
		assertEquals(false, isEqualCT("𨉟呐㗂越", "𨉟呐b越"));
	}
	
	private static void assertMatchesAll(String input, String[] validpatterns, String[] invalidpatterns) {
		for (String pattern : validpatterns)
			assertTrue(matchesSimplePattern(input, pattern));
		for (String pattern : invalidpatterns)
			assertFalse(matchesSimplePattern(input, pattern));
	}
	
	@Test
	void testSimplePattern() {
		assertTrue(matchesSimplePattern("test", "test"));
		assertFalse(matchesSimplePattern("test", "Test"));
		assertFalse(matchesSimplePattern("test", "tes"));
		assertTrue(matchesSimplePattern("test", "te*"));
		assertTrue(matchesSimplePattern("test", "*st"));
		assertFalse(matchesSimplePattern("test", "st*"));
		assertFalse(matchesSimplePattern("test", "*te"));
		assertTrue(matchesSimplePattern("test", "t*es*t"));
		assertTrue(matchesSimplePattern("test", "*t*e*s*t*"));
		assertFalse(matchesSimplePattern("test", "*t*s*e*t*"));
		assertTrue(matchesSimplePattern("test", "*test"));
		assertTrue(matchesSimplePattern("test", "test*"));
		assertTrue(matchesSimplePattern("test", "**test"));
		assertTrue(matchesSimplePattern("test", "test**"));
		assertFalse(matchesSimplePattern("test", "test*t"));
		assertFalse(matchesSimplePattern("test", "*testt"));
		assertMatchesAll("de.tomatengames.util.StringUtil",
				new String[] {"*", "**", "*StringUtil", "de.*.StringUtil",
						"de*tomatengames*.StringUtil", "***StringUtil***",
						"***.***StringUtil", "*tomatengames*", "de.tomatengames*"},
				new String[] {"*HexUtil", "de.*.tomatengames.*.StringUtil",
						"*tomatengames", "tomatengames*"});
	}
}
