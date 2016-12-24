import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11235 {
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

	private static long readLong() {
		int number = readByte();

		while (eolchar(number))
			number = readByte();

		int sign = 1;
		long val = 0;

		if (number == '-') {
			sign = -1;
			number = readByte();
		}

		do {
			if ((number < '0') || (number > '9')) {
				// return sign*val;
				return 0;
			}
			val *= 10;
			val += (number - '0');
			number = readByte();
		} while (!eolchar(number));

		return sign * val;
	}

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	private static int[] baseArray;
	private static STNode[] st;

	static class STNode {
		int maxCount;
		int rightVal;
		int rightValCount;
		int leftVal;
		int leftValCount;
		int startIdx;
		int endIdx;
	}

	private static void buildSegmentTree(int node, int start, int end) {
		if (start == end) {
			st[node] = new STNode();
			st[node].leftValCount = st[node].rightValCount = st[node].maxCount = 1;
			st[node].leftVal = st[node].rightVal = baseArray[start];
			st[node].endIdx = st[node].startIdx = start;
			return;
		}
		int mid = (start + end) >> 1, left = node << 1, right = left + 1;
		buildSegmentTree(left, start, mid);
		buildSegmentTree(right, mid + 1, end);
		st[node] = merge(st[left], st[right]);
	}

	private static STNode merge(STNode p1, STNode p2) {
		STNode result = new STNode();
		int le = p1.endIdx, ls = p1.startIdx, rs = p2.startIdx, re = p2.endIdx;
		if (baseArray[le] == baseArray[rs]) {
			int mergeCount = p1.rightValCount + p2.leftValCount;
			if (baseArray[ls] == baseArray[le]) {
				result.leftValCount = mergeCount;
			} else {
				result.leftValCount = p1.leftValCount;
			}
			if (baseArray[rs] == baseArray[re]) {
				result.rightValCount = mergeCount;
			} else {
				result.rightValCount = p2.rightValCount;
			}
			result.maxCount = max(max(p1.maxCount, p2.maxCount), mergeCount);
		} else {
			result.leftValCount = p1.leftValCount;
			result.rightValCount = p2.rightValCount;
			result.maxCount = max(p1.maxCount, p2.maxCount);
		}
		result.leftVal = p1.leftVal;
		result.rightVal = p2.rightVal;
		result.startIdx = p1.startIdx;
		result.endIdx = p2.endIdx;
		return result;
	}

	private static STNode rangeQuery(int node, int start, int end, int l, int r) {
		if (end < l || start > r) {
			return null;
		}

		if (start >= l && end <= r) {
			return st[node];
		}

		int mid = (start + end) >> 1, left = node << 1, right = left + 1;
		STNode p1 = rangeQuery(left, start, mid, l, r);
		STNode p2 = rangeQuery(right, mid + 1, end, l, r);
		if (p1 == null) {
			return p2;
		}
		if (p2 == null) {
			return p1;
		}
		return merge(p1, p2);
	}

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n;

		while ((n = readInt()) != 0) {
			int q = readInt();
			baseArray = new int[n];
			st = new STNode[n << 2];
			for (int i = 0; i < n; ++i) {
				baseArray[i] = readInt();
			}
			buildSegmentTree(1, 0, n - 1);
			for (int i = 0; i < q; ++i) {
				pw.println(rangeQuery(1, 0, n - 1, readInt() - 1, readInt() - 1).maxCount);
			}

		}

		pw.flush();
		pw.close();
	}
}
