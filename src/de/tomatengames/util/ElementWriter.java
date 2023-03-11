package de.tomatengames.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An {@link ElementWriter} specifies how to write an object to an {@link OutputStream}.
 * 
 * @author Basic7x7
 * @version 2023-03-11
 * @since 1.1
 *
 * @param <T> The type of the elements to write.
 * @see ElementReader
 */
@FunctionalInterface
public interface ElementWriter<T> {
	
	/**
	 * Writes the specified element to the {@link OutputStream}.
	 * @param el The element. The implementation decides if the element may be {@code null}.
	 * @param out The output stream. Must not be {@code null}.
	 * @throws IOException If an I/O error occurs.
	 */
	public void write(T el, OutputStream out) throws IOException;
	
}
