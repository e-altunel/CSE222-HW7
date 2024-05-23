public class StockDataManager {
	private AVLTree avlTree;

	public StockDataManager() {
		avlTree = new AVLTree();
	}

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

	public void
	removeStock(String symbol) {
		avlTree.delete(symbol);
	}

	public Stock
	searchStock(String symbol) {
		return avlTree.search(symbol);
	}

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

	public static void
	main(String[] args) {
		StockDataManager manager = new StockDataManager();
		manager.addOrUpdateStock("AAPL", 150.0, 1000000, 2500000000L);
		manager.addOrUpdateStock("GOOGL", 2800.0, 500000, 1500000000L);
		System.out.println(manager.searchStock("AAPL"));
		manager.removeStock("AAPL");
		System.out.println(manager.searchStock("AAPL"));
	}

	public void
	printTree() {
		System.out.println("Stocks:");
		avlTree.inOrderTraversal();
	}
}
