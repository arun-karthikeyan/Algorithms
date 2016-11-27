import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class UVA156 {
	static class Ananagrams{
		public String word;
		public Ananagrams(String word){
			this.word = word;
		}

		@Override
		public int hashCode(){
			int hash = 0;
			String lowerCaseWord = this.word.toLowerCase();
			for(int i=0, iLen = lowerCaseWord.length(); i<iLen; ++i){
				hash+=lowerCaseWord.charAt(i);
			}
			return hash;
		}
		@Override
		public boolean equals(Object o){
			String w1 = this.word.toLowerCase();
			String w2 = ((Ananagrams)o).word.toLowerCase();
			int[] anagramsw1 = new int[26];
			int[] anagramsw2 = new int[26];
			for(int i=0, iLen=w1.length();i<iLen;++i){
				anagramsw1[w1.charAt(i)-'a']++;
			}
			for(int i=0, iLen=w2.length();i<iLen;++i){
				anagramsw2[w2.charAt(i)-'a']++;
			}
			for(int i=0; i<26; ++i){
				if(anagramsw1[i]!=anagramsw2[i]){
					return false;
				}
			}
			return true;
		}
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line = "";
		HashMap<Ananagrams, Integer> words = new HashMap<Ananagrams, Integer>();
		ArrayList<String> ananagrams = new ArrayList<String>();
		while (!(line=br.readLine()).equals("#")) {
			StringTokenizer st = new StringTokenizer(line);
			while(st.hasMoreTokens()){
				Ananagrams currentWord = new Ananagrams(st.nextToken());
				if(currentWord.word.length()>1){
				if(words.containsKey(currentWord)){
					words.put(currentWord, words.get(currentWord)+1);
				}else{
					words.put(currentWord, 1);
				}
				}else{
					ananagrams.add(currentWord.word);
				}
			}
		}
		Iterator<Ananagrams> iter = words.keySet().iterator();
		while(iter.hasNext()){
			Ananagrams temp = iter.next();
			int val = words.get(temp);
			if(val==1){
				ananagrams.add(temp.word);
			}
		}
		Collections.sort(ananagrams);
		for(int i=0, iLen = ananagrams.size(); i<iLen; ++i){
			pw.println(ananagrams.get(i));
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
