import java.util.Arrays;

public class HelperMethod {
	
	public static void main(String[] args) throws Exception {
		int n = 10, range = 100;
		int[] randomVals = new int[n];
		for(int i=0; i<n; ++i){
			randomVals[i] = (int)(Math.random()*range);
		}
		int[] cumulativeMax = new int[n];
		cumulativeMax[0] = randomVals[0];
		for(int i=1; i<n; ++i){
			cumulativeMax[i] = Math.max(cumulativeMax[i-1],randomVals[i]);
		}
		System.out.println(Arrays.toString(randomVals));
		System.out.println(Arrays.toString(cumulativeMax));
	}
}