import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA12532 {
	private static int[] baseArray;
	private static STNode[] st;
	static class STNode{
		int neg;
		int pos;
		int zero;
	}
	private static void buildST(int node, int start, int end){
		if(start==end){
			st[node] = new STNode();
			if(baseArray[start]==0){
				st[node].zero = 1;
			}else if(baseArray[start]>0){
				st[node].pos = 1;
			}else{
				st[node].neg = 1;
			}
			return;
		}
		int mid = (start+end)>>1, left = node<<1, right = left+1;
		buildST(left, start, mid);
		buildST(right, mid+1, end);
		st[node] = merge(st[left], st[right]);
	}
	private static STNode merge(STNode p1, STNode p2){
		STNode result = new STNode();
		result.neg = p1.neg + p2.neg;
		result.pos = p1.pos + p2.pos;
		result.zero = p1.zero + p2.zero;
		return result;
	}
	private static char getResult(STNode r){
		if(r.zero>0){
			return '0';
		}else if((r.neg&1)==1){
			return '-';
		}else{
			return '+';
		}
	}
	private static STNode rangeQuery(int node, int start, int end, int l, int r){
		if(end<l || start>r){
			return null;
		}
		if(start>=l && end<=r){
			return st[node];
		}
		int mid = (start+end)>>1, left = node<<1, right = left+1;
		STNode p1 = rangeQuery(left, start, mid, l, r);
		STNode p2 = rangeQuery(right, mid+1, end, l, r);
		if(p1==null){
			return p2;
		}
		if(p2==null){
			return p1;
		}
		return merge(p1,p2);
	}
	private static void pointUpdate(int node, int start, int end, int p, int val){
		if(start>p || end<p){
			return;
		}
		if(start==end){
			if(start==p){
				st[node] = new STNode();
				if(val==0){
					st[node].zero = 1;
				}else if(val>0){
					st[node].pos = 1;
				}else{
					st[node].neg = 1;
				}
				baseArray[start]=val;
			}
			return;
		}
		int mid = (start+end)>>1, left = node<<1, right = left+1;
		pointUpdate(left, start, mid, p, val);
		pointUpdate(right, mid+1, end, p, val);
		st[node] = merge(st[left], st[right]);
	}
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	String str;
	while((str=br.readLine())!=null){
		String[] nm = str.split(" ");
		int n = Integer.parseInt(nm[0]), m = Integer.parseInt(nm[1]);
		baseArray = new int[n];
		st = new STNode[n<<2];
		String[] nVal = br.readLine().split(" ");
		for(int i=0; i<n; ++i){
			baseArray[i] = Integer.parseInt(nVal[i]);
		}
		buildST(1, 0, n-1);
		for(int i=0; i<m; ++i){
			String[] q = br.readLine().split(" ");
			if(q[0].charAt(0)=='C'){
				int idx = Integer.parseInt(q[1])-1;
				int val = Integer.parseInt(q[2]);
				pointUpdate(1, 0, n-1, idx, val);
			}else{
				int l = Integer.parseInt(q[1])-1, r = Integer.parseInt(q[2])-1;
				pw.print(getResult(rangeQuery(1, 0, n-1, l, r)));
			}
		}
		pw.println();
	}

	br.close();
	pw.flush();
	pw.close();
}
}
