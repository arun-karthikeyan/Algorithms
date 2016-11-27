import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class UVA156_2 {
private static final String DUPLICATE="*";
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	HashMap<String, String> words = new HashMap<String, String>();
	ArrayList<String> relativeAnanagrams = new ArrayList<String>();
	String line = br.readLine();
	while (!(line.equals("#"))) {
		StringTokenizer st = new StringTokenizer(line);
		while(st.hasMoreTokens()){
			String valueWord = st.nextToken();
			if(valueWord.length()==1){
				relativeAnanagrams.add(valueWord);
				continue;
			}
			char[] keywordArray = valueWord.toLowerCase().toCharArray();
			Arrays.sort(keywordArray);
			String keyWord = new String(keywordArray);
			if(words.containsKey(keyWord)){
				words.put(keyWord, DUPLICATE);
			}else{
				words.put(keyWord, valueWord);
			}
		}
		line = br.readLine();
	}
	Iterator<String> relativeAnanagramsIterator = words.keySet().iterator();
	while(relativeAnanagramsIterator.hasNext()){
		String currentWord = words.get(relativeAnanagramsIterator.next());
		if(!currentWord.equals(DUPLICATE)){
			relativeAnanagrams.add(currentWord);
		}
	}
	Collections.sort(relativeAnanagrams);
	for(String relativeAnanagram: relativeAnanagrams){
		pw.println(relativeAnanagram);
	}

	br.close();
	pw.flush();
	pw.close();
}
}
