package main;

import java.awt.Color;
import java.util.Random;

import graph.SimpleGraph;

public class FunctionalityExampleMain {

	public static void plotPoint(SimpleGraph graph, char letter, int x, int y) {
		if (letter == 'A')
			graph.addPoint(x, y, Color.PINK);
		else if (letter == 'B')
			graph.addPoint(x, y, Color.RED);
		else if (letter == 'C')
			graph.addPoint(x, y, Color.BLUE);
		else if (letter == 'D')
			graph.addPoint(x, y, Color.GREEN);
		else if (letter == 'E')
			graph.addPoint(x, y, Color.YELLOW);
		else if (letter == 'F')
			graph.addPoint(x, y, Color.BLACK);
		else if (letter == 'G')
			graph.addPoint(x, y, Color.MAGENTA);
		else if (letter == 'H')
			graph.addPoint(x, y, Color.ORANGE);
		else if (letter == 'I')
			graph.addPoint(x, y, Color.RED.darker());
		else if (letter == 'J')
			graph.addPoint(x, y, Color.ORANGE.darker());
		else if (letter == 'K')
			graph.addPoint(x, y, Color.CYAN);
		else if (letter == 'L')
			graph.addPoint(x, y, Color.YELLOW.darker());
		else if (letter == 'M')
			graph.addPoint(x, y, Color.MAGENTA.darker());
		else if (letter == 'N')
			graph.addPoint(x, y, Color.CYAN);
		else if (letter == 'O')
			graph.addPoint(x, y, Color.PINK.darker());
		else if (letter == 'P')
			graph.addPoint(x, y, Color.GREEN.darker());
		else if (letter == 'Q')
			graph.addPoint(x, y, Color.RED.darker().darker());
		else if (letter == 'R')
			graph.addPoint(x, y, Color.GRAY);
		else if (letter == 'S')
			graph.addPoint(x, y, Color.GRAY.darker());
		else if (letter == 'T')
			graph.addPoint(x, y, Color.BLUE.brighter());
		else if (letter == 'U')
			graph.addPoint(x, y, Color.GREEN.brighter());
		else if (letter == 'V')
			graph.addPoint(x, y, Color.YELLOW.brighter());
		else if (letter == 'W')
			graph.addPoint(x, y, Color.RED.brighter().brighter());
		else if (letter == 'X')
			graph.addPoint(x, y, Color.MAGENTA.brighter().brighter());
		else if (letter == 'Y')
			graph.addPoint(x, y, Color.PINK.darker().darker().darker());
		else if (letter == 'W')
			graph.addPoint(x, y, Color.CYAN.brighter());
		else if (letter == 'Z')
			graph.addPoint(x, y, Color.RED.darker().darker().darker());
	}

	public static void main(String[] args) throws InterruptedException {

		// Create the graph object
		SimpleGraph graph = new SimpleGraph();

		// Launch a window showing the graph
		graph.display();

		Random random = new Random();

		// ================================================================
		// Adding points to the graph

		// Add first set of points to the graph
		for (int i = 0; i < 10000; i++) {
			int x = (int) random.nextGaussian() * 2;
			int y = (int) random.nextGaussian() * 2;
			plotPoint(graph, 'Z', x, y);

		}
		graph.repaint();

	}

	public static void errorPoint(SimpleGraph graph, Double double1, int epoch, int flag) {
		if (flag == 0)
			graph.addPoint(epoch, double1, Color.PINK);
		else
			graph.addPoint(epoch, double1, Color.GREEN.darker());

	}

}
