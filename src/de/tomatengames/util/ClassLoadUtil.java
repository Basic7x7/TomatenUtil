package de.tomatengames.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClassLoadUtil {
	
	private ClassLoadUtil() {
	}
	
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
	
	public static ClassLoadReport loadAllClasses(ZipFile jar, boolean initialize, ClassLoader classLoader) {
		return loadAllClasses(getAllClassNames(jar), initialize, classLoader);
	}
	
}
