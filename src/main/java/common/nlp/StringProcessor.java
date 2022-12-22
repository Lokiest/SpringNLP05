package common.nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
//https://overface.tistory.com/474
//https://github.com/epicdevs/Word-Cloud/
/*문장을 읽으며 " " 기준으로 문자열을 읽고
removePunctuations 메서드를 통해 {} 대괄호, 숫자 등을 제거한 순수 단어만 추출한다
filter.contains를 통해 blacklist(제외) 등록된 단어인 경우 제외한다
HashMap에 단어를 등록하면서 weight를 적산한다
이후 우선순위 큐에 weight 기준으로 원소를 삽입한다.
 * */
public class StringProcessor implements Iterable<WordCount>{
    private String str;
    private final HashSet<String> filter;
    private ArrayList<WordCount> words;
    
    public StringProcessor(String str, HashSet<String> filter) {
        this.str = str;
        this.filter = filter;
        //processString();
    }
    public ArrayList<WordCount> processString2(Map<String, Integer> count, int minCnt) {
        PriorityQueue<WordCount> pq = new PriorityQueue<WordCount>();
        for (Entry<String, Integer> entry : count.entrySet()) {
        	////////////////////
        	if(filter!=null&&filter.contains(entry.getKey())) {
        		continue;//불용어는 불포함하도록 continue
        	}
        	////////////////////
            pq.add(new WordCount(entry.getKey(), entry.getValue()));
        }
        words = new ArrayList<WordCount>();
        while (!pq.isEmpty()) {
            WordCount wc = pq.poll();
            if (wc.getWord().length() > minCnt) words.add(wc);
        }
        return words;
    }//------------------------------------------------
    
  //단어 카운팅된 맵에서 빈도수가 2이상인 단어들만 sorting해서 WordCount객체에 담아 ArrayList에 저장하여 반환함
  	//WordCount클래스에 Comparable 구현함. 빈도수 내림차순으로
  	public static ArrayList<WordCount> wordCountSortProcessing(Map<String, Integer> count) {        
          PriorityQueue<WordCount> pq = new PriorityQueue<WordCount>();
          for (Entry<String, Integer> entry : count.entrySet()) {
              pq.add(new WordCount(entry.getKey(), entry.getValue()));
          }
          ArrayList<WordCount> words = new ArrayList<WordCount>();
          while (!pq.isEmpty()) {
              WordCount wc = pq.poll();
              if (wc.getWord().length() > 1 && wc.getCnt()>1) words.add(wc);
              //&& wc.n>1 추가 빈도수 2개 이상만 추가
			  //words.add(wc);
          }
          return words;
      }//------------------------------------------------
  	public void setStr(String str) {
  		this.str=str;
  	}
  	//str을 처리하는 메서드==> 이것을 수정하여 processString2()메서드를 활용하자
    private void processString() {
        Scanner scan = new Scanner(str);
        HashMap<String, Integer> count = new HashMap<String, Integer>();
        while (scan.hasNext()) {
            String word = removePunctuations(scan.next());
            if (filter!=null&&filter.contains(word)) continue;
            if (word.equals("")) continue;
            Integer n = count.get(word);
            count.put(word, (n == null) ? 1 : n + 1);
        }
        PriorityQueue<WordCount> pq = new PriorityQueue<WordCount>();
        for (Entry<String, Integer> entry : count.entrySet()) {
            pq.add(new WordCount(entry.getKey(), entry.getValue()));
        }
        words = new ArrayList<WordCount>();
        while (!pq.isEmpty()) {
            WordCount wc = pq.poll();
            if (wc.getWord().length() > 1) words.add(wc);
        }
    }
    
    public void print() {
        Iterator<WordCount> iter = iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    @Override
    public Iterator<WordCount> iterator() {
        return words.iterator();
    }
    
    private static String removePunctuations(String str) {
        return str.replaceAll("\\p{Punct}|\\p{Digit}", "");
    }
}
