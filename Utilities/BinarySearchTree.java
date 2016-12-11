import java.util.Scanner;

public class BinarySearchTree<E extends Comparable<E>> {
	private int size;
	private Node root;

	public BinarySearchTree(E value) {
		root = new Node(value);
		root.parent = root;
		this.size = 1;
	}

	public BinarySearchTree() {
		root = new Node();
		root.parent = root;
		this.size = 0;
	}

	/**
	 * Adds an element of type <E>
	 * 
	 * @param value
	 *            key to be added
	 */
	public void add(E value) {
		if (root.value == null) {
			root.value = value;
		} else {
			Node temp = this.root;
			while (temp != null) {
				if (value.compareTo(temp.value) >= 0) {
					if (temp.right != null) {
						temp = temp.right;
					} else {
						temp.right = new Node(value);
						temp.right.parent = temp;
						break;
					}
				} else {
					if (temp.left != null) {
						temp = temp.left;
					} else {
						temp.left = new Node(value);
						temp.left.parent = temp;
						break;
					}
				}
			}
		}
		this.size++;
	}

	public E findSuccessor(E value) {
		Node temp = search(value);
		if (temp == null) {
			return null;
		}
		if (temp.right != null) {
			// has right child
			return findMin(temp.right).value;
		} else {
			Node parent = temp.parent;
			while (parent != root) {
				if (parent.value.compareTo(value) >= 0) {
					return parent.value;
				}
				parent = parent.parent;
			}
			if (parent.value != null && parent.value.compareTo(value) >= 0) {
				return parent.value;
			}
			return null;
		}
	}

	public E findPredecessor(E value) {
		Node temp = search(value);
		if (temp == null) {
			return null;
		}
		if (temp.left != null) {
			// has left child
			return findMax(temp.left).value;
		} else {
			Node parent = temp.parent;
			while (parent != root) {
				if (parent.value.compareTo(value) <= 0) {
					return parent.value;
				}
				parent = parent.parent;
			}
			if (parent.value != null && parent.value.compareTo(value) <= 0) {
				return parent.value;
			}
			return null;
		}
	}

	public boolean contains(E value) {
		return search(value) != null;

	}

	private Node search(E value) {
		if (root.value == null) {
			return null;
		}
		Node temp = this.root;
		while (temp != null) {
			if (value.compareTo(temp.value) > 0) {
				temp = temp.right;
			} else if (value.compareTo(temp.value) < 0) {
				temp = temp.left;
			} else {
				return temp;
			}
		}
		return null;
	}

	/**
	 * Deletes the first occurrence of the given value
	 * 
	 * @param value
	 *            value to be deleted
	 * @return true if deletion is successful and false if deletion wasn't
	 *         successful
	 */
	public boolean delete(E value) {
		Node toDelete = search(value);
		if (toDelete == null) {
			return false;
		} else {
			delete(toDelete);
			this.size--;
			return true;
		}
	}

	/**
	 * Deletes the given node from the tree
	 * 
	 * @param value
	 * @return
	 */
	private void delete(Node node) {
		if (node == null) {
			return;
		}

		if (node == this.root) {
			if(this.root.left==null && this.root.right==null){
				this.root.value = null;
			}else if(this.root.left==null){
				this.root = this.root.right;
			}else if(this.root.right==null){
				this.root = this.root.left;
			}else{
				Node replacement = findMin(this.root.right);
				this.root.value = replacement.value;
				delete(replacement);
			}
			return;
		}

		if (node.left == null && node.right == null) {
			// leaf case;
			if (node == node.parent.right) {
				node.parent.right = null;
			} else {
				node.parent.left = null;
			}
		} else if (node.left == null) {
			// node has a single right child
			if (node == node.parent.right) {
				node.parent.right = node.right;
			} else {
				node.parent.left = node.right;
			}
			node.right.parent = node.parent;
		} else if (node.right == null) {
			// node has a single left child
			if (node == node.parent.right) {
				node.parent.right = node.left;
			} else {
				node.parent.left = node.left;
			}
			node.left.parent = node.parent;
		} else {
			// node has two children case
			Node replacement = findMin(node.right);
			node.value = replacement.value;
			delete(replacement);
		}
	}

	public E findMin() {
		return findMin(this.root).value;
	}

	private Node findMin(Node node) {
		Node temp = node;
		while (temp.left != null) {
			temp = temp.left;
		}
		return temp;
	}

	public E findMax() {
		return findMax(this.root).value;
	}

	private Node findMax(Node node) {
		Node temp = node;
		while (temp.right != null) {
			temp = temp.right;
		}
		return temp;
	}

	public Node getRoot() {
		return this.root;
	}

	public int size() {
		return this.size;
	}

	public void inOrderTraversal() {
		if (this.size == 0) {
			System.out.println("Nothing to print");
			return;
		}
		if (this.size == 1) {
			System.out.println(this.root.value);
			return;
		}
		inOrder(this.root);
		System.out.println();
	}

	public void postOrderTraversal() {
		if (this.size == 0) {
			System.out.println("Nothing to print");
			return;
		}
		if (this.size == 1) {
			System.out.println(this.root.value);
			return;
		}
		postOrder(this.root);
		System.out.println();
	}

	public void preOrderTraversal() {
		if (this.size == 0) {
			System.out.println("Nothing to print");
			return;
		}
		if (this.size == 1) {
			System.out.println(this.root.value);
			return;
		}
		preOrder(this.root);
		System.out.println();
	}

	private void inOrder(Node node) {
		if (node != null) {
			if (node.left != null) {
				inOrder(node.left);
			}
			System.out.print(node.value + " ");
			if (node.right != null) {
				inOrder(node.right);
			}

		}
	}

	private void postOrder(Node node) {
		if (node != null) {
			if (node.left != null) {
				inOrder(node.left);
			}
			if (node.right != null) {
				inOrder(node.right);
			}
			System.out.print(node.value + " ");
		}
	}

	private void preOrder(Node node) {
		if (node != null) {
			System.out.print(node.value + " ");
			if (node.left != null) {
				inOrder(node.left);
			}
			if (node.right != null) {
				inOrder(node.right);
			}

		}
	}

	class Node {
		Node left;
		Node right;
		Node parent;
		E value;

		public Node() {
		}

		public Node(E value) {
			this.value = value;
		}
	}
	private static final int QUIT = 11;
	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		Scanner in = new Scanner(System.in);
		int choice;
		do{
			System.out.println("1. Add");
			System.out.println("2. Delete");
			System.out.println("3. Contains");
			System.out.println("4. FindMax");
			System.out.println("5. FindMin");
			System.out.println("6. Successor");
			System.out.println("7. Predecessor");
			System.out.println("8. In-Order Traversal");
			System.out.println("9. Pre-Order Traversal");
			System.out.println("10. Post-Order Traversal");
			System.out.println("11. Quit");
			
			choice = in.nextInt();
			
			switch(choice){
			case 1:
				System.out.println("Enter element to add");
				bst.add(in.nextInt());
				break;
			case 2:
				System.out.println("Enter element to delete");
				bst.delete(in.nextInt());
				break;
			case 3:
				System.out.println("Enter element to check");
				System.out.println(bst.contains(in.nextInt()));
				break;
			case 4:
				System.out.println(bst.findMax());
				break;
			case 5:
				System.out.println(bst.findMin());
				break;
			case 6:
				System.out.println("Enter element to find successor");
				System.out.println(bst.findSuccessor(in.nextInt()));
				break;
			case 7:
				System.out.println("Enter element to find predecessor");
				System.out.println(bst.findPredecessor(in.nextInt()));
				break;
			case 8:
				bst.inOrderTraversal();
				break;
			case 9:
				bst.preOrderTraversal();
				break;
			case 10:
				bst.postOrderTraversal();
				break;
			case 11:
				break;
			default:
				System.out.println("Please enter a valid option");
			}
		}while(choice!=QUIT);
		
		in.close();
	}
}
