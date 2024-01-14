package de.tomatengames.util.test;

import static de.tomatengames.util.ReflectionUtil.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ReflectionUtilTest {
	
	@Test
	void testReflection() {
		String hexutil = "de.tomatengames.util.HexUtil";
		assertArrayEquals("0123456789abcdef".toCharArray(), getStatic(hexutil, "hexChars"));
		assertTrue(Class(hexutil).isInstance(construct(hexutil)));
		assertEquals(-1L, (long)runStatic(hexutil, "hexToLong", "FFFFFFFFFFFFFFFF"));
		
		String linked = "de.tomatengames.util.linked.LinkedInt";
		Object l = construct(linked, (byte)10);
		assertEquals(10, (int)get(l, "value"));
		set(l, "value", 100);
		assertEquals(100, (int)run(l, "get"));
		run(l, "set", (short)1000);
		assertEquals(1000, (int)run(l, "get"));
	}
	
}
