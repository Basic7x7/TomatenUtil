package de.tomatengames.util.test;

import de.tomatengames.util.SortUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SortUtilTest {
	
	@Test
	void testNumberPreserving() {
		String[] array1 = new String[] { "hello", "xyz", "test", "abc" };
		Arrays.sort(array1, SortUtil::compareNumberPreserving);
		assertArrayEquals(new String[] { "abc", "hello", "test", "xyz" }, array1);
		
		String[] array2 = new String[] { "a6b", null, "a", "a1", "a10", null, "a2", "abc" };
		Arrays.sort(array2, SortUtil::compareNumberPreserving);
		assertArrayEquals(new String[] { null, null, "a", "a1", "a2", "a6b", "a10", "abc" }, array2);
		
		String[] array3 = new String[] { "17", "3", "1", "2", "2.5", "2.12", "2.7", "4", "2.5.1", "24", "532", "135", "1000",
				"a", "abc", "1a", "1.1", "78", "7g", "3", "1000", "abc" };
		Arrays.sort(array3, SortUtil::compareNumberPreserving);
		assertArrayEquals(new String[] {"1", "1.1", "1a", "2", "2.5", "2.5.1", "2.7", "2.12", "3", "3", "4", "7g", "17", "24",
				"78", "135", "532", "1000", "1000", "a", "abc", "abc" }, array3);
	}
	
}
