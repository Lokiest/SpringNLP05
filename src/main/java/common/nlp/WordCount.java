package common.nlp;

import lombok.Data;
//빈도수 내림차순으로 정렬할 수 있도록 Comparable를 구현

@Data
public class WordCount implements Comparable<WordCount> {
	
	private String word;
	private int cnt;
	
	@Override
	public int compareTo(WordCount o) {
		
//		return this.cnt-o.cnt;
		return o.cnt-this.cnt;
	}
}
