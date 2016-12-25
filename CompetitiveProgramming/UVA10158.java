import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
/**
 * War - Ennai vechu senja problem
 * @author arun
 *
 */
public class UVA10158 {
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
			if (p == -1 || q == -1) {
				return;
			}
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
			if (p == -1) {
				return -1;
			}
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
			if (p == -1 || q == -1) {
				return false;
			}
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

	private static boolean eolchar(int c) {
		return c == ' ' || c == '\n' || c == -1 || c == '\r' || c == '\t';
	}

	private static final int SETFRIENDS = 1, SETENEMIES = 2, AREFRIENDS = 3, AREENEMIES = 4;
	private static UnionFind friend;
	private static int[] enemy;

	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "fileip".equals(args[0])) {
			stream = new FileInputStream(new File("testip.txt"));
		} else {
			stream = System.in;
		}

		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

		int n = readInt();
		friend = new UnionFind(n);
		enemy = new int[n];
		Arrays.fill(enemy, -1);

		while (true) {
			int c = readInt(), x = readInt(), y = readInt();
			if (c == 0 && x == 0 && y == 0) {
				break;
			}
			int xRoot = friend.find(x), yRoot = friend.find(y), yEnemyRoot = friend.find(enemy[yRoot]),
					xEnemyRoot = friend.find(enemy[xRoot]);
			switch (c) {
			case SETFRIENDS:
				if (yRoot == xEnemyRoot || xRoot == yEnemyRoot) {
					pw.println("-1");
				} else {
					if (xEnemyRoot == -1 && yEnemyRoot == -1) {
						friend.union(xRoot, yRoot);
					} else if (yEnemyRoot == -1) {
						friend.union(xRoot, yRoot);
						int newXUnion = friend.find(xRoot);
						enemy[xEnemyRoot] = newXUnion;
						enemy[newXUnion] = xEnemyRoot;
					} else if (xEnemyRoot == -1) {
						friend.union(yRoot, xRoot);
						int newYUnion = friend.find(yRoot);
						enemy[yEnemyRoot] = newYUnion;
						enemy[newYUnion] = yEnemyRoot;
					} else {
						friend.union(xRoot, yRoot);
						int newXYUnion = friend.find(xRoot);
						friend.union(xEnemyRoot, yEnemyRoot);
						int newEnemyUnion = friend.find(xEnemyRoot);
						enemy[newXYUnion] = newEnemyUnion;
						enemy[newEnemyUnion] = newXYUnion;
					}
				}
				break;
			case SETENEMIES:
				if (xRoot != yRoot) {
					if (xEnemyRoot == -1 && yEnemyRoot == -1) {
						enemy[xRoot] = yRoot;
						enemy[yRoot] = xRoot;
					} else if (yEnemyRoot == -1) {
						if (xEnemyRoot != yRoot) {
							friend.union(xEnemyRoot, yRoot);
							int newXEnemy = friend.find(yRoot);
							enemy[xRoot] = newXEnemy;
							enemy[newXEnemy] = xRoot;
						}
					} else if (xEnemyRoot == -1) {
						if (yEnemyRoot != xRoot) {
							friend.union(yEnemyRoot, xRoot);
							int newYEnemy = friend.find(xRoot);
							enemy[yRoot] = newYEnemy;
							enemy[newYEnemy] = yRoot;
						}
					} else {
						friend.union(xEnemyRoot, yRoot);
						int newXEnemy = friend.find(yRoot);
						friend.union(yEnemyRoot, xRoot);
						int newYEnemy = friend.find(xRoot);
						enemy[newXEnemy] = newYEnemy;
						enemy[newYEnemy] = newXEnemy;
					}
				} else {
					pw.println("-1");
				}
				break;
			case AREFRIENDS:
				//checking one case is enough because of commutative property
				if (xRoot == yRoot) {
					pw.println("1");
				} else {
					pw.println("0");
				}
				break;
			case AREENEMIES:
				//checking one case is enough because of commutative property
				if (xRoot == yEnemyRoot) {
					pw.println("1");
				} else {
					pw.println("0");
				}
			}
		}
		pw.flush();
		pw.close();
	}
}
