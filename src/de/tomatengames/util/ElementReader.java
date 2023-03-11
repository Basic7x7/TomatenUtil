package de.tomatengames.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * An {@link ElementReader} specifies how to read an object from an {@link InputStream}.
 * 
 * @author Basic7x7
 * @version 2023-03-11
 * @since 1.1
 *
 * @param <T> The type of the elements to read.
 * @see ElementWriter
 */
public interface ElementReader<T> {
	
	/**
	 * Reads the specified element from the {@link InputStream}.
	 * @param in The input stream. Must not be {@code null}.
	 * @return The read element. The implementation decides if the element may be {@code null}.
	 * @throws IOException If an I/O error occurs.
	 */
	public T read(InputStream in) throws IOException;
	
}
