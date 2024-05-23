import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * GUIVisualization.java
 * This class generates a performance graph to visualize the time taken for various operations on the StockDataManager class.
 */
public class GUIVisualization extends JFrame {
	/**
   * The list of X-axis data points.
   */
	private List<Integer> dataPointsX;
	/**
   * The list of Y-axis data points.
   */
	private List<List<Long> > dataPointsY;
	/**
   * The type of plot to be generated. Can be "line" or "scatter".
   */
	private String plotType;

	/**
   * Constructor to initialize the GUIVisualization object. It generates a performance graph to visualize the time taken for various operations on the StockDataManager class.  
   * @param plotType The type of plot to be generated. Can be "line" or "scatter".
   */
	public GUIVisualization(String plotType) {
		final int sampleSize = 500_000;
		final int division = 500;
		this.plotType = plotType;
		this.dataPointsX = new ArrayList<>();
		this.dataPointsY = new ArrayList<>();
		dataPointsY.add(new ArrayList<>());
		dataPointsY.add(new ArrayList<>());
		dataPointsY.add(new ArrayList<>());
		dataPointsY.add(new ArrayList<>());

		StockDataManager manager = new StockDataManager();
		int i = 0;
		for (; i < 10_000; i++) {
			manager.addOrUpdateStock("Sym" + i, 1000, 1000, 1000);
		}
		long startTime;
		long endTime;
		for (; i <= sampleSize; i++) {
			dataPointsX.add(i / division);

			startTime = System.nanoTime();
			for (int j = 0; j < division; j++) {
				manager.addOrUpdateStock("Sym" + (i + j), 1000, 1000, 1000);
			}
			endTime = System.nanoTime();
			dataPointsY.get(0).add(endTime - startTime);

			startTime = System.nanoTime();
			for (int j = 0; j < division; j++) {
				manager.searchStock("Sym" + (i + j));
			}
			endTime = System.nanoTime();
			dataPointsY.get(1).add(endTime - startTime);

			startTime = System.nanoTime();
			for (int j = 0; j < division; j++) {
				manager.updateStock("Sym" + (i + j), "Aym" + (i + j), 1000, 1000, 1000);
			}
			endTime = System.nanoTime();
			dataPointsY.get(2).add(endTime - startTime);

			startTime = System.nanoTime();
			for (int j = 0; j < division; j++) {
				manager.removeStock("Aym" + (i + j));
			}
			endTime = System.nanoTime();
			dataPointsY.get(3).add(endTime - startTime);

			for (int j = 0; j < division; j++) {
				manager.addOrUpdateStock("Sym" + (i + j), 1000, 1000, 1000);
			}
			i += division;
		}
		setTitle("Performance Graph Visualization");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	/**
   * Method to paint the graph on the GUI.
   */
	@Override
	public void
	paint(Graphics g) {
		super.paint(g);
		drawGraph(g);
	}

	/**
   * Method to draw the graph on the GUI.
   * @param g The Graphics object to draw the graph.
   */
	private void
	drawGraph(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		int padding = 50;
		int labelPadding = 20;
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding,
			    padding,
			    width - 2 * padding - labelPadding,
			    height - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);
		int numberYDivisions = 10;
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = width - padding;
			int y0 = height - ((i * (height - padding * 2 - labelPadding)) / numberYDivisions + padding);
			int y1 = y0;
			if (dataPointsX.size() > 0) {
				g2.setColor(Color.LIGHT_GRAY);
				g2.drawLine(padding + labelPadding + 1 + labelPadding, y0, x1, y1);
				g2.setColor(Color.BLACK);
				String yLabel =
					((int)((getMaxYValue() * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
			}
		}
		for (int i = 0; i < dataPointsX.size(); i++) {
			if (dataPointsX.size() > 1) {
				int x0 = i * (width - padding * 2 - labelPadding) / (dataPointsX.size() - 1) + padding +
					 labelPadding;
				int x1 = x0;
				int y0 = height - padding - labelPadding;
				int y1 = y0 - 4;
				if ((i % ((int)((dataPointsX.size() / 20.0)) + 1)) == 0) {
					g2.setColor(Color.LIGHT_GRAY);
					g2.drawLine(
						x0, height - padding - labelPadding - 1 - labelPadding, x1, padding);
					g2.setColor(Color.BLACK);
					String xLabel = dataPointsX.get(i) + "";
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
				}
				g2.drawLine(x0, y0, x1, y1);
			}
		}
		g2.drawLine(padding + labelPadding, height - padding - labelPadding, padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding,
			    height - padding - labelPadding,
			    width - padding,
			    height - padding - labelPadding);
		Stroke oldStroke = g2.getStroke();
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(2f));

		long maxYValue = getMaxYValue();
		double multiplier = 1.0 / maxYValue * (height - padding * 2 - labelPadding);
		int afterPaddingY = height - padding - labelPadding;

		for (int k = 0; k < 4; k++) {
			g2.setColor(k == 0 ? Color.RED : k == 1 ? Color.GREEN : k == 2 ? Color.BLUE : Color.BLACK);
			if (plotType.equals("line")) {
				for (int i = 0; i < dataPointsX.size() - 1; i++) {
					int x1 = i * (width - padding * 2 - labelPadding) / (dataPointsX.size() - 1) +
						 padding + labelPadding;
					int x2 = (i + 1) * (width - padding * 2 - labelPadding) /
							 (dataPointsX.size() - 1) +
						 padding + labelPadding;
					int y1 = afterPaddingY - (int)((dataPointsY.get(k).get(i) * 1.0) / maxYValue *
								       (height - padding * 2 - labelPadding));
					int y2 = afterPaddingY -
						 (int)((dataPointsY.get(k).get(i + 1) * 1.0) / maxYValue *
						       (height - padding * 2 - labelPadding));
					g2.drawLine(x1, y1, x2, y2);
				}
			} else if (plotType.equals("scatter")) {
				for (int i = 0; i < dataPointsX.size(); i++) {
					int x = i * (width - padding * 2 - labelPadding) / (dataPointsX.size() - 1) +
						padding + labelPadding;
					int y_val = (int)(dataPointsY.get(k).get(i) * multiplier);
					if (y_val > maxYValue)
						continue;
					int y = afterPaddingY - y_val;
					g2.fillOval(x - 3, y - 3, 6, 6);
				}
			}
			g2.setStroke(oldStroke);
		}
	}

	/**
   * Method to get the maximum value of the Y-axis.
   * @return The maximum value of the Y-axis.
   */
	private long
	getMaxYValue() {
		long max = Long.MIN_VALUE;

		for (int k = 0; k < 4; k++) {
			for (int i = 0; i < dataPointsY.get(k).size(); i++) {
				if (dataPointsY.get(k).get(i) > max) {
					max = dataPointsY.get(k).get(i);
				}
			}
		}
		return max;
	}

	/**
   * Main method to run the GUIVisualization class.
   * @param args The command line arguments.
   */
	public static void
	main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			String plotType = "scatter";
			GUIVisualization frame = new GUIVisualization(plotType);
			frame.setVisible(true);
		});
	}
}
