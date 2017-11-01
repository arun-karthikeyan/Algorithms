import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class BookReading {
	private static final int MAXSECONDS = 86400;
	private static final int NIDX = 0;
	private static final int TIDX = 1;
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		
		String[] info = br.readLine().trim().split(" ");
		int n = Integer.parseInt(info[NIDX].trim());
		int t = Integer.parseInt(info[TIDX].trim());
		
		info = br.readLine().trim().split(" ");
		int reqDays = 0;
		
		for(; reqDays<n && t>0; ++reqDays) {
			t-=(MAXSECONDS-Integer.parseInt(info[reqDays].trim()));
		}
		
		pw.println(reqDays);
		
		br.close();
		pw.flush();
		pw.close();
	}
}
