/* txs-begin static
##
const types = ["T", "int", "long", "byte", "short", "char", "float", "double", "boolean"]
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
 * @version
 * 2023-11-10 last modified<br>
 * 2023-01-21 created
 * @since 1.0
 */
public class ArrayUtil {
	
	// Static class
	private ArrayUtil() {
	}
	
	/* txs-begin contains
	##
	const typeObjects = [
		{java: "T", equals: true, generic: "T"}, {java: "T", eq: false, generic: "T"},
		{java: "int"}, {java: "long"}, {java: "byte"}, {java: "short"}, {java: "char"},
		{java: "float"}, {java: "double"}, {java: "boolean"}
		]
		
	for (type in typeObjects) {
		final gen = "<" * type.generic * ">";
		final genDoc = "* @param " * gen * " The type of the elements.";
		
		##
		/**
		 * Returns the first index that is greater than or equal to the {@code startIndex} for which
		 * {@code array[index] %type.equals ? "equals" : "=="; element} applies.
		 * If the array is {@code null}, it does not contain any elements.
		 %genDoc;
		 * @param array The array. May be {@code null}.
		 * @param element The element. %type.generic && 'May be {@code null}.';
		 * @param startIndex The index where to start the search.
		 * @return The first index of the specified element &ge; startIndex.
		 * If no such index exists, {@code -1} is returned.
		 *%'/';
		// %cli.txsinfo();
		public static %gen; int indexOf%type.equals&&"Equal";(%type.java;[] array, %type.java; element, int startIndex) {
			if (array == null) {
				return -1;
			}
			int n = array.length;
			for (int i = startIndex; i < n; i++) {
				##
				if (type.equals) {
					##
					if (Objects.equals(array[i], element)) {
						return i;
					}
					##
				}
				else {
					##
					if (array[i] == element) {
						return i;
					}
					##
				}
				##
			}
			return -1;
		}
	
		/**
		 * Returns the first index of %type.equals
		 	? "an element in the array that is equal to the specified element."
		 	: "the specified element in the array.";
		 * If the array is {@code null}, it does not contain any elements.
		 %genDoc;
		 * @param array The array. May be {@code null}.
		 * @param element The element. %type.generic && 'May be {@code null}.';
		 * @return The first index of the specified element.
		 * If the array does not contain the specified element, {@code -1} is returned.
		 *%'/';
		// %cli.txsinfo();
		public static %gen; int indexOf%type.equals&&"Equal";(%type.java;[] array, %type.java; element) {
			return indexOf%type.equals&&"Equal";(array, element, 0);
		}
	
		/**
		 * Returns the last index in the array that is less than or equal to the {@code startIndex} for which
		 * {@code array[index] %type.equals ? "equals" : "=="; element} applies.
		 * If the array is {@code null}, it does not contain any elements.
		 %genDoc;
		 * @param array The array. May be {@code null}.
		 * @param element The element. %type.generic && 'May be {@code null}.';
		 * @param startIndex The index where to start the search.
		 * @return The last index of the specified element &le; startIndex.
		 * If no such index exists, {@code -1} is returned.
		 *%'/';
		// %cli.txsinfo();
		public static %gen; int lastIndexOf%type.equals&&"Equal";(%type.java;[] array, %type.java; element, int startIndex) {
			if (array == null) {
				return -1;
			}
			if (startIndex >= array.length) {
				startIndex = array.length-1;
			}
			for (int i = startIndex; i >= 0; i--) {
				##
				if (type.equals) {
					##
					if (Objects.equals(array[i], element)) {
						return i;
					}
					##
				}
				else {
					##
					if (array[i] == element) {
						return i;
					}
					##
				}
				##
			}
			return -1;
		}
		
		/**
		 * Returns the last index of %type.equals
		 	? "an element in the array that is equal to the specified element."
		 	: "the specified element in the array.";
		 * If the array is {@code null}, it does not contain any elements.
		 %genDoc;
		 * @param array The array. May be {@code null}.
		 * @param element The element.%type.generic && ' May be {@code null}.';
		 * @return The last index of the specified element.
		 * If the array does not contain the element, {@code -1} is returned.
		 *%'/';
		// %cli.txsinfo();
		public static %gen; int lastIndexOf%type.equals&&"Equal";(%type.java;[] array, %type.java; element) {
			if (array == null) {
				return -1;
			}
			return lastIndexOf%type.equals&&"Equal";(array, element, array.length-1);
		}
		
		/**
		 * Returns if the specified array contains %type.equals
		 	? "an element that is equal to the specified element."
		 	: "the specified element.";
		 * The array {@code null} does not contain any elements.
		 %genDoc;
		 * @param array The array. May be {@code null}.
		 * @param element The element.%type.generic && ' May be {@code null}.';
		 * @return If the array contains the specified element.
		 *%'/';
		// %cli.txsinfo();
		public static %gen; boolean contains%type.equals&&"Equal";(%type.java;[] array, %type.java; element) {
			return indexOf%type.equals&&"Equal";(array, element, 0) >= 0;
		}
		
		##
	}
	##
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
	 * @param <T> The type of the elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param <T> The type of the elements.
	 * @param array The array. May be {@code null}.
	 * @param element The element. May be {@code null}.
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
	 * @param element The element.
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
	 * @param element The element.
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
	 * @param element The element.
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
	 * @param element The element.
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
	 * @param element The element.
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
	 * @param element The element.
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
	 * @param element The element.
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
	 * @param element The element.
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
	##
	for (type in types) {
		// The generic type has an own implementation with an array factory.
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
		 *%'/';
		// %cli.txsinfo();
		public static %type;[] concat(%type;[] array1, %type;[] array2) {
			int n1 = array1 != null ? array1.length : 0;
			int n2 = array2 != null ? array2.length : 0;
			%type;[] target = new %type;[n1+n2];
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
		 *%'/';
		// %cli.txsinfo();
		public static %type;[] concat(%type;[]... arrays) {
			int n = 0;
			for (%type;[] arr : arrays) {
				if (arr != null) {
					n += arr.length;
				}
			}
			
			%type;[] target = new %type;[n];
			int i = 0;
			for (%type;[] arr : arrays) {
				if (arr != null) {
					int len = arr.length;
					System.arraycopy(arr, 0, target, i, len);
					i += len;
				}
			}
			return target;
		}
		
		##
	}
	##
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
	##
	for (type in types) {
		##
		/**
		 * Reverses the specified array inplace.
		 * If the array is {@code null}, nothing happens and {@code null} is returned.
		##
		if (type == "T") {
			##
			 * @param <T> The element type.
			##
		}
		##
		 * @param array The array that should be reversed. May be {@code null}.
		 * @return The specified array for piping.
		 *%'/';
		public static %type=="T" && "<T>"; %type;[] reverse(%type;[] array) {
			if (array == null) {
				return null;
			}
			for (int i = 0, j = array.length-1; i < j; i++, j--) {
				%type; temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
			return array;
		}
		
		##
	}
	##
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
	
	
	/**
	 * Copies the array and inserts the specified element at the specified index.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param <T> The element type.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted. May be {@code null}.
	 * @param index The index where the element should be inserted.
	 * Must not be negative and must not be greater than {@code array.length}.
	 * @param arrayFactory A factory that creates arrays of a given size.
	 * Should be implemented like {@code T[]::new}.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @throws IndexOutOfBoundsException If the index is negative or greater than the length of the array.
	 * @since 1.1
	 */
	public static <T> T[] add(T[] array, T el, int index, IntFunction<T[]> arrayFactory) {
		if (array == null) {
			if (index != 0) {
				throw new IndexOutOfBoundsException("Array length is 0, but target index is " + index + "!");
			}
			T[] newArray = arrayFactory.apply(1);
			newArray[0] = el;
			return newArray;
		}
		
		int n = array.length;
		if (index < 0 || index > n) {
			throw new IndexOutOfBoundsException("Array length is " + n + ", but target index is " + index + "!");
		}
		
		T[] newArray = arrayFactory.apply(n+1);
		System.arraycopy(array, 0, newArray, 0, index);
		newArray[index] = el;
		System.arraycopy(array, index, newArray, index+1, n-index);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element to the front.
	 * The resulting array is
	 * <pre>
	 * [el, array...]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param <T> The element type.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted. May be {@code null}.
	 * @param arrayFactory A factory that creates arrays of a given size.
	 * Should be implemented like {@code T[]::new}.
	 * @return A new array that contains the specified element and all elements from the specified array.
	 * Not {@code null}.
	 * @since 1.1
	 */
	public static <T> T[] addFirst(T[] array, T el, IntFunction<T[]> arrayFactory) {
		if (array == null) {
			T[] newArray = arrayFactory.apply(1);
			newArray[0] = el;
			return newArray;
		}
		int n = array.length;
		T[] newArray = arrayFactory.apply(n+1);
		newArray[0] = el;
		System.arraycopy(array, 0, newArray, 1, n);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element at the end.
	 * The resulting array is
	 * <pre>
	 * [array..., el]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param <T> The element type.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted. May be {@code null}.
	 * @param arrayFactory A factory that creates arrays of a given size.
	 * Should be implemented like {@code T[]::new}.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @since 1.1
	 */
	public static <T> T[] addLast(T[] array, T el, IntFunction<T[]> arrayFactory) {
		if (array == null) {
			T[] newArray = arrayFactory.apply(1);
			newArray[0] = el;
			return newArray;
		}
		int n = array.length;
		T[] newArray = arrayFactory.apply(n+1);
		System.arraycopy(array, 0, newArray, 0, n);
		newArray[n] = el;
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the specified index.
	 * The result is an array with one element less.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param <T> The element type.
	 * @param array The array. May be {@code null}.
	 * @param index The index that should be removed. Must be in range.
	 * @param arrayFactory A factory that creates arrays of a given size.
	 * Should be implemented like {@code T[]::new}.
	 * @return A new array without the element at the specified index. Not {@code null}.
	 * @throws IndexOutOfBoundsException If {@code index < 0} or {@code index >= array.length}.
	 * Occurs always if the array is empty or {@code null}.
	 * @since 1.1
	 */
	public static <T> T[] removeIndex(T[] array, int index, IntFunction<T[]> arrayFactory) {
		if (array == null) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: 0");
		}
		int n = array.length;
		if (index < 0 || index >= n) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: " + n);
		}
		// ==> n >= 1
		
		T[] newArray = arrayFactory.apply(n-1);
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index+1, newArray, index, n-index-1);
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the first appearance of the specified element.
	 * The result is an array with one element less if the array contains the specified element.
	 * If the array does not contain the specified element, the input array is returned and not copied.
	 * To find out if an element was removed, the length of the input array and the result array can be compared.
	 * If the array is {@code null}, it is handled as an empty array.
	 * @param <T> The element type.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be removed. May be {@code null}.
	 * @param arrayFactory A factory that creates arrays of a given size.
	 * Should be implemented like {@code T[]::new}.
	 * @return A new array without the first appearance of the specified element.
	 * Might be {@code null} if the input array is {@code null}.
	 * @since 1.1
	 */
	public static <T> T[] remove(T[] array, T el, IntFunction<T[]> arrayFactory) {
		int index = indexOf(array, el);
		if (index < 0) {
			return array;
		}
		return removeIndex(array, index, arrayFactory);
	}
	
	/**
	 * Copies the array, but skips the first appearance an element that is equal to the specified element.
	 * The result is an array with one element less if the array contains such an element.
	 * If the array does not contain such an element, the input array is returned and not copied.
	 * To find out if an element was removed, the length of the input array and the result array can be compared.
	 * If the array is {@code null}, it is handled as an empty array.
	 * @param <T> The element type.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be removed. May be {@code null}.
	 * @param arrayFactory A factory that creates arrays of a given size.
	 * Should be implemented like {@code T[]::new}.
	 * @return A new array without the first appearance of an element that is equal to the specified element.
	 * Might be {@code null} if the input array is {@code null}.
	 * @since 1.1
	 */
	public static <T> T[] removeEqual(T[] array, T el, IntFunction<T[]> arrayFactory) {
		int index = indexOfEqual(array, el);
		if (index < 0) {
			return array;
		}
		return removeIndex(array, index, arrayFactory);
	}
	
	/* txs-begin addElement
	##
	for (type in types) {
		if (type == "T") {
			continue;
		}
		##
		/**
		 * Copies the array and inserts the specified element at the specified index.
		 * If the array is {@code null}, it is handled like an empty array.
		 * @param array The array. May be {@code null}.
		 * @param el The element that should be inserted.
		 * @param index The index where the element should be inserted.
		 * Must not be negative and must not be greater than {@code array.length}.
		 * @return A new array that contains all elements from the specified array and the specified element.
		 * Not {@code null}.
		 * @throws IndexOutOfBoundsException If the index is negative or greater than the length of the array.
		 * @since 1.1
		 *%'/';
		// %cli.txsinfo();
		public static %type;[] add(%type;[] array, %type; el, int index) {
			if (array == null) {
				if (index != 0) {
					throw new IndexOutOfBoundsException("Array length is 0, but target index is " + index + "!");
				}
				return new %type;[] { el };
			}
			
			int n = array.length;
			if (index < 0 || index > n) {
				throw new IndexOutOfBoundsException("Array length is " + n + ", but target index is " + index + "!");
			}
			
			%type;[] newArray = new %type;[n+1];
			System.arraycopy(array, 0, newArray, 0, index);
			newArray[index] = el;
			System.arraycopy(array, index, newArray, index+1, n-index);
			return newArray;
		}
		
		/**
		 * Copies the array and inserts the specified element to the front.
		 * The resulting array is
		 * <pre>
		 * [el, array...]
		 * </pre>
		 * If the array is {@code null}, it is handled like an empty array.
		 * @param array The array. May be {@code null}.
		 * @param el The element that should be inserted.
		 * @return A new array that contains the specified element and all elements from the specified array.
		 * Not {@code null}.
		 * @since 1.1
		 *%'/';
		// %cli.txsinfo();
		public static %type;[] addFirst(%type;[] array, %type; el) {
			if (array == null) {
				return new %type;[] { el };
			}
			int n = array.length;
			%type;[] newArray = new %type;[n+1];
			newArray[0] = el;
			System.arraycopy(array, 0, newArray, 1, n);
			return newArray;
		}
		
		/**
		 * Copies the array and inserts the specified element at the end.
		 * The resulting array is
		 * <pre>
		 * [array..., el]
		 * </pre>
		 * If the array is {@code null}, it is handled like an empty array.
		 * @param array The array. May be {@code null}.
		 * @param el The element that should be inserted.
		 * @return A new array that contains all elements from the specified array and the specified element.
		 * Not {@code null}.
		 * @since 1.1
		 *%'/';
		// %cli.txsinfo();
		public static %type;[] addLast(%type;[] array, %type; el) {
			if (array == null) {
				return new %type;[] { el };
			}
			int n = array.length;
			%type;[] newArray = new %type;[n+1];
			System.arraycopy(array, 0, newArray, 0, n);
			newArray[n] = el;
			return newArray;
		}
		
		/**
		 * Copies the array, but skips the specified index.
		 * The result is an array with one element less.
		 * If the array is {@code null}, it is handled like an empty array.
		 * @param array The array. May be {@code null}.
		 * @param index The index that should be removed. Must be in range.
		 * @return A new array without the element at the specified index. Not {@code null}.
		 * @throws IndexOutOfBoundsException If {@code index < 0} or {@code index >= array.length}.
		 * Occurs always if the array is empty or {@code null}.
		 * @since 1.1
		 *%'/';
		// %cli.txsinfo();
		public static %type;[] removeIndex(%type;[] array, int index) {
			if (array == null) {
				throw new IndexOutOfBoundsException("index: " + index + ", length: 0");
			}
			int n = array.length;
			if (index < 0 || index >= n) {
				throw new IndexOutOfBoundsException("index: " + index + ", length: " + n);
			}
			
			%type;[] newArray = new %type;[n-1];
			System.arraycopy(array, 0, newArray, 0, index);
			System.arraycopy(array, index+1, newArray, index, n-index-1);
			return newArray;
		}
		
		/**
		 * Copies the array, but skips the first appearance of the specified element.
		 * The result is an array with one element less if the array contains the specified element.
		 * If the array does not contain the specified element, the input array is returned and not copied.
		 * To find out if an element was removed, the length of the input array and the result array can be compared.
		 * If the array is {@code null}, it is handled as an empty array.
		 * @param array The array. May be {@code null}.
		 * @param el The element that should be removed.
		 * @return A new array without the first appearance of the specified element.
		 * Might be {@code null} if the input array is {@code null}.
		 * @since 1.1
		 *%'/';
		// %cli.txsinfo();
		public static %type;[] remove(%type;[] array, %type; el) {
			int index = indexOf(array, el);
			if (index < 0) {
				return array;
			}
			return removeIndex(array, index);
		}
		
		
		##
	}
	##
	txs-end addElement */
	// txs-begin-gen addElement
	/**
	 * Copies the array and inserts the specified element at the specified index.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @param index The index where the element should be inserted.
	 * Must not be negative and must not be greater than {@code array.length}.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @throws IndexOutOfBoundsException If the index is negative or greater than the length of the array.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static int[] add(int[] array, int el, int index) {
		if (array == null) {
			if (index != 0) {
				throw new IndexOutOfBoundsException("Array length is 0, but target index is " + index + "!");
			}
			return new int[] { el };
		}
		
		int n = array.length;
		if (index < 0 || index > n) {
			throw new IndexOutOfBoundsException("Array length is " + n + ", but target index is " + index + "!");
		}
		
		int[] newArray = new int[n+1];
		System.arraycopy(array, 0, newArray, 0, index);
		newArray[index] = el;
		System.arraycopy(array, index, newArray, index+1, n-index);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element to the front.
	 * The resulting array is
	 * <pre>
	 * [el, array...]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains the specified element and all elements from the specified array.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static int[] addFirst(int[] array, int el) {
		if (array == null) {
			return new int[] { el };
		}
		int n = array.length;
		int[] newArray = new int[n+1];
		newArray[0] = el;
		System.arraycopy(array, 0, newArray, 1, n);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element at the end.
	 * The resulting array is
	 * <pre>
	 * [array..., el]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static int[] addLast(int[] array, int el) {
		if (array == null) {
			return new int[] { el };
		}
		int n = array.length;
		int[] newArray = new int[n+1];
		System.arraycopy(array, 0, newArray, 0, n);
		newArray[n] = el;
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the specified index.
	 * The result is an array with one element less.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param index The index that should be removed. Must be in range.
	 * @return A new array without the element at the specified index. Not {@code null}.
	 * @throws IndexOutOfBoundsException If {@code index < 0} or {@code index >= array.length}.
	 * Occurs always if the array is empty or {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static int[] removeIndex(int[] array, int index) {
		if (array == null) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: 0");
		}
		int n = array.length;
		if (index < 0 || index >= n) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: " + n);
		}
		
		int[] newArray = new int[n-1];
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index+1, newArray, index, n-index-1);
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the first appearance of the specified element.
	 * The result is an array with one element less if the array contains the specified element.
	 * If the array does not contain the specified element, the input array is returned and not copied.
	 * To find out if an element was removed, the length of the input array and the result array can be compared.
	 * If the array is {@code null}, it is handled as an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be removed.
	 * @return A new array without the first appearance of the specified element.
	 * Might be {@code null} if the input array is {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static int[] remove(int[] array, int el) {
		int index = indexOf(array, el);
		if (index < 0) {
			return array;
		}
		return removeIndex(array, index);
	}
	
	
	/**
	 * Copies the array and inserts the specified element at the specified index.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @param index The index where the element should be inserted.
	 * Must not be negative and must not be greater than {@code array.length}.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @throws IndexOutOfBoundsException If the index is negative or greater than the length of the array.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static long[] add(long[] array, long el, int index) {
		if (array == null) {
			if (index != 0) {
				throw new IndexOutOfBoundsException("Array length is 0, but target index is " + index + "!");
			}
			return new long[] { el };
		}
		
		int n = array.length;
		if (index < 0 || index > n) {
			throw new IndexOutOfBoundsException("Array length is " + n + ", but target index is " + index + "!");
		}
		
		long[] newArray = new long[n+1];
		System.arraycopy(array, 0, newArray, 0, index);
		newArray[index] = el;
		System.arraycopy(array, index, newArray, index+1, n-index);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element to the front.
	 * The resulting array is
	 * <pre>
	 * [el, array...]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains the specified element and all elements from the specified array.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static long[] addFirst(long[] array, long el) {
		if (array == null) {
			return new long[] { el };
		}
		int n = array.length;
		long[] newArray = new long[n+1];
		newArray[0] = el;
		System.arraycopy(array, 0, newArray, 1, n);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element at the end.
	 * The resulting array is
	 * <pre>
	 * [array..., el]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static long[] addLast(long[] array, long el) {
		if (array == null) {
			return new long[] { el };
		}
		int n = array.length;
		long[] newArray = new long[n+1];
		System.arraycopy(array, 0, newArray, 0, n);
		newArray[n] = el;
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the specified index.
	 * The result is an array with one element less.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param index The index that should be removed. Must be in range.
	 * @return A new array without the element at the specified index. Not {@code null}.
	 * @throws IndexOutOfBoundsException If {@code index < 0} or {@code index >= array.length}.
	 * Occurs always if the array is empty or {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static long[] removeIndex(long[] array, int index) {
		if (array == null) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: 0");
		}
		int n = array.length;
		if (index < 0 || index >= n) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: " + n);
		}
		
		long[] newArray = new long[n-1];
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index+1, newArray, index, n-index-1);
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the first appearance of the specified element.
	 * The result is an array with one element less if the array contains the specified element.
	 * If the array does not contain the specified element, the input array is returned and not copied.
	 * To find out if an element was removed, the length of the input array and the result array can be compared.
	 * If the array is {@code null}, it is handled as an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be removed.
	 * @return A new array without the first appearance of the specified element.
	 * Might be {@code null} if the input array is {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static long[] remove(long[] array, long el) {
		int index = indexOf(array, el);
		if (index < 0) {
			return array;
		}
		return removeIndex(array, index);
	}
	
	
	/**
	 * Copies the array and inserts the specified element at the specified index.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @param index The index where the element should be inserted.
	 * Must not be negative and must not be greater than {@code array.length}.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @throws IndexOutOfBoundsException If the index is negative or greater than the length of the array.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static byte[] add(byte[] array, byte el, int index) {
		if (array == null) {
			if (index != 0) {
				throw new IndexOutOfBoundsException("Array length is 0, but target index is " + index + "!");
			}
			return new byte[] { el };
		}
		
		int n = array.length;
		if (index < 0 || index > n) {
			throw new IndexOutOfBoundsException("Array length is " + n + ", but target index is " + index + "!");
		}
		
		byte[] newArray = new byte[n+1];
		System.arraycopy(array, 0, newArray, 0, index);
		newArray[index] = el;
		System.arraycopy(array, index, newArray, index+1, n-index);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element to the front.
	 * The resulting array is
	 * <pre>
	 * [el, array...]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains the specified element and all elements from the specified array.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static byte[] addFirst(byte[] array, byte el) {
		if (array == null) {
			return new byte[] { el };
		}
		int n = array.length;
		byte[] newArray = new byte[n+1];
		newArray[0] = el;
		System.arraycopy(array, 0, newArray, 1, n);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element at the end.
	 * The resulting array is
	 * <pre>
	 * [array..., el]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static byte[] addLast(byte[] array, byte el) {
		if (array == null) {
			return new byte[] { el };
		}
		int n = array.length;
		byte[] newArray = new byte[n+1];
		System.arraycopy(array, 0, newArray, 0, n);
		newArray[n] = el;
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the specified index.
	 * The result is an array with one element less.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param index The index that should be removed. Must be in range.
	 * @return A new array without the element at the specified index. Not {@code null}.
	 * @throws IndexOutOfBoundsException If {@code index < 0} or {@code index >= array.length}.
	 * Occurs always if the array is empty or {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static byte[] removeIndex(byte[] array, int index) {
		if (array == null) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: 0");
		}
		int n = array.length;
		if (index < 0 || index >= n) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: " + n);
		}
		
		byte[] newArray = new byte[n-1];
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index+1, newArray, index, n-index-1);
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the first appearance of the specified element.
	 * The result is an array with one element less if the array contains the specified element.
	 * If the array does not contain the specified element, the input array is returned and not copied.
	 * To find out if an element was removed, the length of the input array and the result array can be compared.
	 * If the array is {@code null}, it is handled as an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be removed.
	 * @return A new array without the first appearance of the specified element.
	 * Might be {@code null} if the input array is {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static byte[] remove(byte[] array, byte el) {
		int index = indexOf(array, el);
		if (index < 0) {
			return array;
		}
		return removeIndex(array, index);
	}
	
	
	/**
	 * Copies the array and inserts the specified element at the specified index.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @param index The index where the element should be inserted.
	 * Must not be negative and must not be greater than {@code array.length}.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @throws IndexOutOfBoundsException If the index is negative or greater than the length of the array.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static short[] add(short[] array, short el, int index) {
		if (array == null) {
			if (index != 0) {
				throw new IndexOutOfBoundsException("Array length is 0, but target index is " + index + "!");
			}
			return new short[] { el };
		}
		
		int n = array.length;
		if (index < 0 || index > n) {
			throw new IndexOutOfBoundsException("Array length is " + n + ", but target index is " + index + "!");
		}
		
		short[] newArray = new short[n+1];
		System.arraycopy(array, 0, newArray, 0, index);
		newArray[index] = el;
		System.arraycopy(array, index, newArray, index+1, n-index);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element to the front.
	 * The resulting array is
	 * <pre>
	 * [el, array...]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains the specified element and all elements from the specified array.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static short[] addFirst(short[] array, short el) {
		if (array == null) {
			return new short[] { el };
		}
		int n = array.length;
		short[] newArray = new short[n+1];
		newArray[0] = el;
		System.arraycopy(array, 0, newArray, 1, n);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element at the end.
	 * The resulting array is
	 * <pre>
	 * [array..., el]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static short[] addLast(short[] array, short el) {
		if (array == null) {
			return new short[] { el };
		}
		int n = array.length;
		short[] newArray = new short[n+1];
		System.arraycopy(array, 0, newArray, 0, n);
		newArray[n] = el;
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the specified index.
	 * The result is an array with one element less.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param index The index that should be removed. Must be in range.
	 * @return A new array without the element at the specified index. Not {@code null}.
	 * @throws IndexOutOfBoundsException If {@code index < 0} or {@code index >= array.length}.
	 * Occurs always if the array is empty or {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static short[] removeIndex(short[] array, int index) {
		if (array == null) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: 0");
		}
		int n = array.length;
		if (index < 0 || index >= n) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: " + n);
		}
		
		short[] newArray = new short[n-1];
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index+1, newArray, index, n-index-1);
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the first appearance of the specified element.
	 * The result is an array with one element less if the array contains the specified element.
	 * If the array does not contain the specified element, the input array is returned and not copied.
	 * To find out if an element was removed, the length of the input array and the result array can be compared.
	 * If the array is {@code null}, it is handled as an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be removed.
	 * @return A new array without the first appearance of the specified element.
	 * Might be {@code null} if the input array is {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static short[] remove(short[] array, short el) {
		int index = indexOf(array, el);
		if (index < 0) {
			return array;
		}
		return removeIndex(array, index);
	}
	
	
	/**
	 * Copies the array and inserts the specified element at the specified index.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @param index The index where the element should be inserted.
	 * Must not be negative and must not be greater than {@code array.length}.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @throws IndexOutOfBoundsException If the index is negative or greater than the length of the array.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static char[] add(char[] array, char el, int index) {
		if (array == null) {
			if (index != 0) {
				throw new IndexOutOfBoundsException("Array length is 0, but target index is " + index + "!");
			}
			return new char[] { el };
		}
		
		int n = array.length;
		if (index < 0 || index > n) {
			throw new IndexOutOfBoundsException("Array length is " + n + ", but target index is " + index + "!");
		}
		
		char[] newArray = new char[n+1];
		System.arraycopy(array, 0, newArray, 0, index);
		newArray[index] = el;
		System.arraycopy(array, index, newArray, index+1, n-index);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element to the front.
	 * The resulting array is
	 * <pre>
	 * [el, array...]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains the specified element and all elements from the specified array.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static char[] addFirst(char[] array, char el) {
		if (array == null) {
			return new char[] { el };
		}
		int n = array.length;
		char[] newArray = new char[n+1];
		newArray[0] = el;
		System.arraycopy(array, 0, newArray, 1, n);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element at the end.
	 * The resulting array is
	 * <pre>
	 * [array..., el]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static char[] addLast(char[] array, char el) {
		if (array == null) {
			return new char[] { el };
		}
		int n = array.length;
		char[] newArray = new char[n+1];
		System.arraycopy(array, 0, newArray, 0, n);
		newArray[n] = el;
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the specified index.
	 * The result is an array with one element less.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param index The index that should be removed. Must be in range.
	 * @return A new array without the element at the specified index. Not {@code null}.
	 * @throws IndexOutOfBoundsException If {@code index < 0} or {@code index >= array.length}.
	 * Occurs always if the array is empty or {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static char[] removeIndex(char[] array, int index) {
		if (array == null) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: 0");
		}
		int n = array.length;
		if (index < 0 || index >= n) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: " + n);
		}
		
		char[] newArray = new char[n-1];
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index+1, newArray, index, n-index-1);
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the first appearance of the specified element.
	 * The result is an array with one element less if the array contains the specified element.
	 * If the array does not contain the specified element, the input array is returned and not copied.
	 * To find out if an element was removed, the length of the input array and the result array can be compared.
	 * If the array is {@code null}, it is handled as an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be removed.
	 * @return A new array without the first appearance of the specified element.
	 * Might be {@code null} if the input array is {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static char[] remove(char[] array, char el) {
		int index = indexOf(array, el);
		if (index < 0) {
			return array;
		}
		return removeIndex(array, index);
	}
	
	
	/**
	 * Copies the array and inserts the specified element at the specified index.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @param index The index where the element should be inserted.
	 * Must not be negative and must not be greater than {@code array.length}.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @throws IndexOutOfBoundsException If the index is negative or greater than the length of the array.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static float[] add(float[] array, float el, int index) {
		if (array == null) {
			if (index != 0) {
				throw new IndexOutOfBoundsException("Array length is 0, but target index is " + index + "!");
			}
			return new float[] { el };
		}
		
		int n = array.length;
		if (index < 0 || index > n) {
			throw new IndexOutOfBoundsException("Array length is " + n + ", but target index is " + index + "!");
		}
		
		float[] newArray = new float[n+1];
		System.arraycopy(array, 0, newArray, 0, index);
		newArray[index] = el;
		System.arraycopy(array, index, newArray, index+1, n-index);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element to the front.
	 * The resulting array is
	 * <pre>
	 * [el, array...]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains the specified element and all elements from the specified array.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static float[] addFirst(float[] array, float el) {
		if (array == null) {
			return new float[] { el };
		}
		int n = array.length;
		float[] newArray = new float[n+1];
		newArray[0] = el;
		System.arraycopy(array, 0, newArray, 1, n);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element at the end.
	 * The resulting array is
	 * <pre>
	 * [array..., el]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static float[] addLast(float[] array, float el) {
		if (array == null) {
			return new float[] { el };
		}
		int n = array.length;
		float[] newArray = new float[n+1];
		System.arraycopy(array, 0, newArray, 0, n);
		newArray[n] = el;
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the specified index.
	 * The result is an array with one element less.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param index The index that should be removed. Must be in range.
	 * @return A new array without the element at the specified index. Not {@code null}.
	 * @throws IndexOutOfBoundsException If {@code index < 0} or {@code index >= array.length}.
	 * Occurs always if the array is empty or {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static float[] removeIndex(float[] array, int index) {
		if (array == null) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: 0");
		}
		int n = array.length;
		if (index < 0 || index >= n) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: " + n);
		}
		
		float[] newArray = new float[n-1];
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index+1, newArray, index, n-index-1);
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the first appearance of the specified element.
	 * The result is an array with one element less if the array contains the specified element.
	 * If the array does not contain the specified element, the input array is returned and not copied.
	 * To find out if an element was removed, the length of the input array and the result array can be compared.
	 * If the array is {@code null}, it is handled as an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be removed.
	 * @return A new array without the first appearance of the specified element.
	 * Might be {@code null} if the input array is {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static float[] remove(float[] array, float el) {
		int index = indexOf(array, el);
		if (index < 0) {
			return array;
		}
		return removeIndex(array, index);
	}
	
	
	/**
	 * Copies the array and inserts the specified element at the specified index.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @param index The index where the element should be inserted.
	 * Must not be negative and must not be greater than {@code array.length}.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @throws IndexOutOfBoundsException If the index is negative or greater than the length of the array.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static double[] add(double[] array, double el, int index) {
		if (array == null) {
			if (index != 0) {
				throw new IndexOutOfBoundsException("Array length is 0, but target index is " + index + "!");
			}
			return new double[] { el };
		}
		
		int n = array.length;
		if (index < 0 || index > n) {
			throw new IndexOutOfBoundsException("Array length is " + n + ", but target index is " + index + "!");
		}
		
		double[] newArray = new double[n+1];
		System.arraycopy(array, 0, newArray, 0, index);
		newArray[index] = el;
		System.arraycopy(array, index, newArray, index+1, n-index);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element to the front.
	 * The resulting array is
	 * <pre>
	 * [el, array...]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains the specified element and all elements from the specified array.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static double[] addFirst(double[] array, double el) {
		if (array == null) {
			return new double[] { el };
		}
		int n = array.length;
		double[] newArray = new double[n+1];
		newArray[0] = el;
		System.arraycopy(array, 0, newArray, 1, n);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element at the end.
	 * The resulting array is
	 * <pre>
	 * [array..., el]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static double[] addLast(double[] array, double el) {
		if (array == null) {
			return new double[] { el };
		}
		int n = array.length;
		double[] newArray = new double[n+1];
		System.arraycopy(array, 0, newArray, 0, n);
		newArray[n] = el;
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the specified index.
	 * The result is an array with one element less.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param index The index that should be removed. Must be in range.
	 * @return A new array without the element at the specified index. Not {@code null}.
	 * @throws IndexOutOfBoundsException If {@code index < 0} or {@code index >= array.length}.
	 * Occurs always if the array is empty or {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static double[] removeIndex(double[] array, int index) {
		if (array == null) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: 0");
		}
		int n = array.length;
		if (index < 0 || index >= n) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: " + n);
		}
		
		double[] newArray = new double[n-1];
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index+1, newArray, index, n-index-1);
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the first appearance of the specified element.
	 * The result is an array with one element less if the array contains the specified element.
	 * If the array does not contain the specified element, the input array is returned and not copied.
	 * To find out if an element was removed, the length of the input array and the result array can be compared.
	 * If the array is {@code null}, it is handled as an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be removed.
	 * @return A new array without the first appearance of the specified element.
	 * Might be {@code null} if the input array is {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static double[] remove(double[] array, double el) {
		int index = indexOf(array, el);
		if (index < 0) {
			return array;
		}
		return removeIndex(array, index);
	}
	
	
	/**
	 * Copies the array and inserts the specified element at the specified index.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @param index The index where the element should be inserted.
	 * Must not be negative and must not be greater than {@code array.length}.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @throws IndexOutOfBoundsException If the index is negative or greater than the length of the array.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static boolean[] add(boolean[] array, boolean el, int index) {
		if (array == null) {
			if (index != 0) {
				throw new IndexOutOfBoundsException("Array length is 0, but target index is " + index + "!");
			}
			return new boolean[] { el };
		}
		
		int n = array.length;
		if (index < 0 || index > n) {
			throw new IndexOutOfBoundsException("Array length is " + n + ", but target index is " + index + "!");
		}
		
		boolean[] newArray = new boolean[n+1];
		System.arraycopy(array, 0, newArray, 0, index);
		newArray[index] = el;
		System.arraycopy(array, index, newArray, index+1, n-index);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element to the front.
	 * The resulting array is
	 * <pre>
	 * [el, array...]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains the specified element and all elements from the specified array.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static boolean[] addFirst(boolean[] array, boolean el) {
		if (array == null) {
			return new boolean[] { el };
		}
		int n = array.length;
		boolean[] newArray = new boolean[n+1];
		newArray[0] = el;
		System.arraycopy(array, 0, newArray, 1, n);
		return newArray;
	}
	
	/**
	 * Copies the array and inserts the specified element at the end.
	 * The resulting array is
	 * <pre>
	 * [array..., el]
	 * </pre>
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be inserted.
	 * @return A new array that contains all elements from the specified array and the specified element.
	 * Not {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static boolean[] addLast(boolean[] array, boolean el) {
		if (array == null) {
			return new boolean[] { el };
		}
		int n = array.length;
		boolean[] newArray = new boolean[n+1];
		System.arraycopy(array, 0, newArray, 0, n);
		newArray[n] = el;
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the specified index.
	 * The result is an array with one element less.
	 * If the array is {@code null}, it is handled like an empty array.
	 * @param array The array. May be {@code null}.
	 * @param index The index that should be removed. Must be in range.
	 * @return A new array without the element at the specified index. Not {@code null}.
	 * @throws IndexOutOfBoundsException If {@code index < 0} or {@code index >= array.length}.
	 * Occurs always if the array is empty or {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static boolean[] removeIndex(boolean[] array, int index) {
		if (array == null) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: 0");
		}
		int n = array.length;
		if (index < 0 || index >= n) {
			throw new IndexOutOfBoundsException("index: " + index + ", length: " + n);
		}
		
		boolean[] newArray = new boolean[n-1];
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index+1, newArray, index, n-index-1);
		return newArray;
	}
	
	/**
	 * Copies the array, but skips the first appearance of the specified element.
	 * The result is an array with one element less if the array contains the specified element.
	 * If the array does not contain the specified element, the input array is returned and not copied.
	 * To find out if an element was removed, the length of the input array and the result array can be compared.
	 * If the array is {@code null}, it is handled as an empty array.
	 * @param array The array. May be {@code null}.
	 * @param el The element that should be removed.
	 * @return A new array without the first appearance of the specified element.
	 * Might be {@code null} if the input array is {@code null}.
	 * @since 1.1
	 */
	// !!! TextScript generated !!!
	public static boolean[] remove(boolean[] array, boolean el) {
		int index = indexOf(array, el);
		if (index < 0) {
			return array;
		}
		return removeIndex(array, index);
	}
	
	
	// txs-end-gen addElement
	
	
	/* txs-begin isEqualCT
	##
	final ctTypes = ["int", "long", "byte", "char", "short"];
	for (type in ctTypes) {
		final numType = type == "long" ? "long" : "int";
		##
		/**
		 * Performs a near-constant-time comparison for the specified arrays.
		 * <p>
		 * The calculation time depends mostly on the length of {@code actual}.
		 * In some situations it may depend slightly on the length of {@code expected}.
		 * It does not depend on the contents of both arrays.
		 * @param expected The expected array. Not {@code null}.
		 * @param actual The array that should be tested to match the expected array. Not {@code null}.
		 * @return If both arrays are equal.
		 * @since 1.4
		 *%'/';
		// %cli.txsinfo();
		public static boolean isEqualCT(%type;[] expected, %type;[] actual) {
			int expectedLen = expected.length;
			int actualLen = actual.length;
			
			if (actualLen > 0 && expectedLen == 0) {
				return false;
			}
			
			// Near-constant-time comparison.
			// The length of "expected" might be leaked due to potential time differences (i vs 0, cache-misses, branch-prediction, ...).
			%numType; result = expectedLen ^ actualLen;
			for (int i = 0; i < actualLen; i++) {
				%numType; value1 = expected[i < expectedLen ? i : 0];
				%numType; value2 = actual[i];
				result |= value1 ^ value2;
			}
			return result == 0;
		}
		
		##
	}
	##
	txs-end isEqualCT */
	
	// txs-begin-gen isEqualCT
	/**
	 * Performs a near-constant-time comparison for the specified arrays.
	 * <p>
	 * The calculation time depends mostly on the length of {@code actual}.
	 * In some situations it may depend slightly on the length of {@code expected}.
	 * It does not depend on the contents of both arrays.
	 * @param expected The expected array. Not {@code null}.
	 * @param actual The array that should be tested to match the expected array. Not {@code null}.
	 * @return If both arrays are equal.
	 * @since 1.4
	 */
	// !!! TextScript generated !!!
	public static boolean isEqualCT(int[] expected, int[] actual) {
		int expectedLen = expected.length;
		int actualLen = actual.length;
		
		if (actualLen > 0 && expectedLen == 0) {
			return false;
		}
		
		// Near-constant-time comparison.
		// The length of "expected" might be leaked due to potential time differences (i vs 0, cache-misses, branch-prediction, ...).
		int result = expectedLen ^ actualLen;
		for (int i = 0; i < actualLen; i++) {
			int value1 = expected[i < expectedLen ? i : 0];
			int value2 = actual[i];
			result |= value1 ^ value2;
		}
		return result == 0;
	}
	
	/**
	 * Performs a near-constant-time comparison for the specified arrays.
	 * <p>
	 * The calculation time depends mostly on the length of {@code actual}.
	 * In some situations it may depend slightly on the length of {@code expected}.
	 * It does not depend on the contents of both arrays.
	 * @param expected The expected array. Not {@code null}.
	 * @param actual The array that should be tested to match the expected array. Not {@code null}.
	 * @return If both arrays are equal.
	 * @since 1.4
	 */
	// !!! TextScript generated !!!
	public static boolean isEqualCT(long[] expected, long[] actual) {
		int expectedLen = expected.length;
		int actualLen = actual.length;
		
		if (actualLen > 0 && expectedLen == 0) {
			return false;
		}
		
		// Near-constant-time comparison.
		// The length of "expected" might be leaked due to potential time differences (i vs 0, cache-misses, branch-prediction, ...).
		long result = expectedLen ^ actualLen;
		for (int i = 0; i < actualLen; i++) {
			long value1 = expected[i < expectedLen ? i : 0];
			long value2 = actual[i];
			result |= value1 ^ value2;
		}
		return result == 0;
	}
	
	/**
	 * Performs a near-constant-time comparison for the specified arrays.
	 * <p>
	 * The calculation time depends mostly on the length of {@code actual}.
	 * In some situations it may depend slightly on the length of {@code expected}.
	 * It does not depend on the contents of both arrays.
	 * @param expected The expected array. Not {@code null}.
	 * @param actual The array that should be tested to match the expected array. Not {@code null}.
	 * @return If both arrays are equal.
	 * @since 1.4
	 */
	// !!! TextScript generated !!!
	public static boolean isEqualCT(byte[] expected, byte[] actual) {
		int expectedLen = expected.length;
		int actualLen = actual.length;
		
		if (actualLen > 0 && expectedLen == 0) {
			return false;
		}
		
		// Near-constant-time comparison.
		// The length of "expected" might be leaked due to potential time differences (i vs 0, cache-misses, branch-prediction, ...).
		int result = expectedLen ^ actualLen;
		for (int i = 0; i < actualLen; i++) {
			int value1 = expected[i < expectedLen ? i : 0];
			int value2 = actual[i];
			result |= value1 ^ value2;
		}
		return result == 0;
	}
	
	/**
	 * Performs a near-constant-time comparison for the specified arrays.
	 * <p>
	 * The calculation time depends mostly on the length of {@code actual}.
	 * In some situations it may depend slightly on the length of {@code expected}.
	 * It does not depend on the contents of both arrays.
	 * @param expected The expected array. Not {@code null}.
	 * @param actual The array that should be tested to match the expected array. Not {@code null}.
	 * @return If both arrays are equal.
	 * @since 1.4
	 */
	// !!! TextScript generated !!!
	public static boolean isEqualCT(char[] expected, char[] actual) {
		int expectedLen = expected.length;
		int actualLen = actual.length;
		
		if (actualLen > 0 && expectedLen == 0) {
			return false;
		}
		
		// Near-constant-time comparison.
		// The length of "expected" might be leaked due to potential time differences (i vs 0, cache-misses, branch-prediction, ...).
		int result = expectedLen ^ actualLen;
		for (int i = 0; i < actualLen; i++) {
			int value1 = expected[i < expectedLen ? i : 0];
			int value2 = actual[i];
			result |= value1 ^ value2;
		}
		return result == 0;
	}
	
	/**
	 * Performs a near-constant-time comparison for the specified arrays.
	 * <p>
	 * The calculation time depends mostly on the length of {@code actual}.
	 * In some situations it may depend slightly on the length of {@code expected}.
	 * It does not depend on the contents of both arrays.
	 * @param expected The expected array. Not {@code null}.
	 * @param actual The array that should be tested to match the expected array. Not {@code null}.
	 * @return If both arrays are equal.
	 * @since 1.4
	 */
	// !!! TextScript generated !!!
	public static boolean isEqualCT(short[] expected, short[] actual) {
		int expectedLen = expected.length;
		int actualLen = actual.length;
		
		if (actualLen > 0 && expectedLen == 0) {
			return false;
		}
		
		// Near-constant-time comparison.
		// The length of "expected" might be leaked due to potential time differences (i vs 0, cache-misses, branch-prediction, ...).
		int result = expectedLen ^ actualLen;
		for (int i = 0; i < actualLen; i++) {
			int value1 = expected[i < expectedLen ? i : 0];
			int value2 = actual[i];
			result |= value1 ^ value2;
		}
		return result == 0;
	}
	
	// txs-end-gen isEqualCT
}
