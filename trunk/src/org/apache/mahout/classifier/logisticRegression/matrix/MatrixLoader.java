package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.mahout.matrix.AbstractMatrix;

public class MatrixLoader extends MapReduceBase implements
		Mapper<LongWritable, Text, LRSubMatrixKey, LRSubVector> {	
	private String matrixName;
	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<LRSubMatrixKey, LRSubVector> output,
			Reporter reporter) throws IOException {
		System.out.println("~~~~~~~~~~~~");
		System.out.println(value.toString());
		String strs[]=value.toString().split(" ");
		int len = strs.length;
		if (len ==0) return;
		
		LRSubVector[] vectors = new LRSubVector[LRMatrix.colBlock];
		for (int i = 0; i < LRMatrix.colBlock; i++)
			vectors[i] = new LRSubVector(
					LRMatrix.matrixCardinality[AbstractMatrix.COL]);
		for (int i = 1; i + 1 < len; i += 2) {
			int col = Integer.valueOf(strs[i]);
			vectors[col % LRMatrix.colBlock].add(col / LRMatrix.colBlock,
					Double.valueOf(strs[i + 1]));
		}
		// add the constant feature '1'
		vectors[0].add(0, 1);
		
		int rowIndex = Integer.valueOf(strs[0]) % LRMatrix.rowBlock;
		for (int i=0;i<LRMatrix.colBlock;i++){
			vectors[i].order = Integer.valueOf(strs[0]) / LRMatrix.rowBlock;			
			output.collect(new LRSubMatrixKey(rowIndex,i,matrixName), vectors[i]);
		}
	}
	@Override
	public void configure(JobConf job) {
		super.configure(job);
		matrixName = job.get("matrixName");
	}
}
