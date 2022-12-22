package common;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

	// null값을 빈문자열로 치환
	public static String nvl(String str, String chg_str) {
		String res = "";
		if (str == null) {
			res = chg_str;
		} else if (str.equals("")) {
			res = chg_str;
		} else {
			res = str;
		}
		return res;
	}// ----------------------------------------

	public static String nvl(String str) {
		return nvl(str, "");
	}// ----------------------------------------

	// 알파벳과 공백을 제외하고 모두 제거하는 메서드
	public static String remainAlphaSpace(String str) {
		if (str != null)
//				String replace_text = text.replaceAll("[^가-힣a-zA-Z0-9]", " ");
			str = str.replaceAll("[^a-zA-Z ]", " ");
		return str;
	}// ----------------------------------------
		// 한글,알파벳, 숫자가 아닌 문자는 빈공백으로 치환한다

	public static String remainKorAlpha(String str) {
		if (str != null)
			str = str.replaceAll("[^가-힣a-zA-Z0-9]", " ");
		return str;
	}// ----------------------------------------
		// 한글과 공백이 아닌 문자는 빈문자로 치환

	public static String remainKorSpace(String str) {
		if (str != null)
			str = str.replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]", " ");
		return str;
	}// ----------------------------------------

	// 공백문자가 여러개 있을 경우 빈문자열로 대치
	public static String spaceToEmpty(String str) {
		if (str != null)
			str = str.replaceAll("\\s+", " ");
		return str;
	}// ----------------------------------------

	public Map<String, Long> getFrequency(String str) {
//		String[] tks = str.toLowerCase().split("[\\.\\s]+");
//		System.out.println(Arrays.toString(tks));

		Stream<String> stream = Stream.of(str.toLowerCase().split("[\\.\\s,]+")).parallel();

		Map<String, Long> wordCountMap = stream.collect(Collectors.groupingBy(String::toString, Collectors.counting()));
		// collect() 메서드를 이용해서 단어와 단어 빈도 수집, 수집된 값은 Map 유형으로 return
		// String은 단어, Long은 해당 단어의 빈도수 갖음

//		wordCountMap.forEach((k, v)->System.out.println(k + " : " + v));
		return wordCountMap;
	}

}
