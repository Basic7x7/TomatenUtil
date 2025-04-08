package de.tomatengames.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassLoadUtil {
	
	private ClassLoadUtil() {
	}
	
	public static ClassLoadReport loadAllClasses(URLClassLoader loader, boolean initialize, Predicate<String> nameFilter) {
		ClassLoadReport report = new ClassLoadReport();
		URL[] urls = ((URLClassLoader)loader).getURLs();
		for (URL url : urls) {
			try (JarFile jarfile = new JarFile(new File(url.toURI()))) {
				report.addAll(loadAllClasses(jarfile, loader, initialize, nameFilter));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace(); // TODO
			}
		}
		return report;
	}
	
	public static ClassLoadReport loadAllClasses(JarFile jarfile, ClassLoader classloader, boolean initialize, Predicate<String> nameFilter) {
		ClassLoadReport report = new ClassLoadReport();
		Enumeration<JarEntry> it = jarfile.entries();
		while (it.hasMoreElements()) {
			JarEntry entry = it.nextElement();
			String entryname = entry.getName();
			if (!entryname.endsWith(".class"))
				continue;
			String className = entryname.substring(0, entryname.length() - 6).replace('/', '.');
			if (nameFilter != null && !nameFilter.test(className))
				continue;
			try {
				report.addLoadedClass(Class.forName(className, initialize, classloader));
			} catch (Exception e) {
				report.addFailedClass(className, e);
			}
		}
		return report;
	}
	
}
