package de.tomatengames.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Provides methods to convert between a byte array and Base32.
 * 
 * @author LukasE7x7
 * @version 2023-06-18
 * @since 1.3
 */
public class Base32Util {
	
	private Base32Util() {
	}
	
	/**
	 * Converts the specified Base32 string into a byte array.
	 * <p>
	 * Does not currently detect invalid padding.
	 * @param base32 The Base32 string.
	 * Each character my be in lower or upper case. Must not be {@code null}.
	 * @return The bytes represented by the Base32 string. Not {@code null}.
	 * @throws IllegalArgumentException If the string contains non-Base32 characters.
	 */
	public static byte[] base32ToBytes(String base32) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			base32ToBytes(base32.toCharArray(), bout);
		} catch (IOException e) {
			throw new AssertionError(e);
		}
		return bout.toByteArray();
	}
	
	/**
	 * Writes the bytes represented by the specified Base32 string into the {@link OutputStream}.
	 * <p>
	 * Does not currently detect invalid padding.
	 * @param base32Chars The characters of the Base32 string.
	 * Each character my be in lower or upper case. Must not be {@code null}.
	 * @param out The output stream. Must not be {@code null}.
	 * @throws IOException If bytes cannot be written to the output stream.
	 * @throws IllegalArgumentException If the string contains non-Base32 characters.
	 */
	public static void base32ToBytes(char[] base32Chars, OutputStream out) throws IOException {
		int bits = 0;
		int bitpos = 0;
		for (int i = 0; i < base32Chars.length; i++) {
			char c = base32Chars[i];
			if (c == '=') {
				bitpos = 0;
				continue;
			}
			int charvalue = fromBase32Char(c);
			bits = (bits << 5) | charvalue;
			bitpos += 5;
			if (bitpos < 8)
				continue;
			out.write(bits >>> (bitpos -= 8));
		}
	}
	
	/**
	 * Converts the specified byte array to a Base32 string.
	 * @param bytes The bytes that should be converted. Must not be {@code null}.
	 * @param padding If a padding with {@code '='} characters
	 * should be added to the end of the Base32 string.
	 * @return The Base32 string. Not {@code null}.
	 */
	public static String bytesToBase32(byte[] bytes, boolean padding) {
		int bytelen = bytes.length;
		int charlen = (bytelen * 8 + 4) / 5;
		int padlen;
		if (padding) {
			padlen = charlen & 7;
			if (padlen > 0)
				padlen = 8 - padlen;
		} else {
			padlen = 0;
		}
		char[] out = new char[charlen + padlen];
		for (int i = 0; i < charlen; i++) {
			int bitpos = i * 5;
			int bytepos = bitpos >> 3;
			int byte1 = bytes[bytepos];
			int byte2 = bytepos + 1 < bytes.length ? bytes[bytepos + 1] : 0;
			int innerbitpos = bitpos & 7;
			int charvalue;
			switch (innerbitpos) {
				case 0: charvalue = (byte1 & 0b11111000) >>> 3; break;
				case 1: charvalue = (byte1 & 0b01111100) >>> 2; break;
				case 2: charvalue = (byte1 & 0b00111110) >>> 1; break;
				case 3: charvalue = (byte1 & 0b00011111); break;
				case 4: charvalue = ((byte1 & 0b00001111) << 1) | ((byte2 & 0b10000000) >>> 7); break;
				case 5: charvalue = ((byte1 & 0b00000111) << 2) | ((byte2 & 0b11000000) >>> 6); break;
				case 6: charvalue = ((byte1 & 0b00000011) << 3) | ((byte2 & 0b11100000) >>> 5); break;
				case 7: charvalue = ((byte1 & 0b00000001) << 4) | ((byte2 & 0b11110000) >>> 4); break;
				default:
					throw new AssertionError();
			}
			out[i] = toBase32Char(charvalue);
		}
		for (int i = 0; i < padlen; i++) {
			out[charlen + i] = '=';
		}
		return new String(out);
	}
	
	/**
	 * Converts the specified 5 bit integer to the corresponding Base32 character.
	 * @param value5bit The 5 bit integer.
	 * @return The Base32 character in upper case.
	 * @throws IllegalArgumentException If the specified integer is outside the range {@code [0, 0b11111]}.
	 */
	public static char toBase32Char(int value5bit) {
		if (value5bit < 0 || value5bit > 0b11111)
			throw new IllegalArgumentException("Cannot represent " + value5bit + " with one Base32 character");
		if (value5bit < 26)
			return (char)('A' + value5bit);
		return (char)('2' + value5bit - 26);
	}
	
	/**
	 * Converts the specified Base32 character to the 5 bit integer it represents.
	 * @param c The Base32 character. May be in lower or upper case.
	 * @return The 5 bit integer.
	 * @throws IllegalArgumentException If the specified character is not a Base32 character.
	 */
	public static int fromBase32Char(char c) {
		if (c >= 'A' && c <= 'Z')
			return c - 'A';
		if (c >= 'a' && c <= 'z')
			return c - 'a';
		if (c >= '2' && c <= '7')
			return c - '2' + 26;
		throw new IllegalArgumentException("'" + c + "' is not a Base32 character");
	}
	
}
