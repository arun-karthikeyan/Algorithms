import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;

public class UVA195 {
	private static void printPermutations(PrintWriter pw, String prefix, String suffix) {
		if (suffix.length() == 0) {
			pw.println(prefix);
		} else {
			HashSet<Character> visited = new HashSet<Character>();
			for (int i = 0, iLen = suffix.length(); i < iLen; ++i) {
				if(visited.add(suffix.charAt(i))){
					printPermutations(pw, prefix + suffix.charAt(i),
							suffix.substring(0, i) + suffix.substring(i + 1, iLen));
				}
			}
		}
	}
	private static Character[] getCharArray(String a){
		Character[] charArray = new Character[a.length()];
		for(int i=0, iLen = a.length(); i<iLen; ++i){
			charArray[i]=a.charAt(i);
		}
		return charArray;
	}
	private static String getString(Character[] charArray){
		StringBuilder sb = new StringBuilder();
		for(char c: charArray){
			sb.append(c);
		}
		return sb.toString();
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			Character[] origIp = getCharArray(br.readLine());
			Arrays.sort(origIp, new java.util.Comparator<Character>(){

				@Override
				public int compare(Character o1, Character o2) {
					// TODO Auto-generated method stub
					char c1 = Character.toLowerCase(o1.charValue());
					char c2 = Character.toLowerCase(o2.charValue());
					if(c1>c2){
						return 1;
					}else if(c1<c2){
						return -1;
					}else{
						char c3 = o1.charValue(), c4 = o2.charValue();
						if(Character.isUpperCase(c3) && Character.isUpperCase(c4)){
							return 0;
						}else if(Character.isUpperCase(c3)){
							return -1;
						}else if(Character.isUpperCase(c4)){
							return 1;
						}else{
							return 0;
						}
					}
				}
			});
			printPermutations(pw, "", getString(origIp));
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
