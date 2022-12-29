package ex07;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weka.associations.Apriori;
import weka.associations.AssociationRule;
import weka.associations.AssociationRules;
import weka.associations.Item;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;

public class Weka06Apiori {
	
	String file="C:\\Weka-3-9\\data\\CharlesBookClub_preprocess.arff";
	DataSource ds;
	Instances data;
	Apriori model; //연관분석 모델
	
	public void loadArff(String file) throws Exception {
		ds = new DataSource(file);
		data = ds.getDataSet();
		
	}
	
	public void association() throws Exception {
		model = new Apriori();
		model.setLowerBoundMinSupport(0.05);
		model.setMetricType(new SelectedTag(1, model.TAGS_SELECTION));
		model.setMinMetric(1.5);
		model.setNumRules(10);
		model.buildAssociations(data);
		AssociationRules rules = model.getAssociationRules();
		List<AssociationRule> rule_list = rules.getRules();
		
		printRule(rule_list);
		
		//전조현상 A와 병행형상에서 발생한 모든 속성값별 발생 회수 계산하기
		Map<String, Integer> attrNameCounts = countByItemSets(rule_list);
		
		//데이터의 속성명과 속성 index 저장
		List<String> attrNameIndex = indexofInstanceList(data);
		
		//최다 발생하는 아이템의 index를 반환하는 메서드
		int topIndex = fetchTopAttribute(attrNameIndex, attrNameCounts);
		
		//분류 알고리즘으로 최다 발생속성과 연관 속성의 밀접도 확인
		buildOneR(topIndex);
		
	}
	
	private void buildOneR(int topIndex) {
		// TODO Auto-generated method stub
		
	}

	private int fetchTopAttribute(List<String> attrNames, Map<String, Integer> attrNameCounts) {
		String topAttrName = "";
		int topCount = 0;
		int topIndex = 0;
		for(int i=0;i<attrNames.size() - 1;i++) {
			String currAttrName = attrNames.get(i);
			
			if(currAttrName!=null) {
				int cnt = 0;
				cnt = attrNameCounts.get(currAttrName);
				
				if(cnt>topCount) {
					topCount = cnt;
					topAttrName = currAttrName;
					topIndex = i;
				}
			}
		}
		System.out.println("최다 발생 속성 index = " + (topIndex + 1) + ", topAttrName : " + topAttrName);
		return topIndex;
	}

	private List<String> indexofInstanceList(Instances data) {
		List<String> attrNames = new ArrayList<>();
		Instance obj = data.firstInstance();
		
		for(int i=0;i<obj.numAttributes();i++) {
			Attribute attr = obj.attribute(i);
			attrNames.add(attr.name());
		}
		
		return attrNames;
	}

	public void printRule(List<AssociationRule> rule_list) throws Exception {
		for(AssociationRule ar:rule_list) {
//			Collection<Item> col = ar.getPremise();
			System.out.println(ar);
			double metric[] = ar.getMetricValuesForRule();
			System.out.println("신뢰도 : " + metric[0]);
			System.out.println("향상도 : " + metric[1]);
			System.out.println("전조현상 A에 대한 지지도 : " + ar.getPremiseSupport());
			System.out.println("병행현상 B에 대한 지지도 : " + ar.getConsequenceSupport());
			System.out.println("전체 지지도 : " + ar.getTotalSupport());
		}
	}
	
	private Map<String, Integer> countByItemSets(List<AssociationRule> rule_list) {
		Map<String, Integer> map = new HashMap<>();
		for(AssociationRule ar:rule_list) {
			Collection<Item> premise = ar.getPremise();
			map = countByAttribute(premise, map);
			
			Collection<Item> consequence = ar.getConsequence();
			map = countByAttribute(consequence, map);
		}
		
		return map;
	}

	private Map<String, Integer> countByAttribute(Collection<Item> itemSet, Map<String, Integer> map) {
		for(Item item:itemSet) {
			String attrName = item.getAttribute().name();
//			System.out.println(attrName);
			String freq = item.getItemValueAsString();
//			System.out.println(attrName + " : " + freq);
			
			if(map.get(attrName) == null) {
				map.put(attrName, 1);
			} else {
				Integer val = map.get(attrName);
				map.put(attrName, val + 1);
			}
		}
		
		return map;
	}

	public static void main(String[] args) throws Exception {
		Weka06Apiori app = new Weka06Apiori();
		app.loadArff(app.file);
		app.association();
		
	}

}
