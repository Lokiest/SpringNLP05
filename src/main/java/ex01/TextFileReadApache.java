package ex01;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TextFileReadApache {
	
	public static void main(String[] args) throws IOException {
		File file = new File("src/main/java/ex01/readme.txt");
		String str = FileUtils.readFileToString(file, "UTF-8");
		System.out.println(str);
	}

}
