package de.tomatengames.util.linked;

/**
 * Represents a {@code long} value that can be modified.
 *
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
// !!! TextScript generated !!!
public final class LinkedLong {
	private long value;
	
	/**
	 * Creates a new {@link LinkedLong}.
	 * @param initialValue The initial value of this object.
	 */
	public LinkedLong(long initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Sets the value of this object.
	 * @param value The new value.
	 */
	public final void set(long value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this object.
	 * @return The value.
	 */
	public final long get() {
		return this.value;
	}
}