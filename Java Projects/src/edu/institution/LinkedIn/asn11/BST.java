package edu.institution.asn11;

import java.util.*;


public class BST<E extends Comparable<E>> {

	protected TreeNode<E> root;
	protected int size = 0;
	
	public BST() { }
	
	public BST(E[] objects) {
		for (int i=0; i<objects.length; i++) {
			insert(objects[i]);
		}
	}
	
	public boolean search(E e) {
		TreeNode<E> current = root;
		
		while (current != null) {
			if (e.compareTo(current.element) < 0) {
				current = current.left;
			} else if (e.compareTo(current.element) > 0) {
				current = current.right;
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean insert(E e) {
		if (root == null) {
			root = createNewNode(e);
		} else {
			TreeNode<E> parent = null;
			TreeNode<E> current = root;
			while (current != null) {
				if (e.compareTo(current.element) < 0) {
					parent = current;
					current = current.left;
				} else if (e.compareTo(current.element) > 0) {
					parent = current;
					current = current.right;
				} else {
					return false;
				}
			}
				
			if (e.compareTo(parent.element) < 0) {
				parent.left = createNewNode(e);
			} else {
				parent.right = createNewNode(e);
			}
		}
		
		size++;
		return true;
	}
	
	protected TreeNode<E> createNewNode(E e) {
		return new TreeNode<E>(e);
	}
	
	public void inorder() {
		inorder(root);
	}
	
	protected void inorder(TreeNode<E> root) {
		if (root == null) return;
		inorder(root.left);
		System.out.print(root.element + " ");
		inorder(root.right);
	}
	
	public void postorder() {
		postorder(root);
	}
	
	protected void postorder(TreeNode<E> root) {
		if (root == null) return;
		postorder(root.left);
		postorder(root.right);
		System.out.print(root.element + " ");
	}
	
	public void preorder() {
		preorder(root);
	}
	
	protected void preorder(TreeNode<E> root) {
		if (root == null) return;
		System.out.print(root.element + " ");
		preorder(root.left);
		preorder(root.right);
	}
	
	public int getSize() {
		return size;
	}
	
	public TreeNode<E> getRoot() {
		return root;
	}
	
	public ArrayList<TreeNode<E>> path(E e) {
		ArrayList<TreeNode<E>> list = new ArrayList<>();
		TreeNode<E> current = root;
		
		while (current != null) {
			list.add(current);
			if (e.compareTo(current.element) < 0) {
				current = current.left;
			} else if (e.compareTo(current.element) > 0) {
				current = current.right;
			} else {
				break;
			}
		}
		
		return list;
	}
	
	public boolean delete(E e) {
		TreeNode<E> parent = null;
		TreeNode<E> current = root;
		
		while (current != null) {
			if (e.compareTo(current.element) < 0) {
				parent = current;
				current = current.left;
			} else if (e.compareTo(current.element)> 0) {
				parent = current;
				current = current.right;
			} else {
				break;
			}
		}
		
		if (current == null) {
			return false;
		}
		
		if (current.left == null) {
			if (parent == null) {
				root = current.right;
			} else {
				if (e.compareTo(parent.element) < 0) {
					parent.left = current.right;
				} else {
					parent.right = current.right;
				}
			}
		} else {
			TreeNode<E> parentOfRightMost = current;
			TreeNode<E> rightMost = current.left;
			
			while (rightMost.right != null) {
				parentOfRightMost = rightMost;
				rightMost = rightMost.right;
			}
			
			current.element = rightMost.element;
			
			if (parentOfRightMost.right == rightMost) {
				parentOfRightMost.right = rightMost.left;
			} else {
				parentOfRightMost.left = rightMost.left;
			}
		}
		
		size--;
		return true;
	}
	
	public Iterator<E> iterator() {
		return new InorderIterator();
	}
	
	public void clear() {
		root = null;
		size = 0;
	}
	
	
	private class InorderIterator implements Iterator<E> {
		
		private ArrayList<E> list = new ArrayList<>();
		private int current = 0;
		
		public InorderIterator() {
			inorder();
		}
		
		private void inorder() {
			inorder(root);
		}
		
		private void inorder(TreeNode<E> root) {
			if (root == null) return;
			inorder(root.left);
			list.add(root.element);
			inorder(root.right);
		}
		
		public boolean hasNext() {
			return current < list.size();
		}
		
		public E next() {
			return list.get(current++);
		}
		
		public void remove() {
			delete(list.get(current));
			list.clear();
			inorder();
		}
	}
	
	
	
	/* ASSIGNMENT 11 */
	
	//traverses the nodes using the breadth-first traversal algorithm
	//returns a list of elements in the correct order (breadth-first traversal)
	public List<E> breadthFirstTraversal() {
		
		//correctly sorted list that will be returned
		List<E> sortedList = new ArrayList<E>();
		
		//list of the next children in the tree node
		List<TreeNode<E>> children = new ArrayList<TreeNode<E>>();
		
		//add the root to the list to start with
		children.add(root);
		
		//also add the root to the sorted list
		//it should be the first element in the list
		sortedList.add(root.element);
		
		//boolean value to control the while loop
		//initialized by checking if the root has any children
		boolean moreChildren = checkForChildren(children);
		
		//MAIN LOOP
		//if it is not done sorting, keep looping
		while(moreChildren) {
			
			//call the checkForChildren method 
			//that should return the next line of children
			children = getChildren(children);
			
			//add those children to the sortedList
			for(int i = 0; i < children.size(); i++) {
				sortedList.add(children.get(i).element);
			}
			
			//check if there are more children
			//true if there are more children
			//false if everything has been covered
			moreChildren = checkForChildren(children);
			
		}
		
		//return the final sorted list
		return sortedList;
		
	}
	
	
	//returns a list of the children of the nodes that were passed in
	public List<TreeNode<E>> getChildren(List<TreeNode<E>> children){
		
		//list to hold the children of the passed in children
		List<TreeNode<E>> nextChildren = new ArrayList<TreeNode<E>>();
		
		//loop through each of the passed in tree nodes
		for(int i = 0; i < children.size(); i++) {
			
			//variables to hold the right and left children
			TreeNode<E> left = children.get(i).left;
			TreeNode<E> right = children.get(i).right;
			
			//add them to the list if they are not null
			if(left != null)
				nextChildren.add(left);
			if(right != null)
				nextChildren.add(right);
		}
		
		//return the next children to loop through
		return nextChildren;
	}
	
	
	//returns a boolean that determines if there are more nodes left in the tree
	public boolean checkForChildren(List<TreeNode<E>> children) {
		
		//loop through each of the nodes
		//if there is a child found in any of the nodes, return true
		for(int i = 0; i < children.size(); i++) {
			
			//get the right and left children
			TreeNode<E> left = children.get(i).left;
			TreeNode<E> right = children.get(i).right;
			
			//if either are not null, return true
			if(left != null || right != null)
				return true;
		}
		
		//if no children were found, return false
		return false;
		
	}
	
	
	//method that returns the number of edges between the tree's root and farthest leaf
	public int getHeight() {
		
		//first check if there are any children at all
		//if there are none, it has a height of 0
		if(root.right == null && root.left == null)
			return 0;
		
		//otherwise:
		//variable to hold the tree's height
		//this starts at one to hold the root
		int height = 1;
		
		//get the height of the left side
		int leftHeight = sideHeight(root.left);
		
		//get the height of the right side
		int rightHeight = sideHeight(root.right);
		
		//compare the sides, return the largest height
		if(leftHeight > rightHeight) //left is bigger
			return height += leftHeight;
		else if(rightHeight > leftHeight) //right is bigger
			return height += rightHeight;
		else //they are equal, so either one would work
			return height += leftHeight;
	}
	
	
	//method that returns the height of one side of the tree
	public int sideHeight(TreeNode<E> root) {
		
		//variable for the left height
		int height = 0;
		
		//list of the next children in the tree node
		List<TreeNode<E>> children = new ArrayList<TreeNode<E>>();
		
		//add the root to the list to start with
		children.add(root);
		
		//boolean to check for more children
		boolean moreChildren = checkForChildren(children);
		
		//loop through this node, add one to the height each time through the loop
		//this will only loop if there are more children
		while(moreChildren) {
					
			//call the checkForChildren method 
			//that should return the next line of children
			children = getChildren(children);
			
			//check if there are more children
			//true if there are more children
			//false if everything has been covered
			moreChildren = checkForChildren(children);
			
			//add one to the height
			height++;
		}
		
		
		//return the height of the side
		return height;
	}
	
	
	//
	public List<E> nonRecursiveInorder(){
		
		//final sorted list
		List<E> sortedList = new ArrayList<E>();
		
		//stack to temporarily hold the nodes
		Stack<TreeNode<E>> inorderStack = new Stack<TreeNode<E>>();
		
		//value for the next node to get the children of
		TreeNode<E> current = root;
		
		while(current != null || inorderStack.size() > 0) {
			
			//loop through the node and go all the way to the left on the current node
			//this makes the left most nodes appear first in the list
			while(current != null) {
				
				//push the current node onto the stack
				inorderStack.push(current);
				
				//set the current node to the next left node
				//so this loop with then work with the next left node
				//until it runs out of left nodes to work with
				current = current.left;
			}
			
			//now get the first item from the stack
			//this should be the least of all elements on the stack
			current = inorderStack.pop();
			
			//add this item to the sortedList
			//since it is the least in the stack, it should be sorted right
			sortedList.add(current.element);
			
			//check for the current node's right child
			//loop through again with it if it exists
			current = current.right;
			
		}
		
		
		//return the sorted list
		return sortedList;
	}
	
	
}





















