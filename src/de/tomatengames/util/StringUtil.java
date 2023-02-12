package de.tomatengames.util;

import java.util.function.IntPredicate;

/**
 * Provides methods to handle {@link String}s.
 * 
 * @author Basic7x7
 * @version 2023-02-12
 * @since 1.0
 */
public class StringUtil {
	
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
		int n = str.length();
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
}
