import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class UGLYF {
	private static final int POS = 0;
	private static final int VAL = 1;
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int testcases = Integer.parseInt(br.readLine());
	while (testcases-- > 0) {
		String a = br.readLine();
		int aLen = a.length(), aMid = (aLen-1)/2;
		String b = br.readLine();
		int bLen = b.length(), bMid = (bLen-1)/2;
		int[][] aMinPosVal = new int[2][26], bMinPosVal = new int[2][26];
		int min = Integer.MAX_VALUE;
		Arrays.fill(aMinPosVal[POS], -1);
		Arrays.fill(bMinPosVal[POS], -1);
		Arrays.fill(aMinPosVal[VAL], a.length());
		Arrays.fill(bMinPosVal[VAL], b.length());
		for(int i=0; i<aLen; ++i){
			int ch = a.charAt(i)-'A', abs = Math.abs(i-aMid);
			if(abs<aMinPosVal[VAL][ch]){
				aMinPosVal[POS][ch] = i;
				aMinPosVal[VAL][ch] = abs;
			}
		}
		for(int i=0; i<bLen; ++i){
			int ch = b.charAt(i)-'A', abs = Math.abs(i-bMid);
			if(abs<bMinPosVal[VAL][ch]){
				bMinPosVal[POS][ch] = i;
				bMinPosVal[VAL][ch] = abs;
			}
		}
		for(int i=0; i<26; ++i){
			if(aMinPosVal[POS][i]!=-1 && bMinPosVal[POS][i]!=-1){
				int l1 = bLen-bMinPosVal[POS][i]-1, l2 = aLen-aMinPosVal[POS][i]-1;
				int l3 = bMinPosVal[POS][i], l4 = aMinPosVal[POS][i];
				pw.println(l1+" "+l2+" "+l3+" "+l4);
				min = Math.min(min, Math.abs(l1-l2)+Math.abs(l2-l3)+Math.abs(l3-l4)+Math.abs(l4-l1));
			}
		}
		pw.println(min);
	}
	br.close();
	pw.flush();
	pw.close();
}
}
