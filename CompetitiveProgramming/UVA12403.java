

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class UVA12403 {
@SuppressWarnings("unused")
private static final String DONATE = "donate", REPORT = "report";
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int queries = Integer.parseInt(br.readLine());
	int total = 0;
	while(queries-->0){
		StringTokenizer st = new StringTokenizer(br.readLine());
		String query = st.nextToken();
		if(DONATE.equals(query))
		{
			total+=Integer.parseInt(st.nextToken());
		}
		else
		{
			pw.println(total);
		}
	}

	pw.flush();
	pw.close();
	br.close();
}
}
