package de.tomatengames.util.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import de.tomatengames.util.HexUtil;
import de.tomatengames.util.IOUtil;
import de.tomatengames.util.exception.CharacterDecodeException;
import de.tomatengames.util.io.UTF8Reader;

// from TomatenJSON
class UTF8ReaderTestExhaustive2 {
	
	static boolean testInt(CharsetDecoder referencedecoder, byte[] b, int i) throws IOException {
		IOUtil.writeInt(i, b, 0);
		UTF8Reader r = new UTF8Reader(new ByteArrayInputStream(b));
		String referencestring;
		MalformedInputException referenceexception = null;
		try {
			referencestring = referencedecoder.decode(ByteBuffer.wrap(b)).toString();
		} catch (MalformedInputException e) {
			referencestring = null;
			referenceexception = e;
		}
		String readerstring;
		CharacterDecodeException readerexception = null;
		try {
			StringBuilder readerstringb = new StringBuilder();
			int read;
			while ((read = r.read()) >= 0)
				readerstringb.append((char)read);
			readerstring = readerstringb.toString();
		} catch (CharacterDecodeException e) {
			readerstring = null;
			readerexception = e;
		}
		if (Objects.equals(referencestring, readerstring))
			return true;
		System.err.println(HexUtil.intToHex(i) + ": expected " + stringDisplay(referencestring, referenceexception) +
				" but got " + stringDisplay(readerstring, readerexception));
		return false;
	}
	
	private static String stringDisplay(String s, Exception e) {
		return s == null ? "null (" + e.getMessage() + ")" : HexUtil.bytesToHex(s.getBytes(StandardCharsets.UTF_8)) + " (" + s + ")";
	}
	
	public static void main(String[] args) throws IOException {
		
		CharsetDecoder d = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT);
		byte[] b = new byte[4];
		int i = 0;
		boolean allgood = true;
		
		do {
			allgood &= testInt(d, b, i);
			if ((i & 0xFFFFFF) == 0) {
				long max = 1L << 32;
				long l = i & (max - 1);
				System.out.println((100 * l / max) + "% (" + l + "/" + max + ")");
			}
			i++;
		} while (i != 0);
		
		if (allgood)
			System.out.println("Done with no errors :)");
		else
			System.out.println("Done, but encountered errors >:{");
	}
	
}
