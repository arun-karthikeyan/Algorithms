
public class UnionFind {
	int[] parent;

	public UnionFind(int N) {
		this.parent = new int[N];
		for (int i = 0; i < N; ++i) {
			this.parent[i] = i;
		}
	}

	/**
	 * Add a connection between p and q
	 * @param p
	 * @param q
	 */
	public void union(int p, int q) {

	}
	
	/**
	 * component identifier for p (0 to N-1)
	 * @param p
	 * @return
	 */
	public int find(int p){
		return 0;
	}
	
	/**
	 * return true if p and q are in the same component
	 * @param p
	 * @param q
	 * @return
	 */
	public boolean connected(int p, int q){
		return false;
	}
	
	/**
	 * number of components
	 * @return
	 */
	public int count(){
		return 0;
	}
}
