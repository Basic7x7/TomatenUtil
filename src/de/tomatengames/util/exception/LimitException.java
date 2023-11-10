package de.tomatengames.util.exception;

/**
 * A {@link RuntimeException} that indicates that a limit has been exceeded.
 * 
 * @author Basic7x7
 * @version 2023-09-17
 * @since 1.4
 */
public class LimitException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new {@link LimitException}.
	 */
	public LimitException() {
		super();
	}
	
	/**
	 * Creates a new {@link LimitException}.
	 * @param message The detail message.
	 */
	public LimitException(String message) {
		super(message);
	}
	
	/**
	 * Creates a new {@link LimitException}.
	 * @param cause The cause. May be {@code null}.
	 */
	public LimitException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Creates a new {@link LimitException}.
	 * @param message The detail message.
	 * @param cause The cause. May be {@code null}.
	 */
	public LimitException(String message, Throwable cause) {
		super(message, cause);
	}
}
