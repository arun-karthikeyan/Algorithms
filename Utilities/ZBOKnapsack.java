import java.io.*;
import java.util.*;
/**
* Basic 0/1 Knapsack DP O(nk) pseudo polynomial algorithm
*
**/
public class ZBOKnapsack {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        
        int t = Integer.parseInt(br.readLine());
        while(t-->0){
            String[] info = br.readLine().split(" ");
            int n = Integer.parseInt(info[0]);
            int k = Integer.parseInt(info[1]);
            int[] array = new int[n];
            info = br.readLine().split(" ");
            for(int i=0; i<n; ++i){
                array[i] = Integer.parseInt(info[i]);
            }
            int[][] dp = new int[n][k+1];
            for(int i=0; i<n; ++i){
                int start = array[i];
                if(i>0){
                    for(int j=0; j<start && j<=k; ++j){
                        dp[i][j] = dp[i-1][j];
                    }    
                }
                for(int j=start; j<=k; ++j){
                    dp[i][j] = Math.max(start, dp[i][j-start]+start);
                    if(i>0) dp[i][j] = Math.max(dp[i][j], Math.max(dp[i-1][j], dp[i-1][j-start]+start));
                    if(j>0) dp[i][j] = Math.max(dp[i][j], dp[i][j-1]);
                }
            }
            pw.println(dp[n-1][k]);
        }
        
        br.close();
        pw.flush();
        pw.close();
    }
}
