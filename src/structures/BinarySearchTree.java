package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

  public boolean contains(T t) {
    // TODO: Implement the contains() method
    if (t == null)
      throw new NullPointerException();

    BSTNode<T> nodeWithT = traverse(root, t);

    if (nodeWithT != null) {
      return true;
    } else {
      return false;
    }

  }

  private BSTNode<T> traverse(BSTNode<T> node, T data) {
    
    if (node != null) {
      if (node.getData().equals(data)){
        return node;
      } else {
        BSTNode<T> left = traverse(node.getLeft(), data);
        if (left != null && left.getData().equals(data)){
          return left;
        } else {
          BSTNode<T> right = traverse(node.getRight(), data);
          if (right != null && right.getData().equals(data)) {
            return right;
          } else {
            return null;
          }
        }
      }
    } else {
      return null;
    }
  }

  public boolean remove(T t) {
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
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
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
      node.setRight(removeRightmost(node.getRight()));
      return node;
    }
  }

  public T get(T t) {
    // TODO: Implement the get() method
    if (t == null)
      throw new NullPointerException();

    BSTNode<T> nodeWithT = traverse(root, t);

    if (nodeWithT != null) {
      return nodeWithT.getData();
    }

    return null;
  }


  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
    }
    return node;
  }

  @Override
  public T getMinimum() {
    // TODO: Implement the getMinimum() method
    if (isEmpty())
      return null;

    return findMinimum(root, root.getData());
  }

  private T findMinimum(BSTNode<T> node, T minimum) {
    
    if (node != null) {
      T left;
      T right;
      T smaller;

      if (node.getData().compareTo(minimum) < 0) {
        left = findMinimum(node.getLeft(), node.getData());
        right = findMinimum(node.getRight(), node.getData());

        if (left.compareTo(right) < 0) {
          smaller = left;
        } else {
          smaller = right;
        }

        if (smaller.compareTo(node.getData()) < 0) {
          return smaller;
        } else {
          return node.getData();
        }
      } else {
        left = findMinimum(node.getLeft(), minimum);
        right = findMinimum(node.getRight(), minimum);

        if (left.compareTo(right) < 0) {
          smaller = left;
        } else {
          smaller = right;
        }

        if (smaller.compareTo(minimum) < 0) {
          return smaller;
        } else {
          return minimum;
        }
      }
    } else {
      return minimum;
    }
  }

  private T findMaximum(BSTNode<T> node, T maximum) {
    
    if (node != null) {
      T left;
      T right;
      T bigger;

      if (node.getData().compareTo(maximum) > 0) {
        left = findMaximum(node.getLeft(), node.getData());
        right = findMaximum(node.getRight(), node.getData());

        if (left.compareTo(right) > 0) {
          bigger = left;
        } else {
          bigger = right;
        }

        if (bigger.compareTo(node.getData()) > 0) {
          return bigger;
        } else {
          return node.getData();
        }
      } else {
        left = findMaximum(node.getLeft(), maximum);
        right = findMaximum(node.getRight(), maximum);

        if (left.compareTo(right) > 0) {
          bigger = left;
        } else {
          bigger = right;
        }

        if (bigger.compareTo(maximum) > 0) {
          return bigger;
        } else {
          return maximum;
        }
      }
    } else {
      return maximum;
    }
  }


  @Override
  public T getMaximum() {
    // TODO: Implement the getMaximum() method
    if (isEmpty())
      return null;

    return findMaximum(root, root.getData());
  }


  @Override
  public int height() {
    // TODO: Implement the height() method
    return getHeight(root);
  }

  private int getHeight(BSTNode<T> node) {
     if (node == null)
      return -1;

    return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
  }


  public Iterator<T> preorderIterator() {
    // TODO: Implement the preorderIterator() method
    Queue<T> queue = new LinkedList<T>();
    preOrderTraverse(queue, root);
    return queue.iterator();
  }

  private void preOrderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      queue.add(node.getData());
      preOrderTraverse(queue, node.getLeft());
      preOrderTraverse(queue, node.getRight());
    }
  }


  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }


  private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> postorderIterator() {
    // TODO: Implement the postorderIterator() method
    Queue<T> queue = new LinkedList<T>();
    postorderTraverse(queue, root);
    return queue.iterator();
  }

  private void postorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      postorderTraverse(queue, node.getLeft());
      postorderTraverse(queue, node.getRight());
      queue.add(node.getData());
    }
  }


  @Override
  public boolean equals(BSTInterface<T> other) {
    // TODO: Implement the equals() method
    if (other == null)
      throw new NullPointerException();
    
    if (other.isEmpty() && isEmpty()) {
      return true;
    } else if (other.isEmpty() || isEmpty()) {
      return false;
    }
    
    return doubleTraverse(root, other.getRoot());
  }

  public boolean doubleTraverse(BSTNode<T> node1, BSTNode<T> node2) {
    if (node1 != null && node2 != null) {
      if (node1.getData().equals(node2.getData())) {
        boolean equalLeft = doubleTraverse(node1.getLeft(), node2.getLeft());
          if (!equalLeft) {
            return false;
          }
        boolean equalRight = doubleTraverse(node1.getRight(), node2.getRight());

        if (equalRight) {
          return true;
        } else {
          return false;
        }

      } else {
        return false;
      }
    } else if (node1 == null && node2 == null) {
      return true;
    } else {
      return false;
    }
  }


  @Override
  public boolean sameValues(BSTInterface<T> other) {
    // TODO: Implement the sameValues() method
    if (other == null)
      throw new NullPointerException();

    if (other.isEmpty() && isEmpty()) {
      return true;
    } else if (other.isEmpty() || isEmpty()) {
      return false;
    }
    
    Iterator<T> otherIterator = other.inorderIterator();
    Iterator<T> iterator = inorderIterator();

    boolean isEquivalent = false;

    while (otherIterator.hasNext() && iterator.hasNext()) {
      if (otherIterator.next().equals(iterator.next())) {
        isEquivalent = true;
      } else {
        return false;
      }
    }

    if (other.size() == size()) {
      return isEquivalent;
    } else {
      return false;
    }
  }

  @Override
  public boolean isBalanced() {
    // TODO: Implement the isBalanced() method
    int n = size();
    int h = getHeight(root);
    double heightSquared = Math.pow(2, h);
    double heightPlusOneSquared = Math.pow(2, h+1);
    if (n >= heightSquared && n <= heightPlusOneSquared){
      return true;
    } else {
      return false;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public void balance() {
    // TODO: Implement the balanceHelper() method
    Iterator<T> inorderIt = inorderIterator();
    int size = size();
    T[] values = (T[]) new Comparable[size];

    for (int i = 0; i < size; i++) {
      values[i] = inorderIt.next();
    }
    root = null;

    balanceHelper(values, 0, size - 1);
  }

  private void balanceHelper(T[] values, int low, int high) {
    if (low == high) {
      add(values[low]);
    } else if ((low + 1) == high) {
      add(values[low]);
      add(values[high]);
    } else {
      int mid = (low + high)/2;
      add(values[mid]);
      balanceHelper(values, low, mid - 1);
      balanceHelper(values, mid + 1, high);
    }
  }


  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> " + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot +=
            cursor.getData().toString() + " -> " + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count + ";\n";
        count++;
      }
    }
    dot += "};";
    return dot;
  }

  public static void main(String[] args) {
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] {"d", "b", "a", "c", "f", "e", "g"}) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] {"a", "b", "c", "d", "e", "f", "g"}) {
      tree.add(r);
    }
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }
}
