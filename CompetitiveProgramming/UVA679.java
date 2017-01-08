import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
/**
 * Dropping Balls
 * @author arun
 *
 */
public class UVA679 {
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

	private static int[] tree;
	private static int[][][] levelVals;
	private static final int VAL = 0, IDX = 1;

	private static void fillTree(int node, int[] vals, int len) {
		tree[node] = vals[0];
		if (len == 1) {
			return;
		}
		int left = node << 1, right = left + 1;
		int newLen = len >> 1;
		int[] v1 = new int[newLen];
		int[] v2 = new int[newLen];
		for (int i = 0; i < len; i += 2) {
			v1[i >> 1] = vals[i];
			v2[i >> 1] = vals[i + 1];
		}
		fillTree(left, v1, newLen);
		fillTree(right, v2, newLen);
	}

	static class Node {
		int id;
		int val;
		int level;

		public Node(int id, int val, int level) {
			this.id = id;
			this.val = val;
			this.level = level;
		}
	}

	private static int getIdx(int[][] vals, int key) {
		int high = vals.length - 1, low = 0;
		while (low <= high) {
			int mid = (low + high) >> 1;
			if (key < vals[mid][VAL]) {
				high = mid - 1;
			} else if (key > vals[mid][VAL]) {
				low = mid + 1;
			} else {
				return vals[mid][IDX];
			}
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
		int maxLen = (1 << 20) + 1;
		tree = new int[maxLen];
		int[] initValArray = new int[1 << 19];
		for (int i = 0, iLen = initValArray.length; i < iLen; ++i) {
			initValArray[i] = i + 1;
		}
		fillTree(1, initValArray, initValArray.length);
		levelVals = new int[20][][];
		for (int i = 0; i < 20; ++i) {
			levelVals[i] = new int[1 << i][2];
		}

		int[] levelIdx = new int[20];
		Queue<Node> bfs = new LinkedList<Node>();
		bfs.add(new Node(1, 1, 0));
		while (!bfs.isEmpty()) {
			Node currentNode = bfs.remove();
			int level = currentNode.level;
			if (level > 19) {
				continue;
			}
			levelVals[level][levelIdx[level]][VAL] = currentNode.val;
			levelVals[level][levelIdx[level]][IDX] = levelIdx[level]++;
			int id = currentNode.id;
			int left = id << 1, right = left + 1;
			if (left < maxLen) {
				bfs.add(new Node(left, tree[left], level + 1));
			}
			if (right < maxLen) {
				bfs.add(new Node(right, tree[right], level + 1));
			}
		}
		Comparator<int[]> c = new java.util.Comparator<int[]>() {

			@Override
			public int compare(int[] arg0, int[] arg1) {
				// TODO Auto-generated method stub
				return arg0[VAL] - arg1[VAL];
			}

		};
		for (int i = 0; i < 20; ++i) {
			Arrays.sort(levelVals[i], c);
		}

		int n;
		while ((n = readInt()) != -1) {
			while (n-- > 0) {
				int d = readInt() - 1, l = readInt();
				pw.println(getIdx(levelVals[d], l) + (1 << d));
			}
		}
		pw.flush();
		pw.close();
	}
}
