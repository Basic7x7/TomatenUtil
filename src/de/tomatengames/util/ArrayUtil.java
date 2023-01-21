package de.tomatengames.util;

import java.util.Objects;

/**
 * Provides methods to work with arrays.
 * 
 * @author Basic7x7
 * @version 2023-01-21
 * @since 1.0
 */
public class ArrayUtil {
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The {@code ==} operator is used to check each array element.
	 * The array {@code null} does not contain any elements.
	 * @param <T> The type of the array elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	public static <T> boolean contains(T[] array, T element) {
		if (array == null) {
			return false;
		}
		for (T arrEl : array) {
			if (arrEl == element) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns if the specified array contains an element that is equal to the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param <T> The type of the array elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @return If the array contains an element that is equal to the specified element.
	 * @see Object#equals(Object)
	 */
	public static <T> boolean containsEqual(T[] array, T element) {
		if (array == null) {
			return false;
		}
		
		for (T arrEl : array) {
			if (Objects.equals(element, arrEl)) {
				return true;
			}
		}
		return false;
	}
	
	
}
