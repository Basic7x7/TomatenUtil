package de.tomatengames.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
	 * @param input Bytes to write to the standard input of the subprocess. May be {@code null}.
	 * @return An result object containing the standard output of the subprocess, the exit value, ...
	 * @throws IOException If an error occurs.
	 */
	public static ExecGetResult execGet(String[] cmd, String[] envp, File dir, byte[] input) throws IOException {
		Process process = Runtime.getRuntime().exec(cmd, envp, dir);
		
		AtomicReference<IOException> ioException = new AtomicReference<>(null);
		
		Thread inputThread = input == null ? null : asyncIO(() -> {
			OutputStream out = process.getOutputStream();
			out.write(input);
			out.flush();
			out.close();
		}, ioException);
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Thread outputThread = asyncIO(() -> IOUtil.pipeStream(process.getInputStream(), output), ioException);
		
		ByteArrayOutputStream error = new ByteArrayOutputStream();
		Thread errorThread = asyncIO(() -> IOUtil.pipeStream(process.getErrorStream(), error), ioException);
		
		try {
			process.waitFor();
			if (inputThread != null)
				inputThread.join();
			outputThread.join();
			errorThread.join();
		} catch (InterruptedException e) {
			throw new IOException(e);
		}
		
		IOException ex = ioException.get();
		if (ex != null) {
			throw ex;
		}
		
		int exitValue = process.exitValue();
		return new ExecGetResult(output.toByteArray(), error.toByteArray(), exitValue);
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
		return execGet(cmd, null, null, null);
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
	 * @see #execGet(String[], String[], File, byte[])
	 */
	public static String execGetOutput(String[] cmd, String[] envp, File dir) throws IOException {
		ExecGetResult result = execGet(cmd, envp, dir, null);
		if (result.getExitValue() != 0) {
			throw new IOException("Command '" + String.join(" ", cmd) + "' returned exit value "
					+ result.getExitValue() + ". " + "Error stream: " + result.getErrorString());
		}
		return result.getOutputString();
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
	
	private static Thread asyncIO(IORunnable run, AtomicReference<IOException> exception) {
		Thread thread = new Thread(() -> {
			try {
				run.run();
			} catch (IOException e) {
				// set exception if it is the first
				if (!exception.compareAndSet(null, e)) {
					// otherwise add to suppressed of first
					exception.get().addSuppressed(e);
				}
			}
		});
		thread.setDaemon(true);
		thread.start();
		return thread;
	}
	
	private static interface IORunnable {
		void run() throws IOException;
	}
	
}
