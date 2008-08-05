package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.IOException;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class TimesFirstMapper extends MapReduceBase implements
		Mapper<LRSubMatrixKey, LRSubMatrix, TimesKey, LRSubMatrix> {
	String firstName;
	int col1, col2, row1, row2;
	@Override
	public void map(LRSubMatrixKey key, LRSubMatrix value,
			OutputCollector<TimesKey, LRSubMatrix> output, Reporter reporter)
			throws IOException {
		if (key.name.equals(firstName)){
			for (int i=0;i<col2;i++)
				output.collect(new TimesKey(key.row.get(),i,key.col.get()), value);
		}
		else {
			for (int i=0;i<row1;i++) {
				output.collect(new TimesKey(i,key.col.get(),key.row.get()), value);
			}
		}
	}
	@Override
	public void configure(JobConf job) {
		super.configure(job);
		firstName = job.get("firstMatrixName");
		col1 = job.getInt("firstCol", 0);
		col2 = job.getInt("secondCol", 0);
		row1 = job.getInt("firstRow", 0);
		row2 = job.getInt("secondRow", 0);
	}
}
