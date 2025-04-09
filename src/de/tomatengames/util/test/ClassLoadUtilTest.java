package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.tomatengames.util.ClassLoadReport;
import de.tomatengames.util.ClassLoadUtil;

class ClassLoadUtilTest {
	
	static final String TEST_JAR = "testdata/classloadutil/output/ClassLoadUtilTestProject.jar";
	
	static int initializeCounter;
	
	static void noteInitializer() {
		initializeCounter++;
	}
	
	@BeforeEach
	void resetInit() {
		initializeCounter = 0;
	}
	
	@Test
	void testGet() throws IOException {
		Set<String> expected = new HashSet<>();
		expected.add("Empty");
		expected.add("Initializer");
		expected.add("FailInitializer");
		expected.add("Invalid");
		Set<String> actual = new HashSet<>();
		try (JarFile jar = new JarFile(TEST_JAR)) {
			actual.addAll(ClassLoadUtil.getAllClassNames(jar));
		}
		assertEquals(expected, actual);
	}
	
	@ParameterizedTest
	@ValueSource(booleans = {false, true})
	void testLoadAll(boolean initialize) throws IOException {
		URLClassLoader loader = new URLClassLoader(new URL[] {new File(TEST_JAR).toURI().toURL()});
		ClassLoadReport report;
		try (JarFile jar = new JarFile(TEST_JAR)) {
			assertEquals(0, initializeCounter);
			report = ClassLoadUtil.loadAllClasses(jar, initialize, loader);
			assertEquals(initialize ? 1 : 0, initializeCounter);
		}
		
		Set<String> expectedLoaded = new HashSet<>();
		expectedLoaded.add("Empty");
		expectedLoaded.add("Initializer");
		if (!initialize)
			expectedLoaded.add("FailInitializer");
		assertEquals(expectedLoaded, report.getLoadedClasses().stream().map(c -> c.getName()).collect(Collectors.toSet()));
		
		Map<String, Throwable> fails = report.getFailedClassesMap();
		if (initialize) {
			assertEquals(2, fails.size());
			Throwable initfail = fails.get("FailInitializer");
			assertEquals(ExceptionInInitializerError.class, initfail.getClass());
			assertEquals("FailInitializer cannot init", initfail.getCause().getMessage());
		} else {
			assertEquals(1, fails.size());
		}
		assertEquals(ClassFormatError.class, fails.get("Invalid").getClass());
	}
	
}
