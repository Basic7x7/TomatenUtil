package de.tomatengames.util.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import de.tomatengames.util.CharsetUtil;
import de.tomatengames.util.HexUtil;

public class UTF8EncodeTestExhaustive {
	
	public static void main(String[] args) {
		AtomicBoolean ok = new AtomicBoolean(true);
		for (int cp = -0xFFFFFF; cp <= 0xFFFFFF; cp++) {
			if ((cp & 0xFFFFL) == 0L) {
				System.out.println(HexUtil.intToHex(cp));
			}
			byte[] expected = encodeJava(cp);
			byte[] actualArray = encodeUtilArray(cp);
			byte[] actualStream = encodeUtilStream(cp);
			if (!equals(expected, actualArray)) {
				System.err.println("Encode array mismatch for code point " + HexUtil.intToHex(cp) +
						": Expected = " + Arrays.toString(expected) + "; Encoded = " + Arrays.toString(actualArray));
				ok.set(false);
			}
			if (!equals(expected, actualStream)) {
				System.err.println("Encode stream mismatch for code point " + HexUtil.intToHex(cp) +
						": Expected = " + Arrays.toString(expected) + "; Encoded = " + Arrays.toString(actualArray));
				ok.set(false);
			}
		}
		
		if (ok.get()) {
			System.out.println("All tests OK");
		}
		else {
			System.err.println("Found errors");
		}
	}
	
	private static byte[] encodeJava(int codePoint) {
		CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
		encoder.onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
		try {
			return encoder.encode(CharBuffer.wrap(Character.toChars(codePoint))).array();
		} catch (CharacterCodingException | IllegalArgumentException e) {
			return null;
		}
	}
	
	private static byte[] encodeUtilArray(int codePoint) {
		try {
			byte[] bytes = new byte[4];
			int bc = CharsetUtil.encodeUTF8(codePoint, bytes, 0);
			return Arrays.copyOf(bytes, bc);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	private static byte[] encodeUtilStream(int codePoint) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(4)) {
			CharsetUtil.encodeUTF8(codePoint, out);
			return out.toByteArray();
		} catch (IOException | IllegalArgumentException e) {
			return null;
		}
	}
	
	private static boolean equals(byte[] expected, byte[] actual) {
		if (expected == actual) {
			return true;
		}
		if (expected == null || actual == null) {
			return false;
		}
		int n = Integer.max(expected.length, actual.length);
		for (int i = 0; i < n; i++) {
			byte b1 = i < expected.length ? expected[i] : 0;
			byte b2 = i < actual.length ? actual[i] : 0;
			if (b1 != b2) {
				return false;
			}
		}
		return true;
	}
	
}
