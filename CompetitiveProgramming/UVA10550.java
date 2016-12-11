

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA10550 {
	private static int[] returnCombinations(String line)
	{
		StringTokenizer st = new StringTokenizer(line);
		int[] combinations = new int[4];
		combinations[0] = Integer.parseInt(st.nextToken());
		combinations[1] = Integer.parseInt(st.nextToken());
		combinations[2] = Integer.parseInt(st.nextToken());
		combinations[3] = Integer.parseInt(st.nextToken());
		
		if(combinations[0]!=0 || combinations[1]!=0 || combinations[2]!=0 || combinations[3]!=0){
			return combinations;
		}
		else
		{
			return null;
		}
	}
	public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int[] c = new int[4];
	while((c=returnCombinations(br.readLine()))!=null)
	{
		int total = 1080;
		int temp1 = c[0]-c[1];
		total = total + (temp1>0?(temp1*9):((temp1+40)*9));
		temp1 = c[2]-c[1];
		total = total + (temp1>0?(temp1*9):((temp1+40)*9));
		temp1 = c[2]-c[3];
		total = total + (temp1>0?(temp1*9):((temp1+40)*9));
		pw.println(total);
	}

	pw.flush();
	pw.close();
	br.close();
}
}
