

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA12279 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int val, c = 1;
	while((val=Integer.parseInt(br.readLine().trim()))!=0)
	{
		StringTokenizer st = new StringTokenizer(br.readLine());
		int balance = 0;
		while(val-->0)
		{
			if(Integer.parseInt(st.nextToken())!=0){
				balance++;
			}else
			{
				balance--;
			}
				
		}
		pw.println("Case "+c+++": "+balance);
	}

	pw.flush();
	pw.close();
	br.close();
}
}
