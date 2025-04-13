package de.tomatengames.util;

/**
 * The result of a <i>get</i> process.
 * 
 * @author Basic7x7
 * @version 2023-01-23
 * @since 1.0
 */
public class ExecGetResult {
	
	private final byte[] output;
	private final byte[] error;
	private final int exitValue;
	
	ExecGetResult(byte[] output, byte[] error, int exitValue) {
		this.output = output;
		this.error = error;
		this.exitValue = exitValue;
	}
	
	/**
	 * Returns the standard output bytes of the process.
	 * @return The standard output bytes.
	 * @since 1.8
	 */
	public byte[] getOutputBytes() {
		return output;
	}
	
	/**
	 * Returns the standard output of the process as a string using the default charset.
	 * @return The standard output of the process as a string.
	 * @since 1.8
	 */
	public String getOutputString() {
		return new String(this.output);
	}
	
	/**
	 * Returns the standard output of the process using the default charset.
	 * @return The standard output of the process.
	 * @deprecated Use {@link #getOutputString()}
	 */
	@Deprecated
	public CharSequence getOutput() {
		return getOutputString();
	}
	
	/**
	 * Returns the error output of the process.
	 * @return The error output.
	 */
	public byte[] getErrorBytes() {
		return this.error;
	}
	
	/**
	 * Returns the error output of the process as a string using the default charset.
	 * @return the error output of the process as a string
	 * @since 1.8
	 */
	public String getErrorString() {
		return new String(this.error);
	}
	
	/**
	 * Returns the error output of the process using the default charset.
	 * @return the error output of the process
	 * @deprecated Use {@link #getErrorString()}
	 */
	@Deprecated
	public CharSequence getError() {
		return getErrorString();
	}
	
	/**
	 * Returns the exit value of the process.
	 * @return The exit value.
	 */
	public int getExitValue() {
		return this.exitValue;
	}
	
}
