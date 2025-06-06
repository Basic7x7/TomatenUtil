package de.tomatengames.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.stream.Stream;

import de.tomatengames.util.io.AbsoluteFile;
import de.tomatengames.util.io.AbsolutePath;

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
	 * Reads exactly {@code len} bytes from the {@link InputStream} into the specified byte array.
	 * {@code off} is the first byte array index that is written.
	 * @param in The input stream. Must not be {@code null}.
	 * @param arr The array to write the read data in. Must not be {@code null}.
	 * @param off The first index of the byte array that should be written.
	 * @param len The amount of bytes to read. If negative, no bytes are read.
	 * @return {@code arr}
	 * @throws IOException If an I/O error occurs.
	 * @throws EOFException If the input stream reaches the end before reading {@code len} bytes.
	 * @since 1.1
	 */
	public static byte[] readFully(InputStream in, byte[] arr, int off, int len) throws IOException {
		int rest = len;
		int index = off;
		while (rest > 0) {
			int n = in.read(arr, index, rest);
			if (n < 0) {
				throw new EOFException("The byte array could not be read fully!");
			}
			index += n;
			rest -= n;
		}
		return arr;
	}
	
	/**
	 * Reads exactly {@code arr.length} bytes from the {@link InputStream} into the specified byte array.
	 * @param in The input stream. Must not be {@code null}.
	 * @param arr The array to write the read data in. Must not be {@code null}.
	 * @return {@code arr}
	 * @throws IOException If an I/O error occurs.
	 * @throws EOFException If the input stream reaches the end before reading {@code arr.length} bytes.
	 * @since 1.1
	 */
	public static byte[] readFully(InputStream in, byte[] arr) throws IOException {
		return readFully(in, arr, 0, arr.length);
	}
	
	/**
	 * Writes the specified {@link String} to the {@link OutputStream}.
	 * <p>
	 * The first 4 bytes represent the amount of bytes of the string.
	 * These bytes are followed by the UTF-8 byte representation of the string.
	 * If the string is {@code null}, the first 4 bytes represent {@code -1} and no further bytes are written.
	 * @param str The string that should be written. May be {@code null}.
	 * @param out The output stream. Must not be {@code null}.
	 * @throws IOException If an I/O error occurs.
	 * @see #readString(InputStream)
	 * @since 1.1
	 */
	public static void writeString(String str, OutputStream out) throws IOException {
		if (str == null) {
			writeInt(-1, out);
			return;
		}
		byte[] utf = str.getBytes(StandardCharsets.UTF_8);
		writeInt(utf.length, out);
		out.write(utf);
	}
	
	/**
	 * Reads a {@link String} from the specified {@link InputStream}.
	 * This method uses the protocol specified by {@link #writeString(String, OutputStream)}.
	 * @param in The input stream. Must not be {@code null}.
	 * @param byteLengthLimit The maximum amount of bytes that represent the string.
	 * This parameter should be used if the input stream is not trusted.
	 * @return The read string. May be {@code null}.
	 * @throws IOException If an I/O error occurs or the byte length is exceeded.
	 * @throws EOFException If the input streams ends before the string is fully read.
	 * @see #writeString(String, OutputStream)
	 * @see #readString(InputStream)
	 * @since 1.1
	 */
	public static String readString(InputStream in, int byteLengthLimit) throws IOException {
		int n = readInt(in);
		if (n < 0) {
			return null;
		}
		if (n > byteLengthLimit) {
			throw new IOException("Byte length limit exceeded! Limit: " + byteLengthLimit + ", Found: " + n);
		}
		byte[] utf = new byte[n];
		readFully(in, utf);
		return new String(utf, StandardCharsets.UTF_8);
	}
	
	/**
	 * Reads a {@link String} from the specified {@link InputStream}.
	 * This method uses the protocol specified by {@link #writeString(String, OutputStream)}.
	 * @param in The input stream. Must not be {@code null}.
	 * @return The read string. May be {@code null}.
	 * @throws IOException If an I/O error occurs.
	 * @throws EOFException If the input streams ends before the string is fully read.
	 * @see #writeString(String, OutputStream)
	 * @see #readString(InputStream, int)
	 * @since 1.1
	 */
	public static String readString(InputStream in) throws IOException {
		return readString(in, Integer.MAX_VALUE);
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
	 * <p>
	 * If the path represents a directory, all inner files and directories
	 * must be on the same {@link FileStore} as the specified directory.
	 * That means that this method should not be used to delete mount points.
	 * @param path The path that should be deleted. If {@code null}, nothing happens.
	 * @throws IOException If an error occurs. For example, if the path does not exist or cannot be accessed.
	 */
	public static void delete(Path path) throws IOException {
		deleteRec(path, null);
	}
	
	private static void deleteRec(Path path, FileStore baseFileStore) throws IOException {
		if (path == null) {
			return;
		}
		
		// Symbolic links are directly removed (and never interpreted as a directory).
		// Do not check the store of the symbolic link, because getFileStore() might follow the link.
		if (Files.isSymbolicLink(path)) {
			Files.delete(path);
			return;
		}
		
		// Check that this store matches the store of the base path.
		FileStore store = Files.getFileStore(path); // throws IOException if the path does not exist.
		if (baseFileStore != null) {
			if (!baseFileStore.equals(store)) {
				throw new IOException("Cannot delete files in other file store: " + path);
			}
		}
		else {
			// This is the base directory.
			baseFileStore = store;
		}
		
		// Delete regular files.
		if (Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) {
			Files.delete(path);
			return;
		}
		
		// Delete directories recursively.
		if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
			Path[] children;
			try (Stream<Path> stream = Files.list(path)) {
				children = stream.toArray(Path[]::new);
			}
			for (Path child : children) {
				deleteRec(child, baseFileStore);
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
	 * Symbolic links are not followed, but they may be deleted themselves.
	 * <p>
	 * All inner files and directories must be on the same {@link FileStore} as the specified directory.
	 * That means that this method should not be used to delete mount points.
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
		
		// Delete all files from the directory recursively.
		if (Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
			FileStore baseStore = Files.getFileStore(dir);
			Path[] children;
			try (Stream<Path> stream = Files.list(dir)) {
				children = stream.toArray(Path[]::new);
			}
			for (Path child : children) {
				if (filter == null || filter.test(child)) {
					deleteRec(child, baseStore);
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
	
	
	/**
	 * Writes the specified text to the specified file. The UTF-8 encoding is used.
	 * If the file does not exist, it is created.
	 * If the file does already exist, it is overwritten.
	 * @param path The path to the file that should be written.
	 * If {@code null}, nothing happens.
	 * @param text The text that should be written to the file.
	 * If {@code null}, the empty string {@code ""} is written.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void writeTextFile(Path path, String text) throws IOException {
		if (path == null) {
			return;
		}
		
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			writer.write(text != null ? text : "");
			writer.flush();
		}
	}
	
	/**
	 * Reads the specified text file into a {@link String}. The UTF-8 encoding is used.
	 * @param path The path to the file that should be read.
	 * If {@code null}, the empty string {@code ""} is returned.
	 * @return The read text. Not {@code null}.
	 * @throws IOException If an I/O error occurs.
	 * For example, the file does not exist, cannot be read or contains malformed byte sequences.
	 */
	public static String readTextFile(Path path) throws IOException {
		if (path == null) {
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			int ch;
			while ((ch = reader.read()) >= 0) {
				builder.append((char) ch);
			}
		}
		return builder.toString();
	}
	
	/**
	 * Writes the specified bytes to the specified file.
	 * If the file does not exist, it is created.
	 * If the file does already exist, it is overwritten.
	 * @param path The path to the file that should be written.
	 * If {@code null}, nothing happens.
	 * @param bytes The bytes that should be written to the file.
	 * If {@code null}, the file will be empty.
	 * @throws IOException If an I/O error occurs.
	 * @since 1.1
	 */
	public static void writeBinaryFile(Path path, byte[] bytes) throws IOException {
		if (path == null) {
			return;
		}
		try (OutputStream out = Files.newOutputStream(path)) {
			if (bytes != null) {
				out.write(bytes);
			}
			out.flush();
		}
	}
	
	/**
	 * Reads alls bytes of the specified file and returns them as a byte array.
	 * @param path The path to the file that should be read.
	 * If {@code null}, an empty byte array is returned.
	 * @return The read bytes. Not {@code null}.
	 * @throws IOException If an I/O error occurs.
	 * @since 1.1
	 */
	public static byte[] readBinaryFile(Path path) throws IOException {
		if (path == null) {
			return new byte[0];
		}
		try (InputStream in = Files.newInputStream(path);
				ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
			pipeStream(in, bout);
			return bout.toByteArray();
		}
	}
	
	
	/**
	 * Return an array that contains the entries in the directory.
	 * This method behaves like {@link Files#list(Path)},
	 * but all entries are read and the stream is closed.
	 * @param dir The path to the directory.
	 * @return An array that contains the entries in the directory.
	 * @throws IOException See {@link Files#list(Path)}.
	 * @since 1.2
	 */
	public static Path[] listDirectory(Path dir) throws IOException {
		try (Stream<Path> stream = Files.list(dir)) {
			return stream.toArray(Path[]::new);
		}
	}
	
	/**
	 * Returns whether the specified child file is inside the specified parent file.
	 * @param parent the parent file
	 * @param child the child file
	 * @return whether the specified child file is inside the specified parent file
	 * @throws IOException if an {@link IOException} occurs from constructing the related {@link AbsoluteFile}s
	 * @since 1.6
	 */
	public static boolean isFileInside(File parent, File child) throws IOException {
		return isFileInside(parent, child, false);
	}
	
	/**
	 * Returns whether the specified child file is inside the specified parent file.
	 * @param parent the parent file
	 * @param child the child file
	 * @param allowSame whether to return true if the specified parent and child files are equal
	 * @return whether the specified child file is inside the specified parent file
	 * @throws IOException if an {@link IOException} occurs from constructing the related {@link AbsoluteFile}s
	 * @since 1.6
	 */
	public static boolean isFileInside(File parent, File child, boolean allowSame) throws IOException {
		return AbsoluteFile.get(parent).contains(AbsoluteFile.get(child), allowSame);
	}
	
	/**
	 * Returns the file inside the specified parent file resolved from the specified subpath.
	 * @param parent the parent file
	 * @param subpath the subpath to resolve
	 * @return the resolved file
	 * @throws IOException if an {@link IOException} occurs from constructing the related {@link AbsoluteFile}s
	 * @throws FileNotFoundException if the resolved file is not inside the specified parent file
	 * @since 1.6
	 */
	public static File resolveInside(File parent, String subpath) throws IOException {
		return resolveInside(parent, subpath, false);
	}
	
	/**
	 * Returns the file inside the specified parent file resolved from the specified subpath.
	 * @param parent the parent file
	 * @param subpath the subpath to resolve
	 * @param allowSame whether to accept the resolved file if it equals the specified parent file
	 * @return the resolved file
	 * @throws IOException if an {@link IOException} occurs from constructing the related {@link AbsoluteFile}s
	 * @throws FileNotFoundException if the resolved file is not inside the specified parent file
	 * @since 1.6
	 */
	public static File resolveInside(File parent, String subpath, boolean allowSame) throws IOException {
		return AbsoluteFile.get(parent).resolveInside(subpath, allowSame).getFile();
	}
	
	/**
	 * Returns whether the specified child path is inside the specified parent path.
	 * @param parent the parent path
	 * @param child the child path
	 * @return whether this specified child path is inside the specified parent path
	 * @since 1.6
	 */
	public static boolean isPathInside(Path parent, Path child) {
		return isPathInside(parent, child, false);
	}
	
	/**
	 * Returns whether the specified child path is inside the specified parent path.
	 * @param parent the parent path
	 * @param child the child path
	 * @param allowSame whether to return true if the specified parent and child files are equal
	 * @return whether this specified child path is inside the specified parent path
	 * @since 1.6
	 */
	public static boolean isPathInside(Path parent, Path child, boolean allowSame) {
		return AbsolutePath.get(parent).contains(AbsolutePath.get(child), allowSame);
	}
	
	/**
	 * Returns the path inside the specified parent path resolved from the specified subpath.
	 * @param parent the parent path
	 * @param subpath the subpath to resolve
	 * @return the resolved path
	 * @throws InvalidPathException if the resolved path is not inside the specified parent path
	 * @since 1.6
	 */
	public static Path resolveInside(Path parent, String subpath) {
		return resolveInside(parent, subpath, false);
	}
	
	/**
	 * Returns the path inside the specified parent path resolved from the specified subpath.
	 * @param parent the parent path
	 * @param subpath the subpath to resolve
	 * @param allowSame whether to accept the resolved path if it equals the specified parent path
	 * @return the resolved path
	 * @throws InvalidPathException if the resolved path is not inside the specified parent path
	 * @since 1.6
	 */
	public static Path resolveInside(Path parent, String subpath, boolean allowSame) {
		return AbsolutePath.get(parent).resolveInside(subpath, allowSame).getPath();
	}
	
}
