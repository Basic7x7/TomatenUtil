package de.tomatengames.util;

/**
 * Provides methods to work with hexadecimal strings.
 * 
 * @author Basic7x7
 * @version 2023-01-24
 * @since 1.0
 */
public class HexUtil {
	
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
	 * @return The byte array that represents the hexadecimal string.
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
	
}
