import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Dice
 * 
 * @author arun
 *
 */
public class UVA11959 {
	private static String createString(String src, int i, int j, int k, int l, int m, int n) {
		StringBuilder sb = new StringBuilder();
		return sb.append(src.charAt(i)).append(src.charAt(j)).append(src.charAt(k)).append(src.charAt(l))
				.append(src.charAt(m)).append(src.charAt(n)).toString();
	}

	private static boolean checkVerticalRotation(String s1, String s2) {
		if (s1.equals(s2)) {
			return true;
		}

		boolean result = false;
		StringBuilder prev = new StringBuilder(s2);
		for (int i = 1; i < 4; ++i) {
			StringBuilder current = new StringBuilder(prev.substring(0, 2));
			for (int j = i, k = 0; k < 4; ++j, ++k) {
				current.append(prev.charAt(2 + (j % 4)));
			}
			if (current.toString().equals(s1)) {
				return true;
			}
			prev = current;
		}
		return result;
	}

	private static boolean isEqual(String d1, String d2) {
		String pos1 = d2;
		String pos2 = createString(d2, 2, 4, 1, 3, 0, 5);
		String pos3 = createString(d2, 1, 0, 4, 3, 2, 5);
		String pos4 = createString(d2, 4, 2, 0, 3, 1, 5);
		String pos5 = createString(d2, 3, 5, 2, 1, 4, 0);
		String pos6 = createString(d2, 5, 3, 2, 0, 4, 1);
		boolean result = checkVerticalRotation(d1, pos1);
		result |= checkVerticalRotation(d1, pos2);
		result |= checkVerticalRotation(d1, pos3);
		result |= checkVerticalRotation(d1, pos4);
		result |= checkVerticalRotation(d1, pos5);
		result |= checkVerticalRotation(d1, pos6);
		return result;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int testcases = Integer.parseInt(br.readLine());
		while (testcases-- > 0) {
			String[] info = br.readLine().trim().split(" ");
			if (isEqual(info[0].trim(), info[1].trim())) {
				pw.println("Equal");
			} else {
				pw.println("Not Equal");
			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
