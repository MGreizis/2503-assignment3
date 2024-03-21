import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Comparator;

public class BST<T extends Comparable<T>> implements Iterable<T> {

	private BSTNode<T> root;
	private Comparator<T> comparator;

	public BST() {
		// TODO Auto-generated method stub
		// Create a new BST using the natural ordering of T.
		this.root = null;
		this.comparator = null;
	}

	public BST(Comparator<T> c) {
		// TODO Auto-generated method stub
		// Create a new BST using the ordering determined by c
		this.root = null;
		this.comparator = c;
	}

	public void insert(T data) {
		root = insertRec(root, data);
	}

	private BSTNode<T> insertRec(BSTNode<T> node, T data) {
		if (node == null) {
			return new BSTNode<>(data);
		}

		if (compare(data, node.getData()) < 0) {
			node.setLeft(insertRec(node.getLeft(), data));
		} else if (compare(data, node.getData()) > 0) {
			node.setRight(insertRec(node.getRight(), data));
		}

		return node;
	}

	public boolean contains(T data) {
		return containsRec(root, data);
	}

	private boolean containsRec(BSTNode<T> node, T data) {
		if (node == null) {
			return false;
		}

		if (compare(data, node.getData()) == 0) {
			return true;
		}

		if (compare(data, node.getData()) < 0) {
			return containsRec(node.getLeft(), data);
		} else {
			return containsRec(node.getRight(), data);
		}
	}

	private int compare(T data1, T data2) {
		if (comparator != null) {
			return comparator.compare(data1, data2);
		} else {
			return data1.compareTo(data2);
		}
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new BSTIterator<>(root);
	}

	public T min() {
		// TODO Auto-generated method stub
		return null;
	}

	private static class BSTIterator<T extends Comparable<T>> implements Iterator<T> {
		private Queue<T> queue;

		public BSTIterator(BSTNode<T> root) {
			this.queue = new LinkedList<>();
			inOrderTraversal(root);
		}

		private void inOrderTraversal(BSTNode<T> node) {
			if (node != null) {
				inOrderTraversal(node.getLeft());
				queue.add(node.getData());
				inOrderTraversal(node.getRight());
			}
		}

		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public T next() {
			return queue.poll();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
