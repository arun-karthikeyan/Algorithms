

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA10300 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = Integer.parseInt(br.readLine().trim());
	while(testcases-->0){
		int farmers = Integer.parseInt(br.readLine().trim());
		long total = 0l;
		while(farmers-->0){
			StringTokenizer st = new StringTokenizer(br.readLine());
			long size = Long.parseLong(st.nextToken()); st.nextToken();
			long eco = Long.parseLong(st.nextToken());
			total = total + (size*eco);
		}
		pw.println(total);
	}

	pw.flush();
	pw.close();
	br.close();
}
}
