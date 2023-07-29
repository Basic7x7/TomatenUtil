/* txs-begin static
##
:inline=$
##
txs-end static */

package de.tomatengames.util;

/**
 * Provides utilities to handle numbers.
 * 
 * @author Basic7x7
 * @version 2023-07-29
 * @since 1.3
 */
public class NumberUtil {
	
/* txs-begin limit
# for (string type in ["int", "long", "double"]) {
	
	/**
	 * Limits the specified value to the range {@code [min, max]}.
	 * <p>
	 * If {@code min <= value <= max}, {@code value} is returned.
	 * If {@code value < min}, {@code min} is returned.
	 * If {@code value > max}, {@code max} is returned.
	 * If {@code min > max}, the returned value is undefined.
	 * @param value The value.
	 * @param min The minimum of the range. Must be less than or equal {@code max}.
	 * @param max The maximum of the range. Must be greater than or equal {@code min}.
	 * @return The value in the range {@code [min, max]}.
	 *$'/';
	// $txsinfo();
	public static $type; limit($type; value, $type; min, $type; max) {
		if (value <= min) {
			return min;
		}
		if (value >= max) {
			return max;
		}
		return value;
	}
# }
txs-end limit */
// txs-begin-gen limit
	
	/**
	 * Limits the specified value to the range {@code [min, max]}.
	 * <p>
	 * If {@code min <= value <= max}, {@code value} is returned.
	 * If {@code value < min}, {@code min} is returned.
	 * If {@code value > max}, {@code max} is returned.
	 * If {@code min > max}, the returned value is undefined.
	 * @param value The value.
	 * @param min The minimum of the range. Must be less than or equal {@code max}.
	 * @param max The maximum of the range. Must be greater than or equal {@code min}.
	 * @return The value in the range {@code [min, max]}.
	 */
	// !!! TextScript generated !!!
	public static int limit(int value, int min, int max) {
		if (value <= min) {
			return min;
		}
		if (value >= max) {
			return max;
		}
		return value;
	}
	
	/**
	 * Limits the specified value to the range {@code [min, max]}.
	 * <p>
	 * If {@code min <= value <= max}, {@code value} is returned.
	 * If {@code value < min}, {@code min} is returned.
	 * If {@code value > max}, {@code max} is returned.
	 * If {@code min > max}, the returned value is undefined.
	 * @param value The value.
	 * @param min The minimum of the range. Must be less than or equal {@code max}.
	 * @param max The maximum of the range. Must be greater than or equal {@code min}.
	 * @return The value in the range {@code [min, max]}.
	 */
	// !!! TextScript generated !!!
	public static long limit(long value, long min, long max) {
		if (value <= min) {
			return min;
		}
		if (value >= max) {
			return max;
		}
		return value;
	}
	
	/**
	 * Limits the specified value to the range {@code [min, max]}.
	 * <p>
	 * If {@code min <= value <= max}, {@code value} is returned.
	 * If {@code value < min}, {@code min} is returned.
	 * If {@code value > max}, {@code max} is returned.
	 * If {@code min > max}, the returned value is undefined.
	 * @param value The value.
	 * @param min The minimum of the range. Must be less than or equal {@code max}.
	 * @param max The maximum of the range. Must be greater than or equal {@code min}.
	 * @return The value in the range {@code [min, max]}.
	 */
	// !!! TextScript generated !!!
	public static double limit(double value, double min, double max) {
		if (value <= min) {
			return min;
		}
		if (value >= max) {
			return max;
		}
		return value;
	}
// txs-end-gen limit

}
