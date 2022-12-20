package ex01;

import java.io.File;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

/* text minging 작업 중 하나
 * [1] String의 split(), StringTokenizer class
 * [2] BreakIterator
 * [3] 정규식
 */
public class WordDetection {

	public static void main(String[] args) {
		// "Let's get this vis-a-vis", he sait, "these boy's marks are really that
		// well?"
		String input = "Let's get this vis-a-vis\", he said, \"these boy's marks are really that well?";
		String input2 = "레츠 겟 디스 비스어비스 \"히 세드 ";
		System.out.println(input);
		System.out.println(input2);

		String tokens[] = input.split(" ");
		System.out.println(tokens.length);
		System.out.println("==========split=============");
		for (String tk : tokens) {
			System.out.println(tk);
		}

		useStringTokenizer(input);
		useStringTokenizer(input2);

		useBreakIterator(input, Locale.ENGLISH);
		useBreakIterator(input2, Locale.KOREAN);
		
		useSentenceBreakIterator(input, Locale.ENGLISH);
		useSentenceBreakIterator(input2, Locale.KOREAN);
		
		try {
			String fileText = FileUtils.readFileToString(new File("src/main/java/ex01/readme.txt"), "UTF-8");
			useSentenceBreakIterator(fileText, Locale.KOREAN);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void useStringTokenizer(String str) {
		System.out.println("----------StringTokenizer---------------");
		StringTokenizer st = new StringTokenizer(str);
//		StringTokenizer st = new StringTokenizer(str, "-");
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			System.out.println(word);
		}
	}

	// BreakIterator: 텍스트 내의 경계의 위치를 찾아내는 메서드를 가지고 있다.
	// getWordInstance(), getSentenceInstance(), getCharacterInstance()
	// 단어를 탐지해서 추출=>getWordInstance()

	public static void useBreakIterator(String str, Locale loc) {
		System.out.println("--------------BreakIterator-------------");
		BreakIterator it = BreakIterator.getWordInstance(loc);
		it.setText(str);

		int start = it.first(); // 단어가 시작되는 곳의 인덱스 반환
		int end = it.next(); // 다음 단어가 시작되는 곳의 인덱스 반환
		// System.out.println(str.substring(start, end));
		int count = 0;
		while (end != BreakIterator.DONE) {
			String word = str.substring(start, end);

			// 문자이거나 숫자만 걸러 출력
			if (Character.isLetterOrDigit(word.charAt(0))) {
				System.out.println(word);
				count++;
			}
			start = end;
			end = it.next();
		}
		System.out.println("Word count : " + count);
	}
	
	public static void useSentenceBreakIterator(String str, Locale loc) {
		BreakIterator it = BreakIterator.getSentenceInstance(loc);
		it.setText(str);
		int start = it.first();
		int end = it.next();
		
		while(end!=BreakIterator.DONE) {
			String sentence = str.substring(start, end);
			System.out.println(sentence);
			start = end;
			end = it.next();
		}
		
	}
}
