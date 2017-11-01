import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class JapaneseCrossWordStrikeBack {
	private static final String YES = "YES";
	private static final String NO  = "NO";
	
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		
		String[] info = br.readLine().trim().split(" ");
		
		int n = Integer.parseInt(info[0].trim());
		int x = Integer.parseInt(info[1].trim());
		
		info = br.readLine().trim().split(" ");
		int sum = 0;
		for(int i=0; i<n; ++i) {
			sum+=(Integer.parseInt(info[i].trim()));
		}
		if((sum+n-1)==x) {
			pw.println(YES);
		}else {
			pw.println(NO);
		}
		
		br.close();
		pw.flush();
		pw.close();
	}
}
