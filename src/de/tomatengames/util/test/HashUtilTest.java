package de.tomatengames.util.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.HashUtil;
import de.tomatengames.util.HexUtil;

class HashUtilTest {
	private static final Path dir = Paths.get("testdata/hashutil");
	
	@Test
	void testGet() {
		assertNotNull(HashUtil.getSHA1());
		assertNotNull(HashUtil.getSHA256());
		assertNotNull(HashUtil.get(HashUtil.SHA1));
		assertNotNull(HashUtil.get(HashUtil.SHA256));
		assertThrows(IllegalArgumentException.class, () -> HashUtil.get("eihjbgfoeu-not-a-md"));
	}
	
	@Test
	void testByteArrayHash() {
		MessageDigest sha256 = HashUtil.getSHA256();
		assertEquals("a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3",
				HexUtil.bytesToHex(HashUtil.hash(sha256, "123".getBytes(StandardCharsets.UTF_8))));
		assertEquals("46a6de6b0b1708aadbd5578b5fd1f309e0181c6eb15c021201d961f6766d9ec2",
				HexUtil.bytesToHex(HashUtil.hash(sha256, "hello this is a test".getBytes(StandardCharsets.UTF_8))));
		
		assertEquals("6fec2a9601d5b3581c94f2150fc07fa3d6e45808079428354b868e412b76e6bb",
				HexUtil.bytesToHex(HashUtil.hash(sha256,
						"test".getBytes(StandardCharsets.UTF_8), null, "12345".getBytes(StandardCharsets.UTF_8))));
	}
	
	@Test
	void testFileHash() throws IOException {
		MessageDigest sha256 = HashUtil.getSHA256();
		assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",
				HexUtil.bytesToHex(HashUtil.hash(sha256, dir.resolve("1.txt"))));
		assertEquals("5c2d62601975a253afdc823f04026b380c4e94664f04c41ed6c3bf8fc4248039",
				HexUtil.bytesToHex(HashUtil.hash(sha256, dir.resolve("2.txt"))));
	}
	
}
