package de.tomatengames.util.function;

/**
 * A function that accepts 9 generic input parameters and returns
 * an {@code int}.
 * 
 * @param <A> An input parameter type
 * @param <B> An input parameter type
 * @param <C> An input parameter type
 * @param <D> An input parameter type
 * @param <E> An input parameter type
 * @param <F> An input parameter type
 * @param <G> An input parameter type
 * @param <H> An input parameter type
 * @param <I> An input parameter type
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface Ref9ToIntFunction<A, B, C, D, E, F, G, H, I> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @param c An argument.
	 * @param d An argument.
	 * @param e An argument.
	 * @param f An argument.
	 * @param g An argument.
	 * @param h An argument.
	 * @param i An argument.
	 * @return The result.
	 */
	public int apply(A a, B b, C c, D d, E e, F f, G g, H h, I i);
	
}