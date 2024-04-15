package de.tomatengames.util;

import static de.tomatengames.util.RequirementUtil.requireNotNull;

import java.util.HashMap;

/**
 * Undepender provides a shared storage that can be accessed by all classes running in the current JVM
 * using the {@link #set(String, Object)} and {@link #get(String)} methods.
 * <p>
 * The storage operations are thread-safe.
 * <p>
 * The storage can be used to communicate between subsystems without the need
 * that these subsystems have been built against each other.
 * <p>
 * For example, assume two subsystems {@code A.jar} and {@code B.jar} where {@code A.jar} is a soft-dependency of {@code B.jar}.
 * {@code A.jar} defines an operation {@code A.a()} that should be called by {@code B.jar} only if {@code A.jar} is present.
 * Traditionally, {@code A.jar} would be required in the class-path of {@code B.jar},
 * making it a mandatory compile and runtime dependency.
 * With Undepender, {@code A.jar} could set a function into the shared storage to provide the operation.
 * <pre>
 * Undepender.set("A.a", (Function&lt;String, String&gt;) param -&gt; { /* operation &#42;&#47; });
 * </pre>
 * Then {@code B.jar} can get and call {@code "A.a"} if it exists. It does not exist if {@code A.jar} is not installed.
 * <pre>
 * Function&lt;String, String&gt; a = Undepender.get("A.a");
 * if (a != null) {
 *     a.apply(param);
 * }
 * </pre>
 * 
 * @author Basic7x7
 * @version 2024-04-15
 * @since 1.6
 */
public class Undepender {
	private static final HashMap<String, Object> map = new HashMap<>();
	private static final Object lock = new Object();
	
	// Static class
	private Undepender() {
	}
	
	/**
	 * Associates the specified value with the key.
	 * @param key The key. Not {@code null}.
	 * @param value The value. If {@code null}, no value will be associated to the key.
	 */
	public static void set(String key, Object value) {
		requireNotNull("The key ...", key);
		synchronized (lock) {
			if (value != null) {
				map.put(key, value);
			}
			else {
				map.remove(key);
			}
		}
	}
	
	/**
	 * Returns the value associated with the specified key.
	 * <p>
	 * Note that the return value is auto-casted to the requested type. This is an unchecked cast.
	 * It must be ensured that the requested type is compatible with the value.
	 * @param <T> The requested type of the value. The return value is auto-casted to this type.
	 * @param key The key. Not {@code null}.
	 * @return The value associated with the key. If no such value exists, {@code null} is returned.
	 * The return value is auto-casted to the requested type.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key) {
		synchronized (lock) {
			return (T) map.get(key);
		}
	}
}
