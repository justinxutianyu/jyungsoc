package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.mahout.matrix.DenseVector;

public class VectorLoader extends MapReduceBase implements
		Mapper<LongWritable, Text, LRSubMatrixKey, LRSubVector> {
	public String name;
	public double[] temp = new double[1];
	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<LRSubMatrixKey, LRSubVector> output,
			Reporter reporter) throws IOException {
		String strs[] = value.toString().split(" ");
		LRSubVector vector = new LRSubVector(1);
		vector.order = Integer.valueOf(strs[0]) / LRMatrix.rowBlockVector;
		temp[0] = Double.valueOf(strs[1]);
		vector.data = new DenseVector(temp);
		output.collect(new LRSubMatrixKey(Integer.valueOf(strs[0]) % LRMatrix.rowBlockVector,
				0, name), vector);
	}
	@Override
	public void configure(JobConf job) {	
		super.configure(job);
		name = job.get("matrixName");
	}
}
