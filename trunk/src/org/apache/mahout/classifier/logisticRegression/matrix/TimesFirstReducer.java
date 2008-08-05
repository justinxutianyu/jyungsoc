package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.mahout.matrix.AbstractMatrix;

import sun.security.x509.AVA;

public class TimesFirstReducer extends MapReduceBase implements
		Reducer<TimesKey, LRSubMatrix, TimesKey, LRSubMatrix> {

	private int col, row;
	private int cardi[];
	@Override
	public void reduce(TimesKey key, Iterator<LRSubMatrix> values,
			OutputCollector<TimesKey, LRSubMatrix> output, Reporter reporter)
			throws IOException {
		LRSubMatrix l = values.next();
		LRSubMatrix r = values.next();
		LRSubMatrix result = new LRSubMatrix(cardi);
		result.subMatrix = l.subMatrix.times(r.subMatrix);
		output.collect(key, result);		
	}
	@Override
	public void configure(JobConf job) {
		super.configure(job);		
		col = job.getInt("colNumber", 0);
		row = job.getInt("rowNumber", 0);
		cardi = new int[2];
		cardi[AbstractMatrix.COL] = col;
		cardi[AbstractMatrix.ROW] = row;
		
	}
}
