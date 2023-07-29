package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.NumberUtil;

class NumberUtilTest {
	@Test
	void testLimit() {
		assertEquals(5, NumberUtil.limit(5, 0, 10));
		assertEquals(0, NumberUtil.limit(-5, 0, 10));
		assertEquals(10, NumberUtil.limit(20, 0, 10));
		assertEquals(9, NumberUtil.limit(9, 0, 10));
		
		assertEquals(5L, NumberUtil.limit(5L, 4L, 12L));
		assertEquals(4L, NumberUtil.limit(3L, 4L, 12L));
		assertEquals(12L, NumberUtil.limit(1000L, 4L, 12L));
		assertEquals(4L, NumberUtil.limit(-475923427478957432L, 4L, 12L));
		
		assertEquals(5.4, NumberUtil.limit(5.4, 5.4, 7.0));
		assertEquals(6.3, NumberUtil.limit(6.3, 5.4, 7.0));
		assertEquals(5.1, NumberUtil.limit(1.2, 5.1, 6.0));
		assertEquals(6.0, NumberUtil.limit(6.3, 5.1, 6.0));
	}
}
