package de.tomatengames.util.data;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import de.tomatengames.util.StringUtil;
import de.tomatengames.util.map.OrderedHashMap;

public class OrderedHashSet<E> implements Collection<E> {
	
	private final OrderedHashMap<E, Object> map;
	
	public OrderedHashSet() {
		this.map = new OrderedHashMap<>();
	}
	public OrderedHashSet(Collection<? extends E> elements) {
		this();
		addAll(elements);
	}
	
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
	public E getFirst() throws NoSuchElementException {
		return this.map.getFirst().getKey();
	}
	public E getLast() throws NoSuchElementException {
		return this.map.getLast().getKey();
	}
	public E removeFirst() throws NoSuchElementException {
		return this.map.removeFirst().getKey();
	}
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
