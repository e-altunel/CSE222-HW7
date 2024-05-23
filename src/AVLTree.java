/**
 * AVLTree.java
 * This class implements an AVL tree to store Stock objects.
 * The tree is balanced after every insertion and deletion.
 * The tree is traversed in-order to display the stocks.
 */
public class AVLTree {
	/**
   * Node class to store the stock object and the left and right child nodes.
   * The height of the node is also stored.
   */
	private class Node {
		Stock stock;
		Node left, right;
		int height;

		/**
     * Constructor to initialize the stock object and set the height to 1.
     * @param stock The stock object to be stored in the node.
     */
		Node(Stock stock) {
			this.stock = stock;
			this.height = 1;
		}

		/**
     * Method to calculate the height of the node based on the height of the left and right child nodes.
     */
		void
		calculateHeight() {
			int leftHeight = (left != null) ? left.height : 0;
			int rightHeight = (right != null) ? right.height : 0;
			height = 1 + Math.max(leftHeight, rightHeight);
		}
	}

	private Node root;

	/**
   * Constructor to initialize the root node to null.
   */
	public AVLTree() {
		root = null;
	}

	/**
   * Insert a stock into the AVL tree. The tree is balanced after insertion.
   * @param stock The stock object to be inserted.
   */
	public void
	insert(Stock stock) {
		root = insert(root, stock);
		balanceTree();
	}

	/**
   * Method to insert a stock into the AVL tree.
   * The stock is inserted based on the symbol in alphabetical order.
   * 
   * @param node The current node being considered for insertion.
   * @param stock The stock object to be inserted.
   * @return The updated node after insertion.
   */
	private Node
	insert(Node node, Stock stock) {
		if (node == null)
			return new Node(stock);

		if (stock.getSymbol().compareTo(node.stock.getSymbol()) < 0)
			node.left = insert(node.left, stock);
		else
			node.right = insert(node.right, stock);

		node.calculateHeight();
		return node;
	}

	/**
   * Delete a stock from the AVL tree. The tree is balanced after deletion.
   * @param symbol The symbol of the stock to be deleted.
   */
	public void
	delete(String symbol) {
		root = delete(root, symbol);
		balanceTree();
	}

	/**
   * Method to delete a stock from the AVL tree.
   * The stock is deleted based on the symbol.
   * 
   * @param node The current node being considered for deletion.
   * @param symbol The symbol of the stock to be deleted.
   * @return The updated node after deletion.
   */
	private Node
	delete(Node node, String symbol) {
		if (node == null)
			return null;

		if (node.stock.getSymbol().equals(symbol)) {
			if (node.left == null)
				return node.right;
			if (node.right == null)
				return node.left;

			Node minNode = node.right;
			while (minNode.left != null) {
				minNode = minNode.left;
			}
			node.stock = minNode.stock;
			node.right = delete(node.right, minNode.stock.getSymbol());
		} else if (symbol.compareTo(node.stock.getSymbol()) < 0) {
			node.left = delete(node.left, symbol);
		} else {
			node.right = delete(node.right, symbol);
		}
		node.calculateHeight();
		return node;
	}

	/**
   * Search for a stock in the AVL tree based on the symbol.
   * 
   * @param symbol The symbol of the stock to be searched.
   * @return The stock object if found, null otherwise.
   */
	public Stock
	search(String symbol) {
		Node result = search(root, symbol);
		return (result != null) ? result.stock : null;
	}

	/**
   * Method to search for a stock in the AVL tree based on the symbol.
   * The search is performed recursively. Time complexity is O(log n).
   * 
   * @param node The current node being considered for search.
   * @param symbol The symbol of the stock to be searched.
   * @return The node containing the stock object if found, null otherwise.
   */
	private Node
	search(Node node, String symbol) {
		if (node == null)
			return null;

		if (node.stock.getSymbol().equals(symbol))
			return node;
		if (symbol.compareTo(node.stock.getSymbol()) < 0)
			return search(node.left, symbol);
		else
			return search(node.right, symbol);
	}

	/**
   * Method to display the stocks in the AVL tree in in-order traversal.
   */
	public void
	inOrderTraversal() {
		inOrderTraversal(root, 0);
	}

	/**
   * Method to display the stocks in the AVL tree in in-order traversal.
   * The level of the node is used to indent the output for better visualization.
   * 
   * @param node The current node being considered for traversal.
   * @param level The level of the node in the tree.
   */
	private void
	inOrderTraversal(Node node, int level) {
		if (node != null) {
			inOrderTraversal(node.left, level + 1);
			System.out.println(" ".repeat(level * 4) + node.height + " " + node.stock);
			inOrderTraversal(node.right, level + 1);
		}
	}

	/**
   * Method to balance the AVL tree after insertion or deletion.
   * The balance factor of the root node is checked and rotations are performed if necessary.
   * There are four cases to consider:
   * 1. Left-Left (LL) case: Right rotation
   * 2. Left-Right (LR) case: Left rotation on left child followed by right rotation on root
   * 3. Right-Right (RR) case: Left rotation
   * 4. Right-Left (RL) case: Right rotation on right child followed by left rotation on root
   */
	private void
	balanceTree() {
		if (root == null) {
			return;
		}

		int balance = getBalance(root);

		if (balance > 1) {
			if (getBalance(root.left) < 0)
				root.left = rotateLeft(root.left);
			root = rotateRight(root);
		} else if (balance < -1) {
			if (getBalance(root.right) > 0)
				root.right = rotateRight(root.right);
			root = rotateLeft(root);
		}
	}

	/**
   * Method to perform a right rotation on the given node.
   * @param node The node to perform the right rotation on.
   * @return The new root node after the rotation.
   */
	private Node
	rotateRight(Node node) {
		if (node == null)
			return null;
		Node newRoot = node.left;
		node.left = newRoot.right;
		newRoot.right = node;

		newRoot.right.calculateHeight();
		newRoot.calculateHeight();

		return newRoot;
	}

	/**
   * Method to perform a left rotation on the given node.
   * @param node The node to perform the left rotation on.
   * @return The new root node after the rotation.
   */
	private Node
	rotateLeft(Node node) {
		if (node == null)
			return null;
		Node newRoot = node.right;
		node.right = newRoot.left;
		newRoot.left = node;

		newRoot.left.calculateHeight();
		newRoot.calculateHeight();

		return newRoot;
	}

	/**
   * Method to calculate the balance factor of a node.
   * The balance factor is the difference in height of the left and right child nodes.
   * @param node The node to calculate the balance factor for.
   * @return The balance factor of the node.
   */
	private int
	getBalance(Node node) {
		if (node == null) {
			return 0;
		}
		int leftHeight = (node.left != null) ? node.left.height : 0;
		int rightHeight = (node.right != null) ? node.right.height : 0;
		return leftHeight - rightHeight;
	}
}