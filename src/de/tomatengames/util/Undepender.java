package de.tomatengames.util;

import static de.tomatengames.util.RequirementUtil.requireNotNull;

import java.util.HashMap;

public class Undepender {
	private static final HashMap<String, Object> map = new HashMap<>();
	private static final Object lock = new Object();
	
	private Undepender() {
	}
	
	public static void set(String name, Object value) {
		requireNotNull("The name ...", name);
		synchronized (lock) {
			if (value != null) {
				map.put(name, value);
			}
			else {
				map.remove(name);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String name) {
		synchronized (lock) {
			return (T) map.get(name);
		}
	}
}
