package de.tomatengames.util.linked;

/**
 * Represents a {@code char} value that can be modified.
 *
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
// !!! TextScript generated !!!
public final class LinkedChar {
	private char value;
	
	/**
	 * Creates a new {@link LinkedChar}.
	 * @param initialValue The initial value of this object.
	 */
	public LinkedChar(char initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Sets the value of this object.
	 * @param value The new value.
	 */
	public final void set(char value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this object.
	 * @return The value.
	 */
	public final char get() {
		return this.value;
	}
}