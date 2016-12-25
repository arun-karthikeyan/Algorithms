import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Friends
 * @author arun
 *
 */
public class UVA10608 {
/**
 * API for Weighted Quick Union with Path Compression
 * 
 * @author arun
 *
 */
static class UnionFind {
	/**
	 * holds the connected component id for the elements
	 */
	int[] id;

	/**
	 * holds the size of the component of containing each element
	 */
	int[] sz;

	/**
	 * keeps count of the number of connected components
	 */
	int count;

	/**
	 * Initializes N sites with integer names 0 to N-1
	 * 
	 * @param N
	 */
	public UnionFind(int N) {
		this.id = new int[N];
		this.sz = new int[N];
		for (int i = 0; i < N; ++i) {
			this.id[i] = i;
			this.sz[i] = 1;
		}
		this.count = N;

	}

	/**
	 * Add a connection between p and q
	 * 
	 * @param p
	 * @param q
	 */
	public void union(int p, int q) {
		int i = find(p);
		int j = find(q);

		if (i == j) {
			return;
		}
		if (sz[i] < sz[j]) {
			id[i] = j;
			sz[j] += sz[i];
		} else {
			id[j] = i;
			sz[i] += sz[j];
		}
		this.count--;
	}

	/**
	 * component identifier for p (0 to N-1)
	 * 
	 * @param p
	 * @return
	 */
	public int find(int p) {
		if (p == this.id[p]) {
			return p;
		}
		return this.id[p] = find(this.id[p]);
	}

	/**
	 * return true if p and q are in the same component
	 * 
	 * @param p
	 * @param q
	 * @return
	 */
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	/**
	 * number of (disjoint) components
	 * 
	 * @return
	 */
	public int count() {
		return this.count;
	}

	/**
	 * returns the size of the component containing this element
	 * 
	 * @param p
	 * @return
	 */
	public int componentSize(int p) {
		return this.sz[find(p)];
	}
}
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
			//return sign*val;
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
private static int max(int a, int b){
	return a>b?a:b;
}
public static void main(String[] args) throws Exception {
	if (args.length > 0 && "fileip".equals(args[0])) {
		stream = new FileInputStream(new File("testip.txt"));
	} else {
		stream = System.in;
	}

	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int testcases = readInt();

	while (testcases-- > 0) {
		int n = readInt(), m = readInt();
		UnionFind uf = new UnionFind(n);
		for(int i=0; i<m; ++i){
			uf.union(readInt()-1, readInt()-1);
		}
		int max = 1;
		for(int i=0; i<n; ++i){
			max = max(max, uf.sz[i]);
		}
		pw.println(max);
	}

	pw.flush();
	pw.close();
}
}
