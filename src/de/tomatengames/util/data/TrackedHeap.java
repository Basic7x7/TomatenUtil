package de.tomatengames.util.data;

import java.util.Arrays;

public class TrackedHeap<E extends TrackedHeap.Element> {
	private static final int MAX_SIZE = Integer.MIN_VALUE - 50;
	
	private Element[] array;
	private int size;
	
	public TrackedHeap() {
		this.array = new Element[10];
		this.size = 0;
	}
	
	private void grow(int requiredIndex) {
		int n = this.array.length;
		while (n <= requiredIndex) {
			n <<= 2;
			if (n > MAX_SIZE || n < 0) {
				n = MAX_SIZE;
				if (n <= requiredIndex) {
					throw new OutOfMemoryError("TrackedHeap size exceeds limit");
				}
			}
		}
		this.array = Arrays.copyOf(this.array, n);
	}
	
	private boolean heapifyUp(int index) {
		// TODO
		return false;
	}
	
	private boolean heapifyDown(int index) {
		// TODO
		return false;
	}
	
	
	public void insert(E element) {
		// TODO
	}
	
	public boolean remove(E element) {
		// TODO
		return false;
	}
	
	public E getFirst() {
		if (this.size <= 0) {
			return null;
		}
		@SuppressWarnings("unchecked")
		E e = (E) this.array[0];
		return e;
	}
	
	public E removeFirst() {
		if (this.size <= 0) {
			return null;
		}
		int n = --this.size;
		
		// Remove the first element.
		Element first = this.array[0];
		this.array[0] = null;
		first.heap = null;
		first.index = -1;
		
		// If other elements exist in the heap, place the last element to index 0
		// and move it to its position with heapifyDown.
		if (n > 0) {
			Element last = this.array[n];
			this.array[n] = null;
			this.array[0] = last;
			last.index = 0;
			this.heapifyDown(0);
		}
		
		@SuppressWarnings("unchecked")
		E firstE = (E) first;
		return firstE;
	}
	
	public void move(E element) {
		// TODO
	}
	
	public void clear() {
		for (int i = 0, n = this.size; i < n; i++) {
			Element e = this.array[i];
			this.array[i] = null;
			e.heap = null;
			e.index = -1;
		}
		this.size = 0;
	}
	
	public boolean isEmpty() {
		return this.size <= 0;
	}
	
	
	public static class Element {
		private TrackedHeap<?> heap;
		private int index;
		
		public Element() {
			this.heap = null;
			this.index = -1;
		}
	}
}
