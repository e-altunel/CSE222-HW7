import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static void
	main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java Main <input_file>");
			return;
		}

		String inputFile = args[0];
		StockDataManager manager = new StockDataManager();

		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				try {
					processCommand(line, manager);
				} catch (Exception e) {
					System.out.println("Invalid command: " + line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		performPerformanceAnalysis(manager, 1_000_000);
	}

	private static void
	processCommand(String line, StockDataManager manager) throws NullPointerException, NumberFormatException {
		String[] tokens = line.split(" ");
		String command = tokens[0];

		switch (command) {
		case "ADD":
			if (manager.searchStock(tokens[1]) != null) {
				System.out.println("Stock updated:   " + tokens[1]);
			} else {
				System.out.println("Stock added:     " + tokens[1]);
			}
			manager.addOrUpdateStock(tokens[1],
						 Double.parseDouble(tokens[2]),
						 Long.parseLong(tokens[3]),
						 Long.parseLong(tokens[4]));
			break;
		case "REMOVE":
			if (manager.searchStock(tokens[1]) == null) {
				System.out.println("Stock not found: " + tokens[1]);
				break;
			}
			manager.removeStock(tokens[1]);
			System.out.println("Stock removed:   " + tokens[1]);
			break;
		case "SEARCH":
			Stock stock = manager.searchStock(tokens[1]);
			if (stock != null)
				System.out.println("Stock found:     " + stock);
			else
				System.out.println("Stock not found: " + tokens[1]);
			break;
		case "UPDATE":
			if (manager.searchStock(tokens[1]) == null) {
				System.out.println("Stock not found: " + tokens[1]);
				break;
			}
			manager.updateStock(tokens[1],
					    tokens[2],
					    Double.parseDouble(tokens[3]),
					    Long.parseLong(tokens[4]),
					    Long.parseLong(tokens[5]));
			System.out.println("Stock updated:   " + manager.searchStock(tokens[2]));
			break;
		case "PRINT":
			manager.printTree();
			break;
		default:
			System.out.println("Unknown command:  " + command);
			break;
		}
	}

	private static void
	performPerformanceAnalysis(StockDataManager manager, int size) {
		long startTime, endTime;

		startTime = System.nanoTime();
		for (int i = 0; i < size; i++) {
			manager.addOrUpdateStock("SYM" + i,
						 Math.random() * 100,
						 (long)(Math.random() * 1000000),
						 (long)(Math.random() * 1000000000));
		}
		endTime = System.nanoTime();
		System.out.println("Average ADD time: " + (endTime - startTime) / size + " ns");

		startTime = System.nanoTime();
		for (int i = 0; i < size; i++) {
			manager.searchStock("SYM" + i);
		}
		endTime = System.nanoTime();
		System.out.println("Average SEARCH time: " + (endTime - startTime) / size + " ns");

		startTime = System.nanoTime();
		for (int i = 0; i < size; i++) {
			manager.removeStock("SYM" + i);
		}
		endTime = System.nanoTime();
		System.out.println("Average REMOVE time: " + (endTime - startTime) / size + " ns");
	}
}
