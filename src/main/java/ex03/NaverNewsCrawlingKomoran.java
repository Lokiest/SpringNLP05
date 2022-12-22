package ex03;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import common.KomoranUtil;
import common.nlp.CloudImageGenerator;
import common.nlp.CloudViewer;
import common.nlp.StringProcessor;
import common.nlp.WordCount;
import lombok.extern.log4j.Log4j;

@Log4j
public class NaverNewsCrawlingKomoran {

	public static void main(String[] args) throws Exception {
		String url = "https://n.news.naver.com/mnews/article/003/0011603929?sid=105";
		
		NaverNewsCrawlingKomoran app = new NaverNewsCrawlingKomoran();
		app.getNewsContents(url);

	}
	
	public String getNewsContents(String url) throws Exception {
		String str = "";
		//http protocol만 가능, https protocol은 보안상 불가능
		Document doc = Jsoup.connect(url).get();
//		System.out.println(doc);
		//body > div
		Elements newsContent = doc.select("div#dic_area");
		str = newsContent.text();
//		log.info(str);
		
		List<String> nounArr = KomoranUtil.getWordNouns(str);
		Map<String, Integer> wordCuntMap = KomoranUtil.getWordCount(nounArr);
		List<WordCount> wordCountList = KomoranUtil.getWordCountSortProc(wordCuntMap, 0);
		
		log.info(wordCountList);
		HashSet<String> stopWord = new HashSet<>();
		stopWord.add("정치");
		stopWord.add("비교");
		stopWord.add("연료");
		
		StringProcessor strProc = new StringProcessor(str, stopWord);
		
		ArrayList<WordCount> arrList = strProc.processString2(wordCuntMap, 0);
		
		makeWordCloudView(strProc);
		
		return str;
	}
	
	public static final int WIDTH=1200;
	public static final int HEIGHT=800;
	public static final int PADDING=30;

	private void makeWordCloudView(StringProcessor strProc) {
		JFrame f = new JFrame("wordCloudView");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationByPlatform(true);
		f.pack();
		Insets inset = f.getInsets(); //안쪽 여백
		Dimension dim = calcScreenSize(inset);
		f.setSize(dim);
		
		CloudImageGenerator gen = new CloudImageGenerator(WIDTH, HEIGHT, PADDING);
		BufferedImage bufImg = gen.generateImage(strProc, 1);
		CloudViewer panel = new CloudViewer(bufImg);
		f.setContentPane(panel);
		f.setVisible(true);
	}
	
	private static Dimension calcScreenSize(Insets insets) {
        int width = insets.left + insets.right + WIDTH + PADDING * 2;
        int height = insets.top + insets.bottom + HEIGHT + PADDING * 2;
        return new Dimension(width, height);
    }

}
