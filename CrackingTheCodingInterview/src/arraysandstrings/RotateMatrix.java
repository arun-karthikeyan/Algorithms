package arraysandstrings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class RotateMatrix {
	//own method
	static void rotateMatrix(int[][] a){
		for(int i=0, iLen=a.length; i<iLen; ++i){
			for(int j=0; j<i; ++j){
				switchElements(a,i,j,j,i);
			}
		}
		for(int j=0, jLen=a.length/2, colLen=a.length; j<jLen; ++j){
			for(int i=0, iLen=a.length; i<iLen; ++i){
				switchElements(a,i,j,i,colLen-j-1);
			}
		}
	}
	static void switchElements(int[][] a, int i1, int j1, int i2, int j2){
		int temp = a[i1][j1];
		a[i1][j1] = a[i2][j2];
		a[i2][j2] = temp;
	}

	//book method
	static void rotateMatrix2(int[][] a){
		for(int i=0, iLen=a.length/2; i<iLen; ++i){
			for(int j=i, jLen=a.length-j-1; j<jLen; ++j){
				int temp = a[j][a.length-i-1];//right saved to temp
				a[j][a.length-i-1] = a[i][j];//top sent to right
				a[i][j] = a[a.length-i-1][a.length-j-1];//bottom saved to top
				a[a.length-i-1][a.length-j-1] = temp;//right sent to bottom
				temp = a[a.length-j-1][i];//left saved to temp
				a[a.length-j-1][i] = a[i][j];//bottom sent to left
				a[i][j] = temp;//left sent to top
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[][] a = new int[n][n];
		int[][] b = new int[n][n];
		
		for(int i=0;i<n;++i){
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0;j<n;++j){
				a[i][j] = Integer.parseInt(st.nextToken());
				b[i][j] = a[i][j];
			}
		}
		rotateMatrix(a);
		System.out.println(Arrays.deepToString(a));
		rotateMatrix2(b);
		System.out.println(Arrays.deepToString(b));
	}
}
