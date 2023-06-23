package de.tomatengames.util.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A {@link FilterInputStream} that counts the bytes read from it.
 * 
 * @author Basic7x7
 * @version
 * 2023-06-23 modified<br>
 * 2023-04-12 created
 * @since 1.2
 */
public class CountingInputStream extends FilterInputStream {
	private long byteCount;
	private long markedByteCount;
	
	/**
	 * Creates a new {@link CountingInputStream}.
	 * @param in The underlying {@link InputStream}.
	 */
	public CountingInputStream(InputStream in) {
		super(in);
		this.byteCount = 0L;
		this.markedByteCount = 0L;
	}
	
	/**
	 * Returns the number of read bytes.
	 * @return The number of read bytes.
	 */
	public long getByteCount() {
		return this.byteCount;
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
	
	@Override
	public long skip(long n) throws IOException {
		long len = this.in.skip(n);
		if (len > 0L) {
			this.byteCount += len;
		}
		return len;
	}
	
	@Override
	public synchronized void mark(int readlimit) {
		this.in.mark(readlimit);
		this.markedByteCount = this.byteCount;
	}
	
	@Override
	public synchronized void reset() throws IOException {
		this.in.reset();
		this.byteCount = this.markedByteCount;
	}
}
