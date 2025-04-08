package de.tomatengames.util;

import java.util.ArrayList;
import java.util.List;

public class ClassLoadReport {
	
	private final List<Class<?>> loadedClasses;
	private final List<ClassLoadError> failedClasses;
	
	public ClassLoadReport() {
		this.loadedClasses = new ArrayList<>();
		this.failedClasses = new ArrayList<>();
	}
	
	public void addLoadedClass(Class<?> c) {
		this.loadedClasses.add(c);
	}
	public void addFailedClass(String classname, Exception ex) {
		this.failedClasses.add(new ClassLoadError(classname, ex));
	}
	
	public List<Class<?>> getLoadedClasses() {
		return this.loadedClasses;
	}
	public List<ClassLoadError> getFailedClasses() {
		return failedClasses;
	}
	
	public void addAll(ClassLoadReport other) {
		this.loadedClasses.addAll(other.loadedClasses);
		this.failedClasses.addAll(other.failedClasses);
	}
	
	public static class ClassLoadError {
		private final String classname;
		private final Exception exception;
		public ClassLoadError(String classname, Exception e) {
			this.classname = classname;
			this.exception = e;
		}
		public String getClassName() {
			return classname;
		}
		public Exception getException() {
			return exception;
		}
	}
	
}
