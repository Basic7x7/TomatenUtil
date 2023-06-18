package de.tomatengames.util;

import javax.crypto.Mac;

public class TOTPUtil {
	
	/**
	 * Returns the HOTP counter to be used for the TOTP at the specified timestamp in milliseconds (e.g. {@link System#currentTimeMillis()})
	 * using the specified amount of seconds a code should be valid (e.g. 30).
	 * @param codeSeconds The amount of seconds a code should be valid.
	 * @param time The timestamp.
	 * @return The HOTP counter value.
	 */
	public static long totpCounter(int codeSeconds, long time) {
		return time / (1000L * (long)codeSeconds);
	}
	
	/**
	 * Returns the full-length HOTP code (10 digits) resulting from the specified MAC algorithm (e.g. {@link HashUtil#getHmacSHA1(byte[])})
	 * and the specified HOTP counter.
	 * <br>
	 * This is supposed to be faster as the code is simply not cut to a lower amount of digits at this point.
	 * @param mac The MAC algorithm.
	 * @param counter The HOTP counter.
	 * @return The 10-digit HOTP code.
	 */
	public static int hotpFull(Mac mac, long counter) {
		byte[] counterBytes = new byte[8];
		IOUtil.writeLong(counter, counterBytes, 0);
		byte[] hash = mac.doFinal(counterBytes);
		int off = hash[hash.length - 1] & 0xF;
		return ((hash[off] & 0x7F) << 24) | ((hash[off + 1] & 0xFF) << 16) | ((hash[off + 2] & 0xFF) << 8) | (hash[off + 3] & 0xFF);
	}
	
	/**
	 * Returns the modulus to cut the last n (codeLength) digits from some integer.
	 * The digits are supposed to be obtained by that integer % modulus.
	 * The return value is 10^n for n in range [0, 9].
	 * If n >= 10 this will return {@link Integer#MIN_VALUE}, which, used as a modulus, results in the original integer for any non-negative input integer.
	 * @param codeLength The amount of digits to cut (n).
	 * @return The modulus.
	 */
	public static int modulus(int codeLength) {
		if (codeLength >= 10)
			return Integer.MIN_VALUE;
		int mod = 1;
		while (codeLength --> 0)
			mod *= 10;
		return mod;
	}
	
	/**
	 * Returns the HOTP code resulting from the specified MAC algorithm (e.g. {@link HashUtil#getHmacSHA1(byte[])}),
	 * the specified HOTP counter and the specified code length.
	 * @param mac The MAC algorithm.
	 * @param counter The HOTP counter.
	 * @param codeLength The code length.
	 * @return The HOTP code.
	 */
	public static int hotp(Mac mac, long counter, int codeLength) {
		return hotpFull(mac, counter) % modulus(codeLength);
	}
	
	/**
	 * Combines {@link #hotpFull(Mac, long)}/{@link #hotp(Mac, long, int)} and {@link #toString(int, int)}.
	 * @param mac The MAC algorithm.
	 * @param counter The HOTP counter.
	 * @param codeLength The HOTP code's length.
	 * @return The HOTP code as a string.
	 */
	public static String hotpString(Mac mac, long counter, int codeLength) {
		return toString(hotpFull(mac, counter), codeLength);
	}
	
	/**
	 * Returns the specified HOTP/TOTP code as a fixed-length integer string.
	 * Similar to {@link Integer#toString(int)} but prepends zeroes as needed and ignores additional digits.
	 * Ideal for putting in {@link #hotpFull(Mac, long)} along with the desired code length.
	 * This does not insert any spaces.
	 * @param code The code.
	 * @param codeLength The output code length.
	 * @return The code as a string.
	 */
	public static String toString(int code, int codeLength) {
		char[] out = new char[codeLength];
		for (int i = codeLength - 1; i >= 0; i--) {
			out[i] = (char)('0' + (code % 10));
			code /= 10;
		}
		return new String(out);
	}
	
}
