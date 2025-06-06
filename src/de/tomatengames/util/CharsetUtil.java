package de.tomatengames.util;

import static de.tomatengames.util.HexUtil.byteToHex;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.tomatengames.util.exception.CharacterDecodeException;
import de.tomatengames.util.exception.LimitException;

/**
 * Provides utilities to work with character encodings.
 * 
 * @author Basic7x7
 * @version 2024-11-10 last modified
 * @version 2023-04-11 created
 * @since 1.2
 */
public class CharsetUtil {
	
	// Static class
	private CharsetUtil() {
	}
	
	/**
	 * Encodes the specified Unicode code point using UTF-8.
	 * @param codePoint The code point.
	 * @return The encoded UTF-8 bytes combined into an integer.
	 * Higher order bytes that are {@code 0x00} are not part of the encoded code point.
	 * The lowest order byte is always part of the encoded code point.
	 * For example, let {@code enc} be the returned value.
	 * If {@code (enc & 0xFFFFFF00) == 0}, the code point is encoded as a single byte.
	 * If {@code (enc & 0xFFFF0000) == 0} and {@code (enc & 0xFFFFFF00) != 0},
	 * the code point is encoded as two bytes.
	 * @throws IllegalArgumentException If the code point is out of range.
	 */
	public static int encodeUTF8(int codePoint) {
		if (codePoint < 0) {
			throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint));
		}
		if (codePoint <= 0x7F) {
			return codePoint;
		}
		if (codePoint <= 0x7FF) {
			return 0b110_00000__10_000000
					// Bits 0-6 -> 0-6
					| (codePoint & 0b000_00000__00_111111)
					// Bits 6-11 -> 8-13
					| ((codePoint << 2) & 0b000_11111__00_000000);
		}
		if (codePoint <= 0xFFFF) {
			if (0xD800 <= codePoint && codePoint <= 0xDFFF) {
				throw new IllegalArgumentException("Invalid surrogate code point " + Integer.toHexString(codePoint));
			}
			return 0b1110_0000__10_000000__10_000000
					// Bits 0-6 -> 0-6
					| (codePoint & 0b0000_0000__00_000000__00_111111)
					// Bits 6-12 -> 8-14
					| ((codePoint << 2) & 0b0000_0000__00_111111__00_000000)
					// Bits 12-16 -> 16-20
					| ((codePoint << 4) & 0b0000_1111__00_000000__00_000000);
		}
		// Only allow 0x10FFFF codePoints [https://www.rfc-editor.org/rfc/rfc3629#section-3]
		if (codePoint <= 0x10FFFF) {
			return 0b11110_000__10_000000__10_000000__10_000000
					// Bits 0-6 -> 0-6
					| (codePoint & 0b00000_000__00_000000__00_000000__00_111111)
					// Bits 6-12 -> 8-14
					| ((codePoint << 2) & 0b00000_000__00_000000__00_111111__00_000000)
					// Bits 12-18 -> 16-22
					| ((codePoint << 4) & 0b00000_000__00_111111__00_000000__00_000000)
					// Bits 18-21 -> 24-27
					| ((codePoint << 6) & 0b00000_111__00_000000__00_000000__00_000000);
		}
		throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint));
	}
	
	
	/**
	 * Encodes the specified Unicode code point using UTF-8
	 * and writes the result into the {@link OutputStream}.
	 * @param codePoint The code point that should be encoded.
	 * @param out The output stream. Must not be {@code null}.
	 * @return The amount of bytes written. Minimum 1 and maximum 4.
	 * @throws IOException If an I/O error occurs.
	 * @throws IllegalArgumentException If the code point is out of range.
	 * @implNote In most cases the return value is summed.
	 * To make it easier to sum large outputs, the return type is {@code long} instead of {@code int}.
	 */
	public static long encodeUTF8(int codePoint, OutputStream out) throws IOException {
		if (codePoint < 0) {
			throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint));
		}
		if (codePoint <= 0x7F) {
			out.write(codePoint);
			return 1L;
		}
		if (codePoint <= 0x7FF) {
			out.write(0b110_00000 | ((codePoint >>> 6) & 0b11111)); // Bits 6-11
			out.write(0b10_000000 | (codePoint & 0b111111)); // Bits 0-6
			return 2L;
		}
		if (codePoint <= 0xFFFF) {
			if (0xD800 <= codePoint && codePoint <= 0xDFFF) {
				throw new IllegalArgumentException("Invalid surrogate code point " + Integer.toHexString(codePoint));
			}
			out.write(0b1110_0000 | ((codePoint >>> 12) & 0b1111)); // Bits 12-16
			out.write(0b10_000000 | ((codePoint >>> 6) & 0b111111)); // Bits 6-12
			out.write(0b10_000000 | (codePoint & 0b111111)); // Bits 0-6
			return 3L;
		}
		// Only allow 0x10FFFF codePoints [https://www.rfc-editor.org/rfc/rfc3629#section-3]
		if (codePoint <= 0x10FFFF) {
			out.write(0b11110_000 | ((codePoint >>> 18) & 0b111)); // Bits 18-21
			out.write(0b10_000000 | ((codePoint >>> 12) & 0b111111)); // Bits 12-18
			out.write(0b10_000000 | ((codePoint >>> 6) & 0b111111)); // Bits 6-12
			out.write(0b10_000000 | (codePoint & 0b111111)); // Bits 0-6
			return 4L;
		}
		throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint) + "!");
	}
	
	
	/**
	 * Encodes the specified {@link String} using UTF-8 and writes the result into the {@link OutputStream}.
	 * @param str The string that should be encoded. Not {@code null}.
	 * @param out The output stream. Not {@code null}.
	 * @return The amount of bytes written.
	 * @throws IOException If an I/O error occurs.
	 * @throws IllegalArgumentException e.g. if a code point is out of range.
	 */
	public static long encodeUTF8(String str, OutputStream out) throws IOException {
		long written = 0L;
		int n = str.length();
		for (int i = 0; i < n;) {
			int codePoint = str.codePointAt(i);
			written += encodeUTF8(codePoint, out);
			i += Character.charCount(codePoint);
		}
		return written;
	}
	
	/**
	 * Encodes the specified {@link String} using UTF-8 and writes the result into the {@link OutputStream}.
	 * <p>
	 * Up to {@code maxOutput+3} bytes may be written to the output.
	 * If more than {@code maxOutput} bytes should be written to the output, a {@link LimitException} is thrown.
	 * @param str The string that should be encoded. Not {@code null}.
	 * @param out The output stream. Not {@code null}.
	 * @param maxOutput The maximum output byte length. Must not be negative.
	 * @return The amount of bytes written.
	 * @throws IOException If an I/O error occurs.
	 * @throws IllegalArgumentException e.g. if a code point is out of range.
	 * @throws LimitException If the maximum output length is exceeded.
	 * @since 1.4
	 */
	public static long encodeUTF8(String str, OutputStream out, long maxOutput) throws IOException {
		long written = 0L;
		int n = str.length();
		for (int i = 0; i < n;) {
			int codePoint = str.codePointAt(i);
			if (written >= maxOutput) {
				throw new LimitException();
			}
			written += encodeUTF8(codePoint, out);
			i += Character.charCount(codePoint);
		}
		// Check if the last character exceeded the maxOutput limit.
		if (written > maxOutput) {
			throw new LimitException();
		}
		return written;
	}
	
	/**
	 * Encodes the specified {@link String} using UTF-8 and writes the result into the {@link OutputStream}.
	 * The buffer is used to reduce the write operations to the OutputStream.
	 * @param str The string that should be encoded. Not {@code null}.
	 * @param out The output stream. Not {@code null}.
	 * @param buf The buffer. Not {@code null}. The length of the buffer must be at least {@code 4}.
	 * Any positions of the buffer could be written by this method.
	 * @return The amount of bytes written.
	 * @throws IOException If an I/O error occurs.
	 * @throws IllegalArgumentException e.g. if a code point is out of range.
	 * @since 1.7
	 */
	public static long encodeUTF8(String str, OutputStream out, byte[] buf) throws IOException {
		// Only written > maxOutput is checked
		// ==> maxOutput = Long.MAX_VALUE will never throw a LimitException
		return encodeUTF8(str, out, Long.MAX_VALUE, buf);
	}
	
	/**
	 * Encodes the specified {@link String} using UTF-8 and writes the result into the {@link OutputStream}.
	 * The buffer is used to reduce the write operations to the OutputStream.
	 * <p>
	 * Up to {@code maxOutput} bytes may be written to the output.
	 * If more than {@code maxOutput} bytes should be written to the output, a {@link LimitException} is thrown.
	 * @param str The string that should be encoded. Not {@code null}.
	 * @param out The output stream. Not {@code null}.
	 * @param maxOutput The maximum output byte length. Must not be negative.
	 * @param buf The buffer. Not {@code null}. The length of the buffer must be at least {@code 4}.
	 * Any positions of the buffer could be written by this method.
	 * @return The amount of bytes written.
	 * @throws IOException If an I/O error occurs.
	 * @throws IllegalArgumentException e.g. if a code point is out of range.
	 * @throws LimitException If the maximum output length is exceeded.
	 * @since 1.7
	 */
	public static long encodeUTF8(String str, OutputStream out, long maxOutput, byte[] buf) throws IOException {
		long written = 0L;
		int bufMax = buf.length-4;
		int n = str.length();
		for (int i = 0; i < n;) {
			// Fill the buffer and write it to the output.
			int bufCursor = 0;
			while (i < n && bufCursor <= bufMax) {
				int codePoint = str.codePointAt(i);
				bufCursor += CharsetUtil.encodeUTF8(codePoint, buf, bufCursor);
				i += Character.charCount(codePoint);
			}
			written += bufCursor;
			if (written > maxOutput) {
				throw new LimitException();
			}
			out.write(buf, 0, bufCursor);
		}
		return written;
	}
	
	/**
	 * Encodes the specified Unicode code point using UTF-8
	 * and writes the result into the output array at the specified offset.
	 * Depending on the code point, 1 to 4 bytes are written.
	 * @param codePoint The Unicode code point that should be encoded.
	 * @param out The output array into which the encoded data should be written.
	 * Must not be {@code null}. Must be long enough to store the encoded data.
	 * It is recommended that the length is at least {@code offset+4}.
	 * @param offset The start position in the output array to be written.
	 * Must not be negative.
	 * @return The amount of bytes written. Minimum 1 and maximum 4.
	 * @throws IndexOutOfBoundsException If the offset is negative
	 * or the output array is too short to store the encoded code point.
	 * @throws IllegalArgumentException If the code point is out of range.
	 * In this case no bytes are written.
	 * @since 1.3
	 */
	public static int encodeUTF8(int codePoint, byte[] out, int offset) {
		if (codePoint < 0) {
			throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint));
		}
		if (codePoint <= 0x7F) {
			out[offset] = (byte) codePoint;
			return 1;
		}
		if (codePoint <= 0x7FF) {
			out[offset] = (byte) (0b110_00000 | ((codePoint >>> 6) & 0b11111)); // Bits 6-11
			out[offset+1] = (byte) (0b10_000000 | (codePoint & 0b111111)); // Bits 0-6
			return 2;
		}
		if (codePoint <= 0xFFFF) {
			if (0xD800 <= codePoint && codePoint <= 0xDFFF) {
				throw new IllegalArgumentException("Invalid surrogate code point " + Integer.toHexString(codePoint));
			}
			out[offset] = (byte) (0b1110_0000 | ((codePoint >>> 12) & 0b1111)); // Bits 12-16
			out[offset+1] = (byte) (0b10_000000 | ((codePoint >>> 6) & 0b111111)); // Bits 6-12
			out[offset+2] = (byte) (0b10_000000 | (codePoint & 0b111111)); // Bits 0-6
			return 3;
		}
		// Only allow 0x10FFFF codePoints [https://www.rfc-editor.org/rfc/rfc3629#section-3]
		if (codePoint <= 0x10FFFF) {
			out[offset] = (byte) (0b11110_000 | ((codePoint >>> 18) & 0b111)); // Bits 18-21
			out[offset+1] = (byte) (0b10_000000 | ((codePoint >>> 12) & 0b111111)); // Bits 12-18
			out[offset+2] = (byte) (0b10_000000 | ((codePoint >>> 6) & 0b111111)); // Bits 6-12
			out[offset+3] = (byte) (0b10_000000 | (codePoint & 0b111111)); // Bits 0-6
			return 4;
		}
		throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint));
	}
	
	/**
	 * Encodes the specified {@link String} using UTF-8 and writes the result into the byte array.
	 * @param str The string that should be encoded. Not {@code null}.
	 * @param out The output array into which the encoded data should be written.
	 * Must not be {@code null}. Must be long enough to store the encoded data.
	 * It is recommended that the length is at least {@code offset+4*str.length()}.
	 * @param offset The start position in the output array to be written. Must not be negative.
	 * @return The amount of bytes written.
	 * @throws IndexOutOfBoundsException If the offset is negative
	 * or the output array is too short to store the encoded code point.
	 * @throws IllegalArgumentException If a code point is out of range.
	 * @since 1.4
	 */
	public static int encodeUTF8(String str, byte[] out, int offset) {
		int written = 0;
		int n = str.length();
		for (int i = 0; i < n;) {
			int codePoint = str.codePointAt(i);
			int b = encodeUTF8(codePoint, out, offset);
			written += b;
			offset += b;
			i += Character.charCount(codePoint);
		}
		return written;
	}
	
	
	/**
	 * Decodes one UTF-8 code point from the {@link InputStream}.
	 * @param in The InputStream from which the code point should be read. Not {@code null}.
	 * 1 to 4 bytes will be read depending on the code point.
	 * @return The decoded code point in the range from {@code 0} to {@code 10FFFF},
	 * or -1 if the end of the stream is reached.
	 * @throws CharacterDecodeException If the UTF-8 code point cannot be decoded.
	 * @throws IOException If an I/O error occurs.
	 * @since 1.6
	 */
	public static int decodeUTF8(InputStream in) throws IOException, CharacterDecodeException {
		// --- Run UTF8DecodeTestExhaustive after making changes to this method ---
		int b0 = in.read();
		if ((b0 & 0b1000_0000) == 0) { // in particular, b0 != -1
			return b0;
		}
		else if (b0 < 0) { // read byte == -1 ==> end of the input
			return -1;
		}
		else if ((b0 & 0b1110_0000) == 0b1100_0000) {
			int b1 = checkSubsequentUTF8Byte(in.read());
			int result = ((b0 & 0b0001_1111) << 6) | (b1 & 0b0011_1111);
			if (result <= 0x007F) {
				throw new CharacterDecodeException("Invalid overlong UTF-8 sequence: " + byteToHex(b0) + " " + byteToHex(b1));
			}
			return result;
		}
		else if ((b0 & 0b1111_0000) == 0b1110_0000) {
			int b1 = checkSubsequentUTF8Byte(in.read());
			int b2 = checkSubsequentUTF8Byte(in.read());
			int result = ((b0 & 0b0000_1111) << 12) | ((b1 & 0b0011_1111) << 6) | (b2 & 0b0011_1111);
			if (result <= 0x07FF) {
				throw new CharacterDecodeException("Invalid overlong UTF-8 sequence: " + byteToHex(b0) + " " + byteToHex(b1) + " " + byteToHex(b2));
			}
			if (0xD800 <= result && result <= 0xDFFF) {
				throw new CharacterDecodeException("Invalid surrogate in UTF-8: " + Integer.toHexString(result));
			}
			return result;
		}
		else if ((b0 & 0b1111_1000) == 0b1111_0000) {
			int b1 = checkSubsequentUTF8Byte(in.read());
			int b2 = checkSubsequentUTF8Byte(in.read());
			int b3 = checkSubsequentUTF8Byte(in.read());
			int result = ((b0 & 0b0000_0111) << 18) | ((b1 & 0b0011_1111) << 12) | ((b2 & 0b0011_1111) << 6) | (b3 & 0b0011_1111);
			if (result <= 0xFFFF) {
				throw new CharacterDecodeException("Invalid overlong UTF-8 sequence: " + byteToHex(b0) + " " + byteToHex(b1) + " " + byteToHex(b2) + " " + byteToHex(b3));
			}
			// Only allow 0x10FFFF code points [https://www.rfc-editor.org/rfc/rfc3629#section-3]
			if (result > 0x10FFFF) {
				throw new CharacterDecodeException("Invalid UTF-8 code point: " + Integer.toHexString(result));
			}
			return result;
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
}
