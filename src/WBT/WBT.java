package WBT;

public class WBT<K extends Comparable<K>>
{
	  Node<K> root;
	  Node<K> tempParent;
	  /* Normally this should be private or protected. But I'm doing whitebox
	     testing (of the tree shape and content), I need access from the outside.
	  */
	
	  public WBT() {
		  root = null;
	  }
	
	  private static int size(Node<?> u) {
		  return u == null ? 0 : u.num;
	  }
	
	  public void insert(K x) {
		  Node<K> insertNode = new Node<K>();
		  insertNode.key = x;
		  Node<K> currentNode;
		  currentNode = root;
		  boolean run = true;
		  
		  
		  while(run){
			  if(!hasKey(root)){
				  root = insertNode;
				  run = false;
			  }
			  
			  else if(currentNode.key.compareTo(x) < 0){
				  if(!hasKey(currentNode.right)){
					  currentNode.right = insertNode;
					  currentNode.right.parent = currentNode;
					  currentNode.num += 1;
					  rbHelper(root);
					  run = false;
				  }
				  
				  else{
					  currentNode.num += 1;
					  currentNode = currentNode.right;
				  }
			  }
			  
			  else{	  
				  if(!hasKey(currentNode.left)){
					  currentNode.left = insertNode;
					  currentNode.left.parent = currentNode;
					  currentNode.num += 1;
					  rbHelper(root);
					  run = false;
				  }
				  
				  else{
					  currentNode.num += 1;
					  currentNode = currentNode.left;  
				  }
			  }
		  }
	  }
	  
	  public boolean rbHelper(Node<K> p){
		  

		  
		  if (size(p) < 4)
			  return true;
		  
		  else{
			  if(hasKey(p.left))
				  rbHelper(p.left);
			  
			  if(hasKey(p.right))
				  rbHelper(p.right);
			  
			  System.out.println("Current 1:" + p);
			  rebalance(p);
			  System.out.println("Current 2:" + p);
			  
			  return true;
		  }
	  }
	  
	  public void rebalance(Node<K> p){
		  
		  if (p != root)
			  tempParent = p.parent;
		  
		  Node<K> currentNode = new Node<K>();
		  currentNode = p;
		  Node<K> tempLeft = null;
		  Node<K> tempRight = null;
		  
		  if ((size(currentNode.left) + 1) * 3 < size(currentNode.right) + 1){
			  
			  currentNode = currentNode.right;
			  
			  if ((size(currentNode.left) + 1) < ((size(currentNode.right) + 1) * 2)){
				  
				  tempLeft = p;
				  if(hasKey(currentNode.left))
					  tempRight = currentNode.left;
				  
				  p = currentNode;
				  p.left = tempLeft;
				  tempLeft.right = tempRight;
				  
				  root = p;
				  
				  System.out.println("Single Rotation Right");
			  }
			  
			  else{
				  currentNode = currentNode.left;
				  
				  if(hasKey(currentNode.left))
					  tempRight = currentNode.left;
				  if(hasKey(currentNode.right))
					  tempLeft = currentNode.right;
				  
				  currentNode.left = p;
				  currentNode.right = p.right;
				  p = currentNode;
				  
				  p.left.right = tempRight;
				  p.right.left = tempLeft;
				  
				  root = p;
				  System.out.println("Double Rotation Right");
			  }
		  }
		  else if ((size(currentNode.left) + 1) > (size(currentNode.right) + 1) * 3){
			  
			  currentNode = currentNode.left;
			  
			  
			  if ((size(currentNode.left) + 1) * 2 > size(currentNode.right) + 1){
				  
				  
				  tempRight = p;
				  if(hasKey(currentNode.right))
					  tempLeft = currentNode.right;
				  
				  
				  p = currentNode;
				  p.right = tempRight;
				  tempRight.left = tempLeft;
				  
				  root = p;
				  tempParent.right = p;
				  
				  System.out.println("Single Rotation Left");
			  }
			  
			  else{
				  currentNode = currentNode.right;
				  if(hasKey(currentNode.left))
					  tempRight = currentNode.left;
				  if(hasKey(currentNode.right))
					  tempLeft = currentNode.right;
				  
				  currentNode.right = p;
				  currentNode.left = p.left;
				  p = currentNode;
				  
				  p.left.right = tempRight;
				  p.right.left = tempLeft;
				  root = p;
				  System.out.println("Double Rotation Left");
			  }
			  
		  }
		  //System.out.println("the parent? " + tempParent);
		  //System.out.println("Current mid: " + p);
	  }
	  
	  
	  public boolean hasKey(Node<K> n){
		  return (n != null);
	  }
}
