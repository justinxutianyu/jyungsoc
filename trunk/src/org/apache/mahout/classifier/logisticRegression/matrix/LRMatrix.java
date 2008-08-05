package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.pipes.Submitter;
import org.apache.mahout.classifier.logisticRegression.test.output.OutputTest;

public class LRMatrix {	
	private Path location;
	public static Configuration fsconf = new Configuration();
	public static int matrixCardinality[] = {2, 2};
	public static int vectorCardinality[] = {2, 1};
	public static int colBlock = 10, rowBlock = 10;
	public static int colBlockVector = 1, rowBlockVector = 10;
	private String name;
	
	public LRMatrix(String name){
		this.name=new String(name);
	}
	public void loadAsVector(Path inputFile) throws IOException {
		JobConf conf = new JobConf(LRMatrix.class);
		conf.setJobName("load as vector : "+inputFile.toString());
		
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(SequenceFileOutputFormat.class);
		conf.setMapOutputKeyClass(LRSubMatrixKey.class);
		conf.setMapOutputValueClass(LRSubVector.class);
		conf.setOutputKeyClass(LRSubMatrixKey.class);
		conf.setOutputValueClass(LRSubMatrix.class);

		Path outputPath = new Path("LRMatrix/"+name);
		FileSystem.get(new Configuration()).delete(outputPath, true);
		FileInputFormat.setInputPaths(conf, inputFile);
		FileOutputFormat.setOutputPath(conf, new Path("LRMatrix/"+name));
		
		conf.set("matrixName", name);
		conf.setBoolean("loadMatrix",false);
		
		conf.setMapperClass(VectorLoader.class);
		conf.setReducerClass(MatrixDumper.class);
		
		JobClient.runJob(conf);
	}
	public void loadAsMatrix(Path inputFile) throws IOException{
		JobConf conf = new JobConf(LRMatrix.class);
		conf.setJobName("load as matrix : "+inputFile.toString());
				
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(SequenceFileOutputFormat.class);
		conf.setMapOutputKeyClass(LRSubMatrixKey.class);
		conf.setMapOutputValueClass(LRSubVector.class);		
		conf.setOutputKeyClass(LRSubMatrixKey.class);
		conf.setOutputValueClass(LRSubMatrix.class);
		
		Path outputPath = new Path("LRMatrix/"+name);
		FileSystem.get(fsconf).delete(outputPath, true);
		FileInputFormat.setInputPaths(conf, inputFile);
		FileOutputFormat.setOutputPath(conf, outputPath);
		
		conf.set("matrixName", name);
		conf.setBoolean("loadMatrix",true);
		
		conf.setMapperClass(MatrixLoader.class);
		conf.setReducerClass(MatrixDumper.class);
		
		JobClient.runJob(conf);
	}

	public LRMatrix times(LRMatrix other) throws IOException{
		JobConf conf=new JobConf(OutputTest.class);	
		conf.setJobName("matrix("+name+") * matrix("+ other.name+")");
		
		conf.setInputFormat(KeyValueTextInputFormat.class);
		conf.setOutputFormat(SequenceFileOutputFormat.class);
		conf.setOutputKeyClass(TimesKey.class);
		conf.setOutputValueClass(LRSubMatrix.class);
		
		LRMatrix rt = new LRMatrix(name+other.name);
		rt.location = new Path("LRMatrix/"+rt.name);
		FileSystem.get(new Configuration()).delete(rt.location,true);
		return null;
	}
	public LRMatrix minus(LRMatrix other){
		JobConf conf=new JobConf(LRMatrix.class);
		conf.setJobName("matrix("+name+")-matrix("+other.name+")");
		
		conf.setInputFormat(TextInputFormat.class);
		
		return null;
	}

}
