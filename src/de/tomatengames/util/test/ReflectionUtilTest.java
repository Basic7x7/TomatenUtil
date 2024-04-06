package de.tomatengames.util.test;

import static de.tomatengames.util.ReflectionUtil.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.exception.ReflectionException;
import de.tomatengames.util.linked.LinkedInt;

class ReflectionUtilTest {
	
	static short teststatic = 0;
	
	@Test
	void testConvenient() {
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
	
	@Test
	void testField() {
		LinkedInt linked = new LinkedInt(10);
		Field field = field(LinkedInt.class, "value");
		assertEquals(10, getInt(linked, field));
		assertEquals(10, getLong(linked, field));
		assertThrows(IllegalArgumentException.class, () -> getByte(linked, field)); // narrowing cast from int to byte
		assertThrows(IllegalArgumentException.class, () -> setDouble(linked, field, 1)); // narrowing cast from double to int
		assertThrows(ReflectionException.class, () -> getStaticInt(field)); // not static
		setByte(linked, field, (byte)1);
		assertEquals(1, linked.get());
	}
	
	@Test
	void testStaticField() {
		Field field = field(ReflectionUtilTest.class, "teststatic");
		setStaticByte(field, (byte)5);
		assertThrows(IllegalArgumentException.class, () -> getStaticByte(field)); // narrowing cast from short to byte
		assertEquals(5, getStaticShort(field));
		assertEquals(5, getStaticInt(field));
		assertEquals(Short.valueOf((short)5), getStatic(field));
		setStatic(field, Short.valueOf((short)2));
		assertEquals(2, getStaticLong(field));
	}
	
}
