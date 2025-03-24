package de.tomatengames.util.pool;

public interface Pool<E> {
	Pooled<E> claim();
}
