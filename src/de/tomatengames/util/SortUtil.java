package de.tomatengames.util;

import java.util.Comparator;

/**
 * Provides {@link Comparator} functions.
 * 
 * @author Basic7x7
 * @version
 * 2023-03-09 last modified<br>
 * 2023-02-11 created
 * @since 1.0
 */
public class SortUtil {
	
	// Static class
	private SortUtil() {
	}
	
	/**
	 * Compares the specified strings case-insensitive.
	 * If the strings contain numbers, the numbers are compared like numbers and not character-wise.
	 * <p>
	 * Example:
	 * <pre>
	 * compare("Hello", "Test") => "Hello" < "Test"
	 * compare("Test1", "Test2") => "Test1" < "Test2"
	 * compare("Test11", "Test2") => "Test11" > "Test2"
	 * </pre>
	 * The string {@code null} is before all other strings.
	 * @param s1 The first string. May be {@code null}.
	 * @param s2 The second string. May be {@code null}.
	 * @return {@code -1} if {@code s1<s2}, {@code 1} if {@code s1>s2} or {@code 0} if {@code s1=s2}.
	 */
	public static int compareNumberPreserving(String s1, String s2) {
		if (s1 == null) {
			return s2 == null ? 0 : -1;
		}
		else if (s2 == null) {
			return 1;
		}
		
		int n1 = s1.length();
		int n2 = s2.length();
		
		int prevChar = -1;
		for (int i = 0; i < n1 && i < n2; i++) {
			char c1 = Character.toLowerCase(s1.charAt(i));
			char c2 = Character.toLowerCase(s2.charAt(i));
			
			// Of both characters are digits, the length of both numbers is checked.
			// A shorter number is smaller than a larger one.
			// If both numbers are identical in length, they are compared character-wise by the next iterations.
			// If the last character was already a digit, the lengths of the numbers have already been compared.
			// In this case, nothing has to be done here.
			if (Character.isDigit(c1) && Character.isDigit(c2) && (prevChar < 0 || !Character.isDigit(prevChar))) {
				// Calculates the lengths of both numbers.
				int m1, m2;
				for (m1 = i+1; m1 < n1 && Character.isDigit(s1.charAt(m1)); m1++);
				for (m2 = i+1; m2 < n2 && Character.isDigit(s2.charAt(m2)); m2++);
				// If both numbers are different in length, the shorter one is smaller.
				if (m1 < m2)
					return -1;
				else if (m1 > m2)
					return 1;
				// Here is m1 = m2. The numbers can be compared character-wise.
			}
			
			// Compares the character.
			if (c1 < c2)
				return -1;
			else if (c1 > c2)
				return 1;
			
			prevChar = c1; // = c2
		}
		
		// If one string is a prefix of the other, the shorter string is before the other one.
		if (n1 < n2)
			return -1;
		else if (n1 > n2)
			return 1;
		
		// If one string is a prefix of the other and both strings are identical in length,
		// both strings are the same.
		return 0;
	}
	
}
