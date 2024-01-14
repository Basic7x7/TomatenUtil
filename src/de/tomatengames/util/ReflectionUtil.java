package de.tomatengames.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Provides convenient methods to utilize the Reflection API.
 * 
 * @author LukasE7x7
 * @version 2024-01-14
 * @since 1.5
 */
public class ReflectionUtil {
	
	private static final Object[] EMPTY_ARRAY = new Object[0];
	
	// Standard Class/Field/Constructor/Method finding:
	
	/**
	 * Get a class by its name, wraps {@link Class#forName(String)}.
	 * @param name the name of the class
	 * @return the class reference or null if the class is not found
	 */
	public static Class<?> Class(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Get a class field by its name, wraps {@link Class#getDeclaredField(String)}.
	 * @param c the class
	 * @param name the name of the field
	 * @return the field reference or null if no such field is found
	 */
	public static Field field(Class<?> c, String name) {
		try {
			Field f = c.getDeclaredField(name);
			f.setAccessible(true);
			return f;
		} catch (NoSuchFieldException e) {
			return null;
		}
	}
	
	/**
	 * Get a constructor by its parameter types, wraps {@link Class#getDeclaredConstructor(Class...)}.
	 * @param <T> the class type, made available to the output constructor reference without casting, may be <code>?</code>
	 * @param c the class
	 * @param paramTypes the parameter types
	 * @return the constructor reference or null if no such constructor is found
	 */
	public static <T> Constructor<T> constructor(Class<T> c, Class<?>... paramTypes) {
		try {
			Constructor<T> constr = c.getDeclaredConstructor(paramTypes);
			constr.setAccessible(true);
			return constr;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * Get a method by its name and parameter types, wraps {@link Class#getDeclaredMethod(String, Class...)}.
	 * @param c the class
	 * @param name the name of the method
	 * @param paramTypes the parameter types
	 * @return the method reference or null if no such method is found
	 */
	public static Method method(Class<?> c, String name, Class<?>... paramTypes) {
		try {
			Method m = c.getDeclaredMethod(name, paramTypes);
			m.setAccessible(true);
			return m;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	// Searching/Recursive Field/Constructor/Method finding:
	
	/**
	 * Searches the given class hierarchy for any field of the given name.
	 * @param c the most specific class searched
	 * @param name the name of the field
	 * @return the first found field reference or null if no such field is found in the class or any superclass
	 */
	public static Field searchField(Class<?> c, String name) {
		try {
			return c.getField(name);
		} catch (NoSuchFieldException e) {
		}
		while (c != null) {
			Field f = field(c, name);
			if (f != null)
				return f;
			c = c.getSuperclass();
		}
		return null;
	}
	
	private static Class<?> normalizePrimitive(Class<?> c) {
		if (c == Byte.class)
			return byte.class;
		if (c == Short.class)
			return short.class;
		if (c == Integer.class)
			return int.class;
		if (c == Long.class)
			return long.class;
		if (c == Float.class)
			return float.class;
		if (c == Double.class)
			return double.class;
		if (c == Boolean.class)
			return boolean.class;
		if (c == Character.class)
			return char.class;
		return c;
	}
	private static boolean isParameterCompatible(Class<?> required, Class<?> passed) {
		if (passed == null)
			return !required.isPrimitive();
		if (required.isAssignableFrom(passed))
			return true;
		required = normalizePrimitive(required);
		passed = normalizePrimitive(passed);
		if (required == passed)
			return true;
		if (required == short.class)
			return passed == byte.class;
		if (required == int.class)
			return passed == byte.class || passed == short.class || passed == char.class;
		if (required == long.class)
			return passed == byte.class || passed == short.class || passed == char.class || passed == int.class;
		if (required == double.class)
			return passed == float.class;
		return false;
	}
	private static Class<?>[] getClasses(Object... objects) {
		Class<?>[] out = new Class<?>[objects.length];
		for (int i = 0; i < objects.length; i++) {
			Object o = objects[i];
			out[i] = o == null ? null : o.getClass();
		}
		return out;
	}
	
	/**
	 * Searches the given class for a constructor that the given parameter types can be passed to.
	 * @param <T> the class type, made available to the output constructor reference without casting, may be <code>?</code>
	 * @param c the class
	 * @param compatibleParamTypes the passable parameter types
	 * @return a constructor reference matching the given parameter types or null if no such constructor is found
	 */
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> searchConstructor(Class<T> c, Class<?>... compatibleParamTypes) {
		int paramLen = compatibleParamTypes.length;
		constrloop: for (Constructor<?> constr : c.getDeclaredConstructors()) {
			Class<?>[] paramTypes = constr.getParameterTypes();
			if (paramTypes.length != paramLen)
				continue;
			for (int i = 0; i < paramLen; i++) {
				if (!isParameterCompatible(paramTypes[i], compatibleParamTypes[i]))
					continue constrloop;
			}
			constr.setAccessible(true);
			return (Constructor<T>)constr;
		}
		return null;
	}
	
	/**
	 * Searches the given class hierarchy for a method of the given name that the given parameter types can be passed to.
	 * @param c the most specific class
	 * @param name the name of the method
	 * @param compatibleParamTypes the passable parameter types
	 * @return the first found method reference matching the given name and parameter types or null if no such method is found
	 */
	public static Method searchMethod(Class<?> c, String name, Class<?>... compatibleParamTypes) {
		int paramLen = compatibleParamTypes.length;
		while (c != null) {
			methodloop: for (Method m : c.getDeclaredMethods()) {
				if (!m.getName().equals(name))
					continue;
				Class<?>[] paramTypes = m.getParameterTypes();
				if (paramTypes.length != paramLen)
					continue;
				for (int i = 0; i < paramLen; i++) {
					if (!isParameterCompatible(paramTypes[i], compatibleParamTypes[i]))
						continue methodloop;
				}
				m.setAccessible(true);
				return m;
			}
			c = c.getSuperclass();
		}
		return null;
	}
	
	// Standard get/set:
	
	/**
	 * Get the current value of the given field in the given object, wraps {@link Field#get(Object)}.
	 * @param o the object
	 * @param f the field
	 * @return the value
	 */
	public static Object get(Object o, Field f) {
		try {
			return f.get(o);
		} catch (IllegalAccessException e) {
			throw new Error(e);
		}
	}
	
	/**
	 * Set the current value of the given field in the given object, wraps {@link Field#set(Object, Object)}.
	 * @param o the object
	 * @param f the field
	 * @param value the value
	 */
	public static void set(Object o, Field f, Object value) {
		try {
			f.set(o, value);
		} catch (IllegalAccessException e) {
			throw new Error(e);
		}
	}
	
	/**
	 * Get the current value of the given static field.
	 * @param f the field
	 * @return the value
	 */
	public static Object getStatic(Field f) {
		return get(null, f);
	}
	
	/**
	 * Set the current value of the given static field.
	 * @param f the field
	 * @param value the value
	 */
	public static void setStatic(Field f, Object value) {
		set(null, f, value);
	}
	
	// TODO Primitive get/set methods
	
	// Standard Construct/run:
	
	/**
	 * Constructs a new object using the given constructor and constructor parameters, wraps {@link Constructor#newInstance(Object...)}.
	 * @param <T> the type of the returned object
	 * @param constr the constructor reference
	 * @param param the constructor parameters
	 * @return the new object
	 */
	public static <T> T construct(Constructor<T> constr, Object... param) {
		try {
			return constr.newInstance(param);
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
			throw new Error(e);
		}
	}
	
	/**
	 * Constructs a new object using the given constructor and no parameters.
	 * @param <T> the type of the returned object
	 * @param constr the constructor reference
	 * @return the new object
	 */
	public static <T> T construct(Constructor<T> constr) {
		return construct(constr, EMPTY_ARRAY);
	}
	
	/**
	 * Runs the given method of the given object using the given parameters.
	 * @param o the target object
	 * @param m the method reference
	 * @param param the method parameters
	 * @return the return value
	 */
	public static Object run(Object o, Method m, Object... param) {
		try {
			return m.invoke(o, param);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new Error(e);
		}
	}
	
	/**
	 * Runs the given method of the given object with no parameters.
	 * @param o the target object
	 * @param m the method reference
	 * @return the return value
	 */
	public static Object run(Object o, Method m) {
		return run(o, m, EMPTY_ARRAY);
	}
	
	/**
	 * Runs the given static method using the given parameters.
	 * @param m the method reference
	 * @param param the parameters
	 * @return the return value
	 */
	public static Object runStatic(Method m, Object... param) {
		return run(null, m, param);
	}
	
	/**
	 * Runs the given static method using no parameters.
	 * @param m the method reference
	 * @return the return value
	 */
	public static Object runStatic(Method m) {
		return run(null, m);
	}
	
	// Inefficient convenience methods:
	
	/**
	 * Get the current value of the field of the given class and name in the given object.
	 * For efficiency, use {@link #get(Object, Field)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param o the target object
	 * @param fieldClass the field class
	 * @param fieldName the field name
	 * @return the value of the found field
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Object o, Class<?> fieldClass, String fieldName) {
		Field f = searchField(fieldClass, fieldName);
		return (T)get(o, f);
	}
	
	/**
	 * Set the current value of the field of the given class and name in the given object.
	 * For efficiency, use {@link #set(Object, Field, Object)} instead.
	 * @param o the target object
	 * @param fieldClass the field class
	 * @param fieldName the field name
	 * @param value the value for the found field
	 */
	public static void set(Object o, Class<?> fieldClass, String fieldName, Object value) {
		Field f = searchField(fieldClass, fieldName);
		set(o, f, value);
	}
	
	/**
	 * Get the current value of the field of the given name in the given object.
	 * For efficiency, use {@link #get(Object, Field)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param o the target object
	 * @param fieldName the name of the field
	 * @return the value of the found field
	 */
	public static <T> T get(Object o, String fieldName) {
		return get(o, o.getClass(), fieldName);
	}
	
	/**
	 * Set the current value of the field of the given name in the given object.
	 * For efficiency, use {@link #set(Object, Field, Object)} instead.
	 * @param o the target object
	 * @param fieldName the name of the field
	 * @param value the value for the found field
	 */
	public static void set(Object o, String fieldName, Object value) {
		set(o, o.getClass(), fieldName, value);
	}
	
	/**
	 * Get the current value of the static field of the given class and name.
	 * For efficiency, use {@link #getStatic(Field)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param c the field class
	 * @param fieldName the field name
	 * @return the value of the found field
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getStatic(Class<?> c, String fieldName) {
		return (T)get(null, c, fieldName);
	}
	
	/**
	 * Set the current value of the static field of the given class and name.
	 * For efficiency, use {@link #setStatic(Field, Object)} instead.
	 * @param c the field class
	 * @param fieldName the field name
	 * @param value the value for the found field
	 */
	public static void setStatic(Class<?> c, String fieldName, Object value) {
		set(null, c, fieldName, value);
	}
	
	/**
	 * Get the current value of the static field of the given class name and field name.
	 * For efficiency, use {@link #getStatic(Field)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param className the field class name
	 * @param fieldName the field name
	 * @return the value of the found field
	 */
	public static <T> T getStatic(String className, String fieldName) {
		return getStatic(Class(className), fieldName);
	}
	
	/**
	 * Set the current value of the static field of the given class name and field name.
	 * For efficiency, use {@link #setStatic(Field, Object)} instead.
	 * @param className the field class name
	 * @param fieldName the field name
	 * @param value the value for the found field
	 */
	public static void setStatic(String className, String fieldName, Object value) {
		setStatic(Class(className), fieldName, value);
	}
	
	/**
	 * Constructs a new object of the given class using a matching constructor.
	 * For efficiency, use {@link #construct(Constructor, Object...)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param c the class to be instantiated
	 * @param param the constructor parameters
	 * @return the new object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T construct(Class<?> c, Object... param) {
		Constructor<?> constr = searchConstructor(c, getClasses(param));
		return (T)construct(constr, param);
	}
	
	/**
	 * Constructs a new object of the given class name using a matching constructor.
	 * For efficiency, use {@link #construct(Constructor, Object...)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param className the name of the class to be instantiated
	 * @param param the constructor parameters
	 * @return the new object
	 */
	public static <T> T construct(String className, Object... param) {
		return construct(Class(className), param);
	}
	
	// private because this would only give the false impression one could run superclass definitions of methods with this
	@SuppressWarnings("unchecked")
	private static <T> T run(Object o, Class<?> methodClass, String methodName, Object... param) {
		Method m = searchMethod(methodClass, methodName, getClasses(param));
		return (T)run(o, m, param);
	}
	
	/**
	 * Runs a matching method of the given object and name.
	 * For efficiency, use {@link #run(Object, Method, Object...)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param o the target object
	 * @param methodName the name of the method
	 * @param param the method parameters
	 * @return the return value
	 */
	public static <T> T run(Object o, String methodName, Object... param) {
		return run(o, o.getClass(), methodName, param);
	}
	
	/**
	 * Runs a matching method of the given class and name.
	 * For efficiency, use {@link #runStatic(Method, Object...)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param c the method class
	 * @param methodName the method name
	 * @param param the method parameters
	 * @return the return value
	 */
	public static <T> T runStatic(Class<?> c, String methodName, Object... param) {
		return run(null, c, methodName, param);
	}
	
	/**
	 * Runs a matching method of the given class name and method name.
	 * For efficiency, use {@link #runStatic(Method, Object...)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param className the method class
	 * @param methodName the method name
	 * @param param the method parameters
	 * @return the return value
	 */
	public static <T> T runStatic(String className, String methodName, Object... param) {
		return run(null, Class(className), methodName, param);
	}
	
}
