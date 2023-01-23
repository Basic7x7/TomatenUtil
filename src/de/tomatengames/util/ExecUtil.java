package de.tomatengames.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicReference;

public class ExecUtil {
	
	public static ExecGetResult execGet(String[] cmd, String[] envp, File dir) throws IOException {
		Process process = Runtime.getRuntime().exec(cmd, envp, dir);
		
		StringBuilder output = new StringBuilder();
		AtomicReference<IOException> outputException = new AtomicReference<>(null);
		Thread outputThread = readAsync(process.getInputStream(), output, outputException);
		
		StringBuilder error = new StringBuilder();
		AtomicReference<IOException> errorException = new AtomicReference<>(null);
		Thread errorThread = readAsync(process.getErrorStream(), error, errorException);
		
		try {
			process.waitFor();
			outputThread.join();
			errorThread.join();
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
		
		IOException outEx = outputException.get();
		if (outEx != null) {
			throw outEx;
		}
		IOException errEx = errorException.get();
		if (errEx != null) {
			throw errEx;
		}
		
		int exitValue = process.exitValue();
		return new ExecGetResult(output, error, exitValue);
	}
	
	public static ExecGetResult execGet(String... cmd) throws IOException {
		return execGet(cmd, null, null);
	}
	
	public static String execGetOutput(String... cmd) throws IOException {
		ExecGetResult result = execGet(cmd);
		if (result.getExitValue() != 0) {
			throw new IOException("Command '" + String.join(" ", cmd) + "' returned exit value "
					+ result.getExitValue() + ". " + "Error stream: " + result.getError());
		}
		return result.getOutput().toString();
	}
	
	private static Thread readAsync(InputStream in, StringBuilder result, AtomicReference<IOException> exception) {
		Thread thread = new Thread(() -> {
			try (Reader reader = new InputStreamReader(in)) {
				int ch;
				while ((ch = reader.read()) >= 0) {
					result.append((char) ch);
				}
			} catch (IOException e) {
				exception.set(e);
			}
		});
		thread.setDaemon(true);
		thread.start();
		return thread;
	}
	
	
	
	
	/**
	 * Allows to handle the output of a getter process.
	 * 
	 * @author Basic7x7
	 * @version 2023-01-23
	 * @since 1.0
	 */
	@FunctionalInterface
	public static interface ExecGetterHandler<T> {
		/**
		 * This method is called after a getter process terminated.
		 * @param cmd The command that created the process.
		 * @param output The read standard output of the process.
		 * @param error The read error output of the process.
		 * @param exitValue The exit value of the process.
		 * @return The value that should be returned by the {@code exec} method.
		 * @throws IOException If the execution of the process should be considered as failed.
		 */
		public T handle(String[] cmd, CharSequence output, CharSequence error, int exitValue)
			throws IOException;
	}
}
