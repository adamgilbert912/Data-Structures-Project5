package structures;

public class ScapegoatTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private int upperBound;


  @Override
  public void add(T t) {
    // TODO: Implement the add() method
    if (t == null)
      throw new NullPointerException();

    upperBound++;

    BSTNode<T> newNode = new BSTNode<T>(t, null, null);

    root = addToSubtree(root, newNode);
    double heightComparison = (Math.log(upperBound))/(Math.log(1.5));
    if (height() > heightComparison) {
      System.out.println("running");
      BSTNode<T> parentNode = newNode.getParent();

      double newNodeSize = subtreeSize(newNode);
      double parentNodeSize = subtreeSize(parentNode);

      while((newNodeSize/parentNodeSize) <= (2.0/3.0)) {
        newNode = parentNode;
        parentNode = parentNode.getParent();

        newNodeSize = subtreeSize(newNode);
        parentNodeSize = subtreeSize(parentNode);
      }

      System.out.print(parentNode.getData());

      BSTNode<T> scapegoatParent = parentNode.getParent();

      ScapegoatTree<T> tempSubTree = new ScapegoatTree<T>();
      tempSubTree.root = parentNode;
      tempSubTree.balance();

      tempSubTree.root.setParent(scapegoatParent);

      if (tempSubTree.root.getData().compareTo(scapegoatParent.getData()) <= 0) {
        scapegoatParent.setLeft(tempSubTree.root);
      } else {
        scapegoatParent.setRight(tempSubTree.root);
      }
    } else {
      System.out.println("didntrun");
    }
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      BSTNode<T> leftNode = addToSubtree(node.getLeft(), toAdd);
      node.setLeft(leftNode);
      leftNode.setParent(node);
    } else {
      BSTNode<T> rightNode = addToSubtree(node.getRight(), toAdd);
      node.setRight(rightNode);
      rightNode.setParent(node);
    }
    return node;
  }

  @Override
  public boolean remove(T element) {
    // TODO: Implement the remove() method
    boolean isRemoved = removeOG(element);

    if (upperBound > 2*size()) {
      balance();

      upperBound = size();
    }

    return isRemoved;
  }

  private boolean removeOG(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
    // node must not be null
    int result = t.compareTo(node.getData());
    if (result < 0) {
      BSTNode<T> left = removeFromSubtree(node.getLeft(), t);
      node.setLeft(left);
      if (left != null)
        left.setParent(node);
      return node;
    } else if (result > 0) {
      BSTNode<T> right = removeFromSubtree(node.getRight(), t);
      node.setRight(right);
      if (right != null)
        right.setParent(node);
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        BSTNode<T> left = removeRightmost(node.getLeft());
        node.setLeft(left);
        if (left != null)
          left.setParent(node);
        node.setData(predecessorValue);
        return node;
      }
    }
  }

  private T getHighestValue(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BSTNode<T> removeRightmost(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      BSTNode<T> right = removeRightmost(node.getRight());
      node.setRight(right);
      if (right != null)
        right.setParent(node);
      return node;
    }
  }

}
