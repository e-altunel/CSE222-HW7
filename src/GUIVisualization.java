import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
public class GUIVisualization extends JFrame {
	private List<Integer> dataPointsX;
	private List<Long> dataPointsY;
	private String plotType;

	public GUIVisualization(String plotType)
	{
		this.plotType = plotType;
		this.dataPointsX = new ArrayList<>();
		this.dataPointsY = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			dataPointsX.add(i * 1000);
			dataPointsY.add((long)(Math.log(i * 1000) * 1000));
		}
		setTitle("Performance Graph Visualization");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	@Override public void paint(Graphics g)
	{
		super.paint(g);
		drawGraph(g);
	}

	private void drawGraph(Graphics g)
	{
		int width = getWidth();
		int height = getHeight();
		int padding = 50;
		int labelPadding = 20;
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				    RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + labelPadding, padding,
			    width - 2 * padding - labelPadding,
			    height - 2 * padding - labelPadding);
		g2.setColor(Color.BLACK);
		int numberYDivisions = 10;
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + labelPadding;
			int x1 = width - padding;
			int y0 = height -
				 ((i * (height - padding * 2 - labelPadding)) /
					  numberYDivisions +
				  padding);
			int y1 = y0;
			if (dataPointsY.size() > 0) {
				g2.setColor(Color.LIGHT_GRAY);
				g2.drawLine(padding + labelPadding + 1 +
						    labelPadding,
					    y0, x1, y1);
				g2.setColor(Color.BLACK);
				String yLabel = ((int)((getMaxYValue() *
							((i * 1.0) /
							 numberYDivisions)) *
						       100)) /
							100.0 +
						"";
				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5,
					      y0 + (metrics.getHeight() / 2) -
						      3);
			}
		}
		for (int i = 0; i < dataPointsX.size(); i++) {
			if (dataPointsX.size() > 1) {
				int x0 = i *
						 (width - padding * 2 -
						  labelPadding) /
						 (dataPointsX.size() - 1) +
					 padding + labelPadding;
				int x1 = x0;
				int y0 = height - padding - labelPadding;
				int y1 = y0 - 4;
				if ((i % ((int)((dataPointsX.size() / 20.0)) +
					  1)) == 0) {
					g2.setColor(Color.LIGHT_GRAY);
					g2.drawLine(x0,
						    height - padding -
							    labelPadding - 1 -
							    labelPadding,
						    x1, padding);
					g2.setColor(Color.BLACK);
					String xLabel = dataPointsX.get(i) + "";
					FontMetrics metrics =
						g2.getFontMetrics();
					int labelWidth =
						metrics.stringWidth(xLabel);
					g2.drawString(
						xLabel, x0 - labelWidth / 2,
						y0 + metrics.getHeight() + 3);
				}
				g2.drawLine(x0, y0, x1, y1);
			}
		}
		g2.drawLine(padding + labelPadding,
			    height - padding - labelPadding,
			    padding + labelPadding, padding);
		g2.drawLine(padding + labelPadding,
			    height - padding - labelPadding, width - padding,
			    height - padding - labelPadding);
		Stroke oldStroke = g2.getStroke();
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(2f));
		if (plotType.equals("line")) {
			for (int i = 0; i < dataPointsX.size() - 1; i++) {
				int x1 = i *
						 (width - padding * 2 -
						  labelPadding) /
						 (dataPointsX.size() - 1) +
					 padding + labelPadding;
				int y1 = height - padding - labelPadding -
					 (int)((dataPointsY.get(i) * 1.0) /
					       getMaxYValue() *
					       (height - padding * 2 -
						labelPadding));
				int x2 = (i + 1) *
						 (width - padding * 2 -
						  labelPadding) /
						 (dataPointsX.size() - 1) +
					 padding + labelPadding;
				int y2 = height - padding - labelPadding -
					 (int)((dataPointsY.get(i + 1) * 1.0) /
					       getMaxYValue() *
					       (height - padding * 2 -
						labelPadding));
				g2.drawLine(x1, y1, x2, y2);
			}
		} else if (plotType.equals("scatter")) {
			for (int i = 0; i < dataPointsX.size(); i++) {
				int x = i *
						(width - padding * 2 -
						 labelPadding) /
						(dataPointsX.size() - 1) +
					padding + labelPadding;
				int y = height - padding - labelPadding -
					(int)((dataPointsY.get(i) * 1.0) /
					      getMaxYValue() *
					      (height - padding * 2 -
					       labelPadding));
				g2.fillOval(x - 3, y - 3, 6, 6);
			}
		}
		g2.setStroke(oldStroke);
	}

	private long getMaxYValue()
	{
		long max = Long.MIN_VALUE;
		for (Long y : dataPointsY) {
			max = Math.max(max, y);
		}
		return max;
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(() -> {
			String plotType = "scatter";
			GUIVisualization frame = new GUIVisualization(plotType);
			frame.setVisible(true);
		});
	}
}
