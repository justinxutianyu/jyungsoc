package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class MinusMapper extends MapReduceBase implements
		Mapper<IntWritable, Text, LRSubMatrixKey, LRSubMatrix> {

	
	@Override
	public void configure(JobConf job) {		
		super.configure(job);
//		job
	}

	@Override
	public void map(IntWritable key, Text value,
			OutputCollector<LRSubMatrixKey, LRSubMatrix> output,
			Reporter reporter) throws IOException {
		
//		LRSubMatrix matrix=LRSubMatrix.decodeSubMatrix(value.toString());
//		output.collect(
//				new LRSubMatrixKey(matrix.getBlockRowIndex(),matrix.getBlockColIndex()), 
//				matrix);
	}
}
