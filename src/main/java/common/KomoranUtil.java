package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.springframework.stereotype.Component;

import common.nlp.WordCount;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class KomoranUtil {
	
	static Komoran nlp = new Komoran(DEFAULT_MODEL.FULL);
	
	public static List<String> getWordNouns(String str) {
			
			String txt = str.replaceAll("[^가-힣a-zA-Z0-9]", " ");
			txt = txt.trim();
			System.out.println(txt);
			System.out.println("---------------------------------");
			
			//형태소 분석 시작
			KomoranResult res = nlp.analyze(txt);
			//형태소 분석 결과 중 명사만 추출하기
			List<String> nounList = res.getNouns();
			
			if(nounList==null) {
				nounList = new ArrayList<>();
			}
			
			log.info("nounList === " + nounList);
	//		nounList.forEach((s)->System.out.println(s));
			return nounList;
		}
	
	public static Map<String, Integer> getWordCount(List<String> nounList) {
		if(nounList==null) {
			nounList = new ArrayList<>();
		}
		
		//(단어, 빈도수) (눈, 3)
		Map<String, Integer> wMap = new HashMap<>();
		Set<String> set = new HashSet<>(nounList); //중복제거
		
		Iterator<String> it = set.iterator();
		while(it.hasNext()) {
			String word = it.next();
			int frequency = Collections.frequency(nounList, word);
			log.info(word + " : " + word + ", frequency : " + frequency);
			wMap.put(word, frequency);
			
		}
		return wMap;
		
	}
	
	//카운팅된 단어 빈도수 앱에서 빈도수가 2개 이상인 단어들만 정렬해서 return하는 메서드
	public static List<WordCount> getWordCountSortProc(Map<String, Integer> map, int n) {
		
		PriorityQueue<WordCount> pq = new PriorityQueue<WordCount>();
		Set<String> set = map.keySet(); //key값들만 set유형으로 추출
		for(String key:set) {
			Integer val = map.get(key);
			WordCount wc = new WordCount();
			wc.setCnt(val);
			wc.setWord(key);
			pq.add(wc);
		}
		
		List<WordCount> arr = new ArrayList<>();
		while(!pq.isEmpty()) {
			WordCount wc= pq.poll();
			if(wc.getWord().length()>=1 && wc.getCnt()>n) {
				arr.add(wc);
			}
		}
		
		return arr;
		
	}
}
