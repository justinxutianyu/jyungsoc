package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.mahout.matrix.AbstractMatrix;
import org.apache.mahout.matrix.AbstractVector;
import org.apache.mahout.matrix.SparseVector;
import org.apache.mahout.matrix.Vector;

public class LRSubVector implements Writable {
	/* 
	 *	in a line: order vector
	 */
	public int order;	
	public Vector data;
	
	public LRSubVector(){		
	}
	public LRSubVector(int cardinality) {
		data = new SparseVector(LRMatrix.vectorCardinality[AbstractMatrix.COL]);
	}
	@Override
	public void readFields(DataInput in) throws IOException {
		order = in.readInt();	
		data = AbstractVector.decodeVector(in.readLine());
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(order);
		out.writeBytes(data.asFormatString()+"\n");
	}
	public void add(int col, double value) {
		data.setQuick(col, value);
	}

}
