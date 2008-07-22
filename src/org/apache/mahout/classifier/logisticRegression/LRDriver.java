package org.apache.mahout.classifier.logisticRegression;

import org.apache.mahout.matrix.DenseMatrix;
import org.apache.mahout.matrix.Matrix;

public class LRDriver {
	public static void main(String[] args) {
//		double[][] a={{1,1,1,1},{-1,-0.5,-2,-1},{-2,-0.3,-0.2,-1.2},{2,0.3,2,1.2}};
		double[][] a={{1,1},{-1,-0.5},{-2,-0.3},{2,0.3}};
		double[][] r={{1},{0},{0},{1}};
		Matrix data=new DenseMatrix(a);
		Matrix result=new DenseMatrix(r);
		Matrix w;
		LRTraining lrTrain=new LRTraining(data,result);			
		LRTest lrTest=new LRTest();
		Matrix predic=lrTest.test(data, lrTrain.train());
		for (int i=0;i<4;i++)
			System.out.println(predic.getQuick(i, 0));
	}
}
