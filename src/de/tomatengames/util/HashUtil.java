package de.tomatengames.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Provides utilities to work with hashes.
 * 
 * @author Basic7x7
 * @version 2023-02-13
 * @since 1.0
 */
public class HashUtil {
	private static final int BUF_SIZE = 8192;
	
	/**
	 * The name of the {@code MD5} hash algorithm.
	 * <p>
	 * All Java platforms before Java 14 are required to support this algorithm.
	 */
	public static final String MD5 = "MD5";
	
	/**
	 * The name of the {@code SHA-1} hash algorithm.
	 * <p>
	 * All Java platforms are required to support this algorithm.
	 */
	public static final String SHA1 = "SHA-1";
	
	/**
	 * The name of the {@code SHA-256} hash algorithm.
	 * <p>
	 * All Java platforms are required to support this algorithm.
	 */
	public static final String SHA256 = "SHA-256";
	
	/**
	 * The name of the {@code SHA-512} hash algorithm.
	 * <p>
	 * The Java platform is <b>not</b> required to support this algorithm.
	 */
	public static final String SHA512 = "SHA-512";
	
	
	// Static class
	private HashUtil() {
	}
	
	/**
	 * Returns a {@link MessageDigest} object that implements the specified algorithm.
	 * @param algorithm The algorithm name.
	 * @return The {@link MessageDigest} object.
	 * @throws IllegalArgumentException If the algorithm is unknown.
	 * @see MessageDigest#getInstance(String)
	 */
	public static MessageDigest get(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Returns a {@link MessageDigest} object that implements the MD5 algorithm.
	 * @return The {@link MessageDigest} object.
	 * @throws IllegalArgumentException If the MD5 algorithm is not supported by this runtime.
	 * All Java platforms before Java 14 are required to support this algorithm.
	 * @see #get(String)
	 * @since 1.1
	 */
	public static MessageDigest getMD5() {
		return get(MD5);
	}
	
	/**
	 * Returns a {@link MessageDigest} object that implements the SHA-1 algorithm.
	 * @return The {@link MessageDigest} object.
	 * @see #get(String)
	 * @since 1.1
	 */
	public static MessageDigest getSHA1() {
		return get(SHA1); // SHA1 must be supported ==> no IllegalArgumentException
	}
	
	/**
	 * Returns a {@link MessageDigest} object that implements the SHA-256 algorithm.
	 * @return The {@link MessageDigest} object.
	 * @see #get(String)
	 * @since 1.1
	 */
	public static MessageDigest getSHA256() {
		return get(SHA256); // SHA256 must be supported ==> no IllegalArgumentException
	}
	
	/**
	 * Returns a {@link MessageDigest} object that implements the SHA-512 algorithm.
	 * @return The {@link MessageDigest} object.
	 * @throws IllegalArgumentException If the SHA-512 algorithm is not supported by this runtime.
	 * @see #get(String)
	 * @since 1.1
	 */
	public static MessageDigest getSHA512() {
		return get(SHA512);
	}
	
	
	/**
	 * Calculates the hash of the specified bytes and returns it.
	 * @param alg The {@link MessageDigest} that should be used to calculate the hash.
	 * @param bytes The bytes that should be hashed.
	 * @return The hash.
	 */
	public static byte[] hash(MessageDigest alg, byte[] bytes) {
		alg.reset();
		return alg.digest(bytes);
	}
	
	/**
	 * Calculates the hash of the specified bytes and returns it.
	 * @param alg The {@link MessageDigest} that should be used to calculate the hash.
	 * @param bytes Multiple byte arrays that are handled as a single large one.
	 * Must not be {@code null}, but the inner arrays may be {@code null}.
	 * Inner arrays that are {@code null} are ignored.
	 * @return The hash.
	 */
	public static byte[] hash(MessageDigest alg, byte[]... bytes) {
		alg.reset();
		for (byte[] b : bytes) {
			if (b != null) {
				alg.update(b);
			}
		}
		return alg.digest();
	}
	
	/**
	 * Reads data from the input stream and hashes it.
	 * @param alg The {@link MessageDigest} that should be used to calculate the hash.
	 * @param input The input stream from which the data should be read.
	 * @return The hash.
	 * @throws IOException If an I/O error occurs.
	 */
	public static byte[] hash(MessageDigest alg, InputStream input) throws IOException {
		alg.reset();
		byte[] buf = new byte[BUF_SIZE];
		int n;
		while ((n = input.read(buf, 0, BUF_SIZE)) >= 0) {
			alg.update(buf, 0, n);
		}
		return alg.digest();
	}
	
	/**
	 * Reads the content from the specified file and hashes it.
	 * @param alg The {@link MessageDigest} that should be used to calculate the hash.
	 * @param path A path to the file that should be read.
	 * @return The hash of the file's content.
	 * @throws IOException If an I/O error occurs.
	 */
	public static byte[] hash(MessageDigest alg, Path path) throws IOException {
		try (InputStream in = Files.newInputStream(path)) {
			return hash(alg, in);
		}
	}
	
}
