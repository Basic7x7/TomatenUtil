package de.tomatengames.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import de.tomatengames.util.IOUtil;

/**
 * An AbsoluteFile represents an absolute and direct path to a file.
 * It is meant to be used with {@link File}s. See {@link AbsolutePath} for usage with {@link Path}s.
 * 
 * @author LukasE7x7
 * @version 2024-05-21
 * @since 1.6
 */
public class AbsoluteFile {
	
	private final String abspath;
	
	/**
	 * @param abspath must be an absolute and direct path
	 */
	private AbsoluteFile(String abspath) {
		this.abspath = abspath;
	}
	
	/**
	 * Returns an {@link AbsoluteFile} that represents the {@link File#getCanonicalPath()}
	 * of the specified file.
	 * @param file the filename
	 * @return the {@link AbsoluteFile} of the specified file
	 * @throws IOException if an {@link IOException} occurs from {@link File#getCanonicalPath()}
	 */
	public static AbsoluteFile get(String file) throws IOException {
		return get(new File(file));
	}
	
	/**
	 * Returns an {@link AbsoluteFile} that represents the {@link File#getCanonicalPath()}
	 * of the specified file.
	 * @param file the file
	 * @return the {@link AbsoluteFile} of the specified file
	 * @throws IOException if an {@link IOException} occurs from {@link File#getCanonicalPath()}
	 */
	public static AbsoluteFile get(File file) throws IOException {
		return new AbsoluteFile(file.getCanonicalPath());
	}
	
	/**
	 * Returns the absolute and direct path that represents this file.
	 * @return the absolute and direct path that represents this file
	 */
	public String getPath() {
		return this.abspath;
	}
	
	/**
	 * Returns a {@link File} that represents this file.
	 * @return a {@link File} that represents this file
	 */
	public File getFile() {
		return new File(this.abspath);
	}
	
	@Override
	public String toString() {
		return this.abspath;
	}
	
	/**
	 * {@link #contains(AbsoluteFile, boolean)} with allowSame={@value IOUtil#DEFAULT_INSIDE_ALLOWSAME}
	 * @param file the file
	 * @return whether the specified file is inside this file
	 */
	public boolean contains(AbsoluteFile file) {
		return contains(file, false);
	}
	
	/**
	 * Returns whether the specified file is inside this file.
	 * @param file the file
	 * @param allowSame whether to return true if the specified file equals this file
	 * @return whether the specified file is inside this file.
	 */
	public boolean contains(AbsoluteFile file, boolean allowSame) {
		if (allowSame)
			return isInsideOrSameDirectory(this.abspath, file.abspath);
		else
			return isInsideDirectory(this.abspath, file.abspath);
	}
	
	/**
	 * Returns the file inside this file resolved from the specified subpath.
	 * @param subpath the subpath to resolve
	 * @return the resolved file
	 * @throws FileNotFoundException if the resolved file is not inside this file
	 * @see #contains(AbsoluteFile)
	 */
	public AbsoluteFile resolveInside(String subpath) throws IOException {
		return resolveInside(subpath, false);
	}
	
	/**
	 * Returns the file inside this file resolved from the specified subpath.
	 * @param subpath the subpath to resolve
	 * @param allowSame whether to accept the resolved file if it equals this file
	 * @return the resolved file
	 * @throws FileNotFoundException if the resolved file is not inside this file
	 * @see #contains(AbsoluteFile, boolean)
	 */
	public AbsoluteFile resolveInside(String subpath, boolean allowSame) throws IOException {
		AbsoluteFile innerFile = get(new File(this.abspath, subpath));
		if (!this.contains(innerFile, allowSame))
			throw new FileNotFoundException("'" + subpath + "' navigates outside of " + this.abspath);
		return innerFile;
	}
	
	/**
	 * Determines whether the file represented by the specified absolute/canonical file path
	 * is inside the directory represented by the specified absolute/canonical directory path.
	 * The file may also equal the directory. To disallow this case, use {@link #isInsideDirectory(String, String)}.
	 * This is basically {@link String#startsWith(String)} but with some refinements.
	 * @param canonicalDir the directory path as received from {@link File#getCanonicalPath()}
	 * @param canonicalFile the file path as received from {@link File#getCanonicalPath()}
	 * @return whether the file is inside the directory or it is the directory itself
	 */
	private static boolean isInsideOrSameDirectory(String canonicalDir, String canonicalFile) {
		canonicalDir = noTrailingSeparator(canonicalDir);
		if (!canonicalFile.startsWith(canonicalDir))
			return false;
		if (canonicalDir.length() == canonicalFile.length())
			return true;
		return canonicalFile.charAt(canonicalDir.length()) == File.separatorChar;
	}
	
	/**
	 * Determines whether the file represented by the specified absolute/canonical file path
	 * is inside the directory represented by the specified absolute/canonical directory path.
	 * The file may not equal the directory. To allow this case, use {@link #isInsideOrSameDirectory(String, String)}.
	 * This is basically {@link String#startsWith(String)} but with some refinements.
	 * @param canonicalDir the directory path as received from {@link File#getCanonicalPath()}
	 * @param canonicalFile the file path as received from {@link File#getCanonicalPath()}
	 * @return whether the file is inside the directory
	 */
	private static boolean isInsideDirectory(String canonicalDir, String canonicalFile) {
		canonicalDir = noTrailingSeparator(canonicalDir);
		canonicalFile = noTrailingSeparator(canonicalFile);
		return canonicalFile.length() > canonicalDir.length() && canonicalFile.startsWith(canonicalDir);
	}
	
	/**
	 * Cuts off one trailing {@link File#separator} if there is one.
	 * This is meant to normalize results of {@link File#getCanonicalPath()}
	 * in case they end with a {@link File#separator}.
	 * @param path the path that may end with a {@link File#separator}
	 * @return the path without its trailing separator
	 */
	private static String noTrailingSeparator(String path) {
		if (path.endsWith(File.separator))
			return path.substring(0, path.length() - 1);
		return path;
	}
	
}
