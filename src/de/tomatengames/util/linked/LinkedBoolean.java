package de.tomatengames.util.linked;

/**
 * Represents a {@code boolean} value that can be modified.
 *
 * 
 * @author Basic7x7
 * @version 2023-04-13
 * @since 1.2
 */
// !!! TextScript generated !!!
public final class LinkedBoolean {
	private boolean value;
	
	/**
	 * Creates a new {@link LinkedBoolean}.
	 * @param initialValue The initial value of this object.
	 */
	public LinkedBoolean(boolean initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Sets the value of this object.
	 * @param value The new value.
	 */
	public final void set(boolean value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this object.
	 * @return The value.
	 */
	public final boolean get() {
		return this.value;
	}
}