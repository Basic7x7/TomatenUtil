package de.tomatengames.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Provides convenient methods to check requirements.
 * For example, {@link #requireNotNull(Object, String)} can be used to ensure
 * that parameters are not {@code null}.
 * 
 * @author Basic7x7
 * @version 2025-04-07 last modified
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
	
	
	/**
	 * Ensures that the specified {@link Collection} is not null and contains at least 1 element.
	 * @param collection The collection that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must not be null"} or {@code "must not be empty"} is appended.
	 * @throws IllegalArgumentException If the collection is null or empty.
	 * @since 1.8
	 */
	public static void requireNotEmpty(Collection<?> collection, String msg) {
		if (collection == null) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be null"));
		}
		if (collection.isEmpty()) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be empty"));
		}
	}
	
	/**
	 * Ensures that the specified array is not null and has a length of at least 1.
	 * @param <T> The type of the array elements.
	 * @param array The array that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, {@code "must not be null"} or {@code "must not be empty"} is appended.
	 * @throws IllegalArgumentException If the array is null or has a length of 0.
	 * @since 1.8
	 */
	public static <T> void requireNotEmpty(T[] array, String msg) {
		if (array == null) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be null"));
		}
		if (array.length <= 0) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be empty"));
		}
	}
	
	/**
	 * Ensures that the specified {@link Collection} is not null and has a size of at least the specified minimum size.
	 * @param collection The collection that should be checked.
	 * @param minSize The minimum size that the collection should have.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, a suffix like {@code "must not be null"} with a meaningful error description is appended.
	 * @throws IllegalArgumentException If the collection is null or has a size less than the specified minimum size.
	 * @since 1.8
	 */
	public static void requireMinSize(Collection<?> collection, int minSize, String msg) {
		if (collection == null) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be null"));
		}
		int size = collection.size();
		if (size < minSize) {
			if (size <= 0) {
				throw new IllegalArgumentException(optSuffix(msg, "must not be empty (requires " + minSize + " elements)"));
			}
			throw new IllegalArgumentException(optSuffix(msg, "requires " + minSize + " elements (found " + size + ")"));
		}
	}
	
	/**
	 * Ensures that the specified array is not null and has a length of at least the specified minimum length.
	 * @param <T> The type of the array elements.
	 * @param array The array that should be checked.
	 * @param minLength The minimum length that the array should have.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, a suffix like {@code "must not be null"} with a meaningful error description is appended.
	 * @throws IllegalArgumentException If the array is null or has a length less than the specified minimum length.
	 * @since 1.8
	 */
	public static <T> void requireMinLength(T[] array, int minLength, String msg) {
		if (array == null) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be null"));
		}
		int length = array.length;
		if (length < minLength) {
			if (length <= 0) {
				throw new IllegalArgumentException(optSuffix(msg, "must not be empty (requires length " + minLength + ")"));
			}
			throw new IllegalArgumentException(optSuffix(msg, "requires length " + minLength + " (found length " + length + ")"));
		}
	}
	
	/**
	 * Ensures that the specified collection is not null and has exactly the specified number of elements.
	 * @param collection The collection that should be checked.
	 * @param size The exact number of elements that the collection should have.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, a suffix like {@code "must not be null"} with a meaningful error description is appended.
	 * @throws IllegalArgumentException If the collection is null or has a different number of elements than the specified size.
	 * @since 1.8
	 */
	public static void requireExactSize(Collection<?> collection, int size, String msg) {
		if (collection == null) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be null"));
		}
		int s = collection.size();
		if (s != size) {
			throw new IllegalArgumentException(optSuffix(msg, "requires " + size + " elements (found " + s + ")"));
		}
	}
	
	/**
	 * Ensures that the specified array is not null and has exactly the specified length.
	 * @param <T> The type of the array elements.
	 * @param array The array that should be checked.
	 * @param length The exact length that the array should have.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, a suffix like {@code "must not be null"} with a meaningful error description is appended.
	 * @throws IllegalArgumentException If the array is null or has a different length than the specified length.
	 * @since 1.8
	 */
	public static <T> void requireExactLength(T[] array, int length, String msg) {
		if (array == null) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be null"));
		}
		if (array.length != length) {
			throw new IllegalArgumentException(optSuffix(msg, "requires length " + length + " (found length " + array.length + ")"));
		}
	}
	
	/**
	 * Ensures that the specified collection is not null and has a size within the specified range.
	 * @param collection The collection that should be checked.
	 * @param minSize The minimum allowed size of the collection.
	 * @param maxSize The maximum allowed size of the collection.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, a suffix like {@code "must not be null"} with a meaningful error description is appended.
	 * @throws IllegalArgumentException If the collection is null or has a size outside the specified range.
	 * @since 1.8
	 */
	public static void requireSizeInRange(Collection<?> collection, int minSize, int maxSize, String msg) {
		if (collection == null) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be null"));
		}
		int size = collection.size();
		if (minSize > size || size > maxSize) {
			if (minSize <= 0) {
				throw new IllegalArgumentException(optSuffix(msg, "must not contain more than " + maxSize + " elements (found " + size + ")"));
			}
			if (maxSize >= Integer.MAX_VALUE) {
				throw new IllegalArgumentException(optSuffix(msg, "requires at least " + minSize + " elements (found " + size + ")"));
			}
			throw new IllegalArgumentException(optSuffix(msg, "requires " + minSize + " to " + maxSize + " elements (found " + size + ")"));
		}
	}
	
	/**
	 * Ensures that the specified array is not null and has a length within the specified range.
	 * @param <T> The type of elements in the array.
	 * @param array The array that should be checked.
	 * @param minLength The minimum allowed length of the array.
	 * @param maxLength The maximum allowed length of the array.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, a suffix like {@code "must not be null"} with a meaningful error description is appended.
	 * @throws IllegalArgumentException If the array is null or has a length outside the specified range.
	 * @since 1.8
	 */
	public static <T> void requireLengthInRange(T[] array, int minLength, int maxLength, String msg) {
		if (array == null) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be null"));
		}
		int length = array.length;
		if (minLength > length || length > maxLength) {
			if (minLength <= 0) {
				throw new IllegalArgumentException(optSuffix(msg, "must not exceed length " + maxLength + " (found length " + length + ")"));
			}
			if (maxLength >= Integer.MAX_VALUE) {
				throw new IllegalArgumentException(optSuffix(msg, "requires at least the length " + minLength + " (found length " + length + ")"));
			}
			throw new IllegalArgumentException(optSuffix(msg, "requires length " + minLength + " to " + maxLength + " elements (found length " + length + ")"));
		}
	}
	
	
	/**
	 * Ensures that the specified {@link Collection} is not null and all elements satisfy the specified predicate.
	 * @param <T> The type of elements in the collection.
	 * @param collection The collection that should be checked.
	 * @param predicate The predicate that should be checked for each element in the collection. Not null.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, a suffix like {@code "must not be null"} with a meaningful error description is appended.
	 * @param elementErrorMsgSuffix The suffix to be appended to the error message if the predicate fails for an element.
	 * If null, a generic error message is used. Example: {@code "must not contain a null value"}.
	 * @throws IllegalArgumentException If the collection is null or any of its elements do not satisfy the predicate.
	 * @since 1.8
	 */
	public static <T> void requireForAll(Collection<T> collection, Predicate<T> predicate, String msg, String elementErrorMsgSuffix) {
		if (collection == null) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be null"));
		}
		int index = 0;
		for (T element : collection) {
			if (!predicate.test(element)) {
				String suffix = elementErrorMsgSuffix != null ? elementErrorMsgSuffix : "contains an invalid element";
				if (collection instanceof List) {
					suffix += " (at index " + index + ")";
				}
				throw new IllegalArgumentException(optSuffix(msg, suffix));
			}
			index++;
		}
	}
	
	/**
	 * Ensures that the specified array is not null and all elements satisfy the specified predicate.
	 * @param <T> The type of elements in the array.
	 * @param array The array that should be checked.
	 * @param predicate The predicate that should be checked for each element in the array. Not null.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, a suffix like {@code "must not be null"} with a meaningful error description is appended.
	 * @param elementErrorMsgSuffix The suffix to be appended to the error message if the predicate fails for an element.
	 * If null, a generic error message is used. Example: {@code "must not contain a null value"}.
	 * @throws IllegalArgumentException If the array is null or any of its elements do not satisfy the predicate.
	 * @since 1.8
	 */
	public static <T> void requireForAll(T[] array, Predicate<T> predicate, String msg, String elementErrorMsgSuffix) {
		if (array == null) {
			throw new IllegalArgumentException(optSuffix(msg, "must not be null"));
		}
		for (int i = 0, n = array.length; i < n; i++) {
			if (!predicate.test(array[i])) {
				throw new IllegalArgumentException(optSuffix(msg,
						(elementErrorMsgSuffix != null ? elementErrorMsgSuffix : "contains an invalid element") + " (found at index " + i + ""));
			}
		}
	}
	
	/**
	 * Ensures that the specified collection and all of its elements are not null.
	 * @param <T> The type of elements in the collection.
	 * @param collection The collection that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, a suffix like {@code "must not be null"} with a meaningful error description is appended.
	 * @throws IllegalArgumentException If the collection is null or any of its elements are null.
	 * @since 1.8
	 */
	public static <T> void requireAllNotNull(Collection<T> collection, String msg) {
		requireForAll(collection, element -> element != null, msg, "must not contain null");
	}
	
	/**
	 * Ensures that the specified array and all of its elements are not null.
	 * @param <T> The type of elements in the array.
	 * @param array The array that should be checked.
	 * @param msg The message that should be passed to the exception.
	 * If the message ends with {@code "..."}, a suffix like {@code "must not be null"} with a meaningful error description is appended.
	 * @throws IllegalArgumentException If the array is null or any of its elements are null.
	 * @since 1.8
	 */
	public static <T> void requireAllNotNull(T[] array, String msg) {
		requireForAll(array, element -> element != null, msg, "must not contain null");
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
