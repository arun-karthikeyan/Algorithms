

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11332 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int n = Integer.parseInt(br.readLine().trim());
	while(n!=0)
	{
		int currentSum = n;
		while(currentSum>9)
		{
			n = currentSum;
			currentSum = 0;
			while(n>0)
			{
				currentSum = currentSum + (n%10);
				n/=10;
			}	
		}
		pw.println(currentSum);
		n = Integer.parseInt(br.readLine().trim());
	}

	pw.flush();
	pw.close();
	br.close();
}
}
