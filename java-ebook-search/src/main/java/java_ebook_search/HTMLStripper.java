package java_ebook_search;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is just a simple program that replaces all instances of HTML in our
 * dataset for the purposes of indexing. It is not likely to be used elsewhere.
 *
 * @author Aidan O'Grady
 * @version 0.1
 */
public class HTMLStripper {

	/**
	 * Main method for doing stripping.
	 * @param args - program args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		allFiles(new File("/Users/kp_one/Desktop/java"));
	}

	/**
	 * Constructor
	 * @throws Exception
	 */
	public HTMLStripper() throws Exception {
		File parent = new File("files/");
		allFiles(parent);
	}

	/**
	 * Recursively goes through every subdirectory and file.
	 * @param parent - parent directory
	 * @throws Exception
	 */
	private static void allFiles(File parent) throws Exception {
		File[] files = parent.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				allFiles(file);
			} else {
				strip(file);
			}
		}
	}

	/**
	 * Removes all HTML tags from the given file.
	 * @param file - file to be stripped.
	 * @throws Exception
	 */
	private static void strip(File file) throws Exception {
		Path path = file.toPath();
		Charset charset = Charset.defaultCharset();
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll("(?s)<.*?>|&nbsp;", "");
		content = content.replaceAll("[\r\n]+", "\n");
		Files.write(path, content.getBytes(charset));
	}
}
