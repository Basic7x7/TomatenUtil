package de.tomatengames.util;

import javax.crypto.Mac;

public class TOTPUtil {
	
	public static long totpCounter(int codeSeconds, long time) {
		return time / (1000L * (long)codeSeconds);
	}
	
	public static int hotpLong(Mac mac, long counter) {
		byte[] counterBytes = new byte[8];
		IOUtil.writeLong(counter, counterBytes, 0);
		byte[] hash = mac.doFinal(counterBytes);
		int off = hash[hash.length - 1] & 0xF;
		return ((hash[off] & 0x7F) << 24) | ((hash[off + 1] & 0xFF) << 16) | ((hash[off + 2] & 0xFF) << 8) | (hash[off + 3] & 0xFF);
	}
	
	public static int modulus(int codeLength) {
		int mod = 1;
		while (codeLength --> 0)
			mod *= 10;
		return mod;
	}
	
	public static int hotp(Mac mac, long counter, int codeLength) {
		return hotpLong(mac, counter) % modulus(codeLength);
	}
	
	public static String toString(int code, int codeLength) {
		char[] out = new char[codeLength];
		for (int i = codeLength - 1; i >= 0; i--) {
			out[i] = (char)('0' + (code % 10));
			code /= 10;
		}
		return new String(out);
	}
	
}
