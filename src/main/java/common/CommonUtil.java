package common;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
		
	public Map<String, Long> getFrequency(String str) {
//		String[] tks = str.toLowerCase().split("[\\.\\s]+");
//		System.out.println(Arrays.toString(tks));
		
		Stream<String> stream = Stream.of(str.toLowerCase().split("[\\.\\s,]+")).parallel();
		
		Map<String, Long> wordCountMap =	 stream.collect(Collectors.groupingBy(String::toString, Collectors.counting()));
		//collect() 메서드를 이용해서 단어와 단어 빈도 수집, 수집된 값은 Map 유형으로 return
		//String은 단어, Long은 해당 단어의 빈도수 갖음
		
//		wordCountMap.forEach((k, v)->System.out.println(k + " : " + v));
		return wordCountMap;
	}
	
}
