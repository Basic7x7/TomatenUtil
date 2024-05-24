package de.tomatengames.util.exception;

import java.io.IOException;

/**
 * An {@link IOException} that indicates that a character could not be decoded.
 * 
 * @author Basic7x7
 * @version 2024-05-22
 * @since 1.6
 */
public class CharacterDecodeException extends IOException {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates a new {@link CharacterDecodeException}.
	 */
	public CharacterDecodeException() {
		super();
	}
	
	/**
	 * Creates a new {@link CharacterDecodeException}.
	 * @param message The detail message.
	 */
	public CharacterDecodeException(String message) {
		super(message);
	}
	
	/**
	 * Creates a new {@link CharacterDecodeException}.
	 * @param cause The cause. May be {@code null}.
	 */
	public CharacterDecodeException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Creates a new {@link CharacterDecodeException}.
	 * @param message The detail message.
	 * @param cause The cause. May be {@code null}.
	 */
	public CharacterDecodeException(String message, Throwable cause) {
		super(message, cause);
	}
}
