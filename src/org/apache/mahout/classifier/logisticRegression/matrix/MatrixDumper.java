package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.mahout.matrix.Vector;

public class MatrixDumper extends MapReduceBase implements
		Reducer<LRSubMatrixKey, LRSubVector, LRSubMatrixKey, LRSubMatrix> {

	private boolean loadMatrix;
	@Override
	public void reduce(LRSubMatrixKey key, Iterator<LRSubVector> values,
			OutputCollector<LRSubMatrixKey, LRSubMatrix> output,
			Reporter reporter) throws IOException {
		
		LRSubMatrix subMatrix;
		if (loadMatrix)  
			subMatrix = new LRSubMatrix(LRMatrix.matrixCardinality);
		else subMatrix = new LRSubMatrix(LRMatrix.vectorCardinality);
		
		while (values.hasNext()){
			LRSubVector v = values.next();			
//			System.out.println(v.order+" : "+v.data.asFormatString());
			subMatrix.insert(v);
		}		
//		System.out.println(subMatrix.asFormattingString());		
		output.collect(key, subMatrix);
	}
	@Override
	public void configure(JobConf job) {	
		super.configure(job);
		boolean loadMatrix = job.getBoolean("loadMatrix",false);
	}
}
