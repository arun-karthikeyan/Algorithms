package treesandgraphs;

public class BinaryTree<T> {
	TreeNode<T> root;
	
	public BinaryTree(){
		this.root = null;
	}
	
	public BinaryTree(T rootData){
		this.root = new TreeNode<T>(rootData);
	}
	
	class TreeNode<E>{
		TreeNode<E> left;
		TreeNode<E> right;
		E data;
		
		public TreeNode(){
			this.left = null;
			this.right = null;
			this.data = null;
		}
		
		public TreeNode(E data){
			this.data = data;
			this.left = null;
			this.right = null;
		}
	}
	
	public void inOrder(){
		inOrderRecursion(this.root);
		System.out.println("END");
	}
	
	public void preOrder(){
		preOrderRecursion(this.root);
		System.out.println("END");
	}
	
	public void postOrder(){
		postOrderRecursion(this.root);
		System.out.println("END");
	}
	
	private void postOrderRecursion(TreeNode<T> node){
		if(node==null){
			return;
		}
		
		if(node.left!=null){
			postOrderRecursion(node.left);
		}
		
		if(node.right!=null){
			postOrderRecursion(node.right);
		}
		
		System.out.print(node.data+" -> ");
	}
	private void preOrderRecursion(TreeNode<T> node){
		if(node==null){
			return;
		}
		
		System.out.print(node.data+" -> ");
		
		if(node.left!=null){
			preOrderRecursion(node.left);
		}
		
		if(node.right!=null){
			preOrderRecursion(node.right);
		}
	}
	
	private void inOrderRecursion(TreeNode<T> node){
		if(node==null){
			return;
		}
		
		if(node.left!=null){
			inOrderRecursion(node.left);
		}
		
		System.out.print(node.data+" -> ");
		
		if(node.right!=null){
			inOrderRecursion(node.right);
		}
	}
	
	public static void main(String[] args) throws Exception{
		BinaryTree<Integer> bt = new BinaryTree<Integer>(8);
		BinaryTree<Integer>.TreeNode<Integer> leaf1 = bt.new TreeNode<Integer>(2);
		BinaryTree<Integer>.TreeNode<Integer> leaf2 = bt.new TreeNode<Integer>(6);
		BinaryTree<Integer>.TreeNode<Integer> leaf3 = bt.new TreeNode<Integer>(9);
		BinaryTree<Integer>.TreeNode<Integer> leaf4 = bt.new TreeNode<Integer>(20);
		BinaryTree<Integer>.TreeNode<Integer> stree1 = bt.new TreeNode<Integer>(4);
		BinaryTree<Integer>.TreeNode<Integer> stree2 = bt.new TreeNode<Integer>(10);
		
		stree1.left = leaf1;
		stree1.right = leaf2;
		stree2.left = leaf3;
		stree2.right = leaf4;
		bt.root.left = stree1;
		bt.root.right = stree2;
		
		System.out.println("In-Order traversal");
		bt.inOrder();
		
		System.out.println("\nPre-Order traversal");
		bt.preOrder();
		
		System.out.println("\nPost-Order traversal");
		bt.postOrder();
	}
	
}
