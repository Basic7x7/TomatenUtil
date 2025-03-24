package de.tomatengames.util.pool;

import java.util.function.Supplier;

public class Unpool<E> implements Pool<E> {
	
	private final Supplier<E> factory;
	
	public Unpool(Supplier<E> factory) {
		this.factory = factory;
	}
	
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
