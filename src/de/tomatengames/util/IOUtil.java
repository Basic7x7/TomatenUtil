package de.tomatengames.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Provides utilities for file system interactions and streams.
 * 
 * @author Basic7x7
 * @version 2023-01-26
 * @since 1.0
 */
public class IOUtil {
	
	/**
	 * Writes the 4 bytes of the specified value into the byte array.
	 * @param value The value.
	 * @param output The byte array into which the value should be written.
	 * @param offset The index of the first written byte.
	 * @throws ArrayIndexOutOfBoundsException If offset is negative or the array is too small.
	 * @see #readInt(byte[], int)
	 */
	public static void writeInt(int value, byte[] output, int offset) {
		output[offset+0] = (byte) (value >>> 24);
		output[offset+1] = (byte) (value >>> 16);
		output[offset+2] = (byte) (value >>> 8);
		output[offset+3] = (byte) value;
	}
	
	/**
	 * Writes the 4 bytes of the specified value into the output stream.
	 * @param value The value.
	 * @param out The output stream.
	 * @throws IOException If an I/O error occurs.
	 * @see #readInt(InputStream)
	 */
	public static void writeInt(int value, OutputStream out) throws IOException {
		out.write(value >>> 24);
		out.write(value >>> 16);
		out.write(value >>> 8);
		out.write(value);
	}
	
	/**
	 * Reads 4 bytes from the byte array starting at the specified offset and returns them as {@code int}.
	 * @param input The byte array.
	 * @param offset The index of the first read byte.
	 * @return The 4 read bytes.
	 * @throws ArrayIndexOutOfBoundsException If offset is negative or the array is too small.
	 * @see #writeInt(int, byte[], int)
	 */
	public static int readInt(byte[] input, int offset) {
		return (input[offset] << 24) | ((input[offset+1] & 0xFF) << 16) | ((input[offset+2] & 0xFF) << 8) | (input[offset+3] & 0xFF);
	}
	
	/**
	 * Reads 4 bytes from the input stream and returns them as {@code int}.
	 * @param in The input stream.
	 * @return The read 4 bytes.
	 * @throws IOException If an I/O error occurs.
	 * @throws EOFException If the end of the stream is reached.
	 * @see #writeInt(int, OutputStream)
	 */
	public static int readInt(InputStream in) throws IOException {
		return (readUByte(in) << 24) | (readUByte(in) << 16) | (readUByte(in) << 8) | readUByte(in);
	}
	
	
	/**
	 * Writes the 2 lower-order bytes of the specified value into the byte array.
	 * @param value The value.
	 * @param output The byte array into which the value should be written.
	 * @param offset The index of the first written byte.
	 * @throws ArrayIndexOutOfBoundsException If offset is negative or the array is too small.
	 * @see #readShort(byte[], int)
	 */
	public static void writeShort(int value, byte[] output, int offset) {
		output[offset+0] = (byte) (value >>> 8);
		output[offset+1] = (byte) value;
	}
	
	/**
	 * Writes the 2 lower-order bytes of the specified value into the output stream.
	 * @param value The value.
	 * @param out The output stream.
	 * @throws IOException If an I/O error occurs.
	 * @see #readShort(InputStream)
	 */
	public static void writeShort(int value, OutputStream out) throws IOException {
		out.write(value >>> 8);
		out.write(value);
	}
	
	/**
	 * Reads 2 bytes from the byte array starting at the specified offset and returns them
	 * as the 2 lower-order bytes of the returned {@code int}.
	 * @param input The byte array.
	 * @param offset The index of the first read byte.
	 * @return The read 2 bytes.
	 * @throws ArrayIndexOutOfBoundsException If offset is negative or the array is too small.
	 * @see #writeShort(int, byte[], int)
	 */
	public static int readShort(byte[] input, int offset) {
		return (input[offset] << 24) | ((input[offset+1] & 0xFF) << 16) | ((input[offset+2] & 0xFF) << 8) | (input[offset+3] & 0xFF);
	}
	
	/**
	 * Reads 2 bytes from the input stream and returns them
	 * as the 2 lower-order bytes of the returned {@code int}.
	 * @param in The input stream.
	 * @return The read 2 bytes.
	 * @throws IOException If an I/O error occurs.
	 * @throws EOFException If the end of the stream is reached.
	 * @see #writeShort(int, OutputStream)
	 */
	public static int readShort(InputStream in) throws IOException {
		return (readUByte(in) << 8) | readUByte(in);
	}
	
	
	/**
	 * Returns a single byte from the specified input stream.
	 * @param in The input stream.
	 * @return A single byte of data. Only the 8 least significant bits may be set.
	 * @throws IOException If an I/O error occurs.
	 * @throws EOFException If the end of the stream is reached.
	 */
	public static int readUByte(InputStream in) throws IOException {
		int b = in.read();
		if (b < 0) {
			throw new EOFException();
		}
		return b;
	}
}
