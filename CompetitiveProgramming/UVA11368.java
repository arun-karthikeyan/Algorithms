import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

/**
 * O(nlogn) solution {n^2 doesn't pass within time limits} I've used a custom
 * segment tree, however I'm sure that there exists an easier solution
 * 
 * @author arun
 *
 */
public class UVA11368 {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];

	private static int readByte() {
		if (totalchars < 0)
			return 0;
		if (offset >= totalchars) {
			offset = 0;
			try {
				totalchars = stream.read(buffer);
			} catch (IOException e) {
				return 0;
			}
			if (totalchars <= 0)
				return -1;
		}
		return buffer[offset++];
	}

	private static int readInt() {
		int number = readByte();

		while (eolchar(number))
			number = readByte();

		int sign = 1;
		int val = 0;

		if (number == '-') {
			sign = -1;
			number = readByte();
		}

		do {
			if ((number < '0') || (number > '9'))
				return 0;
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));

		return sign * val;
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	private static final int W = 0, H = 1;
	private static int[] st, dolls[];

	private static void buildSegTree(int node, int start, int end) {
		if (start == end) {
			st[node] = start;
			return;
		}
		int mid = (start + end) >> 1, left = node << 1, right = left + 1;
		buildSegTree(left, start, mid);
		buildSegTree(right, mid + 1, end);
		if (dolls[st[left]][H] > dolls[st[right]][H]) {
			st[node] = st[left];
		} else {
			st[node] = st[right];
		}
	}

	// returns the index of the first node with height greater than the given
	// height
	private static int query(int node, int start, int end, int l, int r, int height) {
		if (dolls[st[node]][H] <= height) {
			return -1;
		}
		if (l > end || r < start) {
			return -1;
		}
		if (start == end) {
			return start;
		}
		int left = node << 1, right = left + 1, mid = (start + end) >> 1;
		// actually there is no difference between the partially inbounds case
		// and the completely inbounds case for this application
		int leftSubTree = query(left, start, mid, l, r, height);
		if (leftSubTree == -1) {
			int rightSubTree = query(right, mid + 1, end, l, r, height);
			return rightSubTree;
		}
		return leftSubTree;
	}

	// make the height at position -1, rendering it not usable
	private static void update(int node, int start, int end, int idx) {
		if (idx > end || idx < start) {
			return;
		}
		if (start == end) {
			dolls[start][H] = -1;
			return;
		}
		int left = node << 1, right = left + 1, mid = (start + end) >> 1;
		update(left, start, mid, idx);
		update(right, mid + 1, end, idx);
		if (dolls[st[left]][H] > dolls[st[right]][H]) {
			st[node] = st[left];
		} else {
			st[node] = st[right];
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		Comparator<int[]> dollComparator = new java.util.Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int c = o1[W] - o2[W];
				if (c == 0) {
					return o2[H] - o1[H];
				}
				return c;
			}
		};
		int testcases = readInt();

		while (testcases-- > 0) {
			int n = readInt();
			dolls = new int[n][2];
			for (int i = 0; i < n; ++i) {
				dolls[i][W] = readInt();
				dolls[i][H] = readInt();
			}
			st = new int[n << 2];
			Arrays.sort(dolls, dollComparator);
			buildSegTree(1, 0, n - 1);
			int count = 0;
			int[] weightJumper = new int[n];
			boolean[] dp = new boolean[n];
			weightJumper[n - 1] = -1;
			for (int i = n - 2; i >= 0; --i) {
				if (dolls[i][W] < dolls[i + 1][W]) {
					weightJumper[i] = i + 1;
				} else {
					weightJumper[i] = weightJumper[i + 1];
				}
			}
			for (int i = 0; i < n; ++i) {
				if (!dp[i]) {
					count++;
					for (int j = weightJumper[i], currentHeight = dolls[i][H]; j >= 0 && j < n;) {
						int pos = query(1, 0, n - 1, j, n - 1, currentHeight);
						if (pos != -1) {
							currentHeight = dolls[pos][H];
							dp[pos] = true;
							update(1, 0, n - 1, pos);
							j = weightJumper[pos];
						} else {
							break;
						}
					}
				}
			}
			pw.println(count);
		}

		pw.flush();
		pw.close();
	}
}
