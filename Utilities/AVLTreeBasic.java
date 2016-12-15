public class AVLTreeBasic<E extends Comparable<E>> {
	class AVLNode{
		E value;
		AVLNode left;
		AVLNode right;
		int count = 0;
		int height;
		public AVLNode(E value){
			this.value = value;
			this.left = null;
			this.right = null;
			this.height = 0;
			this.count = 0;
		}
	}
	
	AVLNode root;
	int size;
	public AVLTreeBasic(){
		this.root = null;
		this.size = 0;
	}
	
	public int size(){
		return this.size;
	}
	
	public boolean isEmpty(){
		return this.size==0;
	}
	
	public void insert(E val){
		this.root = insert(this.root, val);
		this.size++;
	}
	
	private AVLNode insert(AVLNode node, E val){
		
		if(node==null){
			return new AVLNode(val);
		}
		int compare = val.compareTo(node.value);
		if(compare>0){
			node.right = insert(node.right, val);
		}else if(compare<0){
			node.left = insert(node.left, val);
		}else{
			node.count++;
		}
		
		int leftTreeHeight = getHeight(node.left);
		int rightTreeHeight = getHeight(node.right);
		if(abs(leftTreeHeight-rightTreeHeight)>1){
			return rotate(node, leftTreeHeight, rightTreeHeight);
		}
		updateHeight(node);
		return node;
	}
	
	public boolean contains(E val){
		AVLNode temp = this.root;
		while(temp!=null){
			int compare = val.compareTo(temp.value);
			if(compare>0){
				temp = temp.right;
			}else if(compare<0){
				temp = temp.left;
			}else{
				return true;
			}
		}
		return false;
	}
	
	private AVLNode rotate(AVLNode node, int leftHeight, int rightHeight){
		if(leftHeight>rightHeight){
			AVLNode child = node.left;
			int childLeftHeight = getHeight(child.left);
			int childRightHeight = getHeight(child.right);
			if(childLeftHeight>childRightHeight){
				return ll(node);
			}else{
				return lr(node);
			}
		}else{
			AVLNode child = node.right;
			int childLeftHeight = getHeight(child.left);
			int childRightHeight = getHeight(child.right);
			if(childRightHeight>childLeftHeight){
				return rr(node);
			}else{
				return rl(node);
			}
		}
	}
	
	private AVLNode ll(AVLNode node){
		AVLNode s1 = node.left;
		node.left = s1.right;
		s1.right = node;
		updateHeight(node);
		updateHeight(s1);
		return s1;
	}
	
	private AVLNode rr(AVLNode node){
		AVLNode s1 = node.right;
		node.right = s1.left;
		s1.left = node;
		updateHeight(node);
		updateHeight(s1);
		return s1;
	}
	
	private AVLNode lr(AVLNode node){
		node.left = rr(node.left);
		return ll(node);
	}
	
	private AVLNode rl(AVLNode node){
		node.right = ll(node.right);
		return rr(node);
	}
	
	private int abs(int a){
		return a<0?-a:a;
	}
	private int getHeight(AVLNode node){
		return node==null?-1:node.height;
	}
	
	private void updateHeight(AVLNode node){
		node.height = max(getHeight(node.left), getHeight(node.right))+1;
	}
	
	private int max(int a, int b){
		return a>b?a:b;
	}
	
	public int getHeight(){
		return this.root.height;
	}
	
	private static boolean DEBUG = true;
	
	public static void main(String[] args) throws Exception{
		System.out.println("Started...");
		int MAX = Integer.MAX_VALUE;
		int N = (int) 2e6;
		AVLTreeBasic<Integer> avl;
		long start = System.currentTimeMillis();
		avl = new AVLTreeBasic<Integer>();
		for (int i = 0; i < N; ++i) {
//			avl.insert((int) (Math.random() * MAX));
			avl.insert(i);
		}
		long end = System.currentTimeMillis();
		if (DEBUG) {
			System.out.println("The height of the tree with " + avl.size() + " nodes is " + avl.getHeight());
			System.out.println("Total Time to Insert " + avl.size() + " values is " + ((end - start) / 1000d) + " seconds");
		}
	}

}
