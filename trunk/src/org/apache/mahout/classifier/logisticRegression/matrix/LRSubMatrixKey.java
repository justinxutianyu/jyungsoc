package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

public class LRSubMatrixKey implements WritableComparable{
	public IntWritable col, row;
	public String name;
	
	public LRSubMatrixKey(){
		col = new IntWritable();
		row = new IntWritable();
	}
	public LRSubMatrixKey(int row, int col, String name){
		this.col=new IntWritable(col);
		this.row=new IntWritable(row);
		this.name=new String(name);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		row.readFields(in);
		col.readFields(in);
		name = in.readLine();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		row.write(out);
		col.write(out);
		out.writeBytes(name+"\n");
	}

	@Override
	public int compareTo(Object o) {
		LRSubMatrixKey key=(LRSubMatrixKey)o;
		if (row.compareTo(key.row)==0) return col.compareTo(key.col);
		return row.compareTo(key.row);
	}
	
}
