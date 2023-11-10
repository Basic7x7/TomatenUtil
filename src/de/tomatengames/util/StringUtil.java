package de.tomatengames.util;

import java.util.function.IntPredicate;

/**
 * Provides methods to handle {@link String}s.
 * 
 * @author Basic7x7
 * @version
 * 2023-11-10 last modified<br>
 * 2023-02-12 created
 * @since 1.0
 */
public class StringUtil {
	
	// Static class
	private StringUtil() {
	}
	
	/**
	 * Counts the amount of characters at the beginning of the string
	 * that match the predicate.
	 * @param str The string.
	 * @param predicate The predicate.
	 * @return The amount of characters.
	 */
	public static int countFront(String str, IntPredicate predicate) {
		int n = str.length();
		int i = 0;
		while (i < n && predicate.test(str.charAt(i))) {
			i++;
		}
		return i;
	}
	
	/**
	 * Counts the amount of characters at the beginning of the string
	 * that are equal to the specified character.
	 * @param str The string.
	 * @param ch The character that should be counted.
	 * @return The amount of characters.
	 */
	public static int countFront(String str, char ch) {
		int n = str.length();
		int i = 0;
		while (i < n && str.charAt(i) == ch) {
			i++;
		}
		return i;
	}
	
	/**
	 * Removes the first characters from the specified string that match the predicate.
	 * @param str The string.
	 * @param predicate The predicate.
	 * @return The trimmed string.
	 */
	public static String trimFront(String str, IntPredicate predicate) {
		int i = countFront(str, predicate);
		return i > 0 ? str.substring(i) : str;
	}
	
	/**
	 * Removes the first characters from the specified string that are equal to the specified character.
	 * @param str The string.
	 * @param ch The character that should be removed from the string.
	 * @return The trimmed string.
	 */
	public static String trimFront(String str, char ch) {
		int i = countFront(str, ch);
		return i > 0 ? str.substring(i) : str;
	}
	
	/**
	 * Removes the first characters from the specified string that match the predicate.
	 * If the string starts with more than {@code maxRemove} characters that match the predicate,
	 * only the first {@code maxRemove} characters are removed.
	 * @param str The string.
	 * @param predicate The predicate.
	 * @param maxRemove The maximum number of characters that can be removed.
	 * @return The trimmed string.
	 */
	public static String trimFront(String str, IntPredicate predicate, int maxRemove) {
		int n = Integer.min(str.length(), maxRemove);
		int i = 0;
		while (i < n && predicate.test(str.charAt(i))) {
			i++;
		}
		return i > 0 ? str.substring(i) : str;
	}
	
	/**
	 * Removes the first characters from the specified string that are equal to the specified character.
	 * If the string starts with more than {@code maxRemove} characters
	 * that are equal to the specified character, only the first {@code maxRemove} characters are removed.
	 * @param str The string.
	 * @param ch The character that should be removed from the string.
	 * @param maxRemove The maximum number of characters that can be removed.
	 * @return The trimmed string.
	 */
	public static String trimFront(String str, char ch, int maxRemove) {
		int n = Integer.min(str.length(), maxRemove);
		int i = 0;
		while (i < n && str.charAt(i) == ch) {
			i++;
		}
		return i > 0 ? str.substring(i) : str;
	}
	
	
	/**
	 * Counts the amount of characters in the string that match the predicate.
	 * @param str The string.
	 * @param predicate The predicate.
	 * @return The amount of characters.
	 */
	public static int count(String str, IntPredicate predicate) {
		int count = 0;
		int n = str.length();
		for (int i = 0; i < n; i++) {
			if (predicate.test(str.charAt(i))) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Counts the amount of characters in the string that are equal to the specified character.
	 * @param str The string.
	 * @param ch The character that should be counted.
	 * @return The amount of characters equal to the specified character.
	 */
	public static int count(String str, char ch) {
		int count = 0;
		int n = str.length();
		for (int i = 0; i < n; i++) {
			if (str.charAt(i) == ch) {
				count++;
			}
		}
		return count;
	}
	
	
	private static boolean startsWithIgnoreCaseUnchecked(String str, String prefix, int offset, int length) {
		for (int i = 0; i < length; i++) {
			char ch1 = str.charAt(offset+i);
			char ch2 = prefix.charAt(i);
			if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns if the string starts with the specified prefix at the specified index.
	 * The case of both strings is ignored.
	 * <p>
	 * The string {@code null} does not start with any prefix.
	 * No string starts with the prefix {@code null}.
	 * In particular, the string {@code null} does not start with the prefix {@code null}.
	 * @param str The string. May be {@code null}.
	 * @param prefix The prefix. May be {@code null}.
	 * @param offset The index where the prefix should start in the specified string.
	 * @return If the string starts with the specified prefix at the specified index.
	 * If the specified offset is out of range, {@code false} is returned.
	 * @since 1.1
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix, int offset) {
		if (str == null || prefix == null) {
			return false;
		}
		int n = str.length();
		int m = prefix.length();
		// false, if the offset is out of range or the prefix does not fit in the string.
		if (offset < 0 || offset+m > n) {
			return false;
		}
		return startsWithIgnoreCaseUnchecked(str, prefix, offset, m);
	}
	
	/**
	 * Returns if the string starts with the specified prefix.
	 * The case of both strings is ignored.
	 * <p>
	 * The string {@code null} does not start with any prefix.
	 * No string starts with the prefix {@code null}.
	 * In particular, the string {@code null} does not start with the prefix {@code null}.
	 * @param str The string. May be {@code null}.
	 * @param prefix The prefix. May be {@code null}.
	 * @return If the string starts with the specified prefix.
	 * @since 1.1
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		return startsWithIgnoreCase(str, prefix, 0);
	}
	
	/**
	 * Returns if the string ends with the specified suffix.
	 * The case of both strings is ignored.
	 * <p>
	 * The string {@code null} does not start with any suffix.
	 * No string ends with the suffix {@code null}.
	 * In particular, the string {@code null} does not end with the suffix {@code null}.
	 * @param str The string. May be {@code null}.
	 * @param suffix The suffix. May be {@code null}.
	 * @return If the string ends with the specified suffix.
	 * @since 1.1
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		return startsWithIgnoreCase(str, suffix, str.length()-suffix.length());
	}
	
	/**
	 * Returns if the string contains the specified substring.
	 * The strings are compared case-insensitive.
	 * <p>
	 * The string {@code null} does not contain any substring.
	 * No string contains the substring {@code null}.
	 * In particular, the string {@code null} does not contain the substring {@code null}.
	 * @param str The string. May be {@code null}.
	 * @param substr The substring. May be {@code null}.
	 * @return If the string contains the specified substring.
	 * @since 1.1
	 */
	public static boolean containsIgnoreCase(String str, String substr) {
		// The string null does not contain any strings and the string null is not contained by any string.
		if (str == null || substr == null) {
			return false;
		}
		int n = str.length();
		int m = substr.length();
		// The string cannot contain a larger string.
		if (m > n) {
			return false;
		}
		
		int lastStartIndex = n-m;
		for (int i = 0; i <= lastStartIndex; i++) {
			if (startsWithIgnoreCaseUnchecked(str, substr, i, m)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Performs a near-constant-time comparison for the specified Strings.
	 * <p>
	 * The calculation time depends mostly on the length of {@code actual}.
	 * In some situations it may depend slightly on the length of {@code expected}.
	 * It does not depend on the contents of both Strings.
	 * @param expected The expected String. Not {@code null}.
	 * @param actual The String that should be tested to match the expected String. Not {@code null}.
	 * @return If both Strings are equal.
	 * @since 1.4
	 */
	public static boolean isEqualCT(String expected, String actual) {
		int expectedLen = expected.length();
		int actualLen = actual.length();
		
		if (actualLen > 0 && expectedLen == 0) {
			return false;
		}
		
		// Near-constant-time comparison.
		// The length of "expected" might be leaked due to potential time differences (i vs 0, cache-misses, branch-prediction, ...).
		int result = expectedLen ^ actualLen;
		for (int i = 0; i < actualLen; i++) {
			int ch1 = expected.charAt(i < expectedLen ? i : 0);
			int ch2 = actual.charAt(i);
			result |= ch1 ^ ch2;
		}
		return result == 0;
	}
}
