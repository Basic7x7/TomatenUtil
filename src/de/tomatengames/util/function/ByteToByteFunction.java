package de.tomatengames.util.function;

/**
 * A function that accepts a {@code byte}
 * and returns a {@code byte}.
 * 
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2021-10-21 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface ByteToByteFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public byte apply(byte e);
	
	/**
	 * Returns the {@code byte} identity function.
	 * The identity function always returns the input argument.
	 * @return The identity function.
	 */
	public static ByteToByteFunction identity() {
		return e -> e;
	}
	
}