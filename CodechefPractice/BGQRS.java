import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class BGQRS {
	private static int totalchars = 0, offset = 0;
	private static InputStream stream;
	private static byte[] buffer = new byte[1024];
	private static final int TWOS=0, FIVES=1;
	private static int[][] cumulatives;
	private static final int MAXIDX = (int)1e5;
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
	
	private static void initCumulatives(){
		cumulatives = new int[2][MAXIDX+1];
		
		Arrays.fill(cumulatives[TWOS], 0);
		Arrays.fill(cumulatives[FIVES], 0);
		
		for(int i=2, iLen = cumulatives[TWOS].length; i<iLen; i<<=1){
			for(int j=i, jLen = cumulatives[TWOS].length; j<jLen; j+=i){
				cumulatives[TWOS][j]++;
			}
		}
		
		for(int i=5, iLen = cumulatives[FIVES].length; i<iLen; i*=5){
			for(int j=i, jLen = cumulatives[FIVES].length; j<jLen; j+=i){
				cumulatives[FIVES][j]++;
			}
		}
		
		for(int i=1; i<=MAXIDX; ++i){
			cumulatives[TWOS][i] += cumulatives[TWOS][i-1];
			cumulatives[FIVES][i] += cumulatives[FIVES][i-1];
		}
		
	}
	
	static class TreeNode{
		int twos;
		int fives;
		public TreeNode(){
			twos = 0;
			fives = 0;
		}
		public TreeNode(int twos, int fives){
			this.twos = twos;
			this.fives = fives;
		}
		public TreeNode(TreeNode node1, TreeNode node2){
			this.twos = node1.twos + node2.twos;
			this.fives = node1.fives + node2.fives;
		}
	}
	
	static class LazyNode{
		int l;
		int r;
		int qType;
		int twos;
		int fives;
		boolean pendingUpdate;
		
		public LazyNode(){
			reinit();
		}
		
		public void reinit(){
			l = r = qType = -1;
			twos = fives = 0;
			pendingUpdate = false;
		}
	}
	
	private static int[] array;
	private static TreeNode[] treeNode;
	private static LazyNode[] lazyNode;
	private static void build(int node, int start, int end){
		if(start==end){
			treeNode[node] = new TreeNode(countTwos(array[start]), countFives(array[start]));
			lazyNode[node] = new LazyNode();
		}else{
			int mid = start + (end-start)/2;
			int leftChild = node<<1, rightChild = leftChild+1;
			build(leftChild, start, mid);
			build(rightChild,mid+1, end);
			treeNode[node] = new TreeNode(treeNode[leftChild], treeNode[rightChild]);
			lazyNode[node] = new LazyNode();
		}
	}
	
	private static void updateRange(int node, int start, int end, int l, int r, int twos, int fives, int qType){
		//check and update lazy node status
		if(lazyNode[node].pendingUpdate){
			finishPendingUpdate(node, start, end);
			lazyNode[node].reinit(); //mark as updated
					
		}
		
		if(start>end || start>r || end<l){ //completely out of bounds
			return;
		}
		int leftChild = node<<1, rightChild = leftChild+1;
		
		
		
		
		//completely inbound update
		if(start>=l && end<=r){
			completelyInboundUpdate(node, start, end, l, r, twos, fives, qType);
			return;
		}
		
		//partially inside and partially outside case
		int mid = start + (end-start)/2;
		updateRange(leftChild, start, mid, l, r, twos, fives, qType);
		updateRange(rightChild, mid+1, end, l, r, twos, fives, qType);
		
		//update current node after updating children
		treeNode[node].twos = treeNode[leftChild].twos + treeNode[rightChild].twos;
		treeNode[node].fives = treeNode[leftChild].fives + treeNode[rightChild].fives;
	}
	private static void completelyInboundUpdate(int node, int start, int end, int l, int r, int twos, int fives, int qType){
		//completely inbound case
		int leftChild = node<<1, rightChild = leftChild+1;
		if(qType==1){
			//query type 1
			int range = end-start+1;
			
			//updating current tree node
			treeNode[node].twos = treeNode[node].twos + range*twos;
			treeNode[node].fives = treeNode[node].fives + range*fives;
			
			//lazy update pushed to children if it contains any
			if(start!=end){ //has leaf node case
				//pushing update to children
				if(lazyNode[leftChild].qType==-1){
					lazyNode[leftChild].qType=1;
				}
				if(lazyNode[rightChild].qType==-1){
					lazyNode[rightChild].qType=1;
				}
				
				lazyNode[leftChild].twos += twos;
				lazyNode[leftChild].fives += fives;
				
				lazyNode[rightChild].twos += twos;
				lazyNode[rightChild].fives += fives;
				
				lazyNode[leftChild].pendingUpdate = true;
				lazyNode[rightChild].pendingUpdate = true;
			}
			
		}else{
			//query type 2
			int range = end-start+1;
			int addedTwos = cumulatives[TWOS][end-l+1]-cumulatives[TWOS][start-l];
			int addedFives = cumulatives[FIVES][end-l+1]-cumulatives[FIVES][start-l];
			int actualTwos = twos*range;
			int actualFives = fives*range;

			//uppdating current tree node
			treeNode[node].twos = actualTwos + addedTwos;
			treeNode[node].fives = actualFives + addedFives;
			
			//lazy update pushed to children if it contains any
			if(start!=end){ //has leaf node case
				//pushing update to children
				
				//updating left child
				lazyNode[leftChild].l = l;
				lazyNode[leftChild].r = r;
				lazyNode[leftChild].twos = twos;
				lazyNode[leftChild].fives = fives;
				lazyNode[leftChild].qType = 2;
				
				//updating right child
				lazyNode[rightChild].l = l;
				lazyNode[rightChild].r = r;
				lazyNode[rightChild].twos = twos;
				lazyNode[rightChild].fives = fives;
				lazyNode[rightChild].qType = 2;
				
				lazyNode[leftChild].pendingUpdate = true;
				lazyNode[rightChild].pendingUpdate = true;
			}
			
		}
	}
	private static void finishPendingUpdate(int node, int start, int end){
		int leftChild = node<<1, rightChild = leftChild+1;
		//pending update case
		if(lazyNode[node].qType==1){
			int range = end-start+1;
			treeNode[node].twos = treeNode[node].twos + lazyNode[node].twos*range;
			treeNode[node].fives = treeNode[node].fives + lazyNode[node].fives*range;
			if(start!=end){ //not a leaf node
				//regardless of what kind of query the child nodes have pending, i can just add the twos and fives to them
				if(lazyNode[leftChild].qType==-1){
					lazyNode[leftChild].qType = 1;
				}
				if(lazyNode[rightChild].qType==-1){
					lazyNode[rightChild].qType = 1;
				}
				lazyNode[leftChild].twos = lazyNode[leftChild].twos + lazyNode[node].twos;
				lazyNode[leftChild].fives = lazyNode[leftChild].fives + lazyNode[node].fives;
				
				lazyNode[rightChild].twos = lazyNode[rightChild].twos + lazyNode[node].twos;
				lazyNode[rightChild].fives = lazyNode[rightChild].fives + lazyNode[node].fives;
				
				lazyNode[leftChild].pendingUpdate = true;
				lazyNode[rightChild].pendingUpdate = true;
				
			}
		}else{
			int range = end-start+1;
			int addedTwos = cumulatives[TWOS][end-lazyNode[node].l+1]-cumulatives[TWOS][start-lazyNode[node].l];
			int addedFives = cumulatives[FIVES][end-lazyNode[node].l+1]-cumulatives[FIVES][start-lazyNode[node].l];
			int actualTwos = lazyNode[node].twos*range;
			int actualFives = lazyNode[node].fives*range;
			treeNode[node].twos = actualTwos + addedTwos;
			treeNode[node].fives = actualFives + addedFives;
	
			if(start!=end){ //not a leaf node
				
				//regardless of what kind of query the child nodes have pending, i must replace their values
				lazyNode[leftChild].qType=2;
				lazyNode[rightChild].qType=2;
				
				lazyNode[leftChild].pendingUpdate = true;
				lazyNode[rightChild].pendingUpdate = true;
				
				lazyNode[leftChild].l = lazyNode[rightChild].l = lazyNode[node].l;
				lazyNode[leftChild].r = lazyNode[rightChild].r = lazyNode[node].r;
				lazyNode[leftChild].twos = lazyNode[rightChild].twos = lazyNode[node].twos;
				lazyNode[leftChild].fives = lazyNode[rightChild].fives = lazyNode[node].fives;
				
			}
		}
		
	}
	
	private static TreeNode queryRange(int node, int start, int end, int l, int r){
		if(start>end || start>r || end<l){
			return new TreeNode();
		}
		int leftChild = node<<1, rightChild = leftChild+1;
		
		if(lazyNode[node].pendingUpdate){
			finishPendingUpdate(node, start, end);
			lazyNode[node].reinit(); //mark as updated
		}
		
		if(start>=l && end<=r){
			return treeNode[node];
		}
		
		int mid = start + (end-start)/2;
		TreeNode p1 = queryRange(leftChild, start, mid, l, r);
		TreeNode p2 = queryRange(rightChild, mid+1, end, l, r);
		
		return new TreeNode(p1, p2);
	}
	
	private static int countTwos(int n){
		int count = 0;
		while(n%2==0){
			n>>=1;
			count++;
		}
		return count;
	}
	private static int countFives(int n){
		int count = 0;
		while(n%5==0){
			n/=5;
			count++;
		}
		return count;
	}
	public static void main(String[] args) throws Exception{
		stream=System.in;
//		stream = new FileInputStream("bgqrsip.txt");
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
//		PrintWriter pw = new PrintWriter(new FileWriter("bgqrsop.txt"));
		initCumulatives();
		int testcases = readInt();
		while(testcases-->0){
			int n = readInt(), m = readInt();
			array = new int[n];
			treeNode = new TreeNode[4*n];
			lazyNode = new LazyNode[4*n];
			long finalResult = 0;
			for(int i=0; i<n; ++i){
				array[i] = readInt();
			}
			
			build(1,0,n-1);
			
			for(int i=0; i<m; ++i){
				int qType = readInt();
				int l = readInt()-1;
				int r = readInt()-1;
				
				switch(qType){
				case 1:
				case 2:
					int x = readInt();
					updateRange(1, 0, n-1, l, r, countTwos(x), countFives(x), qType);
					break;
				case 3:
					TreeNode currentResult = queryRange(1, 0, n-1, l, r);
					finalResult += min(currentResult.fives, currentResult.twos);
					break;
				}
			}
			
			pw.println(finalResult);
			
		}
		pw.flush();
		pw.close();
//		System.out.println("BGQRS Complete");
	}
	
	private static int min(int a, int b){
		return a<b?a:b;
	}
}
