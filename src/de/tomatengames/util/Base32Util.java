package de.tomatengames.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base32Util {
	
	public static byte[] base32ToBytes(String s) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			base32ToBytes(s.toCharArray(), bout);
		} catch (IOException e) {
			throw new AssertionError(e);
		}
		return bout.toByteArray();
	}
	
	public static void base32ToBytes(char[] chars, OutputStream out) throws IOException {
		int bits = 0;
		int bitpos = 0;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
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
	
	public static String bytesToBase32(byte[] b, boolean pad) {
		int bytelen = b.length;
		int charlen = (bytelen * 8 + 4) / 5;
		int padlen;
		if (pad) {
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
			int byte1 = b[bytepos];
			int byte2 = bytepos + 1 < b.length ? b[bytepos + 1] : 0;
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
	
	public static char toBase32Char(int value5bit) {
		if (value5bit < 0 || value5bit > 0b11111)
			throw new IllegalArgumentException("Cannot represent " + value5bit + " with one Base32 character");
		if (value5bit < 26)
			return (char)('A' + value5bit);
		return (char)('2' + value5bit - 26);
	}
	
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
