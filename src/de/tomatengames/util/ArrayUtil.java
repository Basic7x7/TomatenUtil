/* txs-begin static
##
:inline=$
final string[] types = ["T", "int", "long", "byte", "short", "char", "float", "double", "boolean"];
##
txs-end static */

package de.tomatengames.util;

import java.util.Objects;
import java.util.function.IntFunction;

/**
 * Provides methods to work with arrays.
 * 
 * @author Basic7x7
 * @version 2023-01-21
 * @since 1.0
 */
public class ArrayUtil {
	
	/* txs-begin contains
	# for (string type in types) {
	/**
	 * Returns the first index of the specified element in the array
	 * that is greater than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 # if (type == 'T') {
	 * @param <T> The type of the elements.
	 # }
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @param startIndex The index where to start the search.
	 * @return The first index of the specified element &ge; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 *$'/';
	// $txsinfo();
	public static $? type == 'T' && '<T>'; int indexOf($type;[] array, $type; element, int startIndex) {
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
	 # if (type == 'T') {
	 * @param <T> The type of the elements.
	 # }
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 *$'/';
	// $txsinfo();
	public static $? type == 'T' && '<T>'; int indexOf($type;[] array, $type; element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index of the specified element in the array
	 * that is less than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 # if (type == 'T') {
	 * @param <T> The type of the elements.
	 # }
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @param startIndex The index where to start the search.
	 * @return The last index of the specified element &le; startIndex.
	 * If no such index exists, {@code -1} is returned.
	 *$'/';
	// $txsinfo();
	public static $? type == 'T' && '<T>'; int lastIndexOf($type;[] array, $type; element, int startIndex) {
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
	 # if (type == 'T') {
	 * @param <T> The type of the elements.
	 # }
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
	 * @return The last index of the specified element.
	 * If the array does not contain the element, {@code -1} is returned.
	 *$'/';
	// $txsinfo();
	public static $? type == 'T' && '<T>'; int lastIndexOf($type;[] array, $type; element) {
		if (array == null) {
			return -1;
		}
		return lastIndexOf(array, element, array.length-1);
	}
	
	/**
	 * Returns if the specified array contains the specified element.
	 * The array {@code null} does not contain any elements.
	 # if (type == 'T') {
	 * @param <T> The type of the array elements.
	 # }
	 * @param array The array. May be {@code null}.
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 *$'/';
	// $txsinfo();
	public static $? type == 'T' && '<T>'; boolean contains($type;[] array, $type; element) {
		return indexOf(array, element, 0) >= 0;
	}
	# }
	txs-end contains */
	// txs-begin-gen contains
	/**
	 * Returns the first index of the specified element in the array
	 * that is greater than or equal to the {@code startIndex}.
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
	 * Returns the last index of the specified element in the array
	 * that is less than or equal to the {@code startIndex}.
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
	 * Returns the first index of the specified element in the array
	 * that is greater than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(int[] array, int element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index of the specified element in the array
	 * that is less than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
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
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(int[] array, int element) {
		return indexOf(array, element, 0) >= 0;
	}
	/**
	 * Returns the first index of the specified element in the array
	 * that is greater than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(long[] array, long element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index of the specified element in the array
	 * that is less than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
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
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(long[] array, long element) {
		return indexOf(array, element, 0) >= 0;
	}
	/**
	 * Returns the first index of the specified element in the array
	 * that is greater than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(byte[] array, byte element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index of the specified element in the array
	 * that is less than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
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
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(byte[] array, byte element) {
		return indexOf(array, element, 0) >= 0;
	}
	/**
	 * Returns the first index of the specified element in the array
	 * that is greater than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(short[] array, short element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index of the specified element in the array
	 * that is less than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
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
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(short[] array, short element) {
		return indexOf(array, element, 0) >= 0;
	}
	/**
	 * Returns the first index of the specified element in the array
	 * that is greater than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(char[] array, char element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index of the specified element in the array
	 * that is less than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
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
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(char[] array, char element) {
		return indexOf(array, element, 0) >= 0;
	}
	/**
	 * Returns the first index of the specified element in the array
	 * that is greater than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(float[] array, float element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index of the specified element in the array
	 * that is less than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
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
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(float[] array, float element) {
		return indexOf(array, element, 0) >= 0;
	}
	/**
	 * Returns the first index of the specified element in the array
	 * that is greater than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(double[] array, double element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index of the specified element in the array
	 * that is less than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
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
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(double[] array, double element) {
		return indexOf(array, element, 0) >= 0;
	}
	/**
	 * Returns the first index of the specified element in the array
	 * that is greater than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
	 * @return The first index of the specified element.
	 * If the array does not contain the specified element, {@code -1} is returned.
	 */
	// !!! TextScript generated !!!
	public static int indexOf(boolean[] array, boolean element) {
		return indexOf(array, element, 0);
	}
	
	/**
	 * Returns the last index of the specified element in the array
	 * that is less than or equal to the {@code startIndex}.
	 * If the array is {@code null}, it does not contain any elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element. May be {@code null}.
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
	 * @param el The element. May be {@code null}.
	 * @return If the array contains the specified element.
	 */
	// !!! TextScript generated !!!
	public static boolean contains(boolean[] array, boolean element) {
		return indexOf(array, element, 0) >= 0;
	}
	// txs-end-gen contains
	
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
	
}
