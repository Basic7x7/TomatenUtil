package de.tomatengames.util.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.tomatengames.util.CharsetUtil;
import de.tomatengames.util.HexUtil;

class UTF8DecodeTestExhaustive {
	
	public static void main(String[] args) {
		ArrayList<EncodedCodePoint> validCodePoints = new ArrayList<>();
		for (int cp = 0; cp <= 0x10FFFF; cp++) {
			validCodePoints.add(new EncodedCodePoint(cp));
		}
		
		long count = 0L;
		boolean ok = true;
		for (int b0 = 127; b0 < 256; b0++) {
			for (int b1 = 0; b1 < 256; b1++) {
				for (int b2 = 0; b2 < 256; b2++) {
					for (int b3 = 0; b3 < 256; b3++) {
						EncodedCodePoint valid = findMatchingValid(b0, b1, b2, b3, validCodePoints);
						byte[] inputBytes = new byte[] {
								(byte) b0, (byte) b1, (byte) b2, (byte) b3
						};
						InputStream input = new ByteArrayInputStream(inputBytes);
						
						if ((count & 0xFFFFL) == 0L) {
							System.out.println(HexUtil.bytesToHex(inputBytes));
						}
						count++;
						
						if (valid != null) {
							try {
								int decodedCp = CharsetUtil.decodeUTF8(input);
								if (decodedCp != valid.codePoint) {
									System.err.println("CodePoint decode mismatch for input '" + HexUtil.bytesToHex(inputBytes) +
											"': Expected code point = " + valid.codePoint + "; Decoded code point = " + decodedCp);
									ok = false;
								}
							} catch (IOException e) {
								e.printStackTrace();
								ok = false;
							}
						}
						else {
							// Expect failure
							try {
								CharsetUtil.decodeUTF8(input);
								System.err.println("Decode did not fail for invalid input '" + HexUtil.bytesToHex(inputBytes) + "'");
								ok = false;
							} catch (IOException e) {
								// This is the expected behavior.
							}
						}
					}
				}
			}
		}
		
		if (ok) {
			System.out.println("All tests OK");
		}
	}
	
	private static EncodedCodePoint findMatchingValid(int b0, int b1, int b2, int b3, ArrayList<EncodedCodePoint> valid) {
		for (EncodedCodePoint cp : valid) {
			if (cp.matches(b0, b1, b2, b3)) {
				return cp;
			}
		}
		return null;
	}
	
	private static class EncodedCodePoint {
		private final int codePoint;
		private final byte[] utf8;
		private final int len;
		
		public EncodedCodePoint(int codePoint) {
			this.codePoint = codePoint;
			this.utf8 = new byte[4];
			this.len = CharsetUtil.encodeUTF8(codePoint, utf8, 0);
		}
		
		public boolean matches(int b0, int b1, int b2, int b3) {
			if (len <= 0 || len >= 5) {
				return false;
			}
			if (len >= 1 && (b0 & 0xFF) != (utf8[0] & 0xFF)) {
				return false;
			}
			if (len >= 2 && (b1 & 0xFF) != (utf8[1] & 0xFF)) {
				return false;
			}
			if (len >= 3 && (b2 & 0xFF) != (utf8[2] & 0xFF)) {
				return false;
			}
			if (len >= 4 && (b3 & 0xFF) != (utf8[3] & 0xFF)) {
				return false;
			}
			return true;
		}
	}
}
