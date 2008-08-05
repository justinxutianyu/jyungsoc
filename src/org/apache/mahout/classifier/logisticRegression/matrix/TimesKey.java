package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.Time;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

public class TimesKey implements WritableComparable {
	public IntWritable colIndex, rowIndex, order;
	
	public TimesKey(int newRow, int newCol, int order){
		this.colIndex=new IntWritable(newCol);
		this.rowIndex=new IntWritable(newRow);
		this.order=new IntWritable(order);
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		rowIndex.readFields(in);
		colIndex.readFields(in);
		order.readFields(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		rowIndex.write(out);
		colIndex.write(out);
		order.write(out);
	}

	@Override
	public int compareTo(Object o) {
		TimesKey other=(TimesKey)o;
		if (rowIndex.compareTo(other.rowIndex)==0)
			if (colIndex.compareTo(other.colIndex)==0)
				return order.compareTo(other.order);
			else return colIndex.compareTo(other.colIndex);
		else return rowIndex.compareTo(other.rowIndex);		
	}

}
