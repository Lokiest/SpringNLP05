package ex06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;

public class Weka04SupportVectorMachine {

	String file = "C:\\Weka-3-9\\data\\titanic\\phpMYEkMl.arff";
	String fileTest = "C:\\Weka-3-9\\data\\titanic\\phpMYEkMl_test.arff";
	Instances data, train, test;
	Classifier model;
	
	public void loadArff(String file) throws Exception {
		try {
			data = new Instances(new BufferedReader(new FileReader(file)));
			data.randomize(new Random(1));
			
			Standardize stan = new Standardize();
			stan.setInputFormat(data);
			Instances newData = Filter.useFilter(data, stan);
			
			train = newData.trainCV(10, 0, new Random(1));
			test = newData.testCV(10, 0);
			
			train.setClassIndex(1);
			test.setClassIndex(1);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void generateModel_Evaluate() throws Exception {
		Evaluation eval = new Evaluation(train);
		model = new SMO();
		eval.crossValidateModel(model, train, 10, new Random(1));
		model.buildClassifier(train);
		eval.evaluateModel(model, test);
		System.out.println(eval.toSummaryString());
		
		System.out.println("--------------------------");
		System.out.println("total data : " + (int)eval.numInstances());
		System.out.println("정 분류 건 수 : " + (int)eval.correct());
		double percent = eval.correct() / eval.numInstances() * 100;
		System.out.printf("정 분류율 : %.2f", percent);
		
	}
	
	public void predict(String file) throws Exception {
		DataSource ds = new DataSource(file);
		Instances testData = ds.getDataSet();
		testData.setClassIndex(1);
		
		Standardize stan = new Standardize();
		stan.setInputFormat(testData);
		Instances newData = Filter.useFilter(testData, stan);
		
		for(Instance obj:newData) {
			System.out.println("------------------");
			System.out.print(obj);
			System.out.print(" : ");
			System.out.println(model.classifyInstance(obj));
		}
	}
	
	public void predictOne(String pclass, double age, String sex) throws Exception {
		Attribute attrPclass = new Attribute("pclass", Arrays.asList("1", "2", "3"));
		Attribute attrSurvived = new Attribute("survived", Arrays.asList("0", "1"), 1);
		Attribute attrSex = new Attribute("sex", Arrays.asList("male", "female"), 2);
		Attribute attAge = new Attribute("age", 3);
		
		ArrayList<Attribute> attrs = new ArrayList<>();
		attrs.add(attrPclass);
		attrs.add(attrSurvived);
		attrs.add(attrSex);
		attrs.add(attAge);
		
		Instances instances = new Instances("SMO", attrs, 0);
		instances.setClassIndex(1);
		
		Instance obj = new DenseInstance(4);
		obj.setValue(attrPclass, pclass);
		obj.setValue(attrSex, sex);
		obj.setValue(attAge, age);
		obj.setDataset(instances);
		System.out.println("----------predictOne--------------");
		System.out.print(obj);
		System.out.print(" : ");
		System.out.println(model.classifyInstance(obj));
		
	}
	
	public static void main(String[] args) throws Exception {
		
		Weka04SupportVectorMachine app = new Weka04SupportVectorMachine();
		app.loadArff(app.file);
		app.generateModel_Evaluate();
		app.predict(app.fileTest);
		app.predictOne("1", 0.35, "female");
		app.predictOne("2", -0.09, "male");
		app.predictOne("3", 0.29, "female");
	}

}
