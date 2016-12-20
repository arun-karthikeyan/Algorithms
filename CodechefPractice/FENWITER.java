import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class FENWITER {
	private static final char ZERO='0',ONE='1';

	private static boolean contains(String val, char c){
	for(int i=0, iLen=val.length(); i<iLen; ++i){
		if(val.charAt(i)==c){
			return true;
		}
	}
	return false;
	}
	
	private static long countOnes(String val){
		long count = 0;
		for(int i=0, iLen=val.length(); i<iLen; ++i){
			if(val.charAt(i)==ONE){
				count++;
			}
		}
		return count;
	}
	
	private static long countLastConsecutiveOnes(String val){
		long count = 0;
		for(int i=val.length()-1; i>=0; --i){
			if(val.charAt(i)==ONE){
				count++;
			}else{
				break;
			}
		}
		return count;
	}

	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		
		int testcases = Integer.parseInt(br.readLine());
		
		while(testcases-->0){
			StringTokenizer st = new StringTokenizer(br.readLine());
			String l1 = st.nextToken(), l2 = st.nextToken(), l3 = st.nextToken();
			int n = Integer.parseInt(st.nextToken());
			long count = countOnes(l1) + (countOnes(l2)*n) + countOnes(l3);
			long result = 0;
			if(contains(l3,ZERO)){
				long toSubtract = countLastConsecutiveOnes(l3);
				result = count - toSubtract + 1;
			}else{
				if(contains(l2,ZERO)){
					long toSubtract = countLastConsecutiveOnes(l2) + l3.length();
					result = count - toSubtract + 1;
				}else{
					if(contains(l1,ZERO)){
						long toSubtract = countLastConsecutiveOnes(l1) + (l2.length()*n) + l3.length();
						result = count - toSubtract + 1;
					}else{
						result = 1;
					}
				}
			}
			
			pw.println(result);
		}
		
		pw.flush();
		pw.close();
		br.close();
		
	}
}
