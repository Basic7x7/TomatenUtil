package de.tomatengames.util.linked;

/**
 * Represents an {@code int} value that can be modified.
 *
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
// !!! TextScript generated !!!
public final class LinkedInt {
	private int value;
	
	/**
	 * Creates a new {@link LinkedInt}.
	 * @param initialValue The initial value of this object.
	 */
	public LinkedInt(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Sets the value of this object.
	 * @param value The new value.
	 */
	public final void set(int value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this object.
	 * @return The value.
	 */
	public final int get() {
		return this.value;
	}
}