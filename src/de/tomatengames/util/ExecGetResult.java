package de.tomatengames.util;

public class ExecGetResult {
	private final CharSequence output;
	private final CharSequence error;
	private final int exitValue;
	
	ExecGetResult(CharSequence output, CharSequence error, int exitValue) {
		this.output = output;
		this.error = error;
		this.exitValue = exitValue;
	}
	
	public CharSequence getOutput() {
		return this.output;
	}
	
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
