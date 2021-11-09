import java.util.ArrayList;

public class Map {
	public Node[][] matrix;
	public double[] inputs;
	int T;
	private double J, sigma, n, n0, sigma0;
	private double train_error = 0, test_error = 0;

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

	public void updateWeights(Node winner, char letter) {
		for (Node[] s : matrix)
			for (Node node : s) {
				double h = neighborhoodFunction(winner, node);
				for (int i = 0; i < inputs.length; i++)
					node.weights[i] = node.weights[i] + this.n * h * (inputs[i] - node.weights[i]);

			}

	}

	public void addTrainError(double distance) {
		addErrorRun(distance, "train");
	}

	public void addTestError(double distance) {
		addErrorRun(distance, "test");
	}

	private void addErrorRun(double distance, String state) {
		if (state.equals("train")) {
			train_error += distance;
		} else if (state.equals("test")) {
			test_error += distance;
		} else {
			System.out.println("Wrong Inputs");
			System.exit(0);
		}

	}

	public double getTrainningError(int dataSetSize) {
		return train_error * train_error / dataSetSize;

	}

	public double getTestingError(int dataSetSize) {
		return test_error * test_error / dataSetSize;

	}

	public void printAll() {
		for (Node[] s : matrix)
			for (Node n : s)
				n.printNode();
	}

	public void enterNewInput(double new_input[]) {
		for (int i = 0; i < inputs.length; i++)
			inputs[i] = new_input[i];
	}

	public void LVQ(ArrayList<double[]> train_inputs, ArrayList<Character> train_outputs) {
		for (int i = 0; i < train_outputs.size(); i++) {
			Node winner = this.findWinner();
			if (winner.getLabel() == train_outputs.get(i))
				for (int j = 0; j < winner.weights.length; j++)
					winner.weights[j] = winner.weights[j] + n * (train_inputs.get(i)[j] - winner.weights[j]);
			else
				for (int j = 0; j < winner.weights.length; j++)
					winner.weights[j] = winner.weights[j] - n * (train_inputs.get(i)[j] - winner.weights[j]);

		}

	}

	public Node findWinner() {
		double distance = Integer.MAX_VALUE;
		int min_placeX = -1, min_placeY = -1;
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++) {
				double temp = 0;
				for (int a = 0; a < inputs.length; a++) {
					temp += Math.pow(inputs[a] - matrix[i][j].weights[a], 2);
				}
				matrix[i][j].setDistance(distance);
				if (temp <= distance) {
					min_placeX = i;
					min_placeY = j;
					distance = temp;
				}
			}
		return matrix[min_placeX][min_placeY];
	}

	public void resetErrors() {
		test_error = 0;
		train_error = 0;
	}

}
