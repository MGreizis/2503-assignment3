public class BSTNode<T extends Comparable<T>> {
   private T data;
   private BSTNode<T> left;
   private BSTNode<T> right;

   public BSTNode(T data) {
      this.data = data;
      this.left = null;
      this.right = null;
   }

   public T getData() {
      return data;
   }

   public void setData() {
      this.data = data;
   }

   public BSTNode<T> getLeft() {
      return left;
   }

   public void setLeft(BSTNode<T> left) {
      this.left = left;
   }

   public BSTNode<T> getRight() {
      return right;
   }

   public void setRight(BSTNode<T> right) {
      this.right = right;
   }
}
