package de.tomatengames.util.linked;

/**
 * Represents a {@code float} value that can be modified.
 *
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
// !!! TextScript generated !!!
public final class LinkedFloat {
	private float value;
	
	/**
	 * Creates a new {@link LinkedFloat}.
	 * @param initialValue The initial value of this object.
	 */
	public LinkedFloat(float initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Sets the value of this object.
	 * @param value The new value.
	 */
	public final void set(float value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this object.
	 * @return The value.
	 */
	public final float get() {
		return this.value;
	}
}