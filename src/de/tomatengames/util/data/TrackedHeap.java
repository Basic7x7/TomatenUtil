package de.tomatengames.util.data;

import java.util.Arrays;
import java.util.Comparator;

public class TrackedHeap<E extends TrackedHeap.Element> {
	private static final int MAX_SIZE = Integer.MIN_VALUE - 50;
	
	private final Comparator<E> comparator;
	private Element[] array;
	private int size;
	
	public TrackedHeap(Comparator<E> comparator) {
		this.comparator = comparator;
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
	
	@SuppressWarnings("unchecked")
	private boolean heapifyUp(int index, final Element thisElement, boolean forcePlace) {
		final int originalIndex = index;
		
		while (true) {
			if (index <= 0) { // If thisElement is the root
				break;
			}
			
			int parentIndex = (index - 1) >>> 1;
			Element parentElement = this.array[parentIndex];
			
			// If parent < thisElement, the heap condition is met.
			if (this.comparator.compare((E) parentElement, (E) thisElement) <= 0) {
				break;
			}
			
			// If thisElement >= parent, move the parent to the position of thisElement.
			this.array[index] = parentElement;
			parentElement.index = index;
			// thisElement should be the new parent.
			index = parentIndex;
		}
		
		if (forcePlace || index != originalIndex) {
			this.array[index] = thisElement;
			thisElement.index = index;
		}
		return index != originalIndex;
	}
	
	@SuppressWarnings("unchecked")
	private boolean heapifyDown(int index, final Element thisElement, boolean forcePlace) {
		final int originalIndex = index;
		int size = this.size;
		while (true) {
			// Detect the minimum element and index of [thisElement, left, right].
			int leftIndex = 2*index + 1;
			int rightIndex = 2*index + 2;
			int minIndex = index;
			Element minElement = thisElement;
			Element temp = null;
			if (leftIndex < size && this.comparator.compare((E) (temp = this.array[leftIndex]), (E) thisElement) < 0) {
				minIndex = leftIndex;
				minElement = temp;
			}
			if (rightIndex < size && this.comparator.compare((E) (temp = this.array[rightIndex]), (E) minElement) < 0) {
				minIndex = rightIndex;
				minElement = temp;
			}
			
			// If thisElement < left, right, the heap condition is met.
			// Place thisElement to its new index if necessary.
			if (minIndex == index) {
				if (forcePlace || index != originalIndex) {
					this.array[index] = thisElement;
					thisElement.index = index;
				}
				return index != originalIndex;
			}
			
			// Move the minimum element (left or right) to the parent position (of thisElement).
			this.array[index] = minElement;
			minElement.index = index;
			// thisElement should be placed into the spot of the minimum element (left or right).
			index = minIndex;
		}
	}
	
	
	public boolean insert(E element) {
		if (element == null) {
			return false;
		}
		
		Element e = element;
		// Check that the element is not inside any heap.
		if (e.heap != null) {
			if (e.heap == this) {
				return false; // If this heap already contains the element.
			}
			throw new IllegalArgumentException("Element belongs to another heap");
		}
		
		// Grow the array if necessary.
		int index = this.size++;
		if (index >= this.array.length) {
			this.grow(index);
		}
		
		// Insert the element.
		this.heapifyUp(index, element, true);
		return true;
	}
	
	public boolean remove(E element) {
		Element e = element;
		if (this.isInvalidElement(e)) {
			return false;
		}
		
		// Should not happen, because e.heap == this.
		if (this.size <= 0) {
			return false;
		}
		
		int n = --this.size;
		int index = e.index;
		e.heap = null;
		e.index = -1;
		
		// Place the last element to the index of the removed element
		// and move it to a valid position.
		// If n == 0, the heap is empty now.
		if (n > 0 && index < n) {
			Element last = this.array[n];
			if (!this.heapifyUp(index, last, true)) {
				this.heapifyDown(index, last, true);
			}
		}
		this.array[n] = null;
		return true;
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
		first.heap = null;
		first.index = -1;
		
		// If other elements exist in the heap, place the last element to index 0
		// and move it to its position with heapifyDown.
		if (n > 0) {
			this.heapifyDown(0, this.array[n], true);
		}
		this.array[n] = null;
		
		@SuppressWarnings("unchecked")
		E firstE = (E) first;
		return firstE;
	}
	
	public void move(E element) {
		Element e = element;
		if (this.isInvalidElement(e)) {
			return;
		}
		
		if (!this.heapifyUp(e.index, e, false)) {
			this.heapifyDown(e.index, e, false);
		}
	}
	
	public void increase(E element) {
		Element e = element;
		if (this.isInvalidElement(e)) {
			return;
		}
		this.heapifyDown(e.index, e, false);
	}
	
	public void decrease(E element) {
		Element e = element;
		if (this.isInvalidElement(e)) {
			return;
		}
		this.heapifyUp(e.index, e, false);
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
	
	public int size() {
		return this.size;
	}
	
	public boolean contains(E element) {
		if (element == null) {
			return false;
		}
		Element e = element;
		return e.heap == this;
	}
	
	private boolean isInvalidElement(Element element) {
		if (element == null) {
			return false;
		}
		
		Element e = element;
		// Check that the element belongs to this heap.
		if (e.heap != this) {
			if (e.heap == null) {
				return false; // If the element is not in the heap.
			}
			throw new IllegalArgumentException("Element belongs to another heap");
		}
		
		return true;
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
