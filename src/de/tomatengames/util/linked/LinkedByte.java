package de.tomatengames.util.linked;

/**
 * Represents a {@code byte} value that can be modified.
 *
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
// !!! TextScript generated !!!
public final class LinkedByte {
	private byte value;
	
	/**
	 * Creates a new {@link LinkedByte}.
	 * @param initialValue The initial value of this object.
	 */
	public LinkedByte(byte initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Sets the value of this object.
	 * @param value The new value.
	 */
	public final void set(byte value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this object.
	 * @return The value.
	 */
	public final byte get() {
		return this.value;
	}
}