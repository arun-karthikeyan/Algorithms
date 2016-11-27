import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11221 {
	private static final String NOMAGIC = "No magic :(";
	private static boolean isPalindrome(String str){
		for(int i=0, strLen = str.length(), iLen = strLen/2; i<iLen; ++i){
			if(str.charAt(i)!=str.charAt(strLen-i-1)){
				return false;
			}
		}
		return true;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int testcases = Integer.parseInt(br.readLine());
	for(int t=1; t<=testcases; ++t){
		String str = br.readLine().replaceAll("[ ,.!?()]", "");
		int strLen = str.length();
		int perfectSqrt = (int)Math.sqrt(strLen);
		pw.println("Case #"+t+":");
		if((perfectSqrt*perfectSqrt)==strLen){
			char[][] magicSquare = new char[perfectSqrt][perfectSqrt];
			for(int i=0, idx=0;i<perfectSqrt;++i){
				for(int j=0;j<perfectSqrt;++j,idx++){
					magicSquare[i][j]=str.charAt(idx);
				}
			}
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<perfectSqrt;++i){
				for(int j=0;j<perfectSqrt;++j){
					sb.append(magicSquare[j][i]);
				}
			}
			if(isPalindrome(str) && isPalindrome(sb.toString())){
				pw.println(perfectSqrt);
			}else{
				pw.println(NOMAGIC);
			}
			
		}else{
			pw.println(NOMAGIC);
		}
		
	}

	br.close();
	pw.flush();
	pw.close();
}
}
