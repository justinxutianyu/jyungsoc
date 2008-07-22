package org.apache.mahout.classifier.logisticRegression;

import org.apache.mahout.matrix.AbstractMatrix;
import org.apache.mahout.matrix.Matrix;

public class LRTest {
	
	void LRTest(){
		
	}
	Matrix test(Matrix data, Matrix w){
		Matrix predic=data.times(w);
		for (int i=predic.cardinality()[AbstractMatrix.ROW]-1;i>=0;i--) {
			predic.setQuick(i, 0, 1/(1+Math.exp(predic.getQuick(i, 0))));
		}
		return predic;
	}
}
