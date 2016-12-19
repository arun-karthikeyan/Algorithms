/**
 * API for Weighted Quick Union with Path Compression
 * @author arun
 *
 */
public class UnionFind {
	/**
	 * holds the connected component id for the elements
	 */
	int[] id;
	/**
	 * holds an upper-bound on the tree height of the component of each element
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
		
		if(i==j){
			return;
		}
		if(sz[i]<sz[j]){
			id[i] = j;
			sz[j]+=sz[i];
		}else{
			id[j] = i;
			sz[i]+=sz[j];
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
		if(p==this.id[p]){
			return p;
		}
		return this.id[p]=find(this.id[p]);
	}

	/**
	 * return true if p and q are in the same component
	 * 
	 * @param p
	 * @param q
	 * @return
	 */
	public boolean connected(int p, int q) {
		return find(p)==find(q);
	}

	/**
	 * number of components
	 * 
	 * @return
	 */
	public int count() {
		return this.count;
	}
}
