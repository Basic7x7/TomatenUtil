package de.tomatengames.util.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import de.tomatengames.util.CharsetUtil;
import de.tomatengames.util.HexUtil;
import de.tomatengames.util.exception.CharacterDecodeException;
import de.tomatengames.util.io.UTF8Reader;

class UTF8DecodeTestExhaustive {
	
	public static void main(String[] args) throws InterruptedException {
		Trie validCodePoints = new Trie();
		for (int cp = 0; cp <= 0x10FFFF; cp++) {
			try {
				EncodedCodePoint codePoint = new EncodedCodePoint(cp);
				validCodePoints.put(codePoint.utf8, 0, codePoint.len, codePoint);
			} catch (IllegalArgumentException e) {
				continue;
			}
		}
		
		
		AtomicBoolean ok = new AtomicBoolean(true);
		ArrayList<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 256; i++) {
			final int b0 = i;
			Thread thread = new Thread(() -> {
				try {
					long count = 0L;
					byte[] inputBytes = new byte[4];
					inputBytes[0] = (byte) b0;
					char[] cbuf = new char[2];
					char[] cbufControl = new char[2];
					for (int b1 = 0; b1 < 256; b1++) {
						inputBytes[1] = (byte) b1;
						for (int b2 = 0; b2 < 256; b2++) {
							inputBytes[2] = (byte) b2;
							for (int b3 = 0; b3 < 256; b3++) {
								inputBytes[3] = (byte) b3;
								
								InputStream input = new ByteArrayInputStream(inputBytes);
								input.mark(100);
								
								EncodedCodePoint valid = validCodePoints.get(inputBytes, 0);
								
								if ((count & 0xFFFFFL) == 0L) {
									System.out.println(HexUtil.bytesToHex(inputBytes));
								}
								count++;
								
								if (valid != null) {
									try {
										int decodedCp = CharsetUtil.decodeUTF8(input);
										if (decodedCp != valid.codePoint) {
											System.err.println("CodePoint decode mismatch for input '" + HexUtil.bytesToHex(inputBytes) +
													"': Expected code point = " + valid.codePoint + "; Decoded code point = " + decodedCp);
											ok.set(false);
										}
									} catch (CharacterDecodeException e) {
										e.printStackTrace();
										ok.set(false);
									}
									
									input.reset();
									try (UTF8Reader reader = new UTF8Reader(input)) {
										Arrays.fill(cbuf, '\0');
										Arrays.fill(cbufControl, '\0');
										int n = Character.toChars(valid.codePoint, cbufControl, 0);
										int n2 = n;
										for (int j = 0; j < n; j++) {
											int ch = reader.read();
											if (ch < 0) {
												n2 = j;
												break;
											}
											cbuf[j] = (char) ch;
										}
										if (n != n2 || !Arrays.equals(cbuf, cbufControl)) {
											System.err.println("UTF8Reader decode mismatch for input '" + HexUtil.bytesToHex(inputBytes) +
													"': Expected characters = " + charsToString(n, cbufControl) + " (" + n + "); " +
													"Decoded characters = " + charsToString(n2, cbuf) + " (" + n2 + ")");
											ok.set(false);
										}
									}
								}
								else {
									// Expect failure
									try {
										CharsetUtil.decodeUTF8(input);
										System.err.println("Decode did not fail for invalid input '" + HexUtil.bytesToHex(inputBytes) + "'");
										ok.set(false);
									} catch (CharacterDecodeException e) {
										// This is the expected behavior.
									}
									
									input.reset();
									try (UTF8Reader reader = new UTF8Reader(input)) {
										reader.read();
										System.err.println("UTF8Reader decode did not fail for invalid input '" + HexUtil.bytesToHex(inputBytes) + "'");
										ok.set(false);
									} catch (CharacterDecodeException e) {
										// This is the expected behavior.
									}
								}
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					ok.set(false);
				}
			});
			threads.add(thread);
			thread.start();
		}
		
		for (Thread thread : threads) {
			thread.join();
		}
		
		if (ok.get()) {
			System.out.println("All tests OK");
		}
		else {
			System.err.println("Found errors");
		}
	}
	
	private static String charsToString(int n, char... chars) {
		String str = "";
		for (int i = 0; i < n; i++) {
			str += HexUtil.shortToHex(chars[i]);
		}
		return str;
	}
	
	private static class EncodedCodePoint {
		private final int codePoint;
		private final byte[] utf8;
		private final int len;
		
		public EncodedCodePoint(int codePoint) {
			this.codePoint = codePoint;
			this.utf8 = new byte[4];
			this.len = CharsetUtil.encodeUTF8(codePoint, utf8, 0);
		}
	}
	
	private static class Trie {
		private EncodedCodePoint value;
		private final Trie[] next;
		
		public Trie() {
			this.value = null;
			this.next = new Trie[256];
		}
		
		public void put(byte[] key, int offset, int len, EncodedCodePoint value) {
			if (len <= offset) {
				this.value = value;
				return;
			}
			int index = key[offset] & 0xFF;
			Trie nextTrie = this.next[index];
			if (nextTrie == null) {
				nextTrie = this.next[index] = new Trie();
			}
			nextTrie.put(key, offset+1, len, value);
		}
		
		public EncodedCodePoint get(byte[] key, int offset) {
			if (this.value != null) {
				return this.value;
			}
			if (key.length <= offset) {
				return this.value;
			}
			int index = key[offset] & 0xFF;
			Trie nextTrie = this.next[index];
			if (nextTrie == null) {
				return null;
			}
			return nextTrie.get(key, offset+1);
		}
	}
}
