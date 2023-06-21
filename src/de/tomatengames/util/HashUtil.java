package de.tomatengames.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Provides utilities to work with hashes.
 * 
 * @author Basic7x7
 * @version
 * 2023-06-18 modified<br>
 * 2023-02-13 created
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
	
	/**
	 * The name of the {@code HMAC-MD5} MAC algorithm.
	 * <p>
	 * All Java platforms before Java 14 are required to support this algorithm.
	 * @since 1.3
	 */
	public static final String HMAC_MD5 = "HmacMD5";
	
	/**
	 * The name of the {@code HMAC-SHA1} MAC algorithm.
	 * <p>
	 * All Java platforms are required to support this algorithm.
	 * @since 1.3
	 */
	public static final String HMAC_SHA1 = "HmacSHA1";
	
	/**
	 * The name of the {@code HMAC-SHA256} MAC algorithm.
	 * <p>
	 * All Java platforms are required to support this algorithm.
	 * @since 1.3
	 */
	public static final String HMAC_SHA256 = "HmacSHA256";
	
	/**
	 * The name of the {@code HMAC-SHA512} MAC algorithm.
	 * <p>
	 * The Java platform is <b>not</b> required to support this algorithm.
	 * @since 1.3
	 */
	public static final String HMAC_SHA512 = "HmacSHA512";
	
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
	 * Returns a {@link Mac} object that implements the specified algorithm using the specified key.
	 * @param algorithm The algorithm name.
	 * @param key The key.
	 * @return The {@link Mac} object.
	 * @throws IllegalArgumentException If the algorithm is unknown.
	 * @see Mac#getInstance(String)
	 */
	public static Mac getMac(String algorithm, byte[] key) {
		try {
			Mac m = Mac.getInstance(algorithm);
			m.init(new SecretKeySpec(key, algorithm));
			return m;
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
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
	 * Returns a new {@link Mac} object that implements the HMAC-MD5 algorithm using the specified key.
	 * @param key The key.
	 * @return The {@link Mac} object.
	 * @throws IllegalArgumentException If the HMAC-MD5 algorithm is not supported by this runtime.
	 * @see #getMac(String, byte[])
	 * @since 1.3
	 */
	public static Mac getHmacMD5(byte[] key) {
		return getMac(HMAC_MD5, key);
	}
	
	/**
	 * Returns a new {@link Mac} object that implements the HMAC-SHA1 algorithm using the specified key.
	 * @param key The key.
	 * @return The {@link Mac} object.
	 * @see #getMac(String, byte[])
	 * @since 1.3
	 */
	public static Mac getHmacSHA1(byte[] key) {
		return getMac(HMAC_SHA1, key); // HMAC-SHA1 must be supported ==> no IllegalArgumentException
	}
	
	/**
	 * Returns a new {@link Mac} object that implements the HMAC-SHA256 algorithm using the specified key.
	 * @param key The key.
	 * @return The {@link Mac} object.
	 * @see #getMac(String, byte[])
	 * @since 1.3
	 */
	public static Mac getHmacSHA256(byte[] key) {
		return getMac(HMAC_SHA256, key); // HMAC-SHA1 must be supported ==> no IllegalArgumentException
	}
	
	/**
	 * Returns a new {@link Mac} object that implements the HMAC-SHA512 algorithm using the specified key.
	 * @param key The key.
	 * @return The {@link Mac} object.
	 * @throws IllegalArgumentException If the HMAC-SHA512 algorithm is not supported by this runtime.
	 * @see #getMac(String, byte[])
	 * @since 1.3
	 */
	public static Mac getHmacSHA512(byte[] key) {
		return getMac(HMAC_SHA512, key);
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
