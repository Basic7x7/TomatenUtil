package de.tomatengames.util.pool;

import java.util.function.Supplier;

/**
 * The {@code LinkedPool} class implements the {@link Pool} interface and
 * manages a pool of objects by linking them together. It allows to specify a
 * capacity for the pool and provides cleaning utilities.
 *
 * @param <E> The type of object managed by this pool.
 *
 * @version 2025-03-26 created
 * @since 1.8
 */
public class LinkedPool<E> implements Pool<E> {
	
	private final Supplier<E> factory;
	private final long capacity;
	private long size;
	private long intervalMinSize;
	private LinkedPooled poolTop;
	
	/**
	 * Constructs a new {@code LinkedPool} with the specified supplier for creating
	 * objects and an unlimited capacity.
	 *
	 * @param factory A supplier function that provides instances of type {@code E}.
	 */
	public LinkedPool(Supplier<E> factory) {
		this(factory, Long.MAX_VALUE);
	}
	
	/**
	 * Constructs a new {@code LinkedPool} with the specified supplier for creating
	 * objects and a given capacity.
	 *
	 * @param factory  A supplier function that provides instances of type
	 *                 {@code E}.
	 * @param capacity The maximum number of objects that will be stored in the pool.
	 */
	public LinkedPool(Supplier<E> factory, long capacity) {
		this.factory = factory;
		this.capacity = capacity;
		this.size = 0;
		this.intervalMinSize = 0;
		this.poolTop = null;
	}
	
	/**
	 * Returns the maximum capacity of the pool.
	 * The capacity limits the amount of stored objects in the pool.
	 *
	 * @return The maximum number of objects that will be stored in the pool.
	 */
	public long capacity() {
		return capacity;
	}
	
	/**
	 * Returns the amount of stored objects currently in the pool.
	 *
	 * @return The current number of objects in the pool.
	 */
	public long size() {
		return size;
	}
	
	/**
	 * Cleans up the pool by reducing its size to the minimum size recorded since
	 * the last clean operation.
	 */
	public synchronized void clean() {
		reduce(this.size - this.intervalMinSize);
		this.intervalMinSize = this.size;
	}
	
	/**
	 * Reduces the size of the pool to a specified new size. If the given new size
	 * is larger than the current amount of stored objects in the pool, nothing
	 * happens.
	 *
	 * @param newSize The desired new size of the pool.
	 * @throws IllegalArgumentException If the new size is negative.
	 */
	public synchronized void reduce(long newSize) {
		if (newSize < 0)
			throw new IllegalArgumentException("Cannot reduce to negative size " + newSize);
		while (this.size > newSize)
			take();
	}
	
	@Override
	public Pooled<E> claim() {
		Pooled<E> pooled = take();
		return pooled == null ? new LinkedPooled(this.factory.get()) : pooled;
	}
	private synchronized Pooled<E> take() {
		if (this.size <= 0)
			return null;
		this.size--;
		if (this.intervalMinSize > this.size)
			this.intervalMinSize = this.size;
		LinkedPooled pooled = this.poolTop;
		this.poolTop = pooled.next;
		pooled.next = pooled; // claimed / not in pool
		return pooled;
	}
	private synchronized void free(LinkedPooled pooled) {
		if (pooled.next != pooled)
			throw new IllegalStateException("Pooled double free detected, use either free or close, not both");
		if (this.size >= this.capacity)
			return;
		pooled.next = this.poolTop;
		this.poolTop = pooled;
		this.size++;
	}
	
	private class LinkedPooled implements Pooled<E> {
		private final E element;
		
		// this: claimed / not in pool, null: pool tail, other: next in pool
		private LinkedPooled next;
		
		private LinkedPooled(E element) {
			this.element = element;
			this.next = this;
		}
		
		@Override
		public E get() {
			return element;
		}
		@Override
		public void free() {
			LinkedPool.this.free(this);
		}
	}
	
}
