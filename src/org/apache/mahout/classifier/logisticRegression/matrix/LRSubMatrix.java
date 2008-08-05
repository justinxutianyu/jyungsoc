package org.apache.mahout.classifier.logisticRegression.matrix;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.mahout.matrix.AbstractMatrix;
import org.apache.mahout.matrix.Matrix;
import org.apache.mahout.matrix.SparseMatrix;
import org.apache.mahout.matrix.SparseRowMatrix;
import org.apache.mahout.matrix.SparseVector;

public class LRSubMatrix implements Writable{
	public Matrix subMatrix;
	public LRSubMatrix(){
	}
	public LRSubMatrix(Matrix matrix){
		subMatrix=matrix.copy();
	}
	public LRSubMatrix(int[] cardinality) {
		subMatrix = new SparseRowMatrix(cardinality);
	}
	public WritableComparable asWritableComparable(){
		return new Text(subMatrix.asFormatString());
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		decodeMatrix(in.readLine());
	}
	private void decodeMatrix(String line) {
		String strs[] = line.split(";");
		SparseVector vector=new SparseVector();
		for (int i=1;i+1<strs.length;i++) {
			vector.decodeFormat(strs[i]);
			subMatrix.assignRow(i-1, vector);
		}
	}
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeBytes(asFormattingString());
	}
	
	//[;sparseVector;...;...;]
	public String asFormattingString() {
		StringBuilder out=new StringBuilder();
		out.append("[;");
		for (int i=0;i<subMatrix.cardinality()[AbstractMatrix.ROW];i++)
			out.append(subMatrix.getRow(i).asFormatString()).append(";");
		out.append("]\n");
		return out.toString();
	}
	public void insert(LRSubVector vector) {
		System.out.println(vector.order+" "+vector.data.cardinality()+" "+subMatrix.cardinality()[AbstractMatrix.COL]);
		subMatrix.assignRow(vector.order, vector.data);		
	}

}
