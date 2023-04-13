// !!! TextScript generated !!!
package de.tomatengames.util.linked;

/**
 * Represents a {@code double} value that can be modified.
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
public final class LinkedDouble {
	private double value;
	
	/**
	 * Creates a new {@link LinkedDouble}.
	 * @param initialValue The initial value of this object.
	 */
	public LinkedDouble(double initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Sets the value of this object.
	 * @param value The new value.
	 */
	public final void set(double value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this object.
	 * @return The value.
	 */
	public final double get() {
		return this.value;
	}
}