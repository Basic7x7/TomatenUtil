package de.tomatengames.util.test;

import static de.tomatengames.util.StringUtil.count;
import static de.tomatengames.util.StringUtil.countFront;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
