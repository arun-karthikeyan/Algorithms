import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA10945 {
	private static final String PALIN = "You won't be eaten!";
	private static final String NOTPALIN = "Uh oh..";
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
	String line  = br.readLine().toLowerCase();
	while(!line.equals("done")) {
		if(isPalindrome(line.replaceAll("[.,?! ]", ""))){
			pw.println(PALIN);
		}else{
			pw.println(NOTPALIN);
		}
		line = br.readLine().toLowerCase();
	}

	br.close();
	pw.flush();
	pw.close();
}
}
