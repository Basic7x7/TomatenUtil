package de.tomatengames.util.pool;

import java.util.function.Supplier;

public class LinkedPool<E> implements Pool<E> {
	
	private final Supplier<E> factory;
	private final long capacity;
	private long size;
	private long intervalMinSize;
	private LinkedPooled poolTop;
	
	public LinkedPool(Supplier<E> factory) {
		this(factory, Long.MAX_VALUE);
	}
	public LinkedPool(Supplier<E> factory, long capacity) {
		this.factory = factory;
		this.capacity = capacity;
		this.size = 0;
		this.intervalMinSize = 0;
		this.poolTop = null;
	}
	
	public long capacity() {
		return capacity;
	}
	public long size() {
		return size;
	}
	
	public synchronized void clean() {
		reduce(this.size - this.intervalMinSize);
		this.intervalMinSize = this.size;
	}
	
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
