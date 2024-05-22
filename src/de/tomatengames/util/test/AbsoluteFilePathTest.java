package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.tomatengames.util.IOUtil;
import de.tomatengames.util.io.AbsoluteFile;
import de.tomatengames.util.io.AbsolutePath;

class AbsoluteFilePathTest {
	
	File testdataFile = new File("testdata");
	File testdatatxtFile = new File(testdataFile, "test.txt");
	File testdatasusFile = new File(testdataFile, "../test.txt");
	File testdata2File = new File("testdata2");
	
	Path testdataPath = Paths.get("testdata");
	Path testdatatxtPath = testdataPath.resolve("test.txt");
	Path testdatasusPath = testdataPath.resolve("../test.txt");
	Path testdata2Path = Paths.get("testdata2");
	
	@Test
	void testSetup() {
		assertTrue(testdatasusFile.getPath().contains(".."));
		assertTrue(testdatasusPath.toString().contains(".."));
	}
	
	@Test
	void testGet() throws IOException {
		assertEquals(testdatasusFile.getCanonicalPath(), AbsoluteFile.get(testdatasusFile).getPath());
		assertEquals(testdatasusPath.toAbsolutePath().normalize(), AbsolutePath.get(testdatasusPath).getPath());
	}
	
	@Test
	void testIsInside() throws IOException {
		
		assertTrue(testdatatxtFile.getPath().startsWith(testdataFile.getPath()));
		assertFalse(testdataFile.getPath().startsWith(testdatatxtFile.getPath()));
		assertTrue(testdatasusFile.getPath().startsWith(testdataFile.getPath())); // critical
		assertFalse(testdataFile.getPath().startsWith(testdatasusFile.getPath()));
		assertTrue(testdata2File.getPath().startsWith(testdataFile.getPath())); // detail
		assertFalse(testdataFile.getPath().startsWith(testdata2File.getPath()));
		
		assertTrue(IOUtil.isFileInside(testdataFile, testdatatxtFile));
		assertFalse(IOUtil.isFileInside(testdatatxtFile, testdataFile));
		assertFalse(IOUtil.isFileInside(testdataFile, testdatasusFile)); // critical
		assertFalse(IOUtil.isFileInside(testdatasusFile, testdataFile));
		assertFalse(IOUtil.isFileInside(testdataFile, testdata2File)); // detail
		assertFalse(IOUtil.isFileInside(testdata2File, testdataFile));
		
		assertTrue(testdatatxtPath.startsWith(testdataPath));
		assertFalse(testdataPath.startsWith(testdatatxtPath));
		assertTrue(testdatasusPath.startsWith(testdataPath)); // critical
		assertFalse(testdataPath.startsWith(testdatasusPath));
		assertFalse(testdata2Path.startsWith(testdataPath)); // detail (already covered by Path.startsWith)
		assertFalse(testdataPath.startsWith(testdata2Path));
		
		assertTrue(IOUtil.isPathInside(testdataPath, testdatatxtPath));
		assertFalse(IOUtil.isPathInside(testdatatxtPath, testdataPath));
		assertFalse(IOUtil.isPathInside(testdataPath, testdatasusPath)); // critical
		assertFalse(IOUtil.isPathInside(testdatasusPath, testdataPath));
		assertFalse(IOUtil.isPathInside(testdataPath, testdata2Path)); // detail
		assertFalse(IOUtil.isPathInside(testdata2Path, testdataPath));
		
	}
	
	@ParameterizedTest
	@ValueSource(booleans = {false, true})
	void testResolve(boolean allowSame) throws IOException {
		assertEquals(testdatatxtFile.getCanonicalPath(),
				AbsoluteFile.get("testdata").resolveInside("test.txt", allowSame).getPath());
		assertThrows(FileNotFoundException.class, () ->
				AbsoluteFile.get("testdata").resolveInside("../test.txt", allowSame));
		
		assertEquals(testdatatxtPath.toAbsolutePath(),
				AbsolutePath.get("testdata").resolveInside("test.txt", allowSame).getPath());
		assertThrows(InvalidPathException.class, () ->
				AbsolutePath.get("testdata").resolveInside("../test.txt", allowSame));
	}
	
	@Test
	void testAllowSame() throws IOException {
		assertFalse(IOUtil.isFileInside(testdataFile, testdataFile, false));
		assertTrue(IOUtil.isFileInside(testdataFile, testdataFile, true));
		assertTrue(IOUtil.isFileInside(testdataFile, testdatatxtFile, true));
		assertFalse(IOUtil.isFileInside(testdatatxtFile, testdataFile, true));
		assertFalse(IOUtil.isFileInside(testdataFile, testdatasusFile, true)); // critical
		assertFalse(IOUtil.isFileInside(testdatasusFile, testdataFile, true));
		assertFalse(IOUtil.isFileInside(testdataFile, testdata2File, true)); // detail
		assertFalse(IOUtil.isFileInside(testdata2File, testdataFile, true));
		
		assertFalse(IOUtil.isPathInside(testdataPath, testdataPath, false));
		assertTrue(IOUtil.isPathInside(testdataPath, testdataPath, true));
		assertTrue(IOUtil.isPathInside(testdataPath, testdatatxtPath, true));
		assertFalse(IOUtil.isPathInside(testdatatxtPath, testdataPath, true));
		assertFalse(IOUtil.isPathInside(testdataPath, testdatasusPath, true)); // critical
		assertFalse(IOUtil.isPathInside(testdatasusPath, testdataPath, true));
		assertFalse(IOUtil.isPathInside(testdataPath, testdata2Path, true)); // detail
		assertFalse(IOUtil.isPathInside(testdata2Path, testdataPath, true));
	}
	
}
