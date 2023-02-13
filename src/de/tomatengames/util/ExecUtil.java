package de.tomatengames.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Provides methods to run subprocesses.
 * 
 * @author Basic7x7
 * @version 2023-01-23
 * @since 1.0
 */
public class ExecUtil {
	
	// Static class
	private ExecUtil() {
	}
	
	/**
	 * Starts a process using {@link Runtime#exec(String[], String[], File)},
	 * and waits for the process to terminate.
	 * Meanwhile, the standard output and the error output of the process are read into memory.
	 * @param cmd The command to call and its arguments. Must not be {@code null}.
	 * @param envp Environment variables in the format specified by {@link Runtime#exec(String[], String[], File)}.
	 * May be {@code null}.
	 * @param dir The working directory of the subprocess. May be {@code null}.
	 * @return An result object containing the standard output of the process, the exit value, ...
	 * @throws IOException If an error occurs.
	 */
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
	
	/**
	 * Starts a process using {@link Runtime#exec(String[])},
	 * and waits for the process to terminate.
	 * Meanwhile, the standard output and the error output of the process are read into memory.
	 * @param cmd The command to call and its arguments. Must not be {@code null}.
	 * @return An result object containing the standard output of the process, the exit value, ...
	 * @throws IOException If an error occurs.
	 */
	public static ExecGetResult execGet(String... cmd) throws IOException {
		return execGet(cmd, null, null);
	}
	
	/**
	 * Starts a process using {@link Runtime#exec(String[], String[], File)},
	 * and waits for the process to terminate.
	 * Meanwhile, the standard output and the error output of the process are read into memory.
	 * The exit value of the process must be {@code 0}.
	 * @param cmd The command to call and its arguments. Must not be {@code null}.
	 * @param envp Environment variables in the format specified by {@link Runtime#exec(String[], String[], File)}.
	 * May be {@code null}.
	 * @param dir The working directory of the subprocess. May be {@code null}.
	 * @return The standard output as String.
	 * @throws IOException If an error occurs or the exit value of the process is not {@code 0}.
	 * @see #execGet(String[], String[], File)
	 */
	public static String execGetOutput(String[] cmd, String[] envp, File dir) throws IOException {
		ExecGetResult result = execGet(cmd, envp, dir);
		if (result.getExitValue() != 0) {
			throw new IOException("Command '" + String.join(" ", cmd) + "' returned exit value "
					+ result.getExitValue() + ". " + "Error stream: " + result.getError());
		}
		return result.getOutput().toString();
	}
	
	/**
	 * Starts a process using {@link Runtime#exec(String[])},
	 * and waits for the process to terminate.
	 * Meanwhile, the standard output and the error output of the process are read into memory.
	 * The exit value of the process must be {@code 0}.
	 * @param cmd The command to call and its arguments. Must not be {@code null}.
	 * @return The standard output as String.
	 * @throws IOException If an error occurs or the exit value of the process is not {@code 0}.
	 * @see #execGet(String...)
	 */
	public static String execGetOutput(String... cmd) throws IOException {
		return execGetOutput(cmd, null, null);
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
	
}
