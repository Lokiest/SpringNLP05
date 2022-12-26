package ex04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

//NaiveBayes�˰����� �̿��ؼ� iris�����͸� �з��ϴ� �н��� �غ���. (���� �з�)
//1. ������ �ε�
//2. �˰��� ���� �����ؼ� �н��� ����
//3. ��� ����
//4. ������ ���� ���Ϸ� ����==> �Ŀ� �ٸ� ���� �ε��ؼ� �ٽ� �н��� ����
public class Weka01NaiveBayes {
	Instances irisData;
	NaiveBayes model;//�˰��� ��
	DataSource ds=null;
	public void loadArff(String path) {
		try {
		ds=new DataSource(path);
		irisData=ds.getDataSet();
		//arff������ ������ �Ӽ��� Ŭ���� �Ӽ�(���� label)�� ��찡 ����.
		if(irisData.classIndex()==-1) {
			irisData.setClassIndex(irisData.numAttributes()-1);
			//������ �Ӽ��� Ŭ���� �Ӽ����� ����
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}//------------------------------
	
	public void generateModel() {
		model=new NaiveBayes();
		try {
			model.buildClassifier(irisData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//------------------------------
	
	public void evaluate(int numfolds) {
		Evaluation eval=null;
		try {
			eval=new Evaluation(irisData);
			eval.crossValidateModel(model, irisData, numfolds, new Random(1));
			String res=eval.toSummaryString();
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//------------------------------------
	//���� �����͸� �־��� �� ���� ����� �˾ƺ���
	public void testPredict(String path) {
		try {
			DataSource ds=new DataSource(path);
		Instances testData=ds.getDataSet();//new Instances(new BufferedReader(new FileReader(path)));
		testData.setClassIndex(testData.numAttributes()-1);//class �ε����� ������ �Ӽ����� ����
		System.out.println("Real Data : "+testData.numInstances());
		for(int i=0;i<testData.numInstances();i++) {
			double pred=model.classifyInstance(testData.instance(i));
			System.out.println("pred: "+pred);//������
			System.out.println("Test Data : "+i+"---------------");
			System.out.println("predicted value: "+testData.classAttribute().value(((int)pred)));
			//�з� ����� ���ڿ��� ��ȯ			
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String file="C:\\Weka-3-9\\data\\iris.arff";
		String fileTest="C:\\Weka-3-9\\data\\iris_test.arff";
		Weka01NaiveBayes app=new Weka01NaiveBayes();
		app.loadArff(file);
		app.generateModel();
		app.evaluate(10);//���� ������ 10��
		app.testPredict(fileTest);
	}
}
