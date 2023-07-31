package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.HashUtil;
import de.tomatengames.util.TOTPUtil;

class TOTPTest {
	
	private String totp(Mac mac, int codeSeconds, int codeLength, long time) {
		return TOTPUtil.hotpString(mac, TOTPUtil.totpCounter(codeSeconds, time), codeLength);
	}
	
	@Test
	void testTOTP() {
		Mac sha1 = HashUtil.getHmacSHA1("12345678901234567890".getBytes(StandardCharsets.US_ASCII));
		Mac sha256 = HashUtil.getHmacSHA256("12345678901234567890123456789012".getBytes(StandardCharsets.US_ASCII));
		Mac sha512 = HashUtil.getHmacSHA512("1234567890123456789012345678901234567890123456789012345678901234".getBytes(StandardCharsets.US_ASCII));
		assertEquals("94287082", totp(sha1,   30, 8, 59000L));
		assertEquals("46119246", totp(sha256, 30, 8, 59000L));
		assertEquals("90693936", totp(sha512, 30, 8, 59000L));
		assertEquals("07081804", totp(sha1,   30, 8, 1111111109000L));
		assertEquals("68084774", totp(sha256, 30, 8, 1111111109000L));
		assertEquals("25091201", totp(sha512, 30, 8, 1111111109000L));
		assertEquals("14050471", totp(sha1,   30, 8, 1111111111000L));
		assertEquals("67062674", totp(sha256, 30, 8, 1111111111000L));
		assertEquals("99943326", totp(sha512, 30, 8, 1111111111000L));
		assertEquals("89005924", totp(sha1,   30, 8, 1234567890000L));
		assertEquals("91819424", totp(sha256, 30, 8, 1234567890000L));
		assertEquals("93441116", totp(sha512, 30, 8, 1234567890000L));
		assertEquals("69279037", totp(sha1,   30, 8, 2000000000000L));
		assertEquals("90698825", totp(sha256, 30, 8, 2000000000000L));
		assertEquals("38618901", totp(sha512, 30, 8, 2000000000000L));
		assertEquals("65353130", totp(sha1,   30, 8, 20000000000000L));
		assertEquals("77737706", totp(sha256, 30, 8, 20000000000000L));
		assertEquals("47863826", totp(sha512, 30, 8, 20000000000000L));
	}
	
}
