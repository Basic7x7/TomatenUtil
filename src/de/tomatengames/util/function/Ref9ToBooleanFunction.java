package de.tomatengames.util.function;

/**
 * A function that accepts 9 generic input parameters and returns
 * a {@code boolean}.
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
public interface Ref9ToBooleanFunction<A, B, C, D, E, F, G, H, I> {
	
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
	public boolean apply(A a, B b, C c, D d, E e, F f, G g, H h, I i);
	
	
	
	/**
	 * Returns a Ref9ToBooleanFunction that represents {@code !this.apply(...)}.
	 * @return The logical negation of this function.
	 */
	public default Ref9ToBooleanFunction<A, B, C, D, E, F, G, H, I> negate() {
		return (a, b, c, d, e, f, g, h, i) -> !this.apply(a, b, c, d, e, f, g, h, i);
	}
	
	/**
	 * Returns a Ref9ToBooleanFunction that represents {@code !func.apply(...)}.
	 * @param <A> A function parameter type
	 * @param <B> A function parameter type
	 * @param <C> A function parameter type
	 * @param <D> A function parameter type
	 * @param <E> A function parameter type
	 * @param <F> A function parameter type
	 * @param <G> A function parameter type
	 * @param <H> A function parameter type
	 * @param <I> A function parameter type
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static <A, B, C, D, E, F, G, H, I> Ref9ToBooleanFunction<A, B, C, D, E, F, G, H, I> not(Ref9ToBooleanFunction<A, B, C, D, E, F, G, H, I> func) {
		return func.negate();
	}
	
}