public class AVLTree {
	private class Node {
		Stock stock;
		Node left, right;
		int height;

		Node(Stock stock) {
			this.stock = stock;
			this.height = 1;
		}

		void calculateHeight() {
			int leftHeight = (left != null) ? left.height : 0;
			int rightHeight = (right != null) ? right.height : 0;
			height = 1 + Math.max(leftHeight, rightHeight);
		}
	}

	private Node root;

	public void insert(Stock stock) {
		root = insert(root, stock);
		balanceTree();
	}

	private Node insert(Node node, Stock stock) {
		if (node == null)
			return new Node(stock);

		if (stock.getSymbol().compareTo(node.stock.getSymbol()) < 0)
			node.left = insert(node.left, stock);
		else
			node.right = insert(node.right, stock);

		node.calculateHeight();
		return node;
	}

	public void delete(String symbol) {
		root = delete(root, symbol);
		balanceTree();
	}

	private Node delete(Node node, String symbol) {
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

	public Stock search(String symbol) {
		Node result = search(root, symbol);
		return (result != null) ? result.stock : null;
	}

	private Node search(Node node, String symbol) {
		if (node == null)
			return null;

		if (node.stock.getSymbol().equals(symbol))
			return node;
		if (symbol.compareTo(node.stock.getSymbol()) < 0)
			return search(node.left, symbol);
		else
			return search(node.right, symbol);
	}

	public void inOrderTraversal() {
		inOrderTraversal(root, 0);
	}

	private void inOrderTraversal(Node node, int level) {
		if (node != null) {
			inOrderTraversal(node.left, level + 1);
			System.out.println(" ".repeat(level * 4) + node.height + " " + node.stock);
			inOrderTraversal(node.right, level + 1);
		}
	}

	private void balanceTree() {
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

	private Node rotateRight(Node node) {
		if (node == null)
			return null;
		Node newRoot = node.left;
		node.left = newRoot.right;
		newRoot.right = node;

		newRoot.right.calculateHeight();
		newRoot.calculateHeight();

		return newRoot;
	}

	private Node rotateLeft(Node node) {
		if (node == null)
			return null;
		Node newRoot = node.right;
		node.right = newRoot.left;
		newRoot.left = node;

		newRoot.left.calculateHeight();
		newRoot.calculateHeight();

		return newRoot;
	}

	private int getBalance(Node node) {
		if (node == null) {
			return 0;
		}
		int leftHeight = (node.left != null) ? node.left.height : 0;
		int rightHeight = (node.right != null) ? node.right.height : 0;
		return leftHeight - rightHeight;
	}
}