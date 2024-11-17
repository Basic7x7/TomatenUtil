package de.tomatengames.util.map;

import java.util.ConcurrentModificationException;

/**
 * Represents a key-value mapping.
 * This entry type is used by the {@link AbstractHashMap}.
 * 
 * @param <V> The type of the value.
 * 
 * @author Basic7x7
 * @version 2024-01-06 created
 * @since 1.5
 */
// !!! TextScript generated !!!
public interface AbstractEntry<K, V> {
	
	/**
	 * Returns the component 'key' of the key of this mapping.
	 * @return The key of this mapping.
	 */
	public K getKey();
	
	
	/**
	 * Returns the value of this mapping.
	 * @return The value. Not {@code null}.
	 */
	public V getValue();
	
	/**
	 * Sets the value of this mapping.
	 * The previous value is replaced.
	 * <p>
	 * This method can be used instead of {@code map.put(key, value)}.
	 * Iterators do not throw a {@link ConcurrentModificationException} if this method is used.
	 * @param value The new value of this mapping. Must not be {@code null}.
	 * @throws IllegalArgumentException If the new value is {@code null}.
	 */
	public void setValue(V value);
}
