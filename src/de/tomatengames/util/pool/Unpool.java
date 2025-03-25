package de.tomatengames.util.pool;

import java.util.function.Supplier;

/**
 * The {@code Unpool} class implements the {@link Pool} interface but does not
 * actually pool objects. Instead, it creates new instances of the specified
 * type each time an object is claimed. Freeing its {@link Pooled} objects does
 * nothing because there are no pooled objects to manage.
 *
 * @param <E> The type of object provided by this Unpool.
 *
 * @version 2025-03-26 created
 * @since 1.8
 */
public class Unpool<E> implements Pool<E> {
	
	private final Supplier<E> factory;
	
	/**
	 * Constructs a new {@code Unpool} with the specified supplier for creating
	 * objects.
	 *
	 * @param factory A supplier function that provides instances of type {@code E}.
	 */
	public Unpool(Supplier<E> factory) {
		this.factory = factory;
	}
	
	/**
	 * Claims a new instance of type {@code E} by using the underlying supplier.
	 * <p>
	 * The claimed object is not pooled and will be created anew each time. The
	 * {@link Pooled#free()} method of the returned {@link Pooled} instance does
	 * nothing.
	 * </p>
	 *
	 * @return A {@link Pooled} instance wrapping the newly created object.
	 */
	@Override
	public Pooled<E> claim() {
		return new Unpooled<>(this.factory.get());
	}
	
	private static class Unpooled<E> implements Pooled<E> {
		private final E element;
		
		public Unpooled(E element) {
			this.element = element;
		}
		
		@Override
		public E get() {
			return element;
		}
		
		@Override
		public void free() {
		}
	}
	
}
