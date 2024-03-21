package de.tomatengames.util.data;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A heap that allows to efficiently update and remove arbitrary elements.
 * <p>
 * The <i>first</i> element is the smallest element according to the {@link Comparator} specified to the constructor.
 * <p>
 * <b>This implementation is not synchronized.</b>
 * 
 * @param <E> The type of the elements in the heap. Must extend {@link TrackedHeap.Element}.
 * 
 * @author Basic7x7
 * @version 2024-03-21 last modified
 * @version 2024-03-20 created
 * @since 1.5
 */
public class TrackedHeap<E extends TrackedHeap.Element> {
	private static final int MAX_SIZE = Integer.MIN_VALUE - 50;
	
	private final Comparator<E> comparator;
	private Element[] array;
	private int size;
	
	/**
	 * Creates a new and empty {@link TrackedHeap}.
	 * @param comparator The comparator that will be used to compare elements. Not {@code null}.
	 * The smallest element is the first element.
	 */
	public TrackedHeap(Comparator<E> comparator) {
		this.comparator = comparator;
		this.array = new Element[10]; // initial capacity = 10
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
	
	/**
	 * Assumes that {@code thisElement} is at the specified index and tries to move the element up in the heap
	 * until the heap condition is met.
	 * @param index The index of this element in the heap. Must be at least 0 and less than the size of this heap.
	 * If {@code heap[index] != thisElement}, the heap element may be replaced silently.
	 * @param thisElement The element that should be moved. Not {@code null}.
	 * @param forcePlace If {@code true}, {@code thisElement} is always placed into this heap.
	 * It is guaranteed that {@code heap[index]} will be overwritten.
	 * This should only be set to {@code false} if {@code heap[index] == thisElement} is guaranteed.
	 * @return If the element moved.
	 */
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
	
	/**
	 * Assumes that {@code thisElement} is at the specified index and tries to move the element down in the heap
	 * until the heap condition is met.
	 * @param index The index of this element in the heap. Must be at least 0 and less than the size of this heap.
	 * If {@code heap[index] != thisElement}, the heap element may be replaced silently.
	 * @param thisElement The element that should be moved. Not {@code null}.
	 * @param forcePlace If {@code true}, {@code thisElement} is always placed into this heap.
	 * It is guaranteed that {@code heap[index]} will be overwritten.
	 * This should only be set to {@code false} if {@code heap[index] == thisElement} is guaranteed.
	 * @return If the element moved.
	 */
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
	
	/**
	 * Inserts the specified element into this heap.
	 * If the element is already in this heap, nothing happens.
	 * @param element The element that should be inserted. If {@code null}, nothing happens.
	 * @return If the element has been inserted successfully. If {@code false}, nothing happened.
	 * @throws IllegalArgumentException If the element is already in another heap.
	 * @implNote O(log n)
	 */
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
	
	/**
	 * Removes the specified element from this heap.
	 * If the element is not in this heap, nothing happens.
	 * @param element The element that should be removed. If {@code null}, nothing happens.
	 * @return If the element has been removed successfully. If {@code false}, nothing happened.
	 * @implNote O(log n)
	 */
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
	
	/**
	 * Returns the <i>first</i> element of this heap.
	 * The first element is the smallest element according to the {@link Comparator} of the heap.
	 * @return The first element of this heap. If this heap is empty, {@code null} is returned.
	 * @implNote O(1)
	 */
	public E getFirst() {
		if (this.size <= 0) {
			return null;
		}
		@SuppressWarnings("unchecked")
		E e = (E) this.array[0];
		return e;
	}
	
	/**
	 * Returns and removes the <i>first</i> element of this heap.
	 * The first element is the smallest element according to the {@link Comparator} of the heap.
	 * @return The first element of this heap. If this heap is empty, {@code null} is returned and nothing happens.
	 * @implNote O(log n)
	 */
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
	
	/**
	 * Moves the specified element to its position in the heap.
	 * This method should be called after the element changed its value considered by the {@link Comparator}
	 * in order to <i>repair</i> the heap.
	 * <p>
	 * If the element is not present in this heap, nothing happens.
	 * <p>
	 * This method should be preferred over the following semantically equivalent calls.
	 * <pre>
	 * heap.increase(element);
	 * heap.decrease(element);
	 * </pre>
	 * and
	 * <pre>
	 * if (heap.remove(element)) {
	 *     heap.insert(element);
	 * }
	 * </pre>
	 * @param element The element that has been changed and should be moved. If {@code null}, nothing happens.
	 * @implNote O(log n)
	 */
	public void move(E element) {
		Element e = element;
		if (this.isInvalidElement(e)) {
			return;
		}
		
		if (!this.heapifyUp(e.index, e, false)) {
			this.heapifyDown(e.index, e, false);
		}
	}
	
	// TODO JavaDoc
	public void increase(E element) {
		Element e = element;
		if (this.isInvalidElement(e)) {
			return;
		}
		this.heapifyDown(e.index, e, false);
	}
	
	// TODO JavaDoc
	public void decrease(E element) {
		Element e = element;
		if (this.isInvalidElement(e)) {
			return;
		}
		this.heapifyUp(e.index, e, false);
	}
	
	// TODO JavaDoc
	public void clear() {
		for (int i = 0, n = this.size; i < n; i++) {
			Element e = this.array[i];
			this.array[i] = null;
			e.heap = null;
			e.index = -1;
		}
		this.size = 0;
	}
	
	// TODO JavaDoc
	public boolean isEmpty() {
		return this.size <= 0;
	}
	
	/**
	 * Returns the number of elements in this heap.
	 * @return The number of elements in this heap. Not negative.
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Returns if this heap contains the specified element.
	 * @param element The element that should be checked. If {@code null}, {@code false} is returned.
	 * @return If this heap contains the specified element.
	 * @implNote O(1)
	 */
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
		return element.heap == this;
	}
	
	
	/**
	 * An element of a {@link TrackedHeap}.
	 * This element stores additional information to be located efficiently by the heap.
	 * <p>
	 * An element must not be present in multiple {@link TrackedHeap} at the same time.
	 */
	public static abstract class Element {
		private TrackedHeap<?> heap;
		private int index;
		
		/**
		 * Creates a new {@link TrackedHeap.Element}.
		 */
		public Element() {
			this.heap = null;
			this.index = -1;
		}
	}
}
