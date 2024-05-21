public class AVLTree
{
  private class Node
  {
    Stock stock;
    Node left, right;
    int height;

    Node (Stock stock)
    {
      this.stock = stock;
      this.height = 1;
    }
  }

  private Node root;

  public void
  insert (Stock stock)
  {
    root = insert (root, stock);
  }

  private Node
  insert (Node node, Stock stock)
  {
    if (node == null)
      {
        return new Node (stock);
      }
    node.left = insert (node.left, stock);
    return node;
  }

  public void
  delete (String symbol)
  {
    root = delete(root, symbol);
  }

  private Node
  delete (Node node, String symbol)
  {

    return node;
  }

  public Stock
  search (String symbol)
  {
    Node result = search (root, symbol);
    return (result != null) ? result.stock : null;
  }

  private Node
  search (Node node, String symbol)
  {
    if (node == null)
      {
        return null;
      }
    if (node.stock.getSymbol ().equals (symbol))
      {
        return node;
      }
    Node right = search (node.right, symbol);
    if (right != null)
      {
        return right;
      }
    Node left = search (node.left, symbol);
    if (left != null)
      {
        return left;
      }
    return null;
  }

  public void
  inOrderTraversal ()
  {
    inOrderTraversal (root);
  }

  private void
  inOrderTraversal (Node node)
  {
    if (node != null)
      {
        inOrderTraversal (node.left);
        System.out.println (node.stock);
        inOrderTraversal (node.right);
      }
  }
}