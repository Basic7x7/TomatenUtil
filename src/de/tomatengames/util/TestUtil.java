package de.tomatengames.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Provides methods that are helpful for JUnit testing.
 * 
 * @author Basic7x7
 * @version 2023-02-12
 * @since 1.0
 */
public class TestUtil {
	
	// Static class
	private TestUtil() {
	}
	
	/**
	 * Asserts that the specified file exists if and only if {@code expectedExists} is {@code true}.
	 * @param expectedExists If the file must exist. If {@code false}, the file must not exist.
	 * @param basePath The base path.
	 * @param path The path to the file, relative to the base path.
	 */
	public static void assertFileExists(boolean expectedExists, Path basePath, String path) {
		if (Files.exists(basePath.resolve(path)) != expectedExists) {
			if (expectedExists)
				fail(path + " does not exist.");
			else
				fail(path + " exists, but it should not.");
		}
	}
	
	/**
	 * Asserts that the specified file is executable.
	 * On some systems, e.g. Windows, this test never fails.
	 * @param basePath The base path.
	 * @param path The path to the file, relative to the base path.
	 */
	public static void assertFileExecutable(Path basePath, String path) {
		if (!Files.isExecutable(basePath.resolve(path))) {
			fail(path + " is not executable.");
		}
	}
	
	/**
	 * Asserts that the specified text file exists and contains the specified text.
	 * @param expectedText The expected text content of the file.
	 * If {@code null}, the text file must not exist.
	 * @param basePath The base path.
	 * @param path The path to the text file, relative to the base path.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void assertTextFile(String expectedText, Path basePath, String path) throws IOException {
		if (expectedText == null) {
			assertFileExists(false, basePath, path);
			return;
		}
		assertFileExists(true, basePath, path);
		String readText = IOUtil.readTextFile(basePath.resolve(path));
		assertEquals(expectedText, readText);
	}
	
}
