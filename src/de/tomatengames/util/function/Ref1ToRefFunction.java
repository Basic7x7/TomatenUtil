package de.tomatengames.util.function;

/**
 * A function that accepts 1 generic input parameter and returns
 * an {@code Object}.
 * 
 * @param <A> An input parameter type
 * @param <T> The return type
 * 
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface Ref1ToRefFunction<T, A> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @return The result.
	 */
	public T apply(A a);
	
}