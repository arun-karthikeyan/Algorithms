import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class UVA11340 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int testcases = Integer.parseInt(br.readLine());
	while (testcases-- > 0) {
		int charDic = Integer.parseInt(br.readLine());
		HashMap<Character, Integer> cost = new HashMap<Character, Integer>();
		StringTokenizer st;
		for(int i=0; i<charDic; ++i){
			st = new StringTokenizer(br.readLine());
			cost.put(st.nextToken().charAt(0), Integer.parseInt(st.nextToken()));
		}
		int totalLines = Integer.parseInt(br.readLine());
		long result = 0l;
		for(int i=0; i<totalLines; ++i){
			String currentLine = br.readLine();
			for(int j=0, jLen = currentLine.length(); j<jLen; ++j){
				if(cost.containsKey(currentLine.charAt(j))){
				result += cost.get(currentLine.charAt(j));
				}
			}
		}
		pw.printf("%d.%02d$\n",(result/100),(result%100));

	}

	br.close();
	pw.flush();
	pw.close();
}
}
