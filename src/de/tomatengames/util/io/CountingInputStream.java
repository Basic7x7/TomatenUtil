package de.tomatengames.util.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A {@link FilterInputStream} that counts the bytes read from it.
 * 
 * @author Basic7x7
 * @version 2023-04-12
 * @since 1.2
 */
public class CountingInputStream extends FilterInputStream {
	private long byteCount;
	
	/**
	 * Creates a new {@link CountingInputStream}.
	 * @param in The underlying {@link InputStream}.
	 */
	public CountingInputStream(InputStream in) {
		super(in);
		this.byteCount = 0L;
	}
	
	@Override
	public int read() throws IOException {
		int r = this.in.read();
		if (r >= 0) {
			this.byteCount++;
		}
		return r;
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		int n = this.in.read(b);
		if (n >= 0) {
			this.byteCount += n;
		}
		return n;
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int n = this.in.read(b, off, len);
		if (n >= 0) {
			this.byteCount += n;
		}
		return n;
	}
	
	/**
	 * Returns the number of read bytes.
	 * @return The number of read bytes.
	 */
	public long getByteCount() {
		return this.byteCount;
	}
}
