package de.tomatengames.util.io;

import java.io.OutputStream;

/**
 * An {@link OutputStream} that ignores all data written to it.
 * <p>
 * Note that the {@code write(...)} methods do not check if the parameters are valid.
 * Closing this stream has no effect.
 * 
 * @author Basic7x7
 * @version 2023-04-12
 * @since 1.2
 */
public class VoidOutputStream extends OutputStream {
	
	@Override
	public void write(int b) {
	}
	
	@Override
	public void write(byte[] b) {
	}
	
	@Override
	public void write(byte[] b, int off, int len) {
	}
}
