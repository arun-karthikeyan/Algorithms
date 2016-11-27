import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class UVA608_2 {
	private static final String UP = "up", DOWN = "down", EVEN = "even";

	private static HashSet<Character> getHashSet(String s) {
		HashSet<Character> hs = new HashSet<Character>();
		for (int i = 0, iLen = s.length(); i < iLen; ++i) {
			hs.add(s.charAt(i));
		}
		return hs;
	}

	private static HashSet<Character> setUnion(ArrayList<HashSet<Character>> s) {
		HashSet<Character> union = new HashSet<Character>();
		for (int i = 0, iLen = s.size(); i < iLen; ++i) {
			union.addAll(s.get(i));
		}
		return union;
	}

	private static HashSet<Character> setIntersect(ArrayList<HashSet<Character>> s) {
		HashSet<Character> intersect = new HashSet<Character>();
		int listSize = s.size();
		if (listSize > 0) {
			intersect.addAll(s.get(0));
			for (int i = 1, iLen = s.size(); i < iLen; ++i) {
				intersect.retainAll(s.get(i));
			}
		}
		return intersect;
	}

	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			ArrayList<HashSet<Character>> light = new ArrayList<HashSet<Character>>();
			ArrayList<HashSet<Character>> heavy = new ArrayList<HashSet<Character>>();
			HashSet<Character> equal = new HashSet<Character>();
			for (int i = 0; i < 3; ++i) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				String left = st.nextToken(), right = st.nextToken(), verdict = st.nextToken();
				switch (verdict) {
				case UP:
					light.add(getHashSet(right));
					heavy.add(getHashSet(left));
					break;
				case DOWN:
					light.add(getHashSet(left));
					heavy.add(getHashSet(right));
					break;
				case EVEN:
					equal.addAll(getHashSet(left));
					equal.addAll(getHashSet(right));
					break;
				}
			}

			HashSet<Character> unionLight = setUnion(light);
			unionLight.addAll(equal);

			HashSet<Character> unionHeavy = setUnion(heavy);
			unionHeavy.addAll(equal);

			HashSet<Character> lightIntersect = setIntersect(light);

			lightIntersect.removeAll(unionHeavy);

			if (lightIntersect.size() > 0) {
				pw.println(lightIntersect.iterator().next() + " is the counterfeit coin and it is light.");
			} else {
				HashSet<Character> heavyIntersect = setIntersect(heavy);
				heavyIntersect.removeAll(unionLight);
				pw.println(heavyIntersect.iterator().next() + " is the counterfeit coin and it is heavy.");
			}

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
