package de.tomatengames.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.function.Predicate;

/**
 * Provides utilities for file system interactions and streams.
 * 
 * @author Basic7x7
 * @version 2023-01-26
 * @since 1.0
 */
public class IOUtil {
	private static final int BUF_SIZE = 8192;
	
	// Static class
	private IOUtil() {
	}
	
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
	 * Writes the 8 bytes of the specified value into the byte array.
	 * @param value The value.
	 * @param output The byte array into which the value should be written.
	 * @param offset The index of the first written byte.
	 * @throws ArrayIndexOutOfBoundsException If offset is negative or the array is too small.
	 * @see #readLong(byte[], int)
	 */
	public static void writeLong(long value, byte[] output, int offset) {
		output[offset+0] = (byte) (value >>> 56);
		output[offset+1] = (byte) (value >>> 48);
		output[offset+2] = (byte) (value >>> 40);
		output[offset+3] = (byte) (value >>> 32);
		output[offset+4] = (byte) (value >>> 24);
		output[offset+5] = (byte) (value >>> 16);
		output[offset+6] = (byte) (value >>> 8);
		output[offset+7] = (byte) value;
	}
	
	/**
	 * Writes the 8 bytes of the specified value into the output stream.
	 * @param value The value.
	 * @param out The output stream.
	 * @throws IOException If an I/O error occurs.
	 * @see #readLong(InputStream)
	 */
	public static void writeLong(long value, OutputStream out) throws IOException {
		out.write((int) (value >>> 56));
		out.write((int) (value >>> 48));
		out.write((int) (value >>> 40));
		out.write((int) (value >>> 32));
		out.write((int) (value >>> 24));
		out.write((int) (value >>> 16));
		out.write((int) (value >>> 8));
		out.write((int) (value));
	}
	
	/**
	 * Reads 8 bytes from the byte array starting at the specified offset and returns them as {@code long}.
	 * @param input The byte array.
	 * @param offset The index of the first read byte.
	 * @return The 8 read bytes.
	 * @throws ArrayIndexOutOfBoundsException If offset is negative or the array is too small.
	 * @see #writeLong(long, byte[], int)
	 */
	public static long readLong(byte[] input, int offset) {
		return ((input[offset] & 0xFFL) << 56)
				| ((input[offset+1] & 0xFFL) << 48)
				| ((input[offset+2] & 0xFFL) << 40)
				| ((input[offset+3] & 0xFFL) << 32)
				| ((input[offset+4] & 0xFFL) << 24)
				| ((input[offset+5] & 0xFFL) << 16)
				| ((input[offset+6] & 0xFFL) << 8)
				| (input[offset+7] & 0xFFL);
	}
	
	/**
	 * Reads 8 bytes from the input stream and returns them as {@code long}.
	 * @param in The input stream.
	 * @return The read 8 bytes.
	 * @throws IOException If an I/O error occurs.
	 * @throws EOFException If the end of the stream is reached.
	 * @see #writeLong(long, OutputStream)
	 */
	public static long readLong(InputStream in) throws IOException {
		return ((long) readUByte(in) << 56)
				| ((long) readUByte(in) << 48)
				| ((long) readUByte(in) << 40)
				| ((long) readUByte(in) << 32)
				| ((long) readUByte(in) << 24)
				| ((long) readUByte(in) << 16)
				| ((long) readUByte(in) << 8)
				| readUByte(in);
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
		return ((input[offset] & 0xFF) << 8) | (input[offset+1] & 0xFF);
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
	 * Writes a byte {@code 0x01} if the value is {@code true}
	 * or {@code 0x00} if the value is {@code false}.
	 * @param value The value.
	 * @param output The byte array into which the value should be written.
	 * @param offset The index of the written byte.
	 * @throws ArrayIndexOutOfBoundsException If offset is negative or the array is too small.
	 * @see #readBoolean(byte[], int)
	 */
	public static void writeBoolean(boolean value, byte[] output, int offset) {
		output[offset] = value ? (byte) 1 : (byte) 0;
	}
	
	/**
	 * Writes a byte {@code 0x01} if the value is {@code true}
	 * or {@code 0x00} if the value is {@code false}.
	 * @param value The value.
	 * @param out The output stream.
	 * @throws IOException If an I/O error occurs.
	 * @see #readBoolean(InputStream)
	 */
	public static void writeBoolean(boolean value, OutputStream out) throws IOException {
		out.write(value ? 1 : 0);
	}
	
	/**
	 * Returns {@code false} if and only if {@code input[offset]} is {@code 0x00}.
	 * @param input The byte array.
	 * @param offset The index of the read byte.
	 * @return If the read byte is not {@code 0x00}.
	 * @throws ArrayIndexOutOfBoundsException If offset is negative or the array is too small.
	 * @see #writeBoolean(boolean, byte[], int)
	 */
	public static boolean readBoolean(byte[] input, int offset) {
		return input[offset] != 0x00;
	}
	
	/**
	 * Reads a single byte and returns {@code false} if and only if the byte is {@code 0x00}.
	 * @param in The input stream.
	 * @return If the read byte is not {@code 0x00}.
	 * @throws IOException If an I/O error occurs.
	 * @throws EOFException If the end of the stream is reached.
	 * @see #writeBoolean(boolean, OutputStream)
	 */
	public static boolean readBoolean(InputStream in) throws IOException {
		return readUByte(in) != 0x00;
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
	
	
	/**
	 * Reads all data from the {@link InputStream} and writes it to the {@link OutputStream}.
	 * @param in The input stream.
	 * @param out Th output stream.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void pipeStream(InputStream in, OutputStream out) throws IOException {
		// Copy the input to the output.
		byte[] buffer = new byte[BUF_SIZE];
		int n;
		while ((n = in.read(buffer, 0, BUF_SIZE)) >= 0) {
			out.write(buffer, 0, n);
		}
		out.flush();
	}
	
	/**
	 * Deletes the specified path.
	 * If the path represents a file, the file is deleted.
	 * If the path represents a directory, the directory is deleted recursively.
	 * <p>
	 * Symbolic links are not followed, but the symbolic links themselves are deleted.
	 * @param path The path that should be deleted. If {@code null}, nothing happens.
	 * @throws IOException If an error occurs.
	 */
	public static void delete(Path path) throws IOException {
		if (path == null)
			return;
		
		// Deletes regular files.
		// Symbolic links are directly removed (and never interpreted as a directory).
		if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS) || Files.isSymbolicLink(path)) {
			Files.delete(path);
			return;
		}
		
		// Deletes directories recursively.
		if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
			Path[] children = Files.list(path).toArray(Path[]::new);
			for (Path child : children) {
				delete(child);
			}
			Files.delete(path);
			return;
		}
	}
	
	/**
	 * Deletes files and directories inside the specified directory recursively.
	 * The specified directory itself is <b>not</b> deleted.
	 * <p>
	 * Files inside the specified directory that do not match the predicate are not deleted.
	 * This only applies for the direct file list of the directory.
	 * The predicate is <b>not</b> checked recursively.
	 * <p>
	 * Symbolic links are not followed.
	 * @param dir The directory. If {@code null}, a symbolic link or not a directory, nothing happens.
	 * @param filter A predicate to filter the entries in the file list to delete.
	 * If {@code null}, all files are deleted.
	 * @throws IOException If an error occurs.
	 */
	public static void cleanDirectory(Path dir, Predicate<Path> filter) throws IOException {
		// Ignore symbolic links.
		if (dir == null || Files.isSymbolicLink(dir)) {
			return;
		}
		
		// Deletes all files from the directory recursively.
		if (Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			Path[] children = Files.list(dir).toArray(Path[]::new);
			for (Path child : children) {
				if (filter == null || filter.test(child)) {
					delete(child);
				}
			}
			return;
		}
	}
	
	/**
	 * Deletes all files and directories inside the specified directory recursively.
	 * The specified directory itself is <b>not</b> deleted.
	 * <p>
	 * Symbolic links are not followed.
	 * @param dir The directory. If {@code null}, a symbolic link or not a directory, nothing happens.
	 * @throws IOException If an error occurs.
	 */
	public static void cleanDirectory(Path dir) throws IOException {
		cleanDirectory(dir, null);
	}
}
