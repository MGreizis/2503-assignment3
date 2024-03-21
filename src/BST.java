import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Comparator;

public class BST<T extends Comparable<T>> implements Iterable<T> {

   private BSTNode<T> root;
   private Comparator<T> comparator;

   public BST() {
      // Create a new BST using the natural ordering of T.
      this.root = null;
      this.comparator = null;
   }

   public BST(Comparator<T> c) {
      // Create a new BST using the ordering determined by c
      this.root = null;
      this.comparator = c;
   }

   /**
    * Inserts the given data into the binary search tree.
    *
    * @param data the data to be inserted into the binary search tree
    */
   public void insert(T data) {
      root = insertRec(root, data);
   }

   /**
    * Inserts a new node with the given data into the binary search tree.
    *
    * @param node the root of the binary search tree
    * @param data the data to be inserted into the tree
    * @return the root of the binary search tree after insertion
    */
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

   /**
    * Checks if the binary search tree contains a specific element.
    *
    * @param data the element to search for
    * @return true if the element is found, false otherwise
    */
   public boolean contains(T data) {
      return containsRec(root, data);
   }

   /**
    * Recursively checks if a given data value is present in the binary search tree
    * rooted at the given node.
    *
    * @param node the root node of the binary search tree
    * @param data the data value to search for
    * @return true if the data value is found, false otherwise
    */
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

   /**
    * Compares two objects of type T and returns the result.
    *
    * @param data1 the first object to compare
    * @param data2 the second object to compare
    * @return the result of the comparison
    */
   private int compare(T data1, T data2) {
      if (comparator != null) {
         return comparator.compare(data1, data2);
      } else {
         return data1.compareTo(data2);
      }
   }

   /**
    * Returns the size of the binary search tree by recursively counting the number
    * of nodes in the tree.
    *
    * @return the number of nodes in the binary search tree
    */
   public int size() {
      return size(root);
   }

   private int size(BSTNode<T> node) {
      if (node == null) {
         return 0;
      }
      return 1 + size(node.getLeft()) + size(node.getRight());
   }

   /**
    * Get the height of the tree.
    *
    * @return the height of the tree
    */
   public int height() {
      return height(root);
   }

   private int height(BSTNode<T> node) {
      if (node == null) {
         return -1;
      }
      return Math.max(height(node.getLeft()), height(node.getRight())) + 1;
   }

   @Override
   public Iterator<T> iterator() {
      return new BSTIterator<>(root);
   }

   // returns the minimum element of the tree
   public T min() {
      if (root == null) {
         return null;
      }
      return minNode(root).getData();
   }

   /**
    * Finds the minimum node in the binary search tree starting from the given
    * node.
    *
    * @param node the starting node to search from
    * @return the minimum node in the binary search tree
    */
   public BSTNode<T> minNode(BSTNode<T> node) {
      BSTNode<T> minNode = node;
      while (minNode.getLeft() != null) {
         minNode = minNode.getLeft();
      }
      return minNode;
   }

   public void delete(T data) {
      root = deleteRec(root, root.getData());
   }

   /**
    * Deletes a node from the binary search tree recursively.
    *
    * @param node the node to be deleted
    * @param data the data to be deleted
    * @return the updated binary search tree after deletion
    */
   private BSTNode<T> deleteRec(BSTNode<T> node, T data) {
      if (node == null) {
         return null;
      }

      if (comparator == null) {
         if (data.compareTo(node.getData()) < 0) {
            node.setLeft(deleteRec(node.getLeft(), data));
         } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(deleteRec(node.getRight(), data));
         } else {
            if (node.getLeft() == null) {
               return node.getRight();
            } else if (node.getRight() == null) {
               return node.getLeft();
            }

            node.setData(minNode(node.getRight()).getData());
            node.setRight(deleteRec(node.getRight(), node.getData()));
         }
      } else {
         if (comparator.compare(data, node.getData()) < 0) {
            node.setLeft(deleteRec(node.getLeft(), data));
         } else if (comparator.compare(data, node.getData()) > 0) {
            node.setRight(deleteRec(node.getRight(), data));
         } else {
            if (node.getLeft() == null) {
               return node.getRight();
            } else if (node.getRight() == null) {
               return node.getLeft();
            }

            node.setData(minNode(node.getRight()).getData());
            node.setRight(deleteRec(node.getRight(), node.getData()));
         }
      }

      return node;
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
