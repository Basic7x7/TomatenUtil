package de.tomatengames.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.tomatengames.util.exception.ReflectionException;

/**
 * Provides convenient methods to utilize the Reflection API.
 * 
 * @author LukasE7x7
 * @version 2024-01-14
 * @since 1.5
 */
public class ReflectionUtil {
	
	private static final Object[] EMPTY_ARRAY = new Object[0];
	
	private ReflectionUtil() {
	}
	
	// Standard Class/Field/Constructor/Method finding:
	
	/**
	 * Get a class by its name, wraps {@link Class#forName(String)}.
	 * @param name the name of the class
	 * @return the class reference
	 * @throws ReflectionException if the class is not found
	 */
	public static Class<?> Class(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get a class field by its name, wraps {@link Class#getDeclaredField(String)}.
	 * @param c the class
	 * @param name the name of the field
	 * @return the field reference
	 * @throws ReflectionException if no such field is found
	 */
	public static Field field(Class<?> c, String name) {
		try {
			Field f = c.getDeclaredField(name);
			f.setAccessible(true);
			return f;
		} catch (NoSuchFieldException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get a constructor by its parameter types, wraps {@link Class#getDeclaredConstructor(Class...)}.
	 * @param <T> the class type, made available to the output constructor reference without casting
	 * @param c the class
	 * @param paramTypes the parameter types
	 * @return the constructor reference
	 * @throws ReflectionException if no such constructor is found
	 */
	public static <T> Constructor<T> constructor(Class<T> c, Class<?>... paramTypes) {
		try {
			Constructor<T> constr = c.getDeclaredConstructor(paramTypes);
			constr.setAccessible(true);
			return constr;
		} catch (NoSuchMethodException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get a method by its name and parameter types, wraps {@link Class#getDeclaredMethod(String, Class...)}.
	 * @param c the class
	 * @param name the name of the method
	 * @param paramTypes the parameter types
	 * @return the method reference
	 * @throws ReflectionException if no such method is found
	 */
	public static Method method(Class<?> c, String name, Class<?>... paramTypes) {
		try {
			Method m = c.getDeclaredMethod(name, paramTypes);
			m.setAccessible(true);
			return m;
		} catch (NoSuchMethodException e) {
			throw new ReflectionException(e);
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
		while (c != null) {
			try {
				Field f = c.getDeclaredField(name);
				f.setAccessible(true);
				return f;
			} catch (NoSuchFieldException e) {
				c = c.getSuperclass();
			}
		}
		return null;
	}
	
	/**
	 * {@link #searchField(Class, String)} but throws an exception when no field is found.
	 * @param c the most specific class searched
	 * @param name the name of the field
	 * @return the first found field reference
	 * @throws ReflectionException if no such field is found
	 */
	public static Field findField(Class<?> c, String name) {
		Field f = searchField(c, name);
		if (f == null)
			throw new ReflectionException("No field '" + name + "' found in class hierarchy of " + c);
		return f;
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
	 * @param <T> the class type, made available to the output constructor reference without casting
	 * @param c the class
	 * @param compatibleParamTypes the passable parameter types
	 * @return a constructor reference matching the given parameter types or null if no such constructor is found
	 */
	public static <T> Constructor<T> searchConstructor(Class<T> c, Class<?>... compatibleParamTypes) {
		int paramLen = compatibleParamTypes.length;
		constrloop: for (Constructor<?> constrUnknown : c.getDeclaredConstructors()) {
			@SuppressWarnings("unchecked")
			Constructor<T> constr = (Constructor<T>) constrUnknown;
			Class<?>[] paramTypes = constr.getParameterTypes();
			if (paramTypes.length != paramLen)
				continue;
			for (int i = 0; i < paramLen; i++) {
				if (!isParameterCompatible(paramTypes[i], compatibleParamTypes[i]))
					continue constrloop;
			}
			constr.setAccessible(true);
			return constr;
		}
		return null;
	}
	
	/**
	 * {@link #searchConstructor(Class, Class...)} but throws an exception if no constructor is found.
	 * @param <T> the class type
	 * @param c the class
	 * @param compatibleParamTypes the passable parameter types
	 * @return a constructor reference matching the given parameter types
	 * @throws ReflectionException if no such constructor is found
	 */
	public static <T> Constructor<T> findConstructor(Class<T> c, Class<?>... compatibleParamTypes) {
		Constructor<T> constr = searchConstructor(c, compatibleParamTypes);
		if (constr == null)
			throw new ReflectionException("No such constructor found in " + c);
		return constr;
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
	
	/**
	 * {@link #searchMethod(Class, String, Class...)} but throws an exception if no method is found.
	 * @param c the most specific class
	 * @param name the name of the method
	 * @param compatibleParamTypes the passable parameter types
	 * @return the first found method reference matching the given name and parameter types
	 * @throws ReflectionException if no such method is found
	 */
	public static Method findMethod(Class<?> c, String name, Class<?>... compatibleParamTypes) {
		Method m = searchMethod(c, name, compatibleParamTypes);
		if (m == null)
			throw new ReflectionException("No such method '" + name + "' found in " + c);
		return m;
	}
	
	// Standard get/set:
	
	/* txs-begin fieldGetSet
	##
	function typeGetSet(type, method) {
		final an = type in ["Object", "int"] ? "an" : "a";
		final asType = "as " + an + " " + type;
		final toType = "to " + an + " " + type;
		##
		/**
		 * Get the current value of the given field in the given object %asType;, wraps {@link Field%#get%method;(Object)}.
		 * @param object the object
		 * @param field the field
		 * @return the value %asType;
		 * @throws ReflectionException if an {@link IllegalAccessException} occurs
		 *%'/';
		// %cli.txsinfo();
		public static %type; get%method;(Object object, Field field) {
			try {
				return field.get%method;(object);
			} catch (IllegalAccessException e) {
				throw new ReflectionException(e);
			}
		}
		
		/**
		 * Set the current value of the given field in the given object %toType;, wraps {@link Field%#set%method;(Object, %type;)}.
		 * @param object the object
		 * @param field the field
		 * @param value the value %asType;
		 * @throws ReflectionException if an {@link IllegalAccessException} occurs
		 *%'/';
		// %cli.txsinfo();
		public static void set%method;(Object object, Field field, %type; value) {
			try {
				field.set%method;(object, value);
			} catch (IllegalAccessException e) {
				throw new ReflectionException(e);
			}
		}
		
		/**
		 * Get the current value of the given static field %asType;.
		 * @param field the field
		 * @return the value %asType;
		 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
		 *%'/';
		// %cli.txsinfo();
		public static %type; getStatic%method;(Field field) {
			try {
				return field.get%method;(null);
			} catch (IllegalAccessException e) {
				throw new ReflectionException(e);
			} catch (NullPointerException e) {
				throw new ReflectionException(field + " is not static", e);
			}
		}
		
		/**
		 * Set the current value of the given static field %toType;.
		 * @param field the field
		 * @param value the value %asType;
		 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
		 *%'/';
		// %cli.txsinfo();
		public static void setStatic%method;(Field field, %type; value) {
			try {
				field.set%method;(null, value);
			} catch (IllegalAccessException e) {
				throw new ReflectionException(e);
			} catch (NullPointerException e) {
				throw new ReflectionException(field + " is not static", e);
			}
		}
		
		##
	}
	##
	%typeGetSet("Object", "");
	%typeGetSet("boolean", "Boolean");
	%typeGetSet("char", "Char");
	%typeGetSet("byte", "Byte");
	%typeGetSet("short", "Short");
	%typeGetSet("int", "Int");
	%typeGetSet("long", "Long");
	%typeGetSet("float", "Float");
	%typeGetSet("double", "Double");
	txs-end fieldGetSet */
	
	// generated at end of file
	
	// Standard construct/run:
	
	/**
	 * Constructs a new object using the given constructor and constructor parameters, wraps {@link Constructor#newInstance(Object...)}.
	 * @param <T> the type of the returned object
	 * @param constr the constructor reference
	 * @param param the constructor parameters
	 * @return the new object
	 * @throws ReflectionException if a {@link ReflectiveOperationException} occurs,
	 * which may be an {@link IllegalAccessException}, an {@link InstantiationException} or an {@link InvocationTargetException}.
	 */
	public static <T> T construct(Constructor<T> constr, Object... param) {
		try {
			return constr.newInstance(param);
		} catch (ReflectiveOperationException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Constructs a new object using the given constructor and no parameters.
	 * @param <T> the type of the returned object
	 * @param constr the constructor reference
	 * @return the new object
	 * @throws ReflectionException if a {@link ReflectiveOperationException} occurs
	 * @see #construct(Constructor, Object...)
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
	 * @throws ReflectionException if a {@link ReflectiveOperationException} occurs,
	 * which may be an {@link IllegalAccessException} or an {@link InvocationTargetException}.
	 */
	public static Object run(Object o, Method m, Object... param) {
		try {
			return m.invoke(o, param);
		} catch (ReflectiveOperationException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Runs the given method of the given object with no parameters.
	 * @param o the target object
	 * @param m the method reference
	 * @return the return value
	 * @throws ReflectionException if a {@link ReflectiveOperationException} occurs
	 * @see #run(Object, Method, Object...)
	 */
	public static Object run(Object o, Method m) {
		return run(o, m, EMPTY_ARRAY);
	}
	
	/**
	 * Runs the given static method using the given parameters.
	 * @param m the method reference
	 * @param param the parameters
	 * @return the return value
	 * @throws ReflectionException if a {@link ReflectiveOperationException} occurs
	 * @see #run(Object, Method, Object...)
	 */
	public static Object runStatic(Method m, Object... param) {
		return run(null, m, param);
	}
	
	/**
	 * Runs the given static method using no parameters.
	 * @param m the method reference
	 * @return the return value
	 * @throws ReflectionException if a {@link ReflectiveOperationException} occurs
	 * @see #run(Object, Method, Object...)
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
	 * @throws ReflectionException if no such field is found or an {@link IllegalAccessException} occurs
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Object o, Class<?> fieldClass, String fieldName) {
		return (T) get(o, findField(fieldClass, fieldName));
	}
	
	/**
	 * Set the current value of the field of the given class and name in the given object.
	 * For efficiency, use {@link #set(Object, Field, Object)} instead.
	 * @param o the target object
	 * @param fieldClass the field class
	 * @param fieldName the field name
	 * @param value the value for the found field
	 * @throws ReflectionException if no such field is found or an {@link IllegalAccessException} occurs
	 */
	public static void set(Object o, Class<?> fieldClass, String fieldName, Object value) {
		set(o, findField(fieldClass, fieldName), value);
	}
	
	/**
	 * Get the current value of the field of the given name in the given object.
	 * For efficiency, use {@link #get(Object, Field)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param o the target object
	 * @param fieldName the name of the field
	 * @return the value of the found field
	 * @throws ReflectionException if no such field is found or an {@link IllegalAccessException} occurs
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
	 * @throws ReflectionException if no such field is found or an {@link IllegalAccessException} occurs
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
	 * @throws ReflectionException if no such field is found or an {@link IllegalAccessException} occurs
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getStatic(Class<?> c, String fieldName) {
		return (T) get(null, c, fieldName);
	}
	
	/**
	 * Set the current value of the static field of the given class and name.
	 * For efficiency, use {@link #setStatic(Field, Object)} instead.
	 * @param c the field class
	 * @param fieldName the field name
	 * @param value the value for the found field
	 * @throws ReflectionException if no such field is found or an {@link IllegalAccessException} occurs
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
	 * @throws ReflectionException if no such class or field is found or an {@link IllegalAccessException} occurs
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
	 * @throws ReflectionException if no such class or field is found or an {@link IllegalAccessException} occurs
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
	 * @throws ReflectionException if no matching constructor is found or a {@link ReflectiveOperationException} occurs
	 * @see #construct(Constructor, Object...)
	 */
	@SuppressWarnings("unchecked")
	public static <T> T construct(Class<?> c, Object... param) {
		return (T) construct(findConstructor(c, getClasses(param)), param);
	}
	
	/**
	 * Constructs a new object of the given class name using a matching constructor.
	 * For efficiency, use {@link #construct(Constructor, Object...)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param className the name of the class to be instantiated
	 * @param param the constructor parameters
	 * @return the new object
	 * @throws ReflectionException if no such class or no matching constructor is found or
	 * an {@link ReflectiveOperationException} occurs
	 * @see #construct(Constructor, Object...)
	 */
	public static <T> T construct(String className, Object... param) {
		return construct(Class(className), param);
	}
	
	// private because this would only give the false impression one could run superclass definitions of methods with this
	@SuppressWarnings("unchecked")
	private static <T> T run(Object o, Class<?> methodClass, String methodName, Object... param) {
		return (T) run(o, findMethod(methodClass, methodName, getClasses(param)), param);
	}
	
	/**
	 * Runs a matching method of the given object and name.
	 * For efficiency, use {@link #run(Object, Method, Object...)} instead.
	 * @param <T> the returned type, automatically casted
	 * @param o the target object
	 * @param methodName the name of the method
	 * @param param the method parameters
	 * @return the return value
	 * @throws ReflectionException if no matching method is found or a {@link ReflectiveOperationException} occurs
	 * @see #run(Object, Method, Object...)
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
	 * @throws ReflectionException if no matching method is found or a {@link ReflectiveOperationException} occurs
	 * @see #run(Object, Method, Object...)
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
	 * @throws ReflectionException if no such class or matching method is found or a {@link ReflectiveOperationException} occurs
	 * @see #run(Object, Method, Object...)
	 */
	public static <T> T runStatic(String className, String methodName, Object... param) {
		return run(null, Class(className), methodName, param);
	}
	
	// TextScript generated methods:
	
	// txs-begin-gen fieldGetSet
	/**
	 * Get the current value of the given field in the given object as an Object, wraps {@link Field#get(Object)}.
	 * @param object the object
	 * @param field the field
	 * @return the value as an Object
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static Object get(Object object, Field field) {
		try {
			return field.get(object);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Set the current value of the given field in the given object to an Object, wraps {@link Field#set(Object, Object)}.
	 * @param object the object
	 * @param field the field
	 * @param value the value as an Object
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static void set(Object object, Field field, Object value) {
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get the current value of the given static field as an Object.
	 * @param field the field
	 * @return the value as an Object
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static Object getStatic(Field field) {
		try {
			return field.get(null);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Set the current value of the given static field to an Object.
	 * @param field the field
	 * @param value the value as an Object
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static void setStatic(Field field, Object value) {
		try {
			field.set(null, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Get the current value of the given field in the given object as a boolean, wraps {@link Field#getBoolean(Object)}.
	 * @param object the object
	 * @param field the field
	 * @return the value as a boolean
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static boolean getBoolean(Object object, Field field) {
		try {
			return field.getBoolean(object);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Set the current value of the given field in the given object to a boolean, wraps {@link Field#setBoolean(Object, boolean)}.
	 * @param object the object
	 * @param field the field
	 * @param value the value as a boolean
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static void setBoolean(Object object, Field field, boolean value) {
		try {
			field.setBoolean(object, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get the current value of the given static field as a boolean.
	 * @param field the field
	 * @return the value as a boolean
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static boolean getStaticBoolean(Field field) {
		try {
			return field.getBoolean(null);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Set the current value of the given static field to a boolean.
	 * @param field the field
	 * @param value the value as a boolean
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static void setStaticBoolean(Field field, boolean value) {
		try {
			field.setBoolean(null, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Get the current value of the given field in the given object as a char, wraps {@link Field#getChar(Object)}.
	 * @param object the object
	 * @param field the field
	 * @return the value as a char
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static char getChar(Object object, Field field) {
		try {
			return field.getChar(object);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Set the current value of the given field in the given object to a char, wraps {@link Field#setChar(Object, char)}.
	 * @param object the object
	 * @param field the field
	 * @param value the value as a char
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static void setChar(Object object, Field field, char value) {
		try {
			field.setChar(object, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get the current value of the given static field as a char.
	 * @param field the field
	 * @return the value as a char
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static char getStaticChar(Field field) {
		try {
			return field.getChar(null);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Set the current value of the given static field to a char.
	 * @param field the field
	 * @param value the value as a char
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static void setStaticChar(Field field, char value) {
		try {
			field.setChar(null, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Get the current value of the given field in the given object as a byte, wraps {@link Field#getByte(Object)}.
	 * @param object the object
	 * @param field the field
	 * @return the value as a byte
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static byte getByte(Object object, Field field) {
		try {
			return field.getByte(object);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Set the current value of the given field in the given object to a byte, wraps {@link Field#setByte(Object, byte)}.
	 * @param object the object
	 * @param field the field
	 * @param value the value as a byte
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static void setByte(Object object, Field field, byte value) {
		try {
			field.setByte(object, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get the current value of the given static field as a byte.
	 * @param field the field
	 * @return the value as a byte
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static byte getStaticByte(Field field) {
		try {
			return field.getByte(null);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Set the current value of the given static field to a byte.
	 * @param field the field
	 * @param value the value as a byte
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static void setStaticByte(Field field, byte value) {
		try {
			field.setByte(null, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Get the current value of the given field in the given object as a short, wraps {@link Field#getShort(Object)}.
	 * @param object the object
	 * @param field the field
	 * @return the value as a short
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static short getShort(Object object, Field field) {
		try {
			return field.getShort(object);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Set the current value of the given field in the given object to a short, wraps {@link Field#setShort(Object, short)}.
	 * @param object the object
	 * @param field the field
	 * @param value the value as a short
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static void setShort(Object object, Field field, short value) {
		try {
			field.setShort(object, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get the current value of the given static field as a short.
	 * @param field the field
	 * @return the value as a short
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static short getStaticShort(Field field) {
		try {
			return field.getShort(null);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Set the current value of the given static field to a short.
	 * @param field the field
	 * @param value the value as a short
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static void setStaticShort(Field field, short value) {
		try {
			field.setShort(null, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Get the current value of the given field in the given object as an int, wraps {@link Field#getInt(Object)}.
	 * @param object the object
	 * @param field the field
	 * @return the value as an int
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static int getInt(Object object, Field field) {
		try {
			return field.getInt(object);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Set the current value of the given field in the given object to an int, wraps {@link Field#setInt(Object, int)}.
	 * @param object the object
	 * @param field the field
	 * @param value the value as an int
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static void setInt(Object object, Field field, int value) {
		try {
			field.setInt(object, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get the current value of the given static field as an int.
	 * @param field the field
	 * @return the value as an int
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static int getStaticInt(Field field) {
		try {
			return field.getInt(null);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Set the current value of the given static field to an int.
	 * @param field the field
	 * @param value the value as an int
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static void setStaticInt(Field field, int value) {
		try {
			field.setInt(null, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Get the current value of the given field in the given object as a long, wraps {@link Field#getLong(Object)}.
	 * @param object the object
	 * @param field the field
	 * @return the value as a long
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static long getLong(Object object, Field field) {
		try {
			return field.getLong(object);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Set the current value of the given field in the given object to a long, wraps {@link Field#setLong(Object, long)}.
	 * @param object the object
	 * @param field the field
	 * @param value the value as a long
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static void setLong(Object object, Field field, long value) {
		try {
			field.setLong(object, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get the current value of the given static field as a long.
	 * @param field the field
	 * @return the value as a long
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static long getStaticLong(Field field) {
		try {
			return field.getLong(null);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Set the current value of the given static field to a long.
	 * @param field the field
	 * @param value the value as a long
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static void setStaticLong(Field field, long value) {
		try {
			field.setLong(null, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Get the current value of the given field in the given object as a float, wraps {@link Field#getFloat(Object)}.
	 * @param object the object
	 * @param field the field
	 * @return the value as a float
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static float getFloat(Object object, Field field) {
		try {
			return field.getFloat(object);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Set the current value of the given field in the given object to a float, wraps {@link Field#setFloat(Object, float)}.
	 * @param object the object
	 * @param field the field
	 * @param value the value as a float
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static void setFloat(Object object, Field field, float value) {
		try {
			field.setFloat(object, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get the current value of the given static field as a float.
	 * @param field the field
	 * @return the value as a float
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static float getStaticFloat(Field field) {
		try {
			return field.getFloat(null);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Set the current value of the given static field to a float.
	 * @param field the field
	 * @param value the value as a float
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static void setStaticFloat(Field field, float value) {
		try {
			field.setFloat(null, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Get the current value of the given field in the given object as a double, wraps {@link Field#getDouble(Object)}.
	 * @param object the object
	 * @param field the field
	 * @return the value as a double
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static double getDouble(Object object, Field field) {
		try {
			return field.getDouble(object);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Set the current value of the given field in the given object to a double, wraps {@link Field#setDouble(Object, double)}.
	 * @param object the object
	 * @param field the field
	 * @param value the value as a double
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs
	 */
	// !!! TextScript generated !!!
	public static void setDouble(Object object, Field field, double value) {
		try {
			field.setDouble(object, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		}
	}
	
	/**
	 * Get the current value of the given static field as a double.
	 * @param field the field
	 * @return the value as a double
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static double getStaticDouble(Field field) {
		try {
			return field.getDouble(null);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	/**
	 * Set the current value of the given static field to a double.
	 * @param field the field
	 * @param value the value as a double
	 * @throws ReflectionException if an {@link IllegalAccessException} occurs or the given field is not static
	 */
	// !!! TextScript generated !!!
	public static void setStaticDouble(Field field, double value) {
		try {
			field.setDouble(null, value);
		} catch (IllegalAccessException e) {
			throw new ReflectionException(e);
		} catch (NullPointerException e) {
			throw new ReflectionException(field + " is not static", e);
		}
	}
	
	// txs-end-gen fieldGetSet
	
}
