package de.tomatengames.util.function;

/**
 * A function that accepts 1 generic input parameter and returns
 * a {@code byte}.
 * 
 * @param <A> An input parameter type
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface Ref1ToByteFunction<A> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @return The result.
	 */
	public byte apply(A a);
	
}