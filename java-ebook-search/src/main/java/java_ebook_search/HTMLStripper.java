package java_ebook_search;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is just a simple program that replaces all instances of HTML in our
 * dataset for the purposes of indexing. It is not likely to be used elsewhere.
 *
 * Created by Aidan O'Grady on 14/10/15.
 */
public class HTMLStripper {

	public static void main(String[] args) throws Exception {
		allFiles(new File("/Users/kp_one/Desktop/java"));
	}

	public HTMLStripper() throws Exception {
		File parent = new File("files/");
		allFiles(parent);
	}

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

	private static void strip(File file) throws Exception {
		Path path = file.toPath();
		Charset charset = Charset.defaultCharset();
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll("(?s)<.*?>|&nbsp;", "");
		content = content.replaceAll("[\r\n]+", "\n");
		Files.write(path, content.getBytes(charset));
	}
}
