package de.tomatengames.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Provides utilities to work with hashes.
 * 
 * @author Basic7x7
 * @version
 * 2023-07-29 modified<br>
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
	 * @return The hash. Not {@code null}.
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
	 * @return The hash. Not {@code null}.
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
	 * @return The hash. Not {@code null}.
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
	 * @return The hash of the file's content. Not {@code null}.
	 * @throws IOException If an I/O error occurs.
	 */
	public static byte[] hash(MessageDigest alg, Path path) throws IOException {
		try (InputStream in = Files.newInputStream(path)) {
			return hash(alg, in);
		}
	}
	
	/**
	 * UTF8-encodes the specified {@link String} and passes it to the specified {@link MessageDigest}.
	 * <p>
	 * This method behaves like {@code alg.update(str.getBytes(StandardCharsets.UTF_8))},
	 * but requires only constant memory for large strings.
	 * @param alg The {@link MessageDigest} that should be updated.
	 * @param str The string that should be encoded.
	 * @since 1.3
	 */
	public static void updateUTF8(MessageDigest alg, String str) {
		int strLen = str.length();
		
		// Creates a buffer for the encoded data.
		// If the String is not too long, the buffer's length is str.length+4.
		// So, assuming the String contains only ASCII characters,
		// only one hash-update iteration is needed.
		// Also, the buffer size is proportional to the size of the string.
		// The max offset guarantees that 4 bytes are available in the buffer
		// to store the encoded code point in all cases.
		int maxBufOffset = NumberUtil.limit(strLen, 0, 2048);
		int bufLen = maxBufOffset + 4;
		byte[] buf = new byte[bufLen];
		
		int charIndex = 0;
		while (charIndex < strLen) {
			// Fill the buffer with encoded code points.
			int bufOff = 0;
			while (bufOff <= maxBufOffset && charIndex < strLen) {
				int ch = str.codePointAt(charIndex);
				bufOff += CharsetUtil.encodeUTF8(ch, buf, bufOff);
				charIndex += Character.charCount(ch);
			}
			// Update the hash using the buffer.
			alg.update(buf, 0, bufOff);
		}
	}
	
	/**
	 * UTF8-encodes the specified {@link String} and hashes it.
	 * @param alg The {@link MessageDigest} that should be used to calculate the hash. Must not be {@code null}.
	 * @param str The string that should be encoded. Must not be {@code null}.
	 * @return The hash. Not {@code null}.
	 * @since 1.3
	 */
	public static byte[] hashUTF8(MessageDigest alg, String str) {
		alg.reset();
		updateUTF8(alg, str);
		return alg.digest();
	}
	
	
	/**
	 * UTF8-encodes the specified {@link char} array and passes it to the specified {@link MessageDigest}.
	 * <p>
	 * This method behaves like
	 * <pre>
	 * alg.update(new String(chars, offset, length).getBytes(StandardCharsets.UTF_8))
	 * </pre>
	 * but requires only constant memory for large strings and overwrites additional copies of the {@code char} array.
	 * @param alg The {@link MessageDigest} that should be updated. Must not be {@code null}.
	 * @param chars The {@code char} array that should be encoded. Must not be {@code null}.
	 * @param offset The first index of the {@code char} array that should be encoded.
	 * Must not be negative. Must fulfill {@code offset+length <= chars.length}.
	 * @param length The amount of characters of the {@code char} array that should be encoded, starting at {@code offset}.
	 * Must not be negative.
	 * @since 1.4
	 */
	public static void updateUTF8(MessageDigest alg, char[] chars, int offset, int length) {
		// --- Analog to updateUTF8(MessageDigest, String) ---
		int maxBufOffset = NumberUtil.limit(length, 0, 2048);
		int bufLen = maxBufOffset + 4;
		byte[] buf = new byte[bufLen];
		
		int charIndex = offset;
		int end = offset+length;
		while (charIndex < end) {
			// Fill the buffer with encoded code points.
			int bufOff = 0;
			while (bufOff <= maxBufOffset && charIndex < end) {
				int ch = Character.codePointAt(chars, charIndex, end);
				bufOff += CharsetUtil.encodeUTF8(ch, buf, bufOff);
				charIndex += Character.charCount(ch);
			}
			// Update the hash using the buffer.
			alg.update(buf, 0, bufOff);
		}
		
		// Overwrite the buffer to delete the encoded char data.
		Arrays.fill(buf, (byte) 0);
	}
	
	/**
	 * UTF8-encodes the specified {@link char} array and passes it to the specified {@link MessageDigest}.
	 * <p>
	 * This method behaves like {@code alg.update(new String(chars).getBytes(StandardCharsets.UTF_8))},
	 * but requires only constant memory for large strings and overwrites additional copies of the {@code char} array.
	 * @param alg The {@link MessageDigest} that should be updated. Must not be {@code null}.
	 * @param chars The {@code char} array that should be encoded. Must not be {@code null}.
	 * @since 1.4
	 */
	public static void updateUTF8(MessageDigest alg, char[] chars) {
		updateUTF8(alg, chars, 0, chars.length);
	}
	
	/**
	 * UTF8-encodes the specified {@link char} array and hashes it.
	 * @param alg The {@link MessageDigest} that should be used to calculate the hash. Must not be {@code null}.
	 * @param str The {@code char} array that should be encoded. Must not be {@code null}.
	 * @return The hash. Not {@code null}.
	 * @since 1.4
	 */
	public static byte[] hashUTF8(MessageDigest alg, char[] chars) {
		alg.reset();
		updateUTF8(alg, chars);
		return alg.digest();
	}
}
