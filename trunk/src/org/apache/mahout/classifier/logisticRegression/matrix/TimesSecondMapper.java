package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.IOException;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class TimesSecondMapper extends MapReduceBase implements
		Mapper<TimesKey, LRSubMatrix, LRSubMatrixKey, LRSubMatrix> {

	String name;
	@Override
	public void map(TimesKey key, LRSubMatrix value,
			OutputCollector<LRSubMatrixKey, LRSubMatrix> output,
			Reporter reporter) throws IOException {
		output.collect(new LRSubMatrixKey(key.rowIndex.get(), 
				key.colIndex.get(), name), value);				
	}
	@Override
	public void configure(JobConf job) {
		super.configure(job);
		name = job.get("matrixName");
	}
}
