package ex01;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class TextFileRead {

	public static void main(String[] args) {
		
		String file = "src/ex01/readme.txt";
		try (Stream<String> stream = Files.lines(Paths.get(file))) {
			stream.forEach((str)->System.out.println(str));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
