import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
/**
 * Longest Increasing Subsequence - Top Down DP
 * @author arun
 *
 */
public class LIS {
	int[] array;
	int[] memo;

	public LIS(int[] array) {
		this.array = array;
		this.memo = new int[array.length];
		Arrays.fill(memo, -1);
	}
	/**
	 * n^2 LIS approach
	 * @return
	 */
	public int findLIS() {
		if(this.array.length==0){
			return 0;
		}
		int max = 1;
		for(int i=0, iLen = this.array.length; i<iLen; ++i){
			int currentMax = 1;
			for(int j=0; j<i; ++j){
				if(array[j]<array[i]){
					currentMax = Math.max(currentMax, memo[j]+1);
				}
			}
			memo[i] = currentMax;
			max = Math.max(max, currentMax);
		}
		return max;
	}
	
	/**
	 * nlogk LIS approach - where k is the length of the LIS
	 * @param args
	 * @throws Exception
	 */
	public int findLIS2(){
		if(this.array.length==0){
			return 0;
		}
		int end = 0;
		memo[0] = this.array[0];
		for(int i=1, iLen = this.array.length; i<iLen; ++i){
			int key = this.array[i];
			int insertPosition = binarySearchInsertPosition(0,end,key);
			memo[insertPosition] = key;
			if(insertPosition>end) end=insertPosition;
		}
		return end+1;
	}
	
	public int binarySearchInsertPosition(int start, int end, int key){
		int low = start, high = end;
		while(low<=high){
			int mid = (low+high)>>1, val = memo[mid];
			if(key<val){
				high = mid-1;
			}else if(key>val){
				low = mid+1;
			}else{
				return mid;
			}
		}
		return low;
	}

	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		int n = Integer.parseInt(br.readLine());
		int[] vals = new int[n];
		for(int i=0; i<n; ++i){
			vals[i] = Integer.parseInt(br.readLine());
		}
		LIS obj = new LIS(vals);
		System.out.println("Longest Increasing sub-sequence : " + obj.findLIS2());
		br.close();
	}
}
