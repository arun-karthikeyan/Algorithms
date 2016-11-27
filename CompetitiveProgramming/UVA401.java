import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA401 {
	private static final HashMap<Character, Character> mirrorCharacters;
	static{
		mirrorCharacters = new HashMap<Character, Character>();
		mirrorCharacters.put('A', 'A');
		mirrorCharacters.put('E', '3');
		mirrorCharacters.put('H', 'H');
		mirrorCharacters.put('I', 'I');
		mirrorCharacters.put('J', 'L');
		mirrorCharacters.put('L', 'J');
		mirrorCharacters.put('M', 'M');
		mirrorCharacters.put('O', 'O');
		mirrorCharacters.put('S', '2');
		mirrorCharacters.put('T', 'T');
		mirrorCharacters.put('U', 'U');
		mirrorCharacters.put('V', 'V');
		mirrorCharacters.put('W', 'W');
		mirrorCharacters.put('X', 'X');
		mirrorCharacters.put('Y', 'Y');
		mirrorCharacters.put('Z', '5');
		mirrorCharacters.put('1', '1');
		mirrorCharacters.put('2', 'S');
		mirrorCharacters.put('3', 'E');
		mirrorCharacters.put('5', 'Z');
		mirrorCharacters.put('8', '8');
		
	}
	private static final String NOTPALIN = " -- is not a palindrome.";
	private static final String ONLYPALIN = " -- is a regular palindrome.";
	private static final String ONLYMIRROR = " -- is a mirrored string.";
	private static final String BOTH = " -- is a mirrored palindrome.";
	
	private static boolean isMirror(String str){
		
		for(int i=0, strLen = str.length(), iLen=strLen/2; i<=iLen; ++i){
			if(!mirrorCharacters.containsKey(str.charAt(strLen-i-1)) || str.charAt(i)!=mirrorCharacters.get(str.charAt(strLen-i-1))){
				return false;
			}
		}
		return true;
	}
	
	private static boolean isPalindrome(String str){
		for(int i=0, strLen = str.length(), iLen = strLen/2; i<iLen; ++i){
			if(str.charAt(i)!=str.charAt(strLen-i-1)){
				return false;
			}
		}
		return true;
	}
	
	private static String getResultString(String str){
		boolean palin = isPalindrome(str);
		boolean mirror = isMirror(str);
		if(palin){
			if(mirror){
				return str+BOTH;
			}else{
				return str+ONLYPALIN;
			}
		}else{
			if(mirror){
				return str+ONLYMIRROR;
			}else{
				return str+NOTPALIN;
			}
		}
	}
	
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	String str = "";
	while ((str=br.readLine())!=null) {
		pw.println(getResultString(str)+"\n");
	}

	br.close();
	pw.flush();
	pw.close();
}
}
