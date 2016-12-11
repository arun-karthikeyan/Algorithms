

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11547 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine());
	while(testcases-->0)
	{
		int val = Integer.parseInt(br.readLine());
		int result = (((val*63)+7492)*5)-498;
		result = result<0?(result*-1):result;
		result/=10;
		pw.println(result%10);
	}

	pw.flush();
	pw.close();
	br.close();
}
}
