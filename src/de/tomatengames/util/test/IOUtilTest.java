package de.tomatengames.util.test;

import de.tomatengames.util.IOUtil;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class IOUtilTest {
	
	@Test
	void testStreamOutIn() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtil.writeInt(5, out);
		IOUtil.writeInt(-7, out);
		IOUtil.writeLong(75892437690L, out);
		out.write(42);
		IOUtil.writeShort(10, out);
		IOUtil.writeBoolean(true, out);
		IOUtil.writeBoolean(false, out);
		
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		assertEquals(5, IOUtil.readInt(in));
		assertEquals(-7, IOUtil.readInt(in));
		assertEquals(75892437690L, IOUtil.readLong(in));
		assertEquals(42, IOUtil.readUByte(in));
		assertEquals(10, IOUtil.readShort(in));
		assertEquals(true, IOUtil.readBoolean(in));
		assertEquals(false, IOUtil.readBoolean(in));
		assertThrows(IOException.class, () -> IOUtil.readUByte(in));
	}
	
	@Test
	void testByteArrayOutIn() throws IOException {
		byte[] array = new byte[50];
		IOUtil.writeInt(5, array, 0);
		IOUtil.writeInt(-7, array, 4);
		IOUtil.writeLong(75892437690L, array, 10);
		IOUtil.writeShort(10, array, 18);
		IOUtil.writeBoolean(true, array, 20);
		IOUtil.writeBoolean(false, array, 21);
		
		assertEquals(5, IOUtil.readInt(array, 0));
		assertEquals(-7, IOUtil.readInt(array, 4));
		assertEquals(75892437690L, IOUtil.readLong(array, 10));
		assertEquals(10, IOUtil.readShort(array, 18));
		assertEquals(true, IOUtil.readBoolean(array, 20));
		assertEquals(false, IOUtil.readBoolean(array, 21));
	}
	
	@Test
	void testString1() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtil.writeString("test", out);
		IOUtil.writeString("hello", out);
		IOUtil.writeString(null, out);
		IOUtil.writeString("ABC def GHI", out);
		
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		assertEquals("test", IOUtil.readString(in));
		assertEquals("hello", IOUtil.readString(in));
		assertEquals(null, IOUtil.readString(in));
		assertEquals("ABC def GHI", IOUtil.readString(in));
		assertEquals(-1, in.read());
	}
	
	@Test
	void testString2() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtil.writeString("abcdef", out);
		IOUtil.writeString("ABCDEFGHIJKLMNOPQRSTUVWXYZ", out);
		
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		assertEquals("abcdef", IOUtil.readString(in, 10));
		assertThrows(IOException.class, () -> IOUtil.readString(in, 10));
	}
	
	@Test
	void testReadFully() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(new byte[] { 1,2,3,4,5 });
		
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		assertArrayEquals(new byte[] {1,2,3}, IOUtil.readFully(in, new byte[3]));
		assertThrows(IOException.class, () -> IOUtil.readFully(in, new byte[3]));
	}
	
}
