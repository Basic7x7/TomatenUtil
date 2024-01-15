package de.tomatengames.util.function;

/**
 * A function that accepts 1 generic input parameter and returns
 * a {@code double}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface Ref1ToDoubleFunction<A> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @return The result.
	 */
	public double apply(A a);
	
}