package de.tomatengames.util.test;

import de.tomatengames.util.HexUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static de.tomatengames.util.HexUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class HexUtilTest {
	private static final Path dir = Paths.get("testdata/hexutil");
	
	@Test
	void testToHexChar() {
		assertEquals('0', toHexChar(0));
		assertEquals('1', toHexChar(1));
		assertEquals('2', toHexChar(2));
		assertEquals('3', toHexChar(3));
		assertEquals('4', toHexChar(4));
		assertEquals('5', toHexChar(5));
		assertEquals('6', toHexChar(6));
		assertEquals('7', toHexChar(7));
		assertEquals('8', toHexChar(8));
		assertEquals('9', toHexChar(9));
		assertEquals('a', toHexChar(10));
		assertEquals('b', toHexChar(11));
		assertEquals('c', toHexChar(12));
		assertEquals('d', toHexChar(13));
		assertEquals('e', toHexChar(14));
		assertEquals('f', toHexChar(15));
		assertThrows(IllegalArgumentException.class, () -> toHexChar(-1));
		assertThrows(IllegalArgumentException.class, () -> toHexChar(-67));
		assertThrows(IllegalArgumentException.class, () -> toHexChar(16));
		assertThrows(IllegalArgumentException.class, () -> toHexChar(132));
	}
	
	@Test
	void testParseHexChar() {
		assertEquals(0, parseHexChar('0'));
		assertEquals(1, parseHexChar('1'));
		assertEquals(2, parseHexChar('2'));
		assertEquals(3, parseHexChar('3'));
		assertEquals(4, parseHexChar('4'));
		assertEquals(5, parseHexChar('5'));
		assertEquals(6, parseHexChar('6'));
		assertEquals(7, parseHexChar('7'));
		assertEquals(8, parseHexChar('8'));
		assertEquals(9, parseHexChar('9'));
		assertEquals(10, parseHexChar('a'));
		assertEquals(10, parseHexChar('A'));
		assertEquals(11, parseHexChar('b'));
		assertEquals(11, parseHexChar('B'));
		assertEquals(12, parseHexChar('c'));
		assertEquals(12, parseHexChar('C'));
		assertEquals(13, parseHexChar('d'));
		assertEquals(13, parseHexChar('D'));
		assertEquals(14, parseHexChar('e'));
		assertEquals(14, parseHexChar('E'));
		assertEquals(15, parseHexChar('f'));
		assertEquals(15, parseHexChar('F'));
		assertThrows(IllegalArgumentException.class, () -> parseHexChar('g'));
		assertThrows(IllegalArgumentException.class, () -> parseHexChar('G'));
		assertThrows(IllegalArgumentException.class, () -> parseHexChar('x'));
		assertThrows(IllegalArgumentException.class, () -> parseHexChar('('));
	}
	
	@Test
	void testBytesToHex() {
		assertEquals("315110", bytesToHex(new byte[] {49, 81, 16}));
		assertEquals("00", bytesToHex(new byte[] {0}));
		assertEquals("11ff", bytesToHex(new byte[] {17, -1}));
		assertEquals("0711001f", bytesToHex(new byte[] {7, 17, 0, 31}));
		
		assertEquals("", bytesToHex(new byte[] {}));
		assertThrows(NullPointerException.class, () -> bytesToHex(null));
	}
	
	@Test
	void testHexToBytes() {
		assertArrayEquals(new byte[] {49, 81, 16}, hexToBytes("315110"));
		assertArrayEquals(new byte[] {49, 81, 16}, hexToBytes("31511"));
		assertArrayEquals(new byte[] {0}, hexToBytes("0"));
		assertArrayEquals(new byte[] {0}, hexToBytes("00"));
		assertArrayEquals(new byte[] {17, -1}, hexToBytes("11ff"));
		assertArrayEquals(new byte[] {7, 17, 0, 31}, hexToBytes("0711001f"));
		assertArrayEquals(new byte[] {16}, hexToBytes("1")); // 10
		
		assertArrayEquals(new byte[] {}, hexToBytes(""));
		assertThrows(NullPointerException.class, () -> hexToBytes(null));
		assertThrows(IllegalArgumentException.class, () -> hexToBytes("fx"));
		assertThrows(IllegalArgumentException.class, () -> hexToBytes("123G1"));
	}
	
	@Test
	void testIntToHex() {
		assertEquals("00", byteToHex(0));
		assertEquals("31", byteToHex(49));
		assertEquals("ff", byteToHex(-1));
		assertEquals("c4", byteToHex(0xc4));
		assertEquals("e1", byteToHex(0x8fe1));
		
		assertEquals("0000", shortToHex(0));
		assertEquals("0031", shortToHex(49));
		assertEquals("ffff", shortToHex(-1));
		assertEquals("e1c4", shortToHex(0xe1c4));
		assertEquals("6400", shortToHex(0xD7BB6400));
		
		assertEquals("00000000", intToHex(0));
		assertEquals("00000031", intToHex(49));
		assertEquals("ffffffff", intToHex(-1));
		assertEquals("240db2f5", intToHex(0x240DB2F5));
		assertEquals("d7bb6400", intToHex(0xD7BB6400));
		assertEquals("6dc2bf7b", intToHex(0x6DC2BF7B));
		
		assertEquals("0000000000000000", longToHex(0L));
		assertEquals("0000000000000031", longToHex(49L));
		assertEquals("ffffffffffffffff", longToHex(-1L));
		assertEquals("00000000240db2f5", longToHex(0x240DB2F5L));
		assertEquals("5318de851cb8a076", longToHex(0x5318DE851CB8A076L));
		assertEquals("f37830e799b7f3c4", longToHex(0xF37830E799B7F3C4L));
	}
	
	@Test
	void testHexToInt() {
		assertEquals(0, hexToInt("00000000"));
		assertEquals(0, hexToInt("0"));
		assertEquals(1, hexToInt("1"));
		assertEquals(16, hexToInt("10"));
		assertEquals(49, hexToInt("31"));
		assertEquals(49, hexToInt("000031"));
		assertEquals(-1, hexToInt("ffffffff"));
		assertEquals(0x240DB2F5L, hexToInt("240DB2f5"));
		assertEquals(0xD7BB6400, hexToInt("d7bB6400"));
		assertEquals(0x6DC2BF7B, hexToInt("6DC2BF7B"));
		assertThrows(IllegalArgumentException.class, () -> hexToInt("fffffffff")); // Too long
		assertThrows(IllegalArgumentException.class, () -> hexToInt("12G3"));
		assertThrows(NullPointerException.class, () -> hexToInt(null));
		
		assertEquals(0L, hexToLong("0000000000000000"));
		assertEquals(0L, hexToLong("0"));
		assertEquals(1L, hexToLong("1"));
		assertEquals(16L, hexToLong("10"));
		assertEquals(49L, hexToLong("31"));
		assertEquals(49L, hexToLong("000031"));
		assertEquals(-1L, hexToLong("ffffffFfffFfffff"));
		assertEquals(0x240DB2F5L, hexToLong("240db2f5"));
		assertEquals(0x5318DE851CB8A076L, hexToLong("5318de851CB8a076"));
		assertEquals(0xF37830E799B7F3C4L, hexToLong("f37830e799b7f3c4"));
		assertThrows(IllegalArgumentException.class, () -> hexToLong("fffffffffffffffff")); // Too long
		assertThrows(IllegalArgumentException.class, () -> hexToLong("12G3"));
		assertThrows(NullPointerException.class, () -> hexToLong(null));
	}
	
	@Test
	void testHexFile() throws IOException {
		assertEquals("48656c6c6f", HexUtil.fileToHex(dir.resolve("1.txt")));
		assertEquals("6120746573742066696c65", HexUtil.fileToHex(dir.resolve("2.txt")));
		assertEquals("30313233343536373839", HexUtil.fileToHex(dir.resolve("3.txt")));
		assertThrows(NullPointerException.class, () -> HexUtil.fileToHex(null));
	}
	
}
