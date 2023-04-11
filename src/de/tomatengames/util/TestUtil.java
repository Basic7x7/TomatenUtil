package de.tomatengames.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;

import de.tomatengames.util.function.RefConsumerWithThrows;

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
	
	
	/**
	 * Asserts that the binary file exists and matches the hexadecimal string.
	 * @param expectedHex A hex string that represents the expected bytes.
	 * Whitespace characters are ignored.
	 * If {@code null}, the binary file must not exist.
	 * @param basePath The base path.
	 * @param path The path to the binary file, relative to the base path.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void assertBinaryFile(String expectedHex, Path basePath, String path) throws IOException {
		if (expectedHex == null) {
			assertFileExists(false, basePath, path);
			return;
		}
		assertFileExists(true, basePath, path);
		String fileHex = HexUtil.fileToHex(basePath.resolve(path));
		assertEquals(expectedHex.toLowerCase(Locale.ROOT).replaceAll("\\s", ""), fileHex);
	}
	
	/**
	 * Asserts that the binary file exists and matches the specified byte array.
	 * @param expectedBytes The expected bytes of the file.
	 * If {@code null}, the binary file must not exist.
	 * @param basePath The base path.
	 * @param path The path to the binary file, relative to the base path.
	 * @throws IOException If an I/O error occurs.
	 * @since 1.2
	 */
	public static void assertBinaryFile(byte[] expectedBytes, Path basePath, String path) throws IOException {
		if (expectedBytes == null) {
			assertFileExists(false, basePath, path);
			return;
		}
		assertFileExists(true, basePath, path);
		byte[] fileBytes = IOUtil.readBinaryFile(basePath.resolve(path));
		assertArrayEquals(expectedBytes, fileBytes);
	}
	
	
	/**
	 * Runs the specified action on a new {@link OutputStream} and
	 * asserts that the bytes written to the output stream match the expected hexadecimal string.
	 * @param expectedHex A hex string that represents the expected bytes.
	 * Whitespace characters are ignored. Must not be {@code null}.
	 * @param action The action that should output bytes to the output stream.
	 * @throws IOException If an I/O error occurs while writing to the stream.
	 * @since 1.2
	 */
	public static void assertOutputStream(String expectedHex,
			RefConsumerWithThrows<OutputStream, IOException> action) throws IOException {
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			action.accept(out);
			assertEquals(expectedHex.toLowerCase(Locale.ROOT).replaceAll("\\s", ""),
					HexUtil.bytesToHex(out.toByteArray()));
		}
	}
	
	/**
	 * Runs the specified action on a new {@link OutputStream} and
	 * asserts that the bytes written to the output stream match the expected bytes.
	 * @param expectedBytes The bytes expected to be written to the stream. Must not be {@code null}.
	 * @param action The action that should output bytes to the output stream.
	 * @throws IOException If an I/O error occurs while writing to the stream.
	 * @since 1.2
	 */
	public static void assertOutputStream(byte[] expectedBytes,
			RefConsumerWithThrows<OutputStream, IOException> action) throws IOException {
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			action.accept(out);
			assertArrayEquals(expectedBytes, out.toByteArray());
		}
	}
	
	/**
	 * Asserts that both specified files or directories are recursively equal.
	 * If both paths represent files, the content of these files must be equal.
	 * If both paths are directories, they must have the an equal file list.
	 * The inner files and directories must also be equal.
	 * Links are followed.
	 * @param expected The expected file tree.
	 * @param actual The actual file tree.
	 * @param filter A filter that allows to exclude some files from the test.
	 * Files and directories that do not match this filter can appear arbitrarily in file lists.
	 * Directories that do not match this filter are not checked recursively.
	 * Must not be {@code null}.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void assertFileTree(Path expected, Path actual, Predicate<? super Path> filter) throws IOException {
		assertFileTree(expected, actual, actual, 0, filter);
	}
	
	/**
	 * Asserts that both specified files or directories are recursively equal.
	 * If both paths represent files, the content of these files must be equal.
	 * If both paths are directories, they must have the an equal file list.
	 * The inner files and directories must also be equal.
	 * Links are followed.
	 * <p>
	 * Files and directories that start with a {@code "."} are ignored.
	 * They can appear arbitrarily in file lists and they are not checked recursively.
	 * @param expected The expected file tree.
	 * @param actual The actual file tree.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void assertFileTree(Path expected, Path actual) throws IOException {
		assertFileTree(expected, actual,
				p -> !p.getFileName().toString().startsWith("."));
	}
	
	private static void assertFileTree(Path expected, Path actual, Path actualRelative, int depth,
			Predicate<? super Path> filter) throws IOException {
		
		Path act = actualRelative.relativize(actual);
		
		if (!Files.exists(expected)) {
			if (Files.exists(actual)) {
				fail(act + " exists unexpectedly.");
			}
			return;
		}
		
		if (!Files.exists(actual)) {
			fail(act + " does not exist.");
			return;
		}
		
		// Both files exist.
		
		if (depth > 0) {
			assertEquals(expected.getFileName(), actual.getFileName());
		}
		
		if (Files.isDirectory(expected)) {
			if (Files.isDirectory(actual)) {
				Path[] expectedFileList;
				try (Stream<Path> stream = Files.list(expected)) {
					expectedFileList = stream.filter(filter).sorted().toArray(Path[]::new);
				}
				Path[] actualFileList;
				try (Stream<Path> stream = Files.list(actual)) {
					actualFileList = stream.filter(filter).sorted().toArray(Path[]::new);
				}
				int n = expectedFileList.length;
				if (actualFileList.length != n) {
					String[] expectedNames = Arrays.stream(expectedFileList)
							.map(Path::getFileName).map(Object::toString).toArray(String[]::new);
					String[] actualNames = Arrays.stream(actualFileList)
							.map(Path::getFileName).map(Object::toString).toArray(String[]::new);
					fail("The file list of " + act + "differs. Expected: "
							+ Arrays.toString(expectedNames)
							+ ", actual: " + Arrays.toString(actualNames));
					return;
				}
				
				for (int i = 0; i < n; i++) {
					assertFileTree(expectedFileList[i], actualFileList[i], actualRelative,
							depth++, filter);
				}
				return;
			}
			fail(act + " is no directory.");
			return;
		}
		
		if (Files.isDirectory(actual)) {
			fail(act + " is unexpectedly a directory.");
			return;
		}
		
		// Both files are regular files.
		
		try (BufferedInputStream expectedIn = new BufferedInputStream(Files.newInputStream(expected));
				BufferedInputStream actualIn = new BufferedInputStream(Files.newInputStream(actual))) {
			
			int e, a;
			while (true) {
				e = expectedIn.read();
				a = actualIn.read();
				
				if (e == a) {
					if (e < 0) {
						return;
					}
					continue;
				}
				
				fail("The file content of " + act + " differs.");
				return;
			}
		}
	}
	
}
