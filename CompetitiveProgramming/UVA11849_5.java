import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class UVA11849_5 {
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
//private static long TIME = -1;
public static void main(String[] args) throws Exception {
	if (args.length > 0 && "fileip".equals(args[0])) {
		stream = new FileInputStream(new File("testip.txt"));
//		TIME = System.currentTimeMillis();
	} else {
		stream = System.in;
	}

	PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

	int n,m;
	while((n=readInt())!=0 && (m=readInt())!=0){
		int count = 0;
		AVLTree<Integer> jack = new AVLTree<Integer>();
		for(int i=0; i<n; ++i){
			jack.add(readInt());
		}
		for(int i=0; i<m; ++i){
			if(jack.contains(readInt())){
				count++;
			}
		}
		pw.println(count);
	}
	pw.flush();
	pw.close();
	
}
}
class AVLTree<E extends Comparable<E>> {
	class AVLNode {
		AVLNode parent;
		AVLNode left;
		AVLNode right;
		int count;
		int height;
		E value;

		public AVLNode(E value) {
			this.value = value;
			this.parent = null;
			this.left = null;
			this.right = null;
			this.count = 1;
			this.height = 0;
		}
	}

	private AVLNode root;
	private int size;

	public AVLTree() {
		this.root = null;
		this.size = 0;
	}

	private AVLNode searchAndAdd(E value) {
		AVLNode temp = this.root;
		this.size++;
		while (temp != null) {
			int compare = value.compareTo(temp.value);
			if (compare > 0) {
				if (temp.right != null) {
					temp = temp.right;
				} else {
					temp.right = new AVLNode(value);
					temp.right.parent = temp;
					return temp;
				}
			} else if (compare < 0) {
				if (temp.left != null) {
					temp = temp.left;
				} else {
					temp.left = new AVLNode(value);
					temp.left.parent = temp;
					return temp;
				}
			} else {
				temp.count++;
				return null;
			}
		}
		return null;
	}

	private void triggerRotation(AVLNode temp) {
		while(temp!=null){
			int leftTreeHeight = getHeight(temp.left);
			int rightTreeHeight = getHeight(temp.right);
			if(Math.abs(leftTreeHeight-rightTreeHeight)>1){
				temp = rotate(temp, leftTreeHeight, rightTreeHeight);
				if(root.parent!=null){
					root = root.parent;
				}
				leftTreeHeight = getHeight(temp.left);
				rightTreeHeight = getHeight(temp.right);
			}
			temp.height = max(leftTreeHeight, rightTreeHeight) + 1;
			temp = temp.parent;
		}
	}

	public void add(E value) {
		if (this.size == 0) {
			root = new AVLNode(value);
			this.size++;
			return;
		}
		AVLNode temp = searchAndAdd(value);
		if (temp != null) {
			triggerRotation(temp);
		}
	}

	private int getHeight(AVLNode node) {
		return node == null ? -1 : node.height;
	}

	private int max(int a, int b) {
		return a > b ? a : b;
	}

	private AVLNode rotate(AVLNode node, int leftTreeHeight, int rightTreeHeight) {
		if (leftTreeHeight > rightTreeHeight) {
			AVLNode child = node.left;
			int leftSubTreeHeight = getHeight(child.left);
			int rightSubTreeHeight = getHeight(child.right);
			if (leftSubTreeHeight > rightSubTreeHeight) {
				return ll(node);
			} else {
				return lr(node);
			}
		} else {
			AVLNode child = node.right;
			int leftSubTreeHeight = getHeight(child.left);
			int rightSubTreeHeight = getHeight(child.right);
			if (rightSubTreeHeight > leftSubTreeHeight) {
				return rr(node);
			} else {
				return rl(node);
			}
		}
	}

	private AVLNode ll(AVLNode node) {
		AVLNode p = node.parent;
		AVLNode s1 = node.left;
		s1.parent = p;
		node.left = s1.right;
		if(s1.right!=null){
			s1.right.parent = node;
		}
		node.parent = s1;
		s1.right = node;
		if (p != null) {
			if (p.left == node) {
				p.left = s1;
			} else {
				p.right = s1;
			}
		}
		return node;
	}

	private AVLNode lr(AVLNode node) {
		AVLNode s1 = node.left;
		AVLNode s2 = s1.right;
		s1.right = s2.left;
		if(s2.left!=null){
			s2.left.parent = s1;
		}
		node.left = s2;
		s2.parent = node;
		s2.left = s1;
		s1.parent = s2;
		updateHeight(s1);
		updateHeight(s2);
		return ll(node);
	}

	private AVLNode rl(AVLNode node) {
		AVLNode s1 = node.right;
		AVLNode s2 = s1.left;
		s1.left = s2.right;
		if(s2.right!=null){
			s2.right.parent = s1;
		}
		s2.parent = node;
		node.right = s2;
		s2.right = s1;
		s1.parent = s2;
		updateHeight(s1);
		updateHeight(s2);
		return rr(node);
	}
	
	private void updateHeight(AVLNode node){
		node.height =  max(getHeight(node.left), getHeight(node.right)) + 1;
	}

	private AVLNode rr(AVLNode node) {
		AVLNode p = node.parent;
		AVLNode s1 = node.right;
		s1.parent = p;
		node.right = s1.left;
		if(s1.left!=null){
			s1.left.parent = node;
		}
		node.parent = s1;
		s1.left = node;
		if (p != null) {
			if (p.left == node) {
				p.left = s1;
			} else {
				p.right = s1;
			}
		}
		return node;
	}

	public boolean contains(E value) {
		return search(value) != null;
	}

	private AVLNode search(E value) {
		AVLNode temp = this.root;
		while (temp != null) {
			int compare = value.compareTo(temp.value);
			if (compare > 0) {
				temp = temp.right;
			} else if (compare < 0) {
				temp = temp.left;
			} else {
				return temp;
			}
		}
		return null;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int size() {
		return this.size;
	}

	public int getHeight() {
		return this.root.height;
	}
}

