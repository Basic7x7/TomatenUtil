package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.Undepender;

class UndependerTest {
	
	@Test
	void testNull() {
		assertEquals(null, (Object) Undepender.get("c31fbbc6-7208-4749-9199-8629fcba23fd"));
		assertThrows(IllegalArgumentException.class, () -> Undepender.set(null, "test"));
		assertEquals(null, (Object) Undepender.get(null));
	}
	
	@Test
	void testString() {
		assertEquals(null, (String) Undepender.get("str1/eb1facb7-ce07-4bef-a7b6-b195ceac626b"));
		Undepender.set("str1/eb1facb7-ce07-4bef-a7b6-b195ceac626b", "Test");
		Undepender.set("str2/81281c21-3038-4919-b376-12163acd04f5", "ABCDEFG");
		Undepender.set("str3/cd3a500b-1b09-4392-9d24-bcfc2e26719d", "This is test 3");
		assertEquals("ABCDEFG", Undepender.get("str2/81281c21-3038-4919-b376-12163acd04f5"));
		assertEquals(null, (String) Undepender.get("str2"));
		assertEquals("This is test 3", Undepender.get("str3/cd3a500b-1b09-4392-9d24-bcfc2e26719d"));
		assertEquals("Test", Undepender.get("str1/eb1facb7-ce07-4bef-a7b6-b195ceac626b"));
		
		Undepender.set("str2/81281c21-3038-4919-b376-12163acd04f5", "XYZ");
		assertEquals("Test", Undepender.get("str1/eb1facb7-ce07-4bef-a7b6-b195ceac626b"));
		assertEquals("XYZ", Undepender.get("str2/81281c21-3038-4919-b376-12163acd04f5"));
		
		Undepender.set("str2/81281c21-3038-4919-b376-12163acd04f5", null);
		assertEquals(null, (String) Undepender.get("str2/81281c21-3038-4919-b376-12163acd04f5"));
	}
	
	@Test
	void testFunction() {
		Undepender.set("f1/70c3a0e0-b2be-4e85-9a9a-b5aa60e8a4df", (Function<Integer, Integer>) i -> 7*i);
		Undepender.set("f2/f19b4592-6aa0-4432-b670-fc181cf80a22", (Function<String, String>) s -> s+s);
		
		Function<Integer, Integer> f1 = Undepender.get("f1/70c3a0e0-b2be-4e85-9a9a-b5aa60e8a4df");
		assertEquals(49, f1.apply(7));
		assertEquals(70, f1.apply(10));
		
		Function<String, String> f2 = Undepender.get("f2/f19b4592-6aa0-4432-b670-fc181cf80a22");
		assertEquals("TestTest", f2.apply("Test"));
		assertEquals("ABCABC", f2.apply("ABC"));
	}
	
}
