package de.tomatengames.util.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A {@link FilterOutputStream} that counts the amount of bytes written to it.
 * The data is passed to an underlying {@link OutputStream}.
 * <p>
 * Note that this class does not check the parameters passed to the {@code write(...)} methods,
 * but the underlying stream may do so.
 * <p>
 * Closing this stream closes the underlying stream.
 * 
 * @author Basic7x7
 * @version 2023-04-12
 * @since 1.2
 */
public class CountingOutputStream extends FilterOutputStream {
	private long bytesCount;
	
	/**
	 * Creates a new {@link CountingOutputStream}.
	 * @param out The underlying {@link OutputStream}.
	 */
	public CountingOutputStream(OutputStream out) {
		super(out);
		this.bytesCount = 0L;
	}
	
	@Override
	public void write(int b) throws IOException {
		this.out.write(b);
		this.bytesCount++;
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		this.out.write(b);
		this.bytesCount += b.length;
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		this.out.write(b, off, len);
		this.bytesCount += len;
	}
	
	/**
	 * Returns the amount of bytes written to this stream.
	 * @return The amount of bytes written.
	 */
	public long getBytesCount() {
		return bytesCount;
	}
}
