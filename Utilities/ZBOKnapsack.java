import java.io.*;
import java.util.*;
/**
* Basic 0/1 Knapsack DP O(nk) pseudo polynomial algorithm
* With multiple selection of the same element with reduced space trick, uses only k space
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
            int[] dp = new int[k+1];
            for(int i=0; i<n; ++i){
                int start = array[i];
                for(int j=start; j<=k; ++j){
                    dp[j] = Math.max(dp[j], dp[j-start]+start);
                    if(j>0) dp[j] = Math.max(dp[j], dp[j-1]);
                }
            }
            pw.println(dp[k]);
        }        
        br.close();
        pw.flush();
        pw.close();
    }
}
