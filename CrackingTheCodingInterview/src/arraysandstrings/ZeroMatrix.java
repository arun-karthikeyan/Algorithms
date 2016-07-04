package arraysandstrings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ZeroMatrix {
	static void makeRowZero(int[][] a, int row){
		Arrays.fill(a[row], 0);
	}
	static void makeColZero(int[][] a, int col){
		for(int i=0, iLen=a.length; i<iLen; ++i){
			a[i][col] = 0;
		}
	}
	
	static void zeroMatrix(int[][] a){
		boolean[] colZero = new boolean[a[0].length];
		boolean[] rowZero = new boolean[a.length];
		for(int i=0; i<a.length; ++i){
			for(int j=0; j<a[i].length; ++j){
				if(a[i][j]==0){
					colZero[j] = true;
					rowZero[i] = true;
				}
			}
		}
		
		for(int i=0;i<rowZero.length;++i){
			if(rowZero[i]){
				makeRowZero(a,i);
			}
		}
		for(int i=0; i<colZero.length; ++i){
			if(colZero[i]){
				makeColZero(a,i);
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int m = Integer.parseInt(st.nextToken()), n = Integer.parseInt(st.nextToken());
		int[][] a = new int[m][n];
		for(int i=0; i<m; ++i){
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; ++j){
				a[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		zeroMatrix(a);
		System.out.println(Arrays.deepToString(a));
	}
}
