package org.pjp.museum.ui.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class FileUtils {

	public static String getExtension(File path) {
		String name = path.getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}

	public static String getExtension(String path) {
		return path.substring(path.lastIndexOf('.') + 1);
	}

	public static String getBase(String path) {
		return path.substring(0, path.lastIndexOf('.'));
	}

	/*
	 * https://stackoverflow.com/questions/15968883/how-to-zip-a-folder-itself-using-java
	 */
	public static void pack(String sourceDirPath, File zipFile) throws IOException {
	    Path p = zipFile.toPath();
	    try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
	        Path pp = Paths.get(sourceDirPath);
	        Files.walk(pp)
	          .filter(path -> !Files.isDirectory(path))
	          .forEach(path -> {
	              ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
	              try {
	                  zs.putNextEntry(zipEntry);
	                  Files.copy(path, zs);
	                  zs.closeEntry();
	            } catch (IOException e) {
	                System.err.println(e);
	            }
	          });
	    }
	}
	
	/*
	 * https://www.baeldung.com/java-delete-directory
	 */
	public static void deleteDirectory(File directoryToBeDeleted) throws IOException {
		Files.walk(directoryToBeDeleted.toPath())
	      .sorted(Comparator.reverseOrder())
	      .map(Path::toFile)
	      .forEach(File::delete);
	}
	
	private FileUtils() {
		// prevent instantiation
	}
}
