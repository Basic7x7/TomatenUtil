package de.tomatengames.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A ClassLoadReport captures the results of attempting to load various classes,
 * including both successful loads and failures.
 * 
 * @version 2025-04-08 created
 * @since 1.8
 */
public class ClassLoadReport {
	
	private final List<Class<?>> loadedClasses;
	private final List<ClassLoadError> failedClasses;
	
	/**
	 * Constructs a new, empty {@code ClassLoadReport}.
	 */
	public ClassLoadReport() {
		this.loadedClasses = new ArrayList<>();
		this.failedClasses = new ArrayList<>();
	}
	
	/**
	 * Adds a successfully loaded class to the report.
	 * 
	 * @param cls the class that was loaded
	 */
	public void addLoadedClass(Class<?> cls) {
		this.loadedClasses.add(cls);
	}
	
	/**
	 * Adds information about a failed class load attempt to the report.
	 * 
	 * @param className the fully qualified name of the class that failed to load
	 * @param error     the exception or error that occurred during loading
	 */
	public void addFailedClass(String className, Throwable error) {
		this.failedClasses.add(new ClassLoadError(className, error));
	}
	
	/**
	 * Returns a list of all successfully loaded classes.
	 * 
	 * @return a list of {@code Class} objects representing the loaded classes
	 */
	public List<Class<?>> getLoadedClasses() {
		return this.loadedClasses;
	}
	
	/**
	 * Returns a list of all failed class load attempts.
	 * 
	 * @return a list of {@code ClassLoadError} objects representing the failed
	 *         loads
	 */
	public List<ClassLoadError> getFailedClasses() {
		return failedClasses;
	}
	
	/**
	 * Merges another {@code ClassLoadReport} into this report by adding all its
	 * results to this {@code ClassLoadReport}.
	 * 
	 * @param other the other {@code ClassLoadReport} to merge
	 */
	public void addAll(ClassLoadReport other) {
		this.loadedClasses.addAll(other.loadedClasses);
		this.failedClasses.addAll(other.failedClasses);
	}
	
	/**
	 * A ClassLoadError represents a failed class loading attempt.
	 */
	public static class ClassLoadError {
		
		private final String classname;
		private final Throwable error;
		
		/**
		 * Constructs a new {@code ClassLoadError} from the provided information.
		 * 
		 * @param classname the fully qualified name of the class that failed to load
		 * @param error     the exception or error that occurred during loading
		 */
		public ClassLoadError(String classname, Throwable error) {
			this.classname = classname;
			this.error = error;
		}
		
		/**
		 * Returns the fully qualified name of the class that failed to load.
		 * 
		 * @return the class name
		 */
		public String getClassName() {
			return classname;
		}
		
		/**
		 * Returns the exception or error that occurred during class loading.
		 * 
		 * @return the throwable associated with the failed class loading attempt
		 */
		public Throwable getError() {
			return error;
		}
	}
	
}
