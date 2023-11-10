package de.tomatengames.util;

import java.io.IOException;
import java.io.OutputStream;

import de.tomatengames.util.exception.LimitException;

/**
 * Provides utilities to work with character encodings.
 * 
 * @author Basic7x7
 * @version
 * 2023-11-10 last modified<br>
 * 2023-04-11 created
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
			throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint) + "!");
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
		throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint) + "!");
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
			throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint) + "!");
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
	 * @throws IOException If an I/O error occurs or a code point is out of range.
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
	 * If more than {@code maxOutput} bytes are written to the output, an {@link LimitException} is thrown.
	 * @param str The string that should be encoded. Not {@code null}.
	 * @param out The output stream. Not {@code null}.
	 * @param maxOutput The maximum output byte length. Must not be negative.
	 * @return The amount of bytes written.
	 * @throws IOException If an I/O error occurs or a code point is out of range.
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
			throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint) + "!");
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
		throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint) + "!");
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
}
