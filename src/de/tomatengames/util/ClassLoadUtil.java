package de.tomatengames.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.tomatengames.util.exception.ReflectionException;

/**
 * Provides utility methods related to class loading.
 * Currently focused on methods to load all classes from given jar files.
 * 
 * @version 2025-04-08 created
 * @since 1.8
 */
public class ClassLoadUtil {
	
	private ClassLoadUtil() {
	}
	
	/**
	 * Retrieves all fully qualified class names from the specified JAR file by
	 * scanning it for entries whose name ends with ".class".
	 * 
	 * @param jar the ZIP/JAR file to find classes in
	 * @return a collection of fully qualified class names
	 */
	public static Collection<String> getAllClassNames(ZipFile jar) {
		List<String> classNames = new ArrayList<>();
		Enumeration<? extends ZipEntry> it = jar.entries();
		while (it.hasMoreElements()) {
			ZipEntry entry = it.nextElement();
			String entryname = entry.getName();
			if (!entryname.endsWith(".class"))
				continue;
			classNames.add(entryname.substring(0, entryname.length() - 6).replace('/', '.'));
		}
		return classNames;
	}
	
	/**
	 * Loads all classes specified by the given class names using the provided class
	 * loader.
	 * 
	 * @param classNames  a collection of fully qualified class names to be loaded
	 * @param initialize  whether the classes should be initialized upon loading
	 * @param classLoader the class loader to use for loading the classes
	 * @return a report containing information about successfully loaded and failed
	 *         classes
	 */
	public static ClassLoadReport loadAllClasses(Collection<String> classNames, boolean initialize, ClassLoader classLoader) {
		ClassLoadReport report = new ClassLoadReport();
		for (String className : classNames) {
			try {
				report.addLoadedClass(Class.forName(className, initialize, classLoader));
			} catch (Throwable e) {
				report.addFailedClass(className, e);
			}
		}
		return report;
	}
	
	/**
	 * Loads all classes from the specified ZIP/JAR file using the provided class
	 * loader.
	 * 
	 * @param jar         the ZIP/JAR file containing classes
	 * @param initialize  whether the classes should be initialized upon loading
	 * @param classLoader the class loader to use for loading the classes
	 * @return a report containing information about successfully loaded and failed
	 *         classes
	 */
	public static ClassLoadReport loadAllClasses(ZipFile jar, boolean initialize, ClassLoader classLoader) {
		return loadAllClasses(getAllClassNames(jar), initialize, classLoader);
	}
	
	/**
	 * Runs the static initialization of the specified class if it is not already
	 * initialized.
	 * 
	 * @param cls the class to initialize
	 * @throws ExceptionInInitializerError if the initialization fails
	 * @throws ReflectionException         if a {@link ClassNotFoundException} or
	 *                                     {@link LinkageError} occurs unexpectedly
	 */
	public static void initializeClass(Class<?> cls) {
		try {
			Class.forName(cls.getName(), true, cls.getClassLoader());
		} catch (ClassNotFoundException | LinkageError e) {
			throw new ReflectionException(e); // should not happen
		}
	}
	
}
