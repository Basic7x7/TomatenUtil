package de.tomatengames.util;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Provides methods to combine {@link Predicate} objects.
 * 
 * @author Basic7x7
 * @version 2023-04-11
 * @since 1.2
 */
public class PredicateUtil {
	
	// Static class
	private PredicateUtil() {
	}
	
	
	/**
	 * Returns the logical negation of the specified {@link Predicate}.
	 * @param <T> The type of the input parameter.
	 * @param pred The predicate that should be negated. Must not be {@code null}.
	 * @return The logical negation of the specified predicate.
	 * @throws NullPointerException If the specified predicate is {@code null}.
	 */
	public static <T> Predicate<T> not(Predicate<T> pred) {
		return pred.negate();
	}
	
	/**
	 * Returns a {@link Predicate} that represents {@code (pred1.test(t) && pred2.test(t))}.
	 * @param <T> The type of the input parameter.
	 * @param pred1 The first predicate. Must not be {@code null}.
	 * @param pred2 The second predicate. Must not be {@code null}.
	 * @return A predicate that combines both predicates with a logical AND.
	 * @throws NullPointerException If a specified predicate is {@code null}.
	 */
	public static <T> Predicate<T> and(Predicate<? super T> pred1, Predicate<? super T> pred2) {
		Objects.requireNonNull(pred1);
		Objects.requireNonNull(pred2);
		return (t) -> pred1.test(t) && pred2.test(t);
	}
	
	/**
	 * Returns a {@link Predicate} that represents {@code (pred1.test(t) || pred2.test(t))}.
	 * @param <T> The type of the input parameter.
	 * @param pred1 The first predicate. Must not be {@code null}.
	 * @param pred2 The second predicate. Must not be {@code null}.
	 * @return A predicate that combines both predicates with a logical OR.
	 * @throws NullPointerException If a specified predicate is {@code null}.
	 */
	public static <T> Predicate<T> or(Predicate<? super T> pred1, Predicate<? super T> pred2) {
		Objects.requireNonNull(pred1);
		Objects.requireNonNull(pred2);
		return (t) -> pred1.test(t) || pred2.test(t);
	}
}
