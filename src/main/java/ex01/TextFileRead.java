package ex01;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class TextFileRead {

	public static void main(String[] args) {
		
		String file = "src/main/java/ex01/readme.txt";
		try (Stream<String> stream = Files.lines(Paths.get(file), Charset.forName("UTF-8"))) {
			//stream.forEach((str)->System.out.println(str));
			//stream.forEach(new Demo()::foo);
			stream.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> list = Arrays.asList("Apple", "Samsumg", "SK");
		list.forEach(System.out::println);
		
		System.out.println("-----------------------------------");
		
		list.forEach(new Demo()::foo);
		System.out.println("-----------------------------------");
	}
}

class Demo {
	public void foo(String str) {
		System.out.println(str);
	}
}
