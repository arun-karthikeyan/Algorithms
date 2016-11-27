	import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class UVA454 {
	private static boolean isAnagram(String a, String b){
		if(a.length()!=b.length()){
			return false;
		}
		HashMap<Character, Integer> count = new HashMap<Character, Integer>();
		for(int i=0, iLen = a.length(); i<iLen; ++i){
			char c = a.charAt(i);
			if(count.containsKey(c)){
				count.put(c, count.get(c)+1);
			}else{
				count.put(c, 1);
			}
		}
		for(int i=0, iLen = b.length(); i<iLen; ++i){
			char c = b.charAt(i);
			if(count.containsKey(c)){
				int val = count.get(c);
				if(val==0){
					return false;
				}else{
					count.put(c, val-1);
				}
			}else{
				return false;
			}
		}
		return true;
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int testcases = Integer.parseInt(br.readLine());
	boolean once = false;
	while (testcases-- > 0) {
		if(!once)
		{
			br.readLine();//testcase followed by a blank line
			once = true;
		}
		String line = "";
		ArrayList<String> anagrams = new ArrayList<String>();
		while(!"".equals(line=br.readLine()) && line!=null){
			anagrams.add(line);
		}
		Collections.sort(anagrams);
		for(int i=0, iLen = anagrams.size(); i<iLen; ++i){
			String one = anagrams.get(i);
			for(int j=i+1; j<iLen; ++j){
				String two = anagrams.get(j);
				if(isAnagram(one.replaceAll(" ", ""), two.replaceAll(" ", ""))){
					String[] currentAnagramPair = new String[]{one, two};
					Arrays.sort(currentAnagramPair);
					pw.println(currentAnagramPair[0]+" = "+currentAnagramPair[1]);
				}
			}
		}
		if(testcases!=0){
			pw.println();
		}
	}

	br.close();
	pw.flush();
	pw.close();
}
}
