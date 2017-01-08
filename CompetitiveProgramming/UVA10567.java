import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class UVA10567 {
	private static String candidates;
	private static String fbStr;
	private static ArrayList<Integer>[] ratings;
	private static final HashMap<Character, Integer> STATEMAP;
	static {
		STATEMAP = new HashMap<Character, Integer>();
		for (int i = 0; i < 52; i += 2) {
			STATEMAP.put((char) ('a' + (i >> 1)), i);
			STATEMAP.put((char) ('A' + (i >> 1)), i + 1);
		}
	}

	private static int findIndex(ArrayList<Integer> str, int key) {
		int low = 0, len = str.size(), high = len - 1;
		while (low <= high) {
			int mid = (high + low) >> 1;
			int currentVal = str.get(mid);
			if (key < currentVal) {
				high = mid - 1;
			} else if (key > currentVal) {
				low = mid + 1;
			} else {
				return key;
			}
		}
		return low < len ? str.get(low) : -1;
	}

	private static final String MATCH = "Matched";
	private static final String NOMATCH = "Not matched";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		candidates = br.readLine();
		ratings = new ArrayList[52];
		for (int i = 0; i < 52; ++i) {
			ratings[i] = new ArrayList<Integer>();
		}
		for (int i = 0, iLen = candidates.length(); i < iLen; ++i) {
			ratings[STATEMAP.get(candidates.charAt(i))].add(i);
		}
		int q = Integer.parseInt(br.readLine());
		while (q-- > 0) {
			int candidateIdx = 0;
			boolean match = true;
			int startIdx = -1;
			fbStr = br.readLine();
			if (match) {
				for (int i = 0, iLen = fbStr.length(); i < iLen; ++i) {
					int nextPos = findIndex(ratings[STATEMAP.get(fbStr.charAt(i))], candidateIdx);
					if (nextPos < candidateIdx) {
						match = false;
					} else {
						if (i == 0) {
							startIdx = nextPos;
						}
						candidateIdx = nextPos + 1;
					}
				}
			}
			if (match) {
				pw.println(MATCH + " " + startIdx + " " + (candidateIdx - 1));
			} else {
				pw.println(NOMATCH);
			}
		}
		br.close();
		pw.flush();
		pw.close();
	}
}
