import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Caesar Cypher
 * 
 * @author arun
 *
 */
public class UVA554 {
	private static final HashMap<Character, Integer> CMAP;
	private static final HashMap<Integer, Character> RMAP;
	static {
		CMAP = new HashMap<Character, Integer>();
		RMAP = new HashMap<Integer, Character>();
		CMAP.put(' ', 0);
		RMAP.put(0, ' ');
		for (char i = 'A'; i <= 'Z'; ++i) {
			CMAP.put(i, i - 'A' + 1);
			RMAP.put(i - 'A' + 1, i);
		}
	}

	public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line;
		HashSet<String> dict = new HashSet<String>();
		while (!(line = br.readLine()).equals("#")) {
			dict.add(line);
		}
		line = br.readLine();
		int len = line.length();
		int max = 0;
		String mostMatch = line;
		for (int i = 0; i < 27; ++i) {
			int mc = 0;
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < len; ++j) {
				char ch = line.charAt(j);
				int newCharIdx = (CMAP.get(ch) + i) % 27;
				sb.append(RMAP.get(newCharIdx));
			}
			String newLine = sb.toString();
			String info[] = newLine.split("\\s+");
			for (int j = 0, jLen = info.length; j < jLen; ++j) {
				if (dict.contains(info[j])) {
					mc++;
				}
			}
			if (max < mc) {
				max = mc;
				sb = new StringBuilder();
				for (int j = 0, jLen = info.length, off = 0; j < jLen; ++j) {
					int cLen = info[j].length() + (j == 0 ? 0 : 1);
					off += cLen;
					if (off > 60) {
						sb.append('\n');
						off = cLen;
					}
					if (off == cLen) {
						sb.append(info[j]);
					} else {
						sb.append(" " + info[j]);
					}
				}
				mostMatch = sb.toString();
			}
		}
		pw.println(mostMatch);
		br.close();
		pw.flush();
		pw.close();
	}
}
