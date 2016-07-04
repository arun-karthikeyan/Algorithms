package arraysandstrings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class URLify {
static void urlify(char[] sentence, int length, char[] replacement){
	
	if(sentence.length==length){
		return;
	}
	
	int off1 = length-1;
	int off2 = sentence.length-1;
	
	while(off1>=0){
		if(off2==off1){
			return;
		}
		
		if(sentence[off1]!=' '){
			sentence[off2] = sentence[off1];
			off1--;
			off2--;
		}else{
			off1--;
			for(int i=replacement.length-1; i>=0; --i, --off2){
				sentence[off2] = replacement[i];
			}
		}
	}
}

public static void main(String[] srgs) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	char[] sentence = br.readLine().toCharArray();
	int trueLen = Integer.parseInt(br.readLine());
	char[] replacement = new char[]{'%','2','0'};
	
	urlify(sentence, trueLen, replacement);
	
	System.out.println(Arrays.toString(sentence));
}
}
