package de.tomatengames.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Provides utilities to work with character encodings.
 * 
 * @author Basic7x7
 * @version 2023-04-11
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
	 * @throws IOException If an I/O error occurs.
	 * @throws IllegalArgumentException If the code point is out of range.
	 */
	public static void encodeUTF8(int codePoint, OutputStream out) throws IOException {
		if (codePoint < 0) {
			throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint) + "!");
		}
		if (codePoint <= 0x7F) {
			out.write(codePoint);
			return;
		}
		if (codePoint <= 0x7FF) {
			out.write(0b110_00000 | ((codePoint >>> 6) & 0b11111)); // Bits 6-11
			out.write(0b10_000000 | (codePoint & 0b111111)); // Bits 0-6
			return;
		}
		if (codePoint <= 0xFFFF) {
			out.write(0b1110_0000 | ((codePoint >>> 12) & 0b1111)); // Bits 12-16
			out.write(0b10_000000 | ((codePoint >>> 6) & 0b111111)); // Bits 6-12
			out.write(0b10_000000 | (codePoint & 0b111111)); // Bits 0-6
			return;
		}
		// Only allow 0x10FFFF codePoints [https://www.rfc-editor.org/rfc/rfc3629#section-3]
		if (codePoint <= 0x10FFFF) {
			out.write(0b11110_000 | ((codePoint >>> 18) & 0b111)); // Bits 18-21
			out.write(0b10_000000 | ((codePoint >>> 12) & 0b111111)); // Bits 12-18
			out.write(0b10_000000 | ((codePoint >>> 6) & 0b111111)); // Bits 6-12
			out.write(0b10_000000 | (codePoint & 0b111111)); // Bits 0-6
			return;
		}
		throw new IllegalArgumentException("Invalid code point " + Integer.toHexString(codePoint) + "!");
	}
	
	
	/**
	 * Encodes the specified {@link String} using UTF-8 and writes the result into the {@link OutputStream}.
	 * @param str The string that should be encoded.
	 * @param out The output stream.
	 * @throws IOException If an I/O error occurs or a code point is out of range.
	 */
	public static void encodeUTF8(String str, OutputStream out) throws IOException {
		int n = str.length();
		for (int i = 0; i < n; i++) {
			char ch = str.charAt(i);
			// Get the code point at the current index.
			int codePoint = ch;
			if (Character.isHighSurrogate(ch) && ++i < n) {
				char low = str.charAt(i);
				if (Character.isLowSurrogate(low)) {
					codePoint = Character.toCodePoint(ch, low);
				}
				else {
					i--; // Revert the ++i
				}
			}
			encodeUTF8(codePoint, out);
		}
	}
}
