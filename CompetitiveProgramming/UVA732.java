import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Stack;

public class UVA732 {
	private static long[] generateRabinKarpFP(String str) {
		if (str.length() > 0) {
			long[] fp = new long[str.length()];
			fp[0] = str.charAt(0);
			for (int i = 1, iLen = fp.length; i < iLen; ++i) {
				fp[i] = fp[i - 1] + (str.charAt(i) * (i + 1));
			}
			return fp;
		}

		return null;
	}
	
	/*private static String formatPath(String str){
		StringBuilder sb = new StringBuilder();
		if(str.length()>0){
			sb.append(str.charAt(0));
		}
		for(int i=1, iLen = str.length(); i<iLen; ++i){
			sb.append(' ').append(str.charAt(i));
		}
		return sb.toString();
	}*/
	
	//with RBFP
	private static void printPossible(int idx, String str, int len, Stack<Character> stack, long hash, long[] fp, String path, PrintWriter pw){
		if(idx==len && stack.isEmpty()){
//			pw.println(formatPath(path));
			pw.println(path);
			return;
		}
		if(idx<len){
			stack.push(str.charAt(idx));
			printPossible(idx+1, str, len, stack, hash, fp, path+" i", pw);
			stack.pop();
		}
		if(!stack.isEmpty()){
			int fpIdx = idx-stack.size();
			int currentChar = stack.pop();
			long newHash = hash + (currentChar*(fpIdx+1));
			if(fp[fpIdx]==newHash){
				printPossible(idx, str, len, stack, newHash, fp, path+" o", pw);
			}
			stack.push((char)currentChar);
		}
		
	}
	
	//without RBFP
/*	private static void printPossible(int idx, String str, String str2, int len, Stack<Character> stack, String opString, String path, PrintWriter pw){
		if(idx==len && stack.isEmpty()){
//			pw.println(formatPath(path));
			pw.println(path);
			return;
		}
		if(idx<len){
			stack.push(str.charAt(idx));
			printPossible(idx+1, str, str2, len, stack, opString, idx==0?"i":path+" i", pw);
			stack.pop();
		}
		if(!stack.isEmpty()){
			char currentChar = stack.pop();
			String newString = opString + String.valueOf(currentChar);
			if(newString.equals(str2.substring(0, newString.length()))){
				printPossible(idx, str, str2, len, stack, newString, path+" o", pw);
			}
			stack.push(currentChar);
		}
		
	}*/
	
	/*private static boolean isAnagram(String str1, String str2){
		HashMap<Character, Integer> str1Map = new HashMap<Character, Integer>();
		HashMap<Character, Integer> str2Map = new HashMap<Character, Integer>();
		for(int i=0, iLen = str1.length(); i<iLen; ++i){
			if(str2Map.containsKey(str1.charAt(i))){
				str2Map.put(str1.charAt(i), str2Map.get(str1.charAt(i))+1);
			}else{
				str2Map.put(str1.charAt(i), 1);
			}
			if(str1Map.containsKey(str1.charAt(i))){
				str1Map.put(str1.charAt(i), str1Map.get(str1.charAt(i))+1);
			}else{
				str1Map.put(str1.charAt(i), 1);
			}
		}
		Iterator<Character> str1Iter = str1Map.keySet().iterator();
		while(str1Iter.hasNext()){
			char currentChar = str1Iter.next();
			if(!str2Map.containsKey(currentChar) || (str1Map.get(currentChar)!=str2Map.get(currentChar))){
				return false;
			}
		}
		return true;
	}*/
		
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String str;
		while ((str = br.readLine()) != null) {
			String str2 = br.readLine();
			pw.println("[");
			//anagrams check is slower
//			if(str.length()==str2.length() && isAnagram(str.toLowerCase(), str2.toLowerCase())){
			if(str.length()==str2.length()){
//				printPossible(0, str, str2, str.length(), new Stack<Character>(), "", "", pw); //without rbf
				/*Stack<Character> tempStack = new Stack<Character>();
				tempStack.push(str.charAt(0));
				printPossible(1, str, str.length(), tempStack, 0, generateRabinKarpFP(str2), "i", pw); //weirdly takes a lil more time
*/				printPossible(0, str, str.length(), new Stack<Character>(), 0, generateRabinKarpFP(str2), "", pw);
			}
			pw.println("]");
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
