import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA1062 {
	private static final String CASE = "Case ";
	private static final String END = "end";
	private static final String COLON = ": ";
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	String containers;
	int tc = 0;
	while(!END.equals(containers = br.readLine())){
		int[] stacks = new int[26];
		int arrayLen = 0;
		for(int i=0, iLen = containers.length(); i<iLen; ++i){
			int currentChar = containers.charAt(i);
			int minDist = Integer.MAX_VALUE;
			int minIdx = -1;
			for(int j=0; j<arrayLen; ++j){
				int dist = stacks[j]-currentChar;
				if(dist>=0 && dist<minDist){
					minDist = dist;
					minIdx = j;
				}
			}
			if(minIdx!=-1){
				stacks[minIdx]= currentChar;
			}else{
				stacks[arrayLen++]= currentChar;
			}
		}
		pw.println(CASE+(++tc)+COLON+arrayLen);
	}

	br.close();
	pw.flush();
	pw.close();
}
}
