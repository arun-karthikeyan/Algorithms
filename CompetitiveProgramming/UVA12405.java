import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12405 {
	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine()), tc = 1;
		while (testcases-- > 0) {
			int n = Integer.parseInt(br.readLine());
			String field = br.readLine();
			int scareCrows = 0;
			for (int i = 0; i < n;) {
				while (i < n && field.charAt(i) == '#') {
					i++;
				}
				if (i != n) {
					scareCrows++;
					i += 3;
				}
			}
			pw.println("Case " + (tc++) + ": " + scareCrows);

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
