package de.tomatengames.util.io;

import static de.tomatengames.util.HexUtil.byteToHex;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import de.tomatengames.util.HexUtil;
import de.tomatengames.util.exception.CharacterDecodeException;

/**
 * A {@link Reader} that reads bytes from an {@link InputStream} and decodes them to characters using UTF-8.
 * <p>
 * This reader is unbuffered, except for surrogate characters.
 * Each {@code read} operation may cause bytes to be read from the underlying InputStream.
 * <p>
 * This implementation supports {@link #mark(int)} if and only if the underlying InputStream supports {@link InputStream#mark(int)}.
 * 
 * @author Basic7x7
 * @version 2024-11-11
 * @since 1.7
 */
public class UTF8Reader extends Reader {
	private final InputStream in;
	private int buffer;
	private int markedBuffer;
	
	/**
	 * Creates a new {@link UTF8Reader}.
	 * @param in The {@link InputStream} that backs this reader. Not {@code null}.
	 */
	public UTF8Reader(InputStream in) {
		this.in = in;
		this.buffer = -1;
		this.markedBuffer = -1;
	}
	
	@Override
	public int read() throws IOException {
		// --- Run UTF8DecodeTestExhaustive after making changes to this method ---
		
		if (this.buffer >= 0) {
			int buf = this.buffer;
			this.buffer = -1;
			return buf;
		}
		
		int b0 = this.in.read();
		if ((b0 & 0b1000_0000) == 0) { // in particular, b0 != -1
			return b0;
		}
		else if (b0 < 0) { // read byte == -1 ==> end of the input
			return -1;
		}
		else if ((b0 & 0b1110_0000) == 0b1100_0000) {
			int b1 = checkSubsequentUTF8Byte(this.in.read());
			int result = ((b0 & 0b0001_1111) << 6) | (b1 & 0b0011_1111);
			if (result <= 0x007F) {
				throw new CharacterDecodeException("Invalid overlong UTF-8 sequence: " + byteToHex(b0) + " " + byteToHex(b1));
			}
			return result;
		}
		else if ((b0 & 0b1111_0000) == 0b1110_0000) {
			int b1 = checkSubsequentUTF8Byte(this.in.read());
			int b2 = checkSubsequentUTF8Byte(this.in.read());
			int result = ((b0 & 0b0000_1111) << 12) | ((b1 & 0b0011_1111) << 6) | (b2 & 0b0011_1111);
			if (result <= 0x07FF) {
				throw new CharacterDecodeException("Invalid overlong UTF-8 sequence: " + byteToHex(b0) + " " + byteToHex(b1) + " " + byteToHex(b2));
			}
			if (0xD800 <= result && result <= 0xDFFF) {
				throw new CharacterDecodeException("Invalid surrogate sequence in UTF-8: " + byteToHex(b0) + " " + byteToHex(b1) + " " + byteToHex(b2));
			}
			return result;
		}
		else if ((b0 & 0b1111_1000) == 0b1111_0000) {
			int b1 = checkSubsequentUTF8Byte(this.in.read());
			int b2 = checkSubsequentUTF8Byte(this.in.read());
			int b3 = checkSubsequentUTF8Byte(this.in.read());
			int result = ((b0 & 0b0000_0111) << 18) | ((b1 & 0b0011_1111) << 12) | ((b2 & 0b0011_1111) << 6) | (b3 & 0b0011_1111);
			if (result <= 0xFFFF) {
				throw new CharacterDecodeException("Invalid overlong UTF-8 sequence: " + byteToHex(b0) + " " + byteToHex(b1) + " " + byteToHex(b2) + " " + byteToHex(b3));
			}
			// Only allow 0x10FFFF code points [https://www.rfc-editor.org/rfc/rfc3629#section-3]
			if (result > 0x10FFFF) {
				throw new CharacterDecodeException("Invalid UTF-8 code point: " + Integer.toHexString(result));
			}
			this.buffer = Character.lowSurrogate(result);
			return Character.highSurrogate(result);
		}
		else {
			throw new CharacterDecodeException("Invalid first UTF-8 byte: " + HexUtil.byteToHex(b0));
		}
	}
	
	private static final int checkSubsequentUTF8Byte(int b) throws CharacterDecodeException {
		if ((b & 0b1100_0000) == 0b1000_0000) { // in particular, b != -1
			return b;
		}
		if (b < 0) {
			throw new CharacterDecodeException("Invalid UTF-8 sequence: Reached the end of the input");
		}
		throw new CharacterDecodeException("Invalid subsequent UTF-8 byte: " + byteToHex(b));
	}
	
	
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if (off < 0 || len < 0 || len > cbuf.length - off) {
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = 0; i < len; i++) {
			int ch = this.read();
			if (ch < 0) {
				if (i == 0) {
					return -1;
				}
				return i;
			}
			cbuf[off+i] = (char) ch;
		}
		return len;
	}
	
	
	@Override
	public boolean markSupported() {
		return this.in.markSupported();
	}
	
	@Override
	public void mark(int readAheadLimit) throws IOException {
		this.in.mark(readAheadLimit*4);
		this.markedBuffer = this.buffer;
	}
	
	@Override
	public void reset() throws IOException {
		this.in.reset();
		this.buffer = this.markedBuffer;
	}
	
	
	@Override
	public void close() throws IOException {
		this.in.close();
		this.buffer = -1; // Prevents read() from returning a next character
		this.markedBuffer = -1;
	}
}
