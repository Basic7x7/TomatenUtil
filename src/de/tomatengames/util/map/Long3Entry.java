package de.tomatengames.util.map;

import java.util.ConcurrentModificationException;

/**
 * Represents a key-value mapping.
 * This entry type is used by the {@link Long3HashMap}.
 * 
 * @param <V> The type of the value.
 * 
 * @author Basic7x7
 * @version
 * 2023-07-31 created
 * @since 1.3
 */
// !!! TextScript generated !!!
public interface Long3Entry<V> {
	
	/**
	 * Returns the key of this mapping.
	 * @return The key component of this mapping.
	 */
	public long getKey1();
	
	/**
	 * Returns the key of this mapping.
	 * @return The key component of this mapping.
	 */
	public long getKey2();
	
	/**
	 * Returns the key of this mapping.
	 * @return The key component of this mapping.
	 */
	public long getKey3();
	
	
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
