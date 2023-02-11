/* txs-begin static
##
:inline=$
final string[] types = ["T", "int", "long", "byte", "short", "char", "float", "double", "boolean"];
final string[] typesWithEqual = ["T", "T", "int", "long", "byte", "short", "char", "float", "double", "boolean"];
##
txs-end static */

package de.tomatengames.util;

import java.util.Collection;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * Provides methods to work with arrays.
 * 
 * @author Basic7x7
 * @version 2023-01-21
 * @since 1.0
 */
public class ArrayUtil {
	
	// Static class.
	private ArrayUtil() {
	}
	
	/* txs-begin contains
	# for (string type in typesWithEqual) with (first eq) {
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] $eq ? "equals" : "=="; element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 # if (type == 'T') {
	 * @param <T> The type of the elements.
	 # }
	 * @param array The array. May be {@code null}.
	 * @param element The element. $? type == 'T' && 'May be {@code null}.';
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 *$'/';
	// $txsinfo();
	public static $? type == 'T' && '<T>'; int indexOf$eq&&"Equal";($type;[] array, $type; element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			# if (eq) {
			if (Objects.equals(array[i], element)) {
			# } else {
			if (array[i] == element) {
			# }
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of $eq
	 	? "an element in the array that is equal to the specified element."
	 	: "the specified element in the array.";
	 * If the array is {@code null}, it does not contain any elements.
	 # if (type == 'T') {
	 * @param <T> The type of the elements.
	 # }
	 * @param array The array. May be {@code null}.
	 * @param element The element. $? type == 'T' && 'May be {@code null}.';
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 *$'/';
	// $txsinfo();
	public static $? type == 'T' && '<T>'; int indexOf$eq&&"Equal";($type;[] array, $type; element) {
		return indexOf$eq&&"Equal";(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] $eq ? "equals" : "=="; element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 # if (type == 'T') {
	 * @param <T> The type of the elements.
	 # }
	 * @param array The array. May be {@code null}.
	 * @param element The element. $? type == 'T' && 'May be {@code null}.';
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 *$'/';
	// $txsinfo();
	public static $? type == 'T' && '<T>'; int lastIndexOf$eq&&"Equal";($type;[] array, $type; element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			# if (eq) {
			if (Objects.equals(array[i], element)) {
			# } else {
			if (array[i] == element) {
			# }
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of $eq
	 	? "an element in the array that is equal to the specified element."
	 	: "the specified element in the array.";
	 * If the array is {@code null}, it does not contain any elements.
	 # if (type == 'T') {
	 * @param <T> The type of the elements.
	 # }
	 * @param array The array. May be {@code null}.
	 * @param element The element. $? type == 'T' && 'May be {@code null}.';
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 *$'/';
	// $txsinfo();
	public static $? type == 'T' && '<T>'; int lastIndexOf$eq&&"Equal";($type;[] array, $type; element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf$eq&&"Equal";(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains $eq
	 	? "an element that is equal to the specified element."
	 	: "the specified element.";
	 * The array {@code null} does not contain any elements.
	 # if (type == 'T') {
	 * @param <T> The type of the array elements.
	 # }
	 * @param array The array. May be {@code null}.
	 * @param el The element. $? type == 'T' && 'May be {@code null}.';
	 * @return If the array contains the specified element.
	 *$'/';
	// $txsinfo();
	public static $? type == 'T' && '<T>'; boolean contains$eq&&"Equal";($type;[] array, $type; element) {
		return indexOf$eq&&"Equal";(array, element, 0) >= 0;
	}
	
	# }
	txs-end contains */
	// txs-begin-gen contains
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] equals element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param <T> The type of the elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static <T> int indexOfEqual(T[] array, T element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			if (Objects.equals(array[i], element)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of an element in the array that is equal to the specified element.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param <T> The type of the elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static <T> int indexOfEqual(T[] array, T element) {
		return indexOfEqual(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] equals element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param <T> The type of the elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static <T> int lastIndexOfEqual(T[] array, T element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (Objects.equals(array[i], element)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of an element in the array that is equal to the specified element.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param <T> The type of the elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static <T> int lastIndexOfEqual(T[] array, T element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOfEqual(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains an element that is equal to the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param <T> The type of the array elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static <T> boolean containsEqual(T[] array, T element) {
		return indexOfEqual(array, element, 0) >= 0;
	}
	
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param <T> The type of the elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static <T> int indexOf(T[] array, T element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param <T> The type of the elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static <T> int indexOf(T[] array, T element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param <T> The type of the elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static <T> int lastIndexOf(T[] array, T element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param <T> The type of the elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static <T> int lastIndexOf(T[] array, T element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param <T> The type of the array elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static <T> boolean contains(T[] array, T element) {
		return indexOf(array, element, 0) >= 0;
	}
	
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(int[] array, int element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(int[] array, int element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(int[] array, int element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(int[] array, int element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(int[] array, int element) {
		return indexOf(array, element, 0) >= 0;
	}
	
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(long[] array, long element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(long[] array, long element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(long[] array, long element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(long[] array, long element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(long[] array, long element) {
		return indexOf(array, element, 0) >= 0;
	}
	
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(byte[] array, byte element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(byte[] array, byte element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(byte[] array, byte element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(byte[] array, byte element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(byte[] array, byte element) {
		return indexOf(array, element, 0) >= 0;
	}
	
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(short[] array, short element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(short[] array, short element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(short[] array, short element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(short[] array, short element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(short[] array, short element) {
		return indexOf(array, element, 0) >= 0;
	}
	
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(char[] array, char element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(char[] array, char element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(char[] array, char element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(char[] array, char element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(char[] array, char element) {
		return indexOf(array, element, 0) >= 0;
	}
	
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(float[] array, float element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(float[] array, float element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(float[] array, float element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(float[] array, float element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(float[] array, float element) {
		return indexOf(array, element, 0) >= 0;
	}
	
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(double[] array, double element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(double[] array, double element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(double[] array, double element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(double[] array, double element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(double[] array, double element) {
		return indexOf(array, element, 0) >= 0;
	}
	
	
	/**
	 * Returns the first index that is greater than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(boolean[] array, boolean element, int startIndex) {
		if (array == null) {
			return -1;
		}
		int n = array.length;
		for (int i = startIndex; i < n; i++) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the first index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(boolean[] array, boolean element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
	 * {@code array[index] == element} applies.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(boolean[] array, boolean element, int startIndex) {
		if (array == null) {
			return -1;
		}
		if (startIndex >= array.length) {
			startIndex = array.length-1;
		}
		for (int i = startIndex; i >= 0; i--) {
			if (array[i] == element) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns the last index of the specified element in the array.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int lastIndexOf(boolean[] array, boolean element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The array {@code null} does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param el The element.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(boolean[] array, boolean element) {
		return indexOf(array, element, 0) >= 0;
	}
	
	// txs-end-gen contains
	
	
	/**
	 * Concatenates both arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * <p>
	 * All elements from {@code array1} are before all from {@code array2}.
	 * The order of the elements is preserved.
	 * @param <T> The type of the array elements.
	 * @param array1 The first array. May be {@code null}.
	 * @param array2 The second array. May be {@code null}.
	 * @param arrayFactory A factory that should create new arrays of the type {@code T}.
	 * Should be implemented like {@code T[]::new}.
	 * @return An array that contains all elements from both specified arrays.
	 * The returned array is always created using the array factory.
	 * The returned array is not {@code null}.
	 * @throws NullPointerException If the array factory is {@code null} or creates an array {@code null}.
	 * @throws ArrayStoreException If the array factory does not create a {@code T[]}
	 * and a type mismatch occurs.
	 */
	// The arrayFactory is required, even if Arrays.copyOf exists:
	// Let B,C extend A ==> concat(B[], C[]) should return an A[].
	// But Arrays.copyOf will either return a B[] or a C[].
	public static <T> T[] concat(T[] array1, T[] array2, IntFunction<T[]> arrayFactory) {
		int n1 = array1 != null ? array1.length : 0;
		int n2 = array2 != null ? array2.length : 0;
		T[] target = arrayFactory.apply(n1+n2);
		if (array1 != null) {
			System.arraycopy(array1, 0, target, 0, n1);
		}
		if (array2 != null) {
			System.arraycopy(array2, 0, target, n1, n2);
		}
		return target;
	}
	
	/**
	 * Concatenates all specified arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * The order of the arrays and the order of the elements inside each array are preserved.
	 * @param <T> The type of the array elements.
	 * @param arrayFactory A factory that should create new arrays of the type {@code T}.
	 * Should be implemented like {@code T[]::new}.
	 * @param arrays The arrays that should be concatenated. Must not be {@code null},
	 * but the arrays inside may be {@code null}.
	 * @return An array that contains all elements from all specified arrays.
	 * @throws NullPointerException If the array factory is {@code null} or creates an array {@code null}.
	 * @throws ArrayStoreException If the array factory does not create a {@code T[]}
	 * and a type mismatch occurs.
	 */
	@SafeVarargs
	public static <T> T[] concat(IntFunction<T[]> arrayFactory, T[]... arrays) {
		// Calculates the length of the target array.
		int n = 0;
		for (T[] arr : arrays) {
			if (arr != null) {
				n += arr.length;
			}
		}
		
		T[] target = arrayFactory.apply(n);
		
		// Writes the elements to the target array.
		int i = 0;
		for (T[] arr : arrays) {
			if (arr != null) {
				int len = arr.length;
				System.arraycopy(arr, 0, target, i, len);
				i += len;
			}
		}
		return target;
	}
	
	/**
	 * Concatenates the specified collections to a single one.
	 * The type of the resulting collection is specified by the factory.
	 * @param <T> The element type.
	 * @param <C> The type of the resulting collection. Must not be {@code null}.
	 * @param factory The factory that produces an empty collection.
	 * @param collections The collections that should be concatenated.
	 * Must not be {@code null}, but the inner collections may be {@code null}.
	 * @return A collection that contains all elements of the specified collections.
	 * The resulting collection is created using the factory.
	 * If the factory does not return {@code null}, the result is not {@code null}.
	 */
	@SafeVarargs
	public static <T, C extends Collection<T>> C concat(Supplier<C> factory, Collection<? extends T>... collections) {
		C result = factory.get();
		for (Collection<? extends T> col : collections) {
			if (col != null) {
				result.addAll(col);
			}
		}
		return result;
	}
	
	/* txs-begin concat
	# for (string type in types) {
		## // The generic type has an own implementation with an array factory.
		if (type == 'T') {
			continue;
		}
		##
	
	/**
	 * Concatenates both arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * <p>
	 * All elements from {@code array1} are before all from {@code array2}.
	 * The order of the elements is preserved.
	 * @param array1 The first array. May be {@code null}.
	 * @param array2 The second array. May be {@code null}.
	 * @return An array that contains all elements from both specified arrays.
	 * Not {@code null}. A new array is created.
	 *$'/';
	// $txsinfo();
	public static $type;[] concat($type;[] array1, $type;[] array2) {
		int n1 = array1 != null ? array1.length : 0;
		int n2 = array2 != null ? array2.length : 0;
		$type;[] target = new $type;[n1+n2];
		if (array1 != null) {
			System.arraycopy(array1, 0, target, 0, n1);
		}
		if (array2 != null) {
			System.arraycopy(array2, 0, target, n1, n2);
		}
		return target;
	}
	
	/**
	 * Concatenates all specified arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * The order of the arrays and the order of the elements inside each array are preserved.
	 * @param arrays The arrays that should be concatenated. Must not be {@code null},
	 * but the arrays inside may be {@code null}.
	 * @return An array that contains all elements from all specified arrays.
	 * Not {@code null}. A new array is created.
	 *$'/';
	// $txsinfo();
	public static $type;[] concat($type;[]... arrays) {
		int n = 0;
		for ($type;[] arr : arrays) {
			if (arr != null) {
				n += arr.length;
			}
		}
		
		$type;[] target = new $type;[n];
		int i = 0;
		for ($type;[] arr : arrays) {
			if (arr != null) {
				int len = arr.length;
				System.arraycopy(arr, 0, target, i, len);
				i += len;
			}
		}
		return target;
	}
	# }
	txs-end concat */
	// txs-begin-gen concat
	
	/**
	 * Concatenates both arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * <p>
	 * All elements from {@code array1} are before all from {@code array2}.
	 * The order of the elements is preserved.
	 * @param array1 The first array. May be {@code null}.
	 * @param array2 The second array. May be {@code null}.
	 * @return An array that contains all elements from both specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static int[] concat(int[] array1, int[] array2) {
		int n1 = array1 != null ? array1.length : 0;
		int n2 = array2 != null ? array2.length : 0;
		int[] target = new int[n1+n2];
		if (array1 != null) {
			System.arraycopy(array1, 0, target, 0, n1);
		}
		if (array2 != null) {
			System.arraycopy(array2, 0, target, n1, n2);
		}
		return target;
	}
	
	/**
	 * Concatenates all specified arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * The order of the arrays and the order of the elements inside each array are preserved.
	 * @param arrays The arrays that should be concatenated. Must not be {@code null},
	 * but the arrays inside may be {@code null}.
	 * @return An array that contains all elements from all specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static int[] concat(int[]... arrays) {
		int n = 0;
		for (int[] arr : arrays) {
			if (arr != null) {
				n += arr.length;
			}
		}
		
		int[] target = new int[n];
		int i = 0;
		for (int[] arr : arrays) {
			if (arr != null) {
				int len = arr.length;
				System.arraycopy(arr, 0, target, i, len);
				i += len;
			}
		}
		return target;
	}
	
	/**
	 * Concatenates both arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * <p>
	 * All elements from {@code array1} are before all from {@code array2}.
	 * The order of the elements is preserved.
	 * @param array1 The first array. May be {@code null}.
	 * @param array2 The second array. May be {@code null}.
	 * @return An array that contains all elements from both specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static long[] concat(long[] array1, long[] array2) {
		int n1 = array1 != null ? array1.length : 0;
		int n2 = array2 != null ? array2.length : 0;
		long[] target = new long[n1+n2];
		if (array1 != null) {
			System.arraycopy(array1, 0, target, 0, n1);
		}
		if (array2 != null) {
			System.arraycopy(array2, 0, target, n1, n2);
		}
		return target;
	}
	
	/**
	 * Concatenates all specified arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * The order of the arrays and the order of the elements inside each array are preserved.
	 * @param arrays The arrays that should be concatenated. Must not be {@code null},
	 * but the arrays inside may be {@code null}.
	 * @return An array that contains all elements from all specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static long[] concat(long[]... arrays) {
		int n = 0;
		for (long[] arr : arrays) {
			if (arr != null) {
				n += arr.length;
			}
		}
		
		long[] target = new long[n];
		int i = 0;
		for (long[] arr : arrays) {
			if (arr != null) {
				int len = arr.length;
				System.arraycopy(arr, 0, target, i, len);
				i += len;
			}
		}
		return target;
	}
	
	/**
	 * Concatenates both arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * <p>
	 * All elements from {@code array1} are before all from {@code array2}.
	 * The order of the elements is preserved.
	 * @param array1 The first array. May be {@code null}.
	 * @param array2 The second array. May be {@code null}.
	 * @return An array that contains all elements from both specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static byte[] concat(byte[] array1, byte[] array2) {
		int n1 = array1 != null ? array1.length : 0;
		int n2 = array2 != null ? array2.length : 0;
		byte[] target = new byte[n1+n2];
		if (array1 != null) {
			System.arraycopy(array1, 0, target, 0, n1);
		}
		if (array2 != null) {
			System.arraycopy(array2, 0, target, n1, n2);
		}
		return target;
	}
	
	/**
	 * Concatenates all specified arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * The order of the arrays and the order of the elements inside each array are preserved.
	 * @param arrays The arrays that should be concatenated. Must not be {@code null},
	 * but the arrays inside may be {@code null}.
	 * @return An array that contains all elements from all specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static byte[] concat(byte[]... arrays) {
		int n = 0;
		for (byte[] arr : arrays) {
			if (arr != null) {
				n += arr.length;
			}
		}
		
		byte[] target = new byte[n];
		int i = 0;
		for (byte[] arr : arrays) {
			if (arr != null) {
				int len = arr.length;
				System.arraycopy(arr, 0, target, i, len);
				i += len;
			}
		}
		return target;
	}
	
	/**
	 * Concatenates both arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * <p>
	 * All elements from {@code array1} are before all from {@code array2}.
	 * The order of the elements is preserved.
	 * @param array1 The first array. May be {@code null}.
	 * @param array2 The second array. May be {@code null}.
	 * @return An array that contains all elements from both specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static short[] concat(short[] array1, short[] array2) {
		int n1 = array1 != null ? array1.length : 0;
		int n2 = array2 != null ? array2.length : 0;
		short[] target = new short[n1+n2];
		if (array1 != null) {
			System.arraycopy(array1, 0, target, 0, n1);
		}
		if (array2 != null) {
			System.arraycopy(array2, 0, target, n1, n2);
		}
		return target;
	}
	
	/**
	 * Concatenates all specified arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * The order of the arrays and the order of the elements inside each array are preserved.
	 * @param arrays The arrays that should be concatenated. Must not be {@code null},
	 * but the arrays inside may be {@code null}.
	 * @return An array that contains all elements from all specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static short[] concat(short[]... arrays) {
		int n = 0;
		for (short[] arr : arrays) {
			if (arr != null) {
				n += arr.length;
			}
		}
		
		short[] target = new short[n];
		int i = 0;
		for (short[] arr : arrays) {
			if (arr != null) {
				int len = arr.length;
				System.arraycopy(arr, 0, target, i, len);
				i += len;
			}
		}
		return target;
	}
	
	/**
	 * Concatenates both arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * <p>
	 * All elements from {@code array1} are before all from {@code array2}.
	 * The order of the elements is preserved.
	 * @param array1 The first array. May be {@code null}.
	 * @param array2 The second array. May be {@code null}.
	 * @return An array that contains all elements from both specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static char[] concat(char[] array1, char[] array2) {
		int n1 = array1 != null ? array1.length : 0;
		int n2 = array2 != null ? array2.length : 0;
		char[] target = new char[n1+n2];
		if (array1 != null) {
			System.arraycopy(array1, 0, target, 0, n1);
		}
		if (array2 != null) {
			System.arraycopy(array2, 0, target, n1, n2);
		}
		return target;
	}
	
	/**
	 * Concatenates all specified arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * The order of the arrays and the order of the elements inside each array are preserved.
	 * @param arrays The arrays that should be concatenated. Must not be {@code null},
	 * but the arrays inside may be {@code null}.
	 * @return An array that contains all elements from all specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static char[] concat(char[]... arrays) {
		int n = 0;
		for (char[] arr : arrays) {
			if (arr != null) {
				n += arr.length;
			}
		}
		
		char[] target = new char[n];
		int i = 0;
		for (char[] arr : arrays) {
			if (arr != null) {
				int len = arr.length;
				System.arraycopy(arr, 0, target, i, len);
				i += len;
			}
		}
		return target;
	}
	
	/**
	 * Concatenates both arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * <p>
	 * All elements from {@code array1} are before all from {@code array2}.
	 * The order of the elements is preserved.
	 * @param array1 The first array. May be {@code null}.
	 * @param array2 The second array. May be {@code null}.
	 * @return An array that contains all elements from both specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static float[] concat(float[] array1, float[] array2) {
		int n1 = array1 != null ? array1.length : 0;
		int n2 = array2 != null ? array2.length : 0;
		float[] target = new float[n1+n2];
		if (array1 != null) {
			System.arraycopy(array1, 0, target, 0, n1);
		}
		if (array2 != null) {
			System.arraycopy(array2, 0, target, n1, n2);
		}
		return target;
	}
	
	/**
	 * Concatenates all specified arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * The order of the arrays and the order of the elements inside each array are preserved.
	 * @param arrays The arrays that should be concatenated. Must not be {@code null},
	 * but the arrays inside may be {@code null}.
	 * @return An array that contains all elements from all specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static float[] concat(float[]... arrays) {
		int n = 0;
		for (float[] arr : arrays) {
			if (arr != null) {
				n += arr.length;
			}
		}
		
		float[] target = new float[n];
		int i = 0;
		for (float[] arr : arrays) {
			if (arr != null) {
				int len = arr.length;
				System.arraycopy(arr, 0, target, i, len);
				i += len;
			}
		}
		return target;
	}
	
	/**
	 * Concatenates both arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * <p>
	 * All elements from {@code array1} are before all from {@code array2}.
	 * The order of the elements is preserved.
	 * @param array1 The first array. May be {@code null}.
	 * @param array2 The second array. May be {@code null}.
	 * @return An array that contains all elements from both specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static double[] concat(double[] array1, double[] array2) {
		int n1 = array1 != null ? array1.length : 0;
		int n2 = array2 != null ? array2.length : 0;
		double[] target = new double[n1+n2];
		if (array1 != null) {
			System.arraycopy(array1, 0, target, 0, n1);
		}
		if (array2 != null) {
			System.arraycopy(array2, 0, target, n1, n2);
		}
		return target;
	}
	
	/**
	 * Concatenates all specified arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * The order of the arrays and the order of the elements inside each array are preserved.
	 * @param arrays The arrays that should be concatenated. Must not be {@code null},
	 * but the arrays inside may be {@code null}.
	 * @return An array that contains all elements from all specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static double[] concat(double[]... arrays) {
		int n = 0;
		for (double[] arr : arrays) {
			if (arr != null) {
				n += arr.length;
			}
		}
		
		double[] target = new double[n];
		int i = 0;
		for (double[] arr : arrays) {
			if (arr != null) {
				int len = arr.length;
				System.arraycopy(arr, 0, target, i, len);
				i += len;
			}
		}
		return target;
	}
	
	/**
	 * Concatenates both arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * <p>
	 * All elements from {@code array1} are before all from {@code array2}.
	 * The order of the elements is preserved.
	 * @param array1 The first array. May be {@code null}.
	 * @param array2 The second array. May be {@code null}.
	 * @return An array that contains all elements from both specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static boolean[] concat(boolean[] array1, boolean[] array2) {
		int n1 = array1 != null ? array1.length : 0;
		int n2 = array2 != null ? array2.length : 0;
		boolean[] target = new boolean[n1+n2];
		if (array1 != null) {
			System.arraycopy(array1, 0, target, 0, n1);
		}
		if (array2 != null) {
			System.arraycopy(array2, 0, target, n1, n2);
		}
		return target;
	}
	
	/**
	 * Concatenates all specified arrays.
	 * If an array is {@code null}, it is handled like an empty array.
	 * The order of the arrays and the order of the elements inside each array are preserved.
	 * @param arrays The arrays that should be concatenated. Must not be {@code null},
	 * but the arrays inside may be {@code null}.
	 * @return An array that contains all elements from all specified arrays.
	 * Not {@code null}. A new array is created.
	 */
	// !!! TextScript generated !!!
	public static boolean[] concat(boolean[]... arrays) {
		int n = 0;
		for (boolean[] arr : arrays) {
			if (arr != null) {
				n += arr.length;
			}
		}
		
		boolean[] target = new boolean[n];
		int i = 0;
		for (boolean[] arr : arrays) {
			if (arr != null) {
				int len = arr.length;
				System.arraycopy(arr, 0, target, i, len);
				i += len;
			}
		}
		return target;
	}
	// txs-end-gen concat
	
	
	/* txs-begin reverse
# for (string type in types) {
	/**
	 * Reverses the specified array inplace.
	 * If the array is {@code null}, nothing happens and {@code null} is returned.
	 # if (type == "T") {
	 * @param <T> The element type.
	 # }
	 * @param array The array that should be reversed. May be {@code null}.
	 * @return The specified array for piping.
	 *$'/';
	public static $? type=="T" && "<T>"; $type;[] reverse($type;[] array) {
		if (array == null) {
			return null;
		}
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			$type; temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
# }
	 txs-end reverse */
	// txs-begin-gen reverse
	/**
	 * Reverses the specified array inplace.
	 * If the array is {@code null}, nothing happens and {@code null} is returned.
	 * @param <T> The element type.
	 * @param array The array that should be reversed. May be {@code null}.
	 * @return The specified array for piping.
	 */
	public static <T> T[] reverse(T[] array) {
		if (array == null) {
			return null;
		}
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			T temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	/**
	 * Reverses the specified array inplace.
	 * If the array is {@code null}, nothing happens and {@code null} is returned.
	 * @param array The array that should be reversed. May be {@code null}.
	 * @return The specified array for piping.
	 */
	public static int[] reverse(int[] array) {
		if (array == null) {
			return null;
		}
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	/**
	 * Reverses the specified array inplace.
	 * If the array is {@code null}, nothing happens and {@code null} is returned.
	 * @param array The array that should be reversed. May be {@code null}.
	 * @return The specified array for piping.
	 */
	public static long[] reverse(long[] array) {
		if (array == null) {
			return null;
		}
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			long temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	/**
	 * Reverses the specified array inplace.
	 * If the array is {@code null}, nothing happens and {@code null} is returned.
	 * @param array The array that should be reversed. May be {@code null}.
	 * @return The specified array for piping.
	 */
	public static byte[] reverse(byte[] array) {
		if (array == null) {
			return null;
		}
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			byte temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	/**
	 * Reverses the specified array inplace.
	 * If the array is {@code null}, nothing happens and {@code null} is returned.
	 * @param array The array that should be reversed. May be {@code null}.
	 * @return The specified array for piping.
	 */
	public static short[] reverse(short[] array) {
		if (array == null) {
			return null;
		}
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			short temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	/**
	 * Reverses the specified array inplace.
	 * If the array is {@code null}, nothing happens and {@code null} is returned.
	 * @param array The array that should be reversed. May be {@code null}.
	 * @return The specified array for piping.
	 */
	public static char[] reverse(char[] array) {
		if (array == null) {
			return null;
		}
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			char temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	/**
	 * Reverses the specified array inplace.
	 * If the array is {@code null}, nothing happens and {@code null} is returned.
	 * @param array The array that should be reversed. May be {@code null}.
	 * @return The specified array for piping.
	 */
	public static float[] reverse(float[] array) {
		if (array == null) {
			return null;
		}
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			float temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	/**
	 * Reverses the specified array inplace.
	 * If the array is {@code null}, nothing happens and {@code null} is returned.
	 * @param array The array that should be reversed. May be {@code null}.
	 * @return The specified array for piping.
	 */
	public static double[] reverse(double[] array) {
		if (array == null) {
			return null;
		}
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			double temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	/**
	 * Reverses the specified array inplace.
	 * If the array is {@code null}, nothing happens and {@code null} is returned.
	 * @param array The array that should be reversed. May be {@code null}.
	 * @return The specified array for piping.
	 */
	public static boolean[] reverse(boolean[] array) {
		if (array == null) {
			return null;
		}
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			boolean temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
	
	// txs-end-gen reverse
	
}
