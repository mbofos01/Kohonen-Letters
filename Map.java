import java.util.ArrayList;

public class Map {
	public Node[][] matrix;
	public double[] inputs;
	int T;
	double J, sigma, n, n0, sigma0;

	/**
	 * Sigma -> Radius N -> Rate
	 */

	public Map(int dimension, int numberOfInputs, int maxIterations, double learningRate) {
		matrix = new Node[dimension][dimension];
		inputs = new double[numberOfInputs];
		T = maxIterations;
		n = learningRate;
		n0 = learningRate;
		sigma = dimension / 2.0;
		sigma0 = sigma;
		J = T / (Math.log10(sigma));
		for (int i = 0; i < dimension; i++)
			for (int j = 0; j < dimension; j++)
				matrix[i][j] = new Node(i, j, numberOfInputs);

	}

	public double neighborhoodFunction(Node winner, Node random_node) {
		return Math.exp(-(Tools.Euclidean(winner, random_node)) / (2 * Math.pow(sigma, 2)));

	}

	public void updateRate(int currentIteration) {
		n = n0 * Math.exp(-currentIteration / T);
	}

	public void updateSigma(int currentIteration) {
		sigma = sigma0 * Math.exp(-currentIteration / J);
	}

	public void printDetails() {
		System.out.println("T: " + T + " J: " + J + " Rate: " + n + " Radius: " + sigma);
	}

	public void updateWeights(Node winner) {
		for (Node[] s : matrix)
			for (Node node : s) {
				double h = neighborhoodFunction(winner, node);
				for (int i = 0; i < inputs.length; i++)
					node.weights[i] = node.weights[i] + this.n * h * (inputs[i] - node.weights[i]);
			}

	}

	public void printAll() {
		for (Node[] s : matrix)
			for (Node n : s)
				n.printNode();
	}

}
