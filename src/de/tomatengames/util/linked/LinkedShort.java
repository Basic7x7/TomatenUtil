package de.tomatengames.util.linked;

/**
 * Represents a {@code short} value that can be modified.
 *
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
// !!! TextScript generated !!!
public final class LinkedShort {
	private short value;
	
	/**
	 * Creates a new {@link LinkedShort}.
	 * @param initialValue The initial value of this object.
	 */
	public LinkedShort(short initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Sets the value of this object.
	 * @param value The new value.
	 */
	public final void set(short value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this object.
	 * @return The value.
	 */
	public final short get() {
		return this.value;
	}
}