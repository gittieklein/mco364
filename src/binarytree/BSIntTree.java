package binarytree;

import java.util.ArrayList;

public class BSIntTree 
{
	private BSIntNode root;
	
	/**
	 * Constructor
	 * the user passes in a value which becomes the value of the root node
	 * @param value the value for the root of the tree
	 */	
	public BSIntTree(int value)
	{
		root = new BSIntNode(value);
	}
	
	/**
	 * Constructor (no-args)
	 * the user just creates a new tree and it sets the root to null
	 * in this case, the first value added will be the root of the tree
	 */
	public BSIntTree()
	{
		root = null;
	}
	
	/**
	 * Inserts a value into the tree
	 * if the root is null it will set the root to the value the user passes in
	 * if the root is not null you will go through the tree until you find the place where the value goes
	 * duplicate values are ignored - false is returned
	 * @param val
	 * @return if it was inserted or not
	 */
	public boolean insert(int val)
	{
		if(root == null)
		{
			root = new BSIntNode(val);
		}
		else
		{
			BSIntNode parentNode = null;
			BSIntNode childNode = root;
			while(childNode != null)
			{
				if(val > childNode.value)
				{
					parentNode = childNode;
					childNode = childNode.right;
				}
				else if(val < childNode.value)
				{
					parentNode = childNode;
					childNode = childNode.left;
				}
				else
				{
					return false;
				}
			}
			if(val > parentNode.value)
			{
				parentNode.right = new BSIntNode(val, parentNode);
			}
			else
			{
				parentNode.left = new BSIntNode(val, parentNode);
			}
		}
		return true;
    }

    /**
     * Search the tree for a specific value - remove will use to get the node based on the value passed in
     * private and can only be accessed from within the class
     * @param val the value you want to search for
     * @return the node that contains the value (you will also have the parent and children)
     * or null if the value isn't in the tree
     */
    private BSIntNode searchInternal(int val)
    {
		BSIntNode node = root;
		while(node != null)
		{
			if(val == node.value)
			{
				return node;
			}
			else
			{
				if(val > node.value)
				{
					node = node.right;
				}
				else
				{
					node = node.left;
				}
			}
		}
		return null;
    }

    /**
     * Search the tree for a specific value - public to user and returns boolean
     * @param val the value you want to search for
     * @return true or false
     */
    public boolean search(int val)
    {
    	BSIntNode node = root;
		while(node != null)
		{
			if(val == node.value)
			{
				return true;
			}
			else
			{
				if(val > node.value)
				{
					node = node.right;
				}
				else
				{
					node = node.left;
				}
			}
		}
		return false;
    }
    
    /**
     * Removes a node that the user passes in based on value - public to the user
     * takes the value the user passed in and gets the node with that value
     * and calls the private method to remove the node
     * @param val the value the user wants removed
     * @return true if removed false if not in tree
     */
    public boolean remove(int value)
    {
    	BSIntNode node = searchInternal(value);
    	if(node == null)
    		return false;
    	
    	return removeInternal(node);
    }
    
    /**
     * Removes a node that the user passes in - this is private and will be called by public method that passes in value
     * @param val the value the user wants removed
     * @return true if removed false if not in tree
     */
    private boolean removeInternal(BSIntNode valRemove)
    {
    	if(valRemove == null)
		{
			return false;
		}
		//if the node is a leaf set it to null
		else if(valRemove.left == null && valRemove.right == null)
		{
			//first check if the leaf is the root - the only node if so, reset the root to null
			if(valRemove.equals(root))
			{
				root = null;
				return true;
			}
			//make sure the left child isn't null before you check if this node is a left child
			if(valRemove.parent.left != null &&
					valRemove.parent.left.equals(valRemove))
			{
				valRemove.parent.left = null;
			}
			else
			{
				valRemove.parent.right = null;
			}
			return true;
		}
		//if a node has 1 child, it's child takes its place
    	//but first check for a node with one child where the parent is null - the root with one child
    	//in the roots case you can't set the parent to the grandchild because there is no parent so
    	//instead you just reset the node to the next value in the tree
		else if((valRemove.left == null || valRemove.right == null) && valRemove.parent == null)
		{
			//find the child that it has and set it to the new root and set its parent to null because now it
			//doesn't have a parent
			if(valRemove.left == null)
			{
				root = valRemove.right;
				root.parent = null;
				return true;
			}
			else
			{
				root = valRemove.left;
				root.parent = null;
				return true;
			}
		}
		else if(valRemove.left == null || valRemove.right == null)
		{
			BSIntNode child = null;
			//set the child node to the child that the node has (it only has one child)
			if(valRemove.left != null)
			{
				child = valRemove.left;
			}
			else
			{
				child = valRemove.right;
			}
			//check which child the node is to the parent and set the grandchild to that child
			if(valRemove.parent.right != null &&
					valRemove.equals(valRemove.parent.right))
			{
				valRemove.parent.right = child;
				child.parent = valRemove.parent;
				return true;
			}
			else
			{
				valRemove.parent.left = child;
				child.parent = valRemove.parent;
				return true;
			}
		}
		//the last option is if the node we want to remove has 2 children
    	//this will also be for the root
		//take the smallest member of the right sub tree and recursively remove that member 
		else
		{
			BSIntNode current = valRemove.right;	//the current node is the right child of the node to remove
			while(current.left != null)
			{
				current = current.left;
			}
			valRemove.value = current.value;	//instead of resetting the children and parent of the current node
													//replace the value of the node you want to remove
			removeInternal(current);		//now you want to remove the node that you duplicated
											//call the internal remove method (that accepts a node) to do that
			return true;
		}
    }

    /**
     * loop through to get the value of the left most child which is the min value
     * @return the min value
     */
    public int minNode()
    {
    	BSIntNode node = root;
    	while(node.left != null)
    	{
    		node = node.left;
    	}
    	return node.value;
    }

    /**
     * loop through to get the value right most child which is the max value
     * @return the max value
     */
    public int maxNode()
    {
    	BSIntNode node = root;
    	while(node.right != null)
    	{
    		node = node.right;
    	}
    	return node.value;
    }
    
    /**
     * In order traversal of the tree
     * @return array list with the values in order
     */
    public ArrayList<Integer> traverseInOrder()
	{
		//temporary array list that will be returned with all the values of the tree
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		//check if the tree is empty before traversing - if it is return an empty array list
		if(root == null)
		{
			return temp;
		}
		//keep getting the left child of the left child until there are no more and then take the node 
		//and then the right child and then keep going up...
		traverseInOrder(root.left, temp);
		temp.add(root.value);
		traverseInOrder(root.right, temp);
		return temp;
	}
	
    /**
     * private method that is called from within the class 
     * @param root
     * @param temp
     */
	private void traverseInOrder(BSIntNode root, ArrayList<Integer> temp)
	{
		if(root == null) return;
		traverseInOrder(root.left, temp);
		temp.add(root.value);
		traverseInOrder(root.right, temp);
	}
	
	
	/**
	 * a private class that has the nodes for the binary tree
	 * this way there is no outside access and the tree has access to all the fields of the nodes
	 * there is no need for getters and setters because there is direct access
	 * @author Gittie Klein
	 *
	 */
	private class BSIntNode
	{
	    private int value;          // datum of the node
	    private BSIntNode parent;   // perhaps empty connection to the parent node
	    private BSIntNode left;     // left child
	    private BSIntNode right;     // right child;

	    /**
	     * Constructor
	     * It sets the values not received to null
	     * @param val receives the value of the node
	     */
	    private BSIntNode(int val)
	    {
	        value = val;
	        //you set the parent to null so you could go backwards and know when you hit the root of the tree
	        parent = null;
	        //this is needed for searching and adding so you know when to stop - when the child is null  
	        left = null;	
	        right = null;
	    }
	    
	    /**
	     * Constructor
	     * It sets the values not received to null
	     * @param val receives the value of the node
	     * @param prnt receives the parent of the node
	     */
	    private BSIntNode(int val, BSIntNode prnt)
	    {
	        value = val;
	        parent = prnt;
	        //this is needed for searching and adding so you know when to stop - when the child is null  
	        left = null;	
	        right = null;
	    }
		
		/**
		 * Equals method
		 * use this for comparing nodes when removing
		 * nodes are equal if their value, parents and children are the same
		 * nodes are not equal if one has a child/parent that is null and the other has a value
		 * that is why each node first tests if one is null and one isn't
		 * @param node
		 * @return true or false
		 */
		private boolean equals(BSIntNode node)
		{
			if(value != node.value)
			{
				return false;
			}
			if((right == null && node.right != null) || (right != null && node.right == null))
			{
				return false;
			}
			if(right != null && node.right != null)
			{
				if(right.value != node.right.value)
				{
					return false;
				}
			}
			if((left == null && node.left != null) || (left != null && node.left == null))
			{
				return false;
			}
			if(left != null && node.left != null)
			{
				if(left.value != node.left.value)
				{
					return false;
				}
			}
			if((parent == null && node.parent != null) || (parent != null && node.parent == null))
			{
				return false;
			}
			if(parent != null && node.parent != null)
			{
				if(parent.value != node.parent.value)
				{
					return false;
				}
			}

			return true;
		}
	}
}
