/**
 * StockDataManager.java
 * This class manages the stock data using an AVL tree.
 */
public class StockDataManager {
	private AVLTree avlTree;

	/**
    * Constructor to initialize the AVL tree.
    */
	public StockDataManager() {
		avlTree = new AVLTree();
	}

	/**
   * Method to add or update a stock in the AVL tree.
   * If the stock already exists, it is updated with the new values.
   * If the stock does not exist, it is added to the tree.
   * 
   * @param symbol The symbol of the stock.
   * @param price The price of the stock.
   * @param volume The volume of the stock.
   * @param marketCap The market capitalization of the stock.
   * @return 0 if the stock is added, 1 if the stock is updated.
   */
	public int
	addOrUpdateStock(String symbol, double price, long volume, long marketCap) {
		Stock existingStock = avlTree.search(symbol);
		if (existingStock != null) {
			existingStock.setPrice(price);
			existingStock.setVolume(volume);
			existingStock.setMarketCap(marketCap);
			return 1;
		} else {
			Stock newStock = new Stock(symbol, price, volume, marketCap);
			avlTree.insert(newStock);
			return 0;
		}
	}

	/**
   * Method to remove a stock from the AVL tree.
   * @param symbol The symbol of the stock to be removed.
   */
	public void
	removeStock(String symbol) {
		avlTree.delete(symbol);
	}

	/**
   * Method to search for a stock in the AVL tree.
   * @param symbol The symbol of the stock to be searched.
   * @return The stock object if found, null otherwise.
   */
	public Stock
	searchStock(String symbol) {
		return avlTree.search(symbol);
	}

	/**
   * Method to update the details of a stock in the AVL tree.
   * @param symbol The symbol of the stock to be updated.
   * @param newSymbol The new symbol of the stock.
   * @param newPrice The new price of the stock.
   * @param newVolume The new volume of the stock.
   * @param newMarketCap The new market capitalization of the stock.
   */
	public void
	updateStock(String symbol, String newSymbol, double newPrice, long newVolume, long newMarketCap) {
		Stock stock = avlTree.search(symbol);
		if (stock != null) {
			stock.setPrice(newPrice);
			stock.setVolume(newVolume);
			stock.setMarketCap(newMarketCap);
			if (!symbol.equals(newSymbol)) {
				avlTree.delete(symbol);
				stock.setSymbol(newSymbol);
				avlTree.insert(stock);
			}
		}
	}

	/**
   * Main method to test the StockDataManager class.
   * @param args The command line arguments.
   */
	public static void
	main(String[] args) {
		StockDataManager manager = new StockDataManager();
		manager.addOrUpdateStock("AAPL", 150.0, 1000000, 2500000000L);
		manager.addOrUpdateStock("GOOGL", 2800.0, 500000, 1500000000L);
		System.out.println(manager.searchStock("AAPL"));
		manager.removeStock("AAPL");
		System.out.println(manager.searchStock("AAPL"));
	}

	/**
   * Method to print the stocks in the AVL tree.
   */
	public void
	printTree() {
		System.out.println("Stocks:");
		avlTree.inOrderTraversal();
	}
}
