import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Stack;

public class UVA10226 {
public static void main(String[] args) throws Exception {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader br = new BufferedReader(new FileReader("testip.txt"));
	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));
	int testcases = Integer.parseInt(br.readLine());
	br.readLine();
	while (testcases-- > 0) {
		AVLTreeBasic<String> population = new AVLTreeBasic<String>();
		String species;
		while((species=br.readLine())!=null && species.length()>0){
			population.insert(species);
		}
		int size = population.size();
		Iterator<Object[]> iter = population.inOrderIterator();
		while(iter.hasNext()){
			Object[] nextObj = iter.next();
			String nextSpecies = (String)nextObj[0];
			Integer nextSpeciesCount = (Integer)nextObj[1];
			
			pw.printf("%s %.4f\n", nextSpecies, (nextSpeciesCount*100d)/size);
		}
		if(testcases!=0){
			pw.println();
		}
	}

	br.close();
	pw.flush();
	pw.close();
}

static class AVLTreeBasic<E extends Comparable<E>>{
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
			this.count = 1;
		}
	}
	
	class InOrderIterator implements Iterator<Object[]>{
		Stack<AVLNode> sortedList;
		public InOrderIterator(){
			sortedList = new Stack<AVLNode>();
			AVLNode temp = root;
			while(temp!=null){
				sortedList.push(temp);
				temp = temp.left;
			}
		}
		@Override
		public boolean hasNext() {
			return !sortedList.isEmpty();
		}

		@Override
		public Object[] next() {
			AVLNode currentNode = sortedList.pop(), returnVal = currentNode;
			if(currentNode.right!=null){
				currentNode = currentNode.right;
				while(currentNode!=null){
					sortedList.push(currentNode);
					currentNode = currentNode.left;
				}
			}
			return new Object[]{returnVal.value, returnVal.count};
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
		return triggerRotation(node);
	}
	
	private AVLNode triggerRotation(AVLNode node){
		int leftTreeHeight = getHeight(node.left);
		int rightTreeHeight = getHeight(node.right);
		if(abs(leftTreeHeight-rightTreeHeight)>1){
			return rotate(node, leftTreeHeight, rightTreeHeight);
		}
		updateHeight(node);
		return node;
	}
	
	public E delete(E val){
		int oldSize = this.size;
		this.root = delete(this.root, val);
		if(this.size<oldSize){
			return val;
		}else{
			return null;
		}
	}
	
	private AVLNode delete(AVLNode node, E val){
		if(node==null){
			return node;
		}
		int compare = val.compareTo(node.value);
		if(compare>0){
			node.right = delete(node.right, val);
		}else if(compare<0){
			node.left = delete(node.left, val);
		}else{
			this.size--;
			if(node.count>1){
				node.count--;
				return node;
			}
			if(node.left==null && node.right==null){
				return null;
			}else if(node.left==null){
				return node.right;
			}else if(node.right==null){
				return node.left;
			}else{
				node.right = replaceAndRemoveRightMin(node, node.right);
			}
		}
		return triggerRotation(node);
	}
	
	private AVLNode replaceAndRemoveRightMin(AVLNode node, AVLNode rightMin){
		if(rightMin.left==null){
			node.value = rightMin.value;
			node.count = rightMin.count;
			return rightMin.right;
		}
		rightMin.left = replaceAndRemoveRightMin(node, rightMin.left);
		return triggerRotation(rightMin);
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
	
	public Iterator<Object[]> inOrderIterator(){
		return new InOrderIterator();
	}
}

}