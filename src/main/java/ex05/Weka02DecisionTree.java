package ex05;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import javax.swing.JFrame;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

public class Weka02DecisionTree {

	Instances data, train, test;
	Classifier model;
	String path = "C:\\Weka-3-9\\data\\UniversalBank_preprocess.arff";
	
	public Weka02DecisionTree() throws Exception {
		data = new Instances(new BufferedReader(new FileReader(path)));
		
		data.randomize(new Random(1));
		
		train = data.trainCV(10, 0, new Random(1));
		test = data.testCV(10, 0);
		
		train.setClassIndex(train.numAttributes() - 1);
		test.setClassIndex(test.numAttributes() - 1);
		
	}
	
	public void generateModel_Evaluatue() throws Exception {
		
		model = new J48();
		((J48)model).setMinNumObj(10);
		((J48)model).setUnpruned(false);
		
		Evaluation eval = new Evaluation(train);
		eval.crossValidateModel(model, train, 10, new Random(1));
		model.buildClassifier(train);
		
		eval.evaluateModel(model, test);
		int nums = (int)eval.numInstances();
		int accuracy = (int)eval.correct();
		System.out.println("Total Data : " + nums + " //5000");
		System.out.println("정분류 건수 : " + accuracy);
		int percent = accuracy * 100 / nums;
		System.out.println("정분류율 : " + percent + "%");
		
		System.out.println(eval.toSummaryString());
		
		this.treeVeiwInstances(data, (J48)model, eval);
	}
	
	public void testPredict(String path) {
		try {
			Instances testing_data = new Instances(
					new BufferedReader(new FileReader(path)));
			testing_data.setClassIndex(testing_data.numAttributes()-1);
			System.out.println("실제 데이터 수: "+testing_data.numInstances());
			for(int i=0;i<testing_data.numInstances();i++) {
				double pred=model.classifyInstance(testing_data.instance(i));
				System.out.println("\npred: "+pred);
				System.out.println("Test Data: "+i+"---");
				System.out.println((int) testing_data.instance(i).classValue()+"<<<");
				System.out.print("given value: " + testing_data.classAttribute().value(
                           (int) testing_data.instance(i).classValue()));
				System.out.println(". predicted value: "+testing_data.classAttribute().value((int)pred));//분류 결과
				double[] prediction = model.distributionForInstance(testing_data.get(i));
				double prob_class0 = prediction[0];
	            double prob_class1 = prediction[1];

	            System.out.println("---확률 분포-------------------");
	            System.out.println(prob_class0);
	            System.out.println(prob_class1);

	            System.out.println("=================================");
			}//for-------
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void treeVeiwInstances(Instances data, J48 tree, Evaluation eval) throws Exception {

		 String graphName = "";
		 graphName += " 정분류율 = " + String.format("%.2f",eval.pctCorrect()) + " %";
	     TreeVisualizer panel = new TreeVisualizer(null,tree.graph(),new PlaceNode2());
	     
	     JFrame frame = new JFrame(graphName);
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.getContentPane().setLayout(new BorderLayout());
	     frame.getContentPane().add(panel);
	     frame.setSize(new Dimension(800,500));
	     frame.setLocationRelativeTo(null);
	     frame.setVisible(true);
	     panel.fitToScreen();
	     System.out.println("See the " + graphName + " plot");
	 }

	
	public static void main(String[] args) throws Exception {
		
		Weka02DecisionTree app = new Weka02DecisionTree();
		app.generateModel_Evaluatue();
		app.testPredict("C:\\Weka-3-9\\data\\UniversalBank_realData.arff");
		
		
	}
}
