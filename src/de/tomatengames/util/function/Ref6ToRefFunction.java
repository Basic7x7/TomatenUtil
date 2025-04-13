package de.tomatengames.util.function;

/**
 * A function that accepts 6 generic input parameters and returns
 * an {@code Object}.
 * 
 * @param <A> An input parameter type
 * @param <B> An input parameter type
 * @param <C> An input parameter type
 * @param <D> An input parameter type
 * @param <E> An input parameter type
 * @param <F> An input parameter type
 * @param <T> The return type
 * 
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface Ref6ToRefFunction<T, A, B, C, D, E, F> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @param c An argument.
	 * @param d An argument.
	 * @param e An argument.
	 * @param f An argument.
	 * @return The result.
	 */
	public T apply(A a, B b, C c, D d, E e, F f);
	
}