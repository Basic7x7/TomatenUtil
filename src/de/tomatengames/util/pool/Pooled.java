package de.tomatengames.util.pool;

public interface Pooled<E> extends AutoCloseable {
	
	E get();
	void free();
	
	@Override
	default void close() {
		free();
	}
}
