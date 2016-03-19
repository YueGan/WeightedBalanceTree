package WBT;

public class Node<K>
{
  public K key;
  public Node<K> left, right;
  public Node<K> parent;            // you may use parent or ignore it
  public int num;

  /* This displays the tree in the format: 
       (...left subtree...)key(...right subtree...)
     down to 10 levels.
     You may find it useful when debugging.
   */
  public String toString() {
    return toStringLevel(10);
  }

  /* This displays the tree in the format: 
       (...left subtree...)key(...right subtree...)
     down to the level specified.
     You may find it useful when debugging.
   */
  public String toStringLevel(int lvl) {
    if (lvl == 0) {
      return "...";
    }
    String s = "";
    if (left != null) {
      s += '(' + left.toStringLevel(lvl - 1) + ')';
    }
    s += key.toString();
    if (right != null) {
      s += '(' + right.toStringLevel(lvl - 1) + ')';
    }
    return s;
  }
}
