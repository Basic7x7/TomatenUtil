package de.tomatengames.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Provides methods to work with hexadecimal strings.
 * 
 * @author Basic7x7
 * @version 2024-05-21 last modified
 * @version 2023-01-24 created
 * @since 1.0
 */
public class HexUtil {
	private static final char[] hexChars = "0123456789abcdef".toCharArray();
	
	// Static class
	private HexUtil() {
	}
	
	/**
	 * Returns the hexadecimal character that represents the specified value.
	 * @param value The value (0-15).
	 * @return The hexadecimal character (0-9, a-f).
	 * @throws IllegalArgumentException If the value is out of range.
	 */
	public static char toHexChar(int value) {
		if (value < 0x0 || value > 0xF) {
			throw new IllegalArgumentException("Value " + value + " cannot be represented as a hex char!");
		}
		return hexChars[value];
	}
	
	/**
	 * Converts the specified byte array into a hexadecimal string.
	 * Each byte is represented by 2 characters.
	 * @param bytes The byte array.
	 * @return The hexadecimal string. Not {@code null}. Alphabetic characters are in lower case.
	 * @throws NullPointerException If the byte array is {@code null}.
	 */
	public static String bytesToHex(byte[] bytes) {
		int n = bytes.length;
		char[] chars = new char[n*2];
		for (int i = 0; i < n; i++) {
			int b = bytes[i];
			chars[2*i] = hexChars[(b >>> 4) & 0xF];
			chars[2*i+1] = hexChars[b & 0xF];
		}
		return new String(chars);
	}
	
	/**
	 * Converts the bits of the specified {@code long} into a hexadecimal string.
	 * The first character represents the most significant bits.
	 * @param value The {@code long}.
	 * @return The hexadecimal string. Not {@code null}. The length is always {@code 16}.
	 * Alphabetic characters are in lower case.
	 */
	public static String longToHex(long value) {
		char[] chars = new char[Long.BYTES*2];
		for (int i = 0; i < Long.BYTES*2; i++) {
			chars[i] = hexChars[(int) ((value >>> (4*((Long.BYTES*2-1)-i))) & 0xF)];
		}
		return new String(chars);
	}
	
	/**
	 * Converts the bits of the specified {@code int} into a hexadecimal string.
	 * The first character represents the most significant bits.
	 * @param value The {@code int}.
	 * @return The hexadecimal string. Not {@code null}. The length is always {@code 8}.
	 * Alphabetic characters are in lower case.
	 */
	public static String intToHex(int value) {
		char[] chars = new char[Integer.BYTES*2];
		for (int i = 0; i < Integer.BYTES*2; i++) {
			chars[i] = hexChars[(value >>> (4*((Integer.BYTES*2-1)-i))) & 0xF];
		}
		return new String(chars);
	}
	
	/**
	 * Converts the 8 least significant bits of the specified {@code int} into a hexadecimal string.
	 * The other bits are ignored.
	 * The first character of the result represents the most significant bits.
	 * @param value The {@code int} value.
	 * @return The hexadecimal string. Not {@code null}. The length is always {@code 2}.
	 * Alphabetic characters are in lower case.
	 * @since 1.6
	 */
	public static String byteToHex(int value) {
		return new String(new char[] { hexChars[(value >>> 4) & 0xF], hexChars[value & 0xF] });
	}
	
	/**
	 * Converts the 16 least significant bits of the specified {@code int} into a hexadecimal string.
	 * The other bits are ignored.
	 * The first character of the result represents the most significant bits.
	 * @param value The {@code int} value.
	 * @return The hexadecimal string. Not {@code null}. The length is always {@code 4}.
	 * Alphabetic characters are in lower case.
	 * @since 1.6
	 */
	public static String shortToHex(int value) {
		return new String(new char[] {
				hexChars[(value >>> 12) & 0xF],
				hexChars[(value >>> 8) & 0xF],
				hexChars[(value >>> 4) & 0xF],
				hexChars[value & 0xF] });
	}
	
	/**
	 * Reads the specified {@link InputStream} and returns its content as a hex string.
	 * Each byte of the input is represented by 2 characters.
	 * @param input The input stream.
	 * @return The hexadecimal string. Not {@code null}. Alphabetic characters are in lower case.
	 * @throws IOException If an I/O error occurs.
	 * @throws NullPointerException If the input stream is {@code null}.
	 */
	public static String streamToHex(InputStream input) throws IOException {
		StringBuilder builder = new StringBuilder();
		int b;
		while ((b = input.read()) >= 0) {
			builder.append(hexChars[(b >>> 4) & 0xF]);
			builder.append(hexChars[b & 0xF]);
		}
		return builder.toString();
	}
	
	/**
	 * Reads the specified file and returns its content as a hex string.
	 * Each byte of the file is represented by 2 characters.
	 * @param path The path to the file.
	 * @return The hexadecimal string. Not {@code null}. Alphabetic characters are in lower case.
	 * @throws IOException If an I/O error occurs.
	 * @throws NullPointerException If the path is {@code null}.
	 */
	public static String fileToHex(Path path) throws IOException {
		try (InputStream in = new BufferedInputStream(Files.newInputStream(path))) {
			return streamToHex(in);
		}
	}
	
	
	/**
	 * Returns the value of the specified hexadecimal character.
	 * @param hexChar The hexadecimal character (0-9, a-f, A-F).
	 * @return The value that the character represents (0-15).
	 * It is guaranteed that only the 4 least significant bits may be set.
	 * @throws IllegalArgumentException If the character is not a hex character.
	 */
	public static int parseHexChar(char hexChar) {
		switch (hexChar) {
		case '0': return 0x0;
		case '1': return 0x1;
		case '2': return 0x2;
		case '3': return 0x3;
		case '4': return 0x4;
		case '5': return 0x5;
		case '6': return 0x6;
		case '7': return 0x7;
		case '8': return 0x8;
		case '9': return 0x9;
		case 'a': case 'A': return 0xA;
		case 'b': case 'B': return 0xB;
		case 'c': case 'C': return 0xC;
		case 'd': case 'D': return 0xD;
		case 'e': case 'E': return 0xE;
		case 'f': case 'F': return 0xF;
		default:
			throw new IllegalArgumentException("Unknown hex char '" + hexChar + "'!");
		}
	}
	
	/**
	 * Reads the specified hexadecimal string into a byte array.
	 * <p>
	 * Two characters of the string are read into one byte.
	 * The first hex char represents the 4 most significant bits
	 * and the second hex char the 4 least significant bits of the byte.
	 * If the length of the hex string is odd, the 4 least significant bits of the last byte are {@code 0000}.
	 * @param hexString The hexadecimal string.
	 * @return The byte array that represents the hexadecimal string. Not {@code null}.
	 * @throws NullPointerException If the input string is {@code null}.
	 * @throws IllegalArgumentException If the string contains a non-hexadecimal character.
	 */
	public static byte[] hexToBytes(String hexString) {
		int n = hexString.length();
		int byteLength = n / 2;
		if (n % 2 == 1) {
			byteLength++;
		}
		
		byte[] bytes = new byte[byteLength];
		for (int i = 0; i < byteLength; i++) {
			int b = parseHexChar(hexString.charAt(2*i)) << 4;
			int secondIndex = 2*i+1;
			if (secondIndex < n) {
				b |= parseHexChar(hexString.charAt(secondIndex));
			}
			bytes[i] = (byte) b;
		}
		return bytes;
	}
	
	/**
	 * Reads the specified hexadecimal string into a {@code long}.
	 * Each character represents 4 bits of the result.
	 * The last character of the string represents the least significant bits.
	 * <p>
	 * Note: A {@code '-'} sign is not allowed.
	 * @param hexString The hexadecimal string. Must not be {@code null}.
	 * Must not be larger than 16 characters.
	 * @return A {@code long} that represents the hex string.
	 * @throws NullPointerException If the hex string is {@code null}.
	 * @throws IllegalArgumentException If the hex string is too large or contains non-hexadecimal characters.
	 */
	public static long hexToLong(String hexString) {
		int n = hexString.length();
		if (n > Long.BYTES*2) {
			throw new IllegalArgumentException("Hex string is too large to be stored as a 64 bit integer!");
		}
		long value = 0L;
		for (int i = 0; i < n; i++) {
			long b = parseHexChar(hexString.charAt(n-1-i));
			value |= b << (4*i);
		}
		return value;
	}
	
	/**
	 * Reads the specified hexadecimal string into an {@code int}.
	 * Each character represents 4 bits of the result.
	 * The last character of the string represents the least significant bits.
	 * <p>
	 * Note: A {@code '-'} sign is not allowed.
	 * @param hexString The hexadecimal string. Must not be {@code null}.
	 * Must not be larger than 8 characters.
	 * @return An {@code int} that represents the hex string.
	 * @throws NullPointerException If the hex string is {@code null}.
	 * @throws IllegalArgumentException If the hex string is too large or contains non-hexadecimal characters.
	 * @since 1.1
	 */
	public static int hexToInt(String hexString) {
		int n = hexString.length();
		if (n > Integer.BYTES*2) {
			throw new IllegalArgumentException("Hex string is too large to be stored as a 32 bit integer!");
		}
		int value = 0;
		for (int i = 0; i < n; i++) {
			int b = parseHexChar(hexString.charAt(n-1-i));
			value |= b << (4*i);
		}
		return value;
	}
	
}
