package de.tomatengames.util.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.RequirementUtil;
import de.tomatengames.util.TestUtil;

class RequirementUtilTest {
	
	@Test
	void testNotNull() {
		TestUtil.assertThrows(IllegalArgumentException.class, "The value must not be null",
				() -> RequirementUtil.requireNotNull(null, "The value..."));
		
		TestUtil.assertThrows(IllegalArgumentException.class, "The value must not be null",
				() -> RequirementUtil.requireNotNull(null, "The value ..."));
		
		RequirementUtil.requireNotNull(new Object(), "The object...");
	}
	
	@Test
	void testNotNegative() {
		TestUtil.assertThrows(IllegalArgumentException.class, "The number must not be negative (was -5)",
				() -> RequirementUtil.requireNotNegative(-5, "The number..."));
		
		TestUtil.assertThrows(IllegalArgumentException.class, "The number must not be negative (was -100.5)",
				() -> RequirementUtil.requireNotNegative(-100.5, "The number..."));
		
		RequirementUtil.requireNotNegative(0, "The number...");
		RequirementUtil.requireNotNegative(5, "The number...");
		RequirementUtil.requireNotNegative(0.0, "The number...");
	}
	
	@Test
	void testNotEmpty() {
		TestUtil.assertThrows(IllegalArgumentException.class, "The list must not be empty",
				() -> RequirementUtil.requireNotEmpty(Arrays.asList(), "The list..."));
		TestUtil.assertThrows(IllegalArgumentException.class, "The list must not be null",
				() -> RequirementUtil.requireNotEmpty((List<?>) null, "The list..."));
		
		TestUtil.assertThrows(IllegalArgumentException.class, "The array must not be empty",
				() -> RequirementUtil.requireNotEmpty(new String[] {}, "The array..."));
		TestUtil.assertThrows(IllegalArgumentException.class, "The array must not be null",
				() -> RequirementUtil.requireNotEmpty((String[]) null, "The array..."));
		
		RequirementUtil.requireNotEmpty(Arrays.asList("e"), "The list...");
		RequirementUtil.requireNotEmpty(new String[] {"e"}, "The array...");
	}
	
	@Test
	void testAllNotNull() {
		TestUtil.assertThrows(IllegalArgumentException.class, "The list must not be null",
				() -> RequirementUtil.requireAllNotNull((List<?>) null, "The list..."));
		TestUtil.assertThrows(IllegalArgumentException.class, "The list must not contain null (found at index 1)",
				() -> RequirementUtil.requireAllNotNull(Arrays.asList("A", null, "B"), "The list..."));
		TestUtil.assertThrows(IllegalArgumentException.class, "The set must not contain null",
				() -> RequirementUtil.requireAllNotNull(new HashSet<>(Arrays.asList("A", null, "B")), "The set..."));
		
		RequirementUtil.requireAllNotNull(Arrays.asList("A", "B"), "The list...");
		RequirementUtil.requireAllNotNull(Arrays.asList(), "The list...");
		
		TestUtil.assertThrows(IllegalArgumentException.class, "The array must not be null",
				() -> RequirementUtil.requireAllNotNull((String[]) null, "The array..."));
		TestUtil.assertThrows(IllegalArgumentException.class, "The array must not contain null (found at index 1)",
				() -> RequirementUtil.requireAllNotNull(new String[] {"A", null, "B"}, "The array..."));
		
		RequirementUtil.requireAllNotNull(new String[] {"A", "B"}, "The array...");
		RequirementUtil.requireAllNotNull(new String[] {}, "The array...");
	}
	
}
