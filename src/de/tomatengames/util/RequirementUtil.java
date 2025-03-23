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
	 * If the message ends with {@code "..."}, {@code "must not be null"} is appended.
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
	 * If the message ends with {@code "..."}, {@code "must not be null"}
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
	
	
	/**
	 * Ensures that the specified number is not negative. 0 is allowed.
	 * @param num The number that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must not be negative (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is negative.
	 * @since 1.8
	 */
	public static void requireNotNegative(int num, String msg) {
		if (num < 0) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be negative (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is not negative. 0 is allowed.
	 * @param num The number that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must not be negative (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is negative.
	 * @since 1.8
	 */
	public static void requireNotNegative(long num, String msg) {
		if (num < 0L) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be negative (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is not negative. 0 is allowed.
	 * @param num The number that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must not be negative (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is negative.
	 * @since 1.8
	 */
	public static void requireNotNegative(double num, String msg) {
		if (num < 0.0) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be negative (was " + num + ")"));
		}
	}
	
	
	/**
	 * Ensures that the specified number is positive. 0 is not allowed.
	 * @param num The number that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be positive (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is not positive.
	 * @since 1.8
	 */
	public static void requirePositive(int num, String msg) {
		if (num <= 0) {
			throw new IllegalArgumentException(optSuffix(msg, "must be positive (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is positive. 0 is not allowed.
	 * @param num The number that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be positive (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is not positive.
	 * @since 1.8
	 */
	public static void requirePositive(long num, String msg) {
		if (num <= 0L) {
			throw new IllegalArgumentException(optSuffix(msg, "must be positive (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is positive. 0 is not allowed.
	 * @param num The number that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be positive (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is not positive.
	 * @since 1.8
	 */
	public static void requirePositive(double num, String msg) {
		if (num <= 0.0) {
			throw new IllegalArgumentException(optSuffix(msg, "must be positive (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is less than or equal to the maximum value.
	 * @param num The number that should be checked.
	 * @param max The maximum value.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be less than or equal to max (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is greater than the maximum value.
	 * @since 1.8
	 */
	public static void requireLessOrEqual(int num, int max, String msg) {
		if (num > max) {
			throw new IllegalArgumentException(optSuffix(msg, "must be less than or equal to " + max + " (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is less than or equal to the maximum value.
	 * @param num The number that should be checked.
	 * @param max The maximum value.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be less than or equal to max (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is greater than the maximum value.
	 * @since 1.8
	 */
	public static void requireLessOrEqual(long num, long max, String msg) {
		if (num > max) {
			throw new IllegalArgumentException(optSuffix(msg, "must be less than or equal to " + max + " (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is less than or equal to the maximum value.
	 * @param num The number that should be checked.
	 * @param max The maximum value.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be less than or equal to max (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is greater than the maximum value.
	 * @since 1.8
	 */
	public static void requireLessOrEqual(double num, double max, String msg) {
		if (num > max) {
			throw new IllegalArgumentException(optSuffix(msg, "must be less than or equal to " + max + " (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is greater than or equal to the minimum value.
	 * @param num The number that should be checked.
	 * @param min The minimum value.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be greater than or equal to min (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is less than the minimum value.
	 * @since 1.8
	 */
	public static void requireGreaterOrEqual(int num, int min, String msg) {
		if (num < min) {
			throw new IllegalArgumentException(optSuffix(msg, "must be greater than or equal to " + min + " (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is greater than or equal to the minimum value.
	 * @param num The number that should be checked.
	 * @param min The minimum value.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be greater than or equal to min (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is less than the minimum value.
	 * @since 1.8
	 */
	public static void requireGreaterOrEqual(long num, long min, String msg) {
		if (num < min) {
			throw new IllegalArgumentException(optSuffix(msg, "must be greater than or equal to " + min + " (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is greater than or equal to the minimum value.
	 * @param num The number that should be checked.
	 * @param min The minimum value.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be greater than or equal to min (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is less than the minimum value.
	 * @since 1.8
	 */
	public static void requireGreaterOrEqual(double num, double min, String msg) {
		if (num < min) {
			throw new IllegalArgumentException(optSuffix(msg, "must be greater than or equal to " + min + " (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is in the range [min, max].
	 * @param num The number that should be checked.
	 * @param min The minimum value (inclusive).
	 * @param max The maximum value (inclusive).
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be between min and max (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is not within the range.
	 * @since 1.8
	 */
	public static void requireInRange(int num, int min, int max, String msg) {
		if (num < min || num > max) {
			throw new IllegalArgumentException(optSuffix(msg, "must be between " + min + " and " + max + " (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is in the range [min, max].
	 * @param num The number that should be checked.
	 * @param min The minimum value (inclusive).
	 * @param max The maximum value (inclusive).
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be between min and max (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is not within the range.
	 * @since 1.8
	 */
	public static void requireInRange(long num, long min, long max, String msg) {
		if (num < min || num > max) {
			throw new IllegalArgumentException(optSuffix(msg, "must be between " + min + " and " + max + " (was " + num + ")"));
		}
	}
	
	/**
	 * Ensures that the specified number is in the range [min, max].
	 * @param num The number that should be checked.
	 * @param min The minimum value (inclusive).
	 * @param max The maximum value (inclusive).
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must be between min and max (was num)"} is appended.
	 * @throws IllegalArgumentException If the number is not within the range.
	 * @since 1.8
	 */
	public static void requireInRange(double num, double min, double max, String msg) {
		if (num < min || num > max) {
			throw new IllegalArgumentException(optSuffix(msg, "must be between " + min + " and " + max + " (was " + num + ")"));
		}
	}
	
	
	
	private static String optSuffix(String msg, String optSuffix) {
		if (msg != null && msg.endsWith("...")) {
			String prefix = msg.substring(0, msg.length()-3); // Remove the "..."
			if (!prefix.endsWith(" ")) { // Append a space if none exists in the original message
				prefix += " ";
			}
			return prefix + optSuffix;
		}
		return msg;
	}
}
