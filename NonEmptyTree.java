package tree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 *  
 */
 public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	/* Provide whatever instance variables you need */
	 
	protected K key;
	protected V val;
	private Tree<K, V> leftTree;
	private Tree<K, V> rightTree;

	/**
	 * Only constructor we need.
	 * @param key
	 * @param value
	 * @param left
	 * @param right
	 */
	public NonEmptyTree(K key, V value, Tree<K, V> left, Tree<K, V> right) {

		this.key = key;
		val = value;
		leftTree = left;
		rightTree = right;
	
	}

	public V search(K key) {
		
		if(key.compareTo(this.key) > 0) {
			
			return rightTree.search(key);
			
		} else if(key.compareTo(this.key) < 0) {
			
			return leftTree.search(key);
			
		} else {
			
			return val;
		
		}
		
	}
	
	public NonEmptyTree<K, V> insert(K key, V value) {

		if(key.compareTo(this.key) > 0) {
			
			rightTree = rightTree.insert(key, value);
		
		} else if(key.compareTo(this.key) < 0) {

			leftTree = leftTree.insert(key, value);
		
		} else {
			
			this.key = key;
			this.val = value;
		
		}
		
		return this;
		
	}
	
	public Tree<K, V> delete(K key) {

		if(this.key.compareTo(key) < 0) {
			
			rightTree = rightTree.delete(key);
			
		} else if(this.key.compareTo(key) > 0) {
			
			leftTree = leftTree.delete(key);
			
		} else {

			try {
				
				K temp = leftTree.max();
				
				this.val = search(temp);
				this.key = temp;
				
				leftTree = leftTree.delete(temp);
				
			} catch(TreeIsEmptyException e) {
				
				return rightTree;
				
			}
			
		}
		
		return this;
		
	}

	public K max() {
		
		try {
			
			return rightTree.max();
			
		} catch(TreeIsEmptyException e) {
			
			return this.key;
			
		}
		
	}

	public K min() {
		
		try {
			
			return leftTree.min();
			
		} catch(TreeIsEmptyException e) {
			
			return this.key;
			
		}
		
	}

	public int size() {
		
		return 1 + leftTree.size() + rightTree.size();
		
	}

	public void addKeysToCollection(Collection<K> c) {
		
		leftTree.addKeysToCollection(c);
		c.add(this.key);
		rightTree.addKeysToCollection(c);
		
	}
	
	public Tree<K, V> subTree(K fromKey, K toKey) {
		
		if(this.key.compareTo(toKey) >= 0) {
			
			return leftTree.subTree(fromKey, toKey);
			
		} else if(this.key.compareTo(fromKey) <= 0) {
			
			return rightTree.subTree(fromKey, toKey);
			
		} else {
			
			return new NonEmptyTree<K, V>(this.key, this.val, leftTree.subTree(fromKey, toKey), rightTree.subTree(fromKey, toKey));
			
		}
		
	}
	
	public int height() {
		
		int leftTreeHeight = 0;
		int rightTreeHeight = 0;
		
		leftTreeHeight = leftTree.height();
		
		rightTreeHeight = rightTree.height();
		
		return leftTreeHeight > rightTreeHeight ? leftTreeHeight + 1 : rightTreeHeight + 1;
		
	}
	
	public void inorderTraversal(TraversalTask<K,V> p) {
		
		leftTree.inorderTraversal(p);
		p.performTask(this.key, this.val);
		rightTree.inorderTraversal(p);
		
	}
	
	public void rightRootLeftTraversal(TraversalTask<K,V> p) {
		
		rightTree.rightRootLeftTraversal(p);
		p.performTask(this.key, this.val);
		leftTree.rightRootLeftTraversal(p);
		
	}
	
}