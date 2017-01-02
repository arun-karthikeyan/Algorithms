import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Fun Coloring - Flagged Problem, I/O fails in UVA
 * Try after some time
 * 
 * @author arun
 *
 */
public class UVA12348 {
	private static int n, m, OK;
	private static int[] subsets, bits;
	private static char currentResult;

	private static void solve(int path, int invalid) {
		boolean result = true;
		for (int i = 0; i < m && result; ++i) {
			int b = subsets[i] & path;
			result &= (bits[i] == 1 || (b > 0 && b < subsets[i]));
		}
		if (result) {
			currentResult = 'Y';
		} else {
			int vp = OK & (~invalid);
			while (vp > 0) {
				int p = vp & (-vp);
				vp -= p;
				solve(path | p, (p << 1) - 1);
			}
		}
	}

	private static int getInteger(String s) {
		int num = 0;
		for (int i = 0, iLen = s.length(); i < iLen; ++i) {
			char val = s.charAt(i);
			if (val >= '0' && val <= '9') {
				num = num * 10 + (val - '0');
			} else {
				break;
			}
		}
		return num;
	}

	private static int countBits(int val) {
		int count = 0;
		while (val > 0) {
			int p = val & (-val);
			val -= p;
			count++;
		}
		return count;
	}

	public static void main(String[] args) throws Exception {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		int k = getInteger(br.readLine().trim());

		StringBuilder result = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			String[] info = line.trim().split(" ");
			n = getInteger(info[0].trim());
			m = getInteger(info[1].trim());
			subsets = new int[m];
			bits = new int[m];
			OK = (1 << n) - 1;
			currentResult = 'N';
			for (int j = 0; j < m; ++j) {
				String[] currentSet = br.readLine().trim().split(" ");
				int len = currentSet.length;
				for (int i = 0; i < len; ++i) {
					subsets[j] |= (1 << (getInteger(currentSet[i].trim()) - 1));
				}
				bits[j] = countBits(subsets[j]);
			}

			solve(0, 0);
			result.append(currentResult);
			if (k != 0) {
				br.readLine();
			}
			// tc++;
		}
		pw.print(result);

		br.close();
		pw.flush();
		pw.close();
	}
}
