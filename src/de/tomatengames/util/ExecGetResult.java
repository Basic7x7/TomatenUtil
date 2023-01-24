package de.tomatengames.util;

/**
 * The result of a <i>get</i> process.
 * 
 * @author Basic7x7
 * @version 2023-01-23
 * @since 1.0
 */
public class ExecGetResult {
	private final CharSequence output;
	private final CharSequence error;
	private final int exitValue;
	
	ExecGetResult(CharSequence output, CharSequence error, int exitValue) {
		this.output = output;
		this.error = error;
		this.exitValue = exitValue;
	}
	
	/**
	 * Returns the standard output of the process.
	 * <p>
	 * Note: The returned value might not be a {@link String}.
	 * Use {@link CharSequence#toString()}.
	 * @return The standard output.
	 */
	public CharSequence getOutput() {
		return this.output;
	}
	
	/**
	 * Returns the error output of the process.
	 * <p>
	 * Note: The returned value might not be a {@link String}.
	 * Use {@link CharSequence#toString()}.
	 * @return The error output.
	 */
	public CharSequence getError() {
		return this.error;
	}
	
	/**
	 * Returns the exit value of the process.
	 * @return The exit value.
	 */
	public int getExitValue() {
		return this.exitValue;
	}
}
