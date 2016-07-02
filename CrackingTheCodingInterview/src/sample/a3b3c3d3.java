package sample;
import java.util.ArrayList;
import java.util.Collections;
/*
 * numbers such that a^3 + b^3 = c^3 + d^3 and 1<=a<=b<=c<=d<=1000
 */
public class a3b3c3d3 {
	static class numSum{
		int numA;
		int numB;
		int sum;
		public numSum(int numA, int numB){
			this.numA = numA;
			this.numB = numB;
			this.sum = numA*numA*numA + numB*numB*numB;
		}
	}
	public static void main(String[] args){
		ArrayList<numSum> allVals = new ArrayList<numSum>((int)1e6);
		
		for(int i=1; i<=1000; ++i){
			for(int j=i+1; j<=1000; ++j) {
				allVals.add(new numSum(i,j));
			}
		}
		
		Collections.sort(allVals, new java.util.Comparator<numSum>(){

			@Override
			public int compare(numSum arg0, numSum arg1) {
				// TODO Auto-generated method stub
				return arg0.sum-arg1.sum;
			}
			
		});
		
		for(int i=1, j=allVals.size();i<j;++i){
			if(allVals.get(i).sum==allVals.get(i-1).sum) {
				System.out.println(allVals.get(i).sum+" | "+"a:"+allVals.get(i).numA+" b:"+allVals.get(i).numB+" c:"+allVals.get(i-1).numA+" d:"+allVals.get(i-1).numB);
			}
		}
	}
}
