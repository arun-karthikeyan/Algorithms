import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class UVA10131 {
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

	private static final int WEIGHT = 0, IQ = 1, IDX = 2;
	private static final int LIS = 0, PARENT = 1;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int iq, w, idx = 1;

		ArrayList<int[]> data = new ArrayList<int[]>();

		while (true) {
			w = readInt();
			iq = readInt();
			if (w == 0 && iq == 0) {
				break;
			}
			data.add(new int[] { w, iq, idx++ });
		}

		Collections.sort(data, new java.util.Comparator<int[]>() {
			@Override
			public int compare(int[] arg0, int[] arg1) {
				return arg0[WEIGHT] - arg1[WEIGHT];
			}
		});
		int dataSize = data.size();
		int[][] dp = new int[dataSize][2];
		dp[0][PARENT] = 0;
		dp[0][LIS] = 1;

		for (int i = 1; i < dataSize; ++i) {
			int[] currentElephant = data.get(i);
			int maxMatch = Integer.MIN_VALUE, maxIdx = -1;
			for (int j = 0; j < i; ++j) {
				int[] previousElephant = data.get(j);
				if (previousElephant[WEIGHT] < currentElephant[WEIGHT] && previousElephant[IQ] > currentElephant[IQ]) {
					if (maxMatch < dp[j][LIS]) {
						maxMatch = dp[j][LIS];
						maxIdx = j;
					}
				}
			}
			if (maxMatch == Integer.MIN_VALUE) {
				dp[i][PARENT] = i;
				dp[i][LIS] = 1;
			} else {
				dp[i][PARENT] = maxIdx;
				dp[i][LIS] = maxMatch + 1;
			}
		}

		int max = Integer.MIN_VALUE, maxIdx = -1;
		for (int i = 0; i < dataSize; ++i) {
			if (max < dp[i][LIS]) {
				max = dp[i][LIS];
				maxIdx = i;
			}
		}
		Stack<Integer> backTrack = new Stack<Integer>();
		while (dp[maxIdx][PARENT] != maxIdx) {
			backTrack.push(data.get(maxIdx)[IDX]);
			maxIdx = dp[maxIdx][PARENT];
		}
		backTrack.push(data.get(maxIdx)[IDX]);
		pw.println(backTrack.size());
		while (!backTrack.isEmpty()) {
			pw.println(backTrack.pop());
		}
		pw.flush();
		pw.close();
	}
}
