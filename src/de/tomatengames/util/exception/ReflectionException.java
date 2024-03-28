package de.tomatengames.util.exception;

public class ReflectionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new {@link ReflectionException}.
	 */
	public ReflectionException() {
		super();
	}
	
	/**
	 * Creates a new {@link ReflectionException}.
	 * @param message The detail message.
	 */
	public ReflectionException(String message) {
		super(message);
	}
	
	/**
	 * Creates a new {@link ReflectionException}.
	 * @param cause The cause.
	 */
	public ReflectionException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Creates a new {@link ReflectionException}.
	 * @param message The detail message.
	 * @param cause The cause.
	 */
	public ReflectionException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
