package org.apache.mahout.classifier.logisticRegression;

import org.apache.mahout.matrix.AbstractMatrix;
import org.apache.mahout.matrix.DenseMatrix;
import org.apache.mahout.matrix.DenseVector;
import org.apache.mahout.matrix.Matrix;
import org.apache.mahout.matrix.Vector;

public class LRTraining {
	private Matrix w, dw;
	private Matrix data;
	private Matrix result, predic;
	private int features, samples;
	static double learnRate=0.1;
	private double threshold=0.01;
	public LRTraining(Matrix data, Matrix result){
		this.data=data;
		this.result=result;
		features=data.cardinality()[AbstractMatrix.COL];
		samples=data.cardinality()[AbstractMatrix.ROW];
		threshold*=samples;
	}
	
	
	public Matrix train(){
		w=new DenseMatrix(features,1);
		initWeight(w);
		while (finish(dw)){
			prediction();
			Matrix h=inverseHessian(predic);
			dw=h.times(data.transpose().times(result.minus(predic))).times(learnRate);
//			dw=data.transpose().times(result.minus(predic)).times(learnRate);
			w=w.plus(dw);
//			for (int i=0;i<features;i++) System.out.println(w.getQuick(i, 0));				
		}
		return w;
	}
	private void prediction() {
		predic=data.times(w);
		for (int i=0;i<samples;i++) 
			predic.setQuick(i, 0, 1/(1+Math.exp(predic.getQuick(i, 0))));
		// TODO Auto-generated method stub
		
	}
	private int loops=0;
	private boolean finish(Matrix dw) {
		loops++;
		return loops<100;
//		int i;
//		double deta=0;
//		for (i=0;i<features;i++){
//			deta+=dw.get(i, 0);
//			if (deta>threshold) return false;
//		}			
//		return true;
	}
	
	/**
	 * @param w the vector need to be initialized, and the values are in (-0.5,0.5)
	 */
	private void initWeight(Matrix w) {
		for (int i=0;i<features;i++){
			w.setQuick(i, 0, Math.random()-0.5);
		}		
	}	
	private Matrix inverse(Matrix x){
		int dim=features, index;
//		double a[][]=x.toArray();
		Matrix rt=getIdentityMatrix(features);
//		double b[][]=rt.toArray();
		int i, j;
		int order[]=new int[dim];
		for (i=0;i<dim;i++){
			for (index=0;index<dim && Math.abs(x.getQuick(i, index))<1e-6;index++);
			order[index]=i;
			double p=x.get(i, index);
			x.assignRow(i, x.getRow(i).divide(p));
			rt.assignRow(i, rt.getRow(i).divide(p));
			for (j=0;j<dim;j++)
				if (i!=j && Math.abs(x.getQuick(j, index))>1e-6){
					p=x.getQuick(j, index);					
					x.assignRow(j, x.getRow(j).minus(x.getRow(i).times(p)));
					rt.assignRow(j, rt.getRow(j).minus(rt.getRow(i).times(p)));
				}			
		}
		for (i=0;i<dim;i++) {
			x.assignRow(i, rt.getRow(order[i]));
		}
		return x;
	}
	private Matrix getIdentityMatrix(int dim) {
		Matrix m=new DenseMatrix(dim,dim);
		for (int i=0;i<dim;i++) m.setQuick(i, i, 1);
		return m;
	}


	private Matrix inverseHessian(Matrix predic){
		Matrix xt=data.transpose();
		int i;
		for (i=0;i<features;i++) { 
			xt.assignRow(i, xt.getRow(i).times(predic.getColumn(0)));			
		}
		return inverse(xt.times(data));				
	}
}
