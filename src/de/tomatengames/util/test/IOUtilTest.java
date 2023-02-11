package de.tomatengames.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.tomatengames.util.IOUtil;

class IOUtilTest {
	
	@Test
	void testStreamOutIn() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		IOUtil.writeInt(5, out);
		IOUtil.writeInt(-7, out);
		IOUtil.writeLong(75892437690L, out);
		IOUtil.writeShort(10, out);
		IOUtil.writeBoolean(true, out);
		IOUtil.writeBoolean(false, out);
		
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		assertEquals(5, IOUtil.readInt(in));
		assertEquals(-7, IOUtil.readInt(in));
		assertEquals(75892437690L, IOUtil.readLong(in));
		assertEquals(10, IOUtil.readShort(in));
		assertEquals(true, IOUtil.readBoolean(in));
		assertEquals(false, IOUtil.readBoolean(in));
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
	
}
