package de.tomatengames.util;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.IntPredicate;

/**
 * Provides methods to handle {@link String}s.
 * 
 * @author Basic7x7
 * @version 2025-03-23 last modified
 * @version 2023-02-12 created
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
	
	/**
	 * Tests if the specified input string matches the specified simple pattern.
	 * The pattern may contain '*' to match any substring at its place.
	 * @param input the input to test whether it matches the pattern
	 * @param pattern the pattern
	 * @return whether the input matches the pattern
	 * @author LukasE7x7
	 * @since 1.7
	 */
	public static boolean matchesSimplePattern(String input, String pattern) {
		int inputLen = input.length();
		int patternLen = pattern.length();
		
		// The parts of the pattern may be separated into 3 categories that are checked separately.
		// Characters before the first '*' are the prefix, the input needs to start with it.
		// Characters between two '*'s are inner parts, the input needs to contain inner parts in their order.
		// Characters after the last '*' are the suffix, the input needs to end with it.
		
		// check prefix
		int inputIndex = 0;
		while (true) {
			if (inputIndex == patternLen)
				return patternLen == inputLen; // pattern contains no placeholder
			if (inputIndex == inputLen) {
				// input end reached
				// pattern may only contain '*' from here
				while (inputIndex < patternLen) {
					if (pattern.charAt(inputIndex) != '*')
						return false; // input too short
					inputIndex++;
				}
				return true; // input=pattern+{'*'}
			}
			char patternChar = pattern.charAt(inputIndex);
			if (patternChar == '*')
				break; // first '*' found, continue with inner parts check
			if (patternChar != input.charAt(inputIndex))
				return false;
			inputIndex++;
		}
		
		// check inner parts
		int placeholderIndex = inputIndex;
		int nextPlaceholderIndex;
		placeholder_loop: while (true) {
			
			// find next placeholder
			nextPlaceholderIndex = placeholderIndex + 1;
			while (true) {
				// if last placeholder, skip to suffix check
				if (nextPlaceholderIndex >= patternLen)
					break placeholder_loop;
				// if not last placeholder, continue with inner part check
				if (pattern.charAt(nextPlaceholderIndex) == '*')
					break;
				nextPlaceholderIndex++;
			}
			
			int partLen = nextPlaceholderIndex - placeholderIndex - 1;
			
			// search for part
			while (true) {
				if (inputIndex + partLen > inputLen)
					return false;
				
				// test for part at current inputIndex
				int searchOffset = 0;
				while (true) {
					if (searchOffset >= partLen) {
						// part found
						inputIndex += partLen;
						placeholderIndex = nextPlaceholderIndex;
						continue placeholder_loop;
					}
					if (input.charAt(inputIndex + searchOffset) !=
							pattern.charAt(placeholderIndex + 1 + searchOffset)) {
						// part not on this index, try next
						break;
					}
					searchOffset++;
				}
				
				// try next input index
				inputIndex++;
			}
		}
		
		// check suffix
		int patternSuffixLen = patternLen - placeholderIndex - 1;
		if (inputLen - inputIndex < patternSuffixLen)
			return false; // remaining input too short
		inputIndex = inputLen - patternSuffixLen; // jump to suffix
		for (int off = 0; off < patternSuffixLen; off++) {
			if (input.charAt(inputIndex + off) != pattern.charAt(placeholderIndex + 1 + off))
				return false;
		}
		return true;
	}
	
	/**
	 * Joins an array of objects into a single string using a specified delimiter
	 * and conversion function.
	 * <p>
	 * The method converts each element in the array to strings using the given conversion function,
	 * then concatenates these strings with the provided delimiter.
	 * </p>
	 *
	 * @param <T> The type of elements in the array.
	 * @param part The array containing the elements to join.
	 * @param conversion The function that converts each element to a String.
	 * @param delimiter The string used to separate the converted parts.
	 * @return A single string obtained by joining all elements after converting and
	 * 	separating them with the specified delimiter. Returns an empty string
	 * 	if the input array is null or has no elements.
	 *
	 * @author LukasE7x7
	 * @since 1.8
	 */
	public static <T> String join(T[] parts, Function<T, String> conversion, String delimiter) {
		return join(parts, conversion, delimiter, delimiter);
	}
	
	/**
	 * Joins an array of objects into a single string using specified delimiters and
	 * a conversion function.
	 * <p>
	 * This method converts each element in the array to strings using the given
	 * conversion function, then concatenates these strings with the
	 * provided delimiter. It allows to specify a different delimiter for the last element.
	 * </p>
	 *
	 * @param <T> The type of elements in the part array.
	 * @param parts The array containing the elements to join.
	 * @param conversion The function that converts each element to a String.
	 * @param delimiter The string used to separate all but the last converted part.
	 * @param lastDelimiter The string used before the last converted part.
	 * @return A single string obtained by joining all elements after converting and
	 *  separating them with the specified delimiters. Returns an empty
	 *  string if the input array is null or has no elements.
	 *
	 * @author LukasE7x7
	 * @since 1.8
	 */
	public static <T> String join(T[] parts, Function<T, String> conversion, String delimiter, String lastDelimiter) {
		if (parts == null || parts.length <= 0)
			return "";
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < parts.length; i++) {
			if (i > 0)
				out.append(i == parts.length - 1 ? lastDelimiter : delimiter);
			out.append(conversion.apply(parts[i]));
		}
		return out.toString();
	}
	
	/**
	 * Joins the elements from the given iterator into a single string using the
	 * specified delimiter and conversion function.
	 * <p>
	 * The method converts each element from the iterator to strings using the given
	 * conversion function, then concatenates these strings with the
	 * provided delimiter.
	 * </p>
	 *
	 * @param <T> The type of elements in the part array.
	 * @param parts The iterator containing the elements to join.
	 * @param conversion The function that converts each element to a String.
	 * @param delimiter The string used to separate the converted parts.
	 * @return A single string obtained by joining all elements after converting and
	 *  separating them with the specified delimiter. Returns an empty string
	 *  if the iterator is null or has no elements.
	 *
	 * @author LukasE7x7
	 * @since 1.8
	 */
	public static <T> String join(Iterator<T> parts, Function<T, String> conversion, String delimiter) {
		return join(parts, conversion, delimiter, delimiter);
	}
	
	/**
	 * Joins the elements from the given iterator into a single string using the
	 * specified delimiters and conversion function.
	 * <p>
	 * This method converts each element from the iterator to strings using the given
	 * conversion function, then concatenates these strings with the
	 * provided delimiter. It allows to specify a different delimiter for the last element.
	 * </p>
	 *
	 * @param <T> The type of elements in the part array.
	 * @param parts The iterator containing the elements to join.
	 * @param conversion The function that converts each element to a String.
	 * @param delimiter The string used to separate all but the last converted part.
	 * @param lastDelimiter A string used before the last converted part.
	 * @return A single string obtained by joining all elements after converting and
	 *  separating them with the specified delimiters. Returns an empty
	 *  string if the iterator is null or has no elements.
	 *
	 * @author LukasE7x7
	 * @since 1.8
	 */
	public static <T> String join(Iterator<T> parts, Function<T, String> conversion, String delimiter, String lastDelimiter) {
		if (parts == null || !parts.hasNext())
			return "";
		boolean first = true;
		StringBuilder out = new StringBuilder();
		while (true) {
			String part = conversion.apply(parts.next());
			boolean hasNext = parts.hasNext();
			if (!first)
				out.append(hasNext ? delimiter : lastDelimiter);
			out.append(part);
			if (!hasNext)
				return out.toString();
			first = false;
		}
	}
	
	/**
	 * Joins the objects from the given iterable into a single string using a specified delimiter
	 * and conversion function.
	 * <p>
	 * This method converts each element in the iterable to strings using the given
	 * conversion function, then concatenates these strings with the specified
	 * delimiter.
	 * </p>
	 *
	 * @param <T> The type of elements in the array.
	 * @param parts The iterable containing the elements to join.
	 * @param conversion The function that converts each element to a String.
	 * @param delimiter The string used to separate consecutive converted elements.
	 * @return A single string obtained by joining all elements after converting
	 *  them and separating with the specified delimiter. Returns an empty
	 *  string if the iterable is null or has no elements.
	 *
	 * @author LukasE7x7
	 * @since 1.8
	 */
	public static <T> String join(Iterable<T> parts, Function<T, String> conversion, String delimiter) {
		return join(parts, conversion, delimiter, delimiter);
	}
	
	/**
	 * Joins the objects from the given iterable into a single string using specified delimiters
	 * and conversion function.
	 * <p>
	 * This method converts each element in the iterable to strings using the given
	 * conversion function. It concatenates these strings with the specified
	 * delimiter for all elements except before the last, where it uses the specified
	 * last delimiter.
	 * </p>
	 *
	 * @param <T> The type of elements in the iterable.
	 * @param parts The iterable containing the elements to join.
	 * @param conversion The function that converts each element to a String.
	 * @param delimiter The string used to separate consecutive converted elements.
	 * @param lastDelimiter The string used before the last converted element.
	 * @return A single string obtained by joining all elements after converting
	 *  them and separating with the specified delimiters. Returns an empty
	 *  string if the iterable is null or has no elements.
	 *
	 * @author LukasE7x7
	 * @since 1.8
	 */
	public static <T> String join(Iterable<T> parts, Function<T, String> conversion, String delimiter, String lastDelimiter) {
		return parts == null ? "" : join(parts.iterator(), conversion, delimiter, lastDelimiter);
	}
	
	
	/**
	 * Returns the starting index of the first occurrence of the specified part of the pattern in the specified part of the input string.
	 * The pattern is limited to the substring starting at {@code patternOffset} with the length {@code patternLength}.
	 * @param input The input string. Not null.
	 * @param offset The index where to start searching for the pattern. Not negative.
	 * @param length The length of the input to search for the pattern. Not negative.
	 * {@code offset+length} must not be greater than the length of the input string.
	 * @param pattern The pattern to search for. Not null.
	 * @param patternOffset The beginning index of the pattern. Not negative.
	 * @param patternLength The length of the part of the pattern to search for. Not negative.
	 * {@code patternOffset+patternLength} must not be greater than the length of the pattern string. 
	 * @return The index of the first occurrence of the part of the pattern in the part of the input string,
	 * or {@code -1} if no such index is found.
	 * @since 1.8
	 */
	public static int indexOf(String input, int offset, int length, String pattern, int patternOffset, int patternLength) {
		int lastIndex = offset + (length - patternLength);
		mainLoop: for (int i = offset; i <= lastIndex; i++) {
			for (int j = 0; j < patternLength; j++) {
				if (input.charAt(i+j) != pattern.charAt(patternOffset+j)) {
					continue mainLoop;
				}
			}
			return i;
		}
		return -1;
	}
	
	
	/**
	 * Returns the starting index of the last occurrence of the specified part of the pattern in the specified part of the input string.
	 * The pattern is limited to the substring starting at {@code patternOffset} with the length {@code patternLength}.
	 * @param input The input string. Not null.
	 * @param offset The index where to start searching for the pattern. Not negative.
	 * @param length The length of the input to search for the pattern. Not negative.
	 * {@code offset+length} must not be greater than the length of the input string.
	 * @param pattern The pattern to search for. Not null.
	 * @param patternOffset The beginning index of the pattern. Not negative.
	 * @param patternLength The length of the part of the pattern to search for. Not negative.
	 * {@code patternOffset+patternLength} must not be greater than the length of the pattern string. 
	 * @return The index of the last occurrence of the part of the pattern in the part of the input string,
	 * or {@code -1} if no such index is found.
	 * @since 1.8
	 */
	public static int lastIndexOf(String input, int offset, int length, String pattern, int patternOffset, int patternLength) {
		mainLoop: for (int i = offset + (length - patternLength); i >= offset; i--) {
			for (int j = 0; j < patternLength; j++) {
				if (input.charAt(i+j) != pattern.charAt(patternOffset+j)) {
					continue mainLoop;
				}
			}
			return i;
		}
		return -1;
	}
	
}
