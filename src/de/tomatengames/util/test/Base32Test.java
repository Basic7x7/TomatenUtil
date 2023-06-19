package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import de.tomatengames.util.Base32Util;

class Base32Test {
	
	private String base32(String s) {
		return Base32Util.bytesToBase32(s.getBytes(StandardCharsets.UTF_8), true);
	}
	private String unbase32(String s) {
		return new String(Base32Util.base32ToBytes(s), StandardCharsets.UTF_8);
	}
	private void checkBase32(String encoded, String decoded) {
		assertEquals(encoded, base32(decoded));
		assertEquals(decoded, unbase32(encoded));
	}
	
	@Test
	void testBase32() {
		checkBase32("", "");
		checkBase32("MY======", "f");
		checkBase32("MZXQ====", "fo");
		checkBase32("MZXW6===", "foo");
		checkBase32("MZXW6YQ=", "foob");
		checkBase32("MZXW6YTB", "fooba");
		checkBase32("MZXW6YTBOI======", "foobar");
		assertEquals("fofo", unbase32("MZXQ====MZXQ===="));
	}
	
}
