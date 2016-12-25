import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

public class UVA10685 {
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

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
		String line;
		while ((line = br.readLine()) != null) {
			String[] cr = line.split(" ");
			int c = Integer.parseInt(cr[0]), r = Integer.parseInt(cr[1]);
			if (c == 0 && r == 0) {
				break;
			}
			HashMap<String, Integer> creatureMap = new HashMap<String, Integer>();
			for (int i = 0; i < c; ++i) {
				creatureMap.put(br.readLine(), creatureMap.size());
			}
			UnionFind uf = new UnionFind(c);
			for (int i = 0; i < r; ++i) {
				String[] relation = br.readLine().split(" ");
				uf.union(creatureMap.get(relation[0]), creatureMap.get(relation[1]));
			}
			int max = 1;
			for (int i = 0; i < c; ++i) {
				max = max(uf.sz[i], max);
			}
			pw.println(max);
			br.readLine();

		}

		br.close();
		pw.flush();
		pw.close();
	}
}
