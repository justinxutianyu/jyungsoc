package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class MinusReducer extends MapReduceBase implements 
	Reducer<LRSubMatrixKey, LRSubMatrix, LRSubMatrixKey, LRSubMatrix>{

	@Override
	public void reduce(LRSubMatrixKey key, Iterator<LRSubMatrix> values,
			OutputCollector<LRSubMatrixKey, LRSubMatrix> output,
			Reporter reporter) throws IOException {
		LRSubMatrix matrix;
//		for (LRSubMatrix submatrix){
//			
//		}
		
		
	}

}
