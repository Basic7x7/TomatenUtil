package de.tomatengames.util.io;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * An AbsolutePath represents an absolute and direct {@link Path}.
 * See {@link AbsoluteFile} for usage with {@link File}s.
 * 
 * @author LukasE7x7
 * @version 2024-05-21
 * @since 1.6
 */
public class AbsolutePath {
	
	private final Path abspath;
	
	/**
	 * @param abspath must be an absolute and direct path
	 */
	private AbsolutePath(Path abspath) {
		this.abspath = abspath;
	}
	
	/**
	 * Returns an {@link AbsolutePath} that represents the absolute and direct equivalent {@link Path}.
	 * @param path the input path name
	 * @return the {@link AbsolutePath} of the specified path
	 */
	public static AbsolutePath get(String path) {
		return get(Paths.get(path));
	}
	
	/**
	 * Returns an {@link AbsolutePath} that represents the absolute and direct equivalent {@link Path}.
	 * @param path the input {@link Path}
	 * @return the {@link AbsolutePath} of the specified path
	 */
	public static AbsolutePath get(Path path) {
		return new AbsolutePath(path.toAbsolutePath().normalize());
	}
	
	/**
	 * Returns this AbsolutePath as a {@link Path}.
	 * It is absolute and direct/normalized.
	 * @return the {@link Path}
	 */
	public Path getPath() {
		return abspath;
	}
	
	@Override
	public String toString() {
		return this.abspath.toString();
	}
	
	/**
	 * {@link #contains(AbsolutePath, boolean)} with allowSame=false.
	 * @param path the path
	 * @return whether the specified path is inside this path
	 */
	public boolean contains(AbsolutePath path) {
		return contains(path, false);
	}
	
	/**
	 * Returns whether the specified path is inside this path.
	 * @param path the path
	 * @param allowSame whether to return true if the specified path equals this path
	 * @return whether the specified path is inside this path.
	 */
	public boolean contains(AbsolutePath path, boolean allowSame) {
		return path.abspath.startsWith(this.abspath) && (allowSame || !this.abspath.startsWith(path.abspath));
	}
	
	/**
	 * Returns the path inside this path resolved from the specified subpath.
	 * @param subpath the subpath to resolve
	 * @return the resolved path
	 * @throws InvalidPathException if the resolved path is not inside this path
	 * @see #contains(AbsolutePath)
	 */
	public AbsolutePath resolveInside(String subpath) {
		return resolveInside(subpath, false);
	}
	
	/**
	 * Returns the path inside this path resolved from the specified subpath.
	 * @param subpath the subpath to resolve
	 * @param allowSame whether to accept the resolved path if it equals this path
	 * @return the resolved path
	 * @throws InvalidPathException if the resolved path is not inside this path
	 * @see #contains(AbsolutePath, boolean)
	 */
	public AbsolutePath resolveInside(String subpath, boolean allowSame) {
		AbsolutePath innerPath = get(this.abspath.resolve(subpath));
		if (!this.contains(innerPath, allowSame))
			throw new InvalidPathException(subpath, "'" + subpath + "' navigates out of " + this.abspath);
		return innerPath;
	}
	
}
