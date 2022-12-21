package ex02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.KomoranUtil;
import common.nlp.WordCount;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.extern.log4j.Log4j;

//https://github.com/shineware/KOMORAN

@Log4j
public class KomoranTest {
	
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
	
	public static void main(String[] args) {
		String str="눈이 부시게 푸르른 날은 그리운 사람을 그리워 하자. 저기 저기 저, 가을 꽃 자리 초록이 지쳐 단풍 드는데";
		str+="눈이 내리면 어이 하리야 봄이 또 오면 어이 하리야 내가 죽고서 네가 산다면! 네가 죽고서 내가 산다면? ";
		str+="눈이 부시게 푸르른 날은 그리운 사람을 그리워 하자 -서정주 푸르른 날 Seo Jung Ju 12345";
		
		//한글 알파벳 숫자 아닌 문자열 제거하기
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
		
		nounList.forEach((s)->System.out.println(s));
		
		Map<String, Integer> map = getWordCount(nounList);
		
		List<WordCount> wordList = KomoranUtil.getWordCountSortProc(map, 0); //빈도수 2개 이상 나오는 단어 목록
		System.out.println(wordList);
	}
	
}
