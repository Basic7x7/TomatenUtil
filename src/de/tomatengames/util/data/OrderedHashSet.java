package de.tomatengames.util.data;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

import de.tomatengames.util.StringUtil;
import de.tomatengames.util.map.OrderedHashMap;

/**
 * A set that maintains the insertion order of its elements. This class is a
 * wrapper around an {@link OrderedHashMap} where each element is used as a key.
 *
 * <p>
 * New elements are added to the end of the ordered set.
 * Adding an element that is already present does nothing, its position is kept.
 * </p>
 *
 * @param <E> The type of elements in this set.
 *
 * @author LukasE7x7
 * @version 2025-02-19 created
 * @since 1.8
 */
public class OrderedHashSet<E> implements Set<E> {
	
	/**
	 * The underlying map that stores the elements of this set. Each element is used
	 * as a key, and the value associated with each key is ignored.
	 */
	private final OrderedHashMap<E, Object> map;
	
	/**
	 * Creates an empty ordered hash set.
	 */
	public OrderedHashSet() {
		this.map = new OrderedHashMap<>();
	}
	
	/**
	 * Creates an ordered hash set containing all elements from the specified
	 * collection. The elements are added in the order they appear in the
	 * collection.
	 *
	 * @param elements The collection of elements to add to this set.
	 */
	public OrderedHashSet(Collection<? extends E> elements) {
		this();
		addAll(elements);
	}
	
	/**
	 * Returns the number of elements in this set as a long value. This method is
	 * useful when dealing with large sets that may exceed the maximum size
	 * representable by an integer.
	 *
	 * @return The number of elements in this set.
	 */
	public long sizeLong() {
		return this.map.size();
	}
	
	@Override
	public int size() {
		long size = sizeLong();
		if (size >= Integer.MAX_VALUE)
			return Integer.MAX_VALUE;
		return (int)size;
	}
	@Override
	public boolean add(E element) {
		return this.map.put(element, this) == null;
	}
	@Override
	public boolean contains(Object element) {
		return this.map.containsKey(element);
	}
	@Override
	public boolean remove(Object element) {
		return this.map.remove(element) != null;
	}
	@Override
	public void clear() {
		this.map.clear();
	}
	@Override
	public boolean isEmpty() {
		return this.map.isEmpty();
	}
	
	/**
	 * Returns the first element added to this set, or throws a
	 * {@link NoSuchElementException} if this set is empty.
	 *
	 * @return The first element added to this set.
	 * @throws NoSuchElementException If this set is empty.
	 */
	public E getFirst() throws NoSuchElementException {
		return this.map.getFirst().getKey();
	}
	
	/**
	 * Returns the last element added to this set, or throws a
	 * {@link NoSuchElementException} if this set is empty.
	 *
	 * @return The last element added to this set.
	 * @throws NoSuchElementException If this set is empty.
	 */
	public E getLast() throws NoSuchElementException {
		return this.map.getLast().getKey();
	}
	
	/**
	 * Removes the first element added to this set, or throws a
	 * {@link NoSuchElementException} if this set is empty. The removed element is
	 * returned as the result of this method.
	 *
	 * @return The first element added to this set.
	 * @throws NoSuchElementException If this set is empty.
	 */
	public E removeFirst() throws NoSuchElementException {
		return this.map.removeFirst().getKey();
	}
	
	/**
	 * Removes the last element added to this set, or throws a
	 * {@link NoSuchElementException} if this set is empty. The removed element is
	 * returned as the result of this method.
	 *
	 * @return The last element added to this set.
	 * @throws NoSuchElementException If this set is empty.
	 */
	public E removeLast() throws NoSuchElementException {
		return this.map.removeLast().getKey();
	}
	
	@Override
	public Iterator<E> iterator() {
		final Iterator<Entry<E, Object>> mapIt = this.map.iterator();
		return new Iterator<E>() {
			@Override
			public boolean hasNext() {
				return mapIt.hasNext();
			}
			@Override
			public E next() {
				return mapIt.next().getKey();
			}
			@Override
			public void remove() {
				mapIt.remove();
			}
		};
	}
	
	/**
	 * Matching the {@link #equals(Object)} method of OrderedHashSet,
	 * the hash code is similar to those of {@link java.util.List}s
	 * as the order of the stored elements is relevant.
	 *
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		int hash = 1;
		for (E element : this)
			hash = 31 * hash + Objects.hashCode(element);
		return hash;
	}
	
	/**
	 * Returns whether this OrderedHashSet is equal to the given other object.
	 * Another object is considered equal iff it is also an OrderedHashSet
	 * and contains equal elements at equal positions, similar to {@link java.util.List#equals(Object)}s.
	 *
	 * @param obj the other object
	 * @return whether this OrderedHashSet is equal to the given object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderedHashSet<?> other = (OrderedHashSet<?>)obj;
		if (this.sizeLong() != other.sizeLong())
			return false;
		Iterator<?> thisIt = this.iterator();
		Iterator<?> otherIt = other.iterator();
		while (true) {
			if (!thisIt.hasNext())
				return !otherIt.hasNext();
			if (!otherIt.hasNext())
				return false;
			if (!Objects.equals(thisIt.next(), otherIt.next()))
				return false;
		}
	}
	
	@Override
	public String toString() {
		return "[" + StringUtil.join(this, String::valueOf, ", ") + "]";
	}
	
	@Override
	public boolean addAll(Collection<? extends E> elements) {
		boolean changed = false;
		for (E element : elements)
			changed |= add(element);
		return changed;
	}
	@Override
	public boolean containsAll(Collection<?> elements) {
		for (Object element : elements) {
			if (!contains(element))
				return false;
		}
		return true;
	}
	@Override
	public boolean removeAll(Collection<?> elements) {
		boolean changed = false;
		for (Object element : elements)
			changed |= remove(element);
		return changed;
	}
	@Override
	public boolean retainAll(Collection<?> elements) {
		boolean changed = false;
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			if (!elements.contains(it.next())) {
				it.remove();
				changed = true;
			}
		}
		return changed;
	}
	
	@Override
	public Object[] toArray() {
		return toArray(new Object[size()]);
	}
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		int n = this.size();
		if (a.length < n)
			a = (T[])Array.newInstance(a.getClass().getComponentType(), n);
		int i = 0;
		Iterator<E> it = iterator();
		while (it.hasNext())
			a[i++] = (T)it.next();
		if (a.length > n)
			a[n] = null;
		return a;
	}
	
}
