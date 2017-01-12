import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA183 {
	private static int n, m;
	private static int ZERO = 0, ONE = 1, D = 2;

	private static String bToD(int[][] bType, int rs, int re, int cs, int ce) {
		if (rs > re || cs > ce) {
			return "";
		}
		boolean containsZero = false, containsOne = false;
		for (int i = rs; i <= re; ++i) {
			for (int j = cs; j <= ce; ++j) {
				if (bType[i][j] == ZERO) {
					containsZero = true;
					if (containsOne) {
						break;
					}
				} else {
					containsOne = true;
					if (containsZero) {
						break;
					}
				}
			}
		}
		if (containsZero && containsOne) {
			int rMid = (rs + re) >> 1, cMid = (cs + ce) >> 1;
			String topLeft = bToD(bType, rs, rMid, cs, cMid);
			String topRight = bToD(bType, rs, rMid, cMid + 1, ce);
			String bottomLeft = bToD(bType, rMid + 1, re, cs, cMid);
			String bottomRight = bToD(bType, rMid + 1, re, cMid + 1, ce);
			return "D" + topLeft + topRight + bottomLeft + bottomRight;
		} else if (containsZero) {
			return "0";
		} else {
			return "1";
		}
	}

	private static int dToB(String dType, int idx, int[][] bType, int rs, int re, int cs, int ce) {
		if (idx == dType.length() || cs > ce || rs > re) {
			return idx;
		}
		char currentChar = dType.charAt(idx);
		if (currentChar == 'D') {
			int rMid = (rs + re) >> 1, cMid = (cs + ce) >> 1;
			// topLeft case
			int nextIdx = dToB(dType, idx + 1, bType, rs, rMid, cs, cMid);
			nextIdx = dToB(dType, nextIdx, bType, rs, rMid, cMid + 1, ce);
			nextIdx = dToB(dType, nextIdx, bType, rMid + 1, re, cs, cMid);
			return dToB(dType, nextIdx, bType, rMid + 1, re, cMid + 1, ce);
		} else if (currentChar == '0') {
			for (int i = rs; i <= re; ++i) {
				for (int j = cs; j <= ce; ++j) {
					bType[i][j] = 0;
				}
			}
			return idx + 1;
		} else {
			for (int i = rs; i <= re; ++i) {
				for (int j = cs; j <= ce; ++j) {
					bType[i][j] = 1;
				}
			}
			return idx + 1;
		}
	}

	private static String getPrintableString(String s) {
		StringBuilder sb = new StringBuilder();
		int sLen = s.length();
		if (sLen > 0) {
			sb.append(s.charAt(0));
		}
		for (int i = 1; i < sLen; ++i) {
			if ((i % 50) == 0) {
				sb.append("\n");
			}
			sb.append(s.charAt(i));
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		// PrintWriter pw = new PrintWriter("testop.txt");
		String line = br.readLine();

		while (!"#".equals(line)) {
			String[] info = line.split(" * ");
			char type = info[0].charAt(0);
			n = Integer.parseInt(info[1]);
			m = Integer.parseInt(info[2]);
			StringBuilder sb = new StringBuilder();
			while (!"#".equals(line = br.readLine()) && !line.contains(" ")) {
				sb.append(line);
			}
			switch (type) {
			case 'B': {
				int[][] bType = new int[n][m];
				for (int i = 0, idx = 0; i < n; ++i) {
					for (int j = 0; j < m; ++j, ++idx) {
						bType[i][j] = sb.charAt(idx) - '0';
					}
				}
				pw.printf("%s%4d%4d\n", "D", n, m);
				pw.println(getPrintableString(bToD(bType, 0, n - 1, 0, m - 1)));
			}
				break;
			case 'D':
				int[][] bType = new int[n][m];
				dToB(sb.toString(), 0, bType, 0, n - 1, 0, m - 1);
				sb = new StringBuilder();
				for (int i = 0, idx = 0; i < n; ++i) {
					for (int j = 0; j < m; ++j, ++idx) {
						if (idx != 0 && ((idx % 50) == 0)) {
							sb.append("\n");
						}
						sb.append(bType[i][j]);
					}
				}
				pw.printf("%s%4d%4d\n", "B", n, m);
				pw.println(sb.toString());

			}
		}

		br.close();
		pw.flush();
		pw.close();
	}
}
