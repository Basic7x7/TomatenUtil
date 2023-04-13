// !!! TextScript generated !!!
package de.tomatengames.util.linked;

/**
 * Represents an {@code Object} value that can be modified.
 * The value may be {@code null}.
 * 
 * @param T The type of the value.
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
public final class Linked<T> {
	private T value;
	
	/**
	 * Creates a new {@link Linked}.
	 * @param initialValue The initial value of this object.
	 */
	public Linked(T initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Sets the value of this object.
	 * @param value The new value.
	 */
	public final void set(T value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this object.
	 * @return The value.
	 */
	public final T get() {
		return this.value;
	}
}