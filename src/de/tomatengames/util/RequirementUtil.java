package de.tomatengames.util;

/**
 * Provides convenient methods to check requirements.
 * For example, {@link #requireNotNull(Object, String)} can be used to ensure
 * that parameters are not {@code null}.
 * 
 * @author Basic7x7
 * @version 2025-03-23 last modified
 * @version 2023-06-13 created
 * @since 1.3
 */
public class RequirementUtil {
	
	/**
	 * Ensures that the specified object is not {@code null}.
	 * @param obj The object that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code " ..."}, the string {@code "must not be null"} is appended.
	 * @throws IllegalArgumentException If the object is {@code null}.
	 */
	public static void requireNotNull(Object obj, String msg) throws IllegalArgumentException {
		if (obj == null) {
			msg = optSuffix(msg, "must not be null");
			throw new IllegalArgumentException(msg);
		}
	}
	
	/**
	 * Ensures that the specified object is not {@code null}.
	 * @param obj The object that should be checked.
	 * @throws IllegalArgumentException If the object is {@code null}.
	 * @see #requireNotNull(Object, String)
	 */
	public static void requireNotNull(Object obj) throws IllegalArgumentException {
		requireNotNull(obj, "The object ...");
	}
	
	
	/**
	 * Ensures that the specified string is not {@code null}
	 * and does not only contain whitespace characters.
	 * @param str The string that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code " ..."}, the string {@code "must not be null"}
	 * or {@code "must not be blank"} is appended.
	 * @throws IllegalArgumentException If the string is {@code null} or blank.
	 */
	public static void requireNotBlank(String str, String msg) {
		if (str == null) {
			msg = optSuffix(msg, "must not be null");
			throw new IllegalArgumentException(msg);
		}
		if (str.trim().isEmpty()) {
			msg = optSuffix(msg, "must not be blank");
			throw new IllegalArgumentException(msg);
		}
	}
	
	/**
	 * Ensures that the specified string is not {@code null}
	 * and does not only contain whitespace characters.
	 * @param str The string that should be checked.
	 * @throws IllegalArgumentException If the string is {@code null} or blank.
	 * @see #requireNotBlank(String, String)
	 */
	public static void requireNotBlank(String str) {
		requireNotBlank(str, "The string ...");
	}
	
	
	private static String optSuffix(String msg, String optSuffix) {
		if (msg != null && msg.endsWith(" ...")) {
			return msg.substring(0, msg.length()-3) + optSuffix;
		}
		return msg;
	}
}
