package de.tomatengames.util;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.File;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Provides methods to handle ZIP files.
 * 
 * @author Basic7x7
 * @version 2023-02-12
 * @since 1.0
 */
public class ZipUtil {
	
	// Static class
	private ZipUtil() {
	}
	
	/**
	 * Extracts the entries of the specified ZIP file into the specified target directory.
	 * @param zipFile The ZIP file.
	 * @param targetDir The target directory.
	 * @throws IOException If an error occurs.
	 */
	public static void extractZIP(File zipFile, Path targetDir) throws IOException {
		try (ZipFile zip = new ZipFile(zipFile, StandardCharsets.UTF_8)) {
			extractZIP(zip, targetDir);
		}
	}
	
	/**
	 * Extracts the entries of the specified ZIP file into the specified target directory.
	 * @param zip The ZIP file.
	 * @param targetDir The target directory.
	 * @throws IOException If an error occurs.
	 */
	public static void extractZIP(ZipFile zipFile, Path targetDir) throws IOException {
		targetDir = targetDir.toAbsolutePath().normalize();
		ZipEntry entry;
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			entry = entries.nextElement();
			
			// Gets the path/name of the entry without the first '/'.
			String name = entry.getName();
			if (name.startsWith("/")) {
				name = name.substring(1);
			}
			
			// Determines the path of the target file.
			Path target = targetDir.resolve(name).toAbsolutePath().normalize();
			if (!target.startsWith(targetDir)) {
				throw new IOException("Invalid target path of zip entry: " + target);
			}
			
			if (entry.isDirectory()) {
				// Creates an empty directory.
				if (!Files.exists(target)) {
					Files.createDirectories(target);
				}
			}
			else {
				// Create parent directories if needed.
				Path parent = target.getParent();
				if (!Files.exists(parent)) {
					Files.createDirectories(parent);
				}
				
				// Writes the target file.
				try (ReadableByteChannel inChannel = Channels.newChannel(zipFile.getInputStream(entry));
						FileChannel outChannel = FileChannel.open(target, WRITE, CREATE, TRUNCATE_EXISTING)) {
					outChannel.transferFrom(inChannel, 0, Long.MAX_VALUE);
				}
			}
		}
	}
	
}
