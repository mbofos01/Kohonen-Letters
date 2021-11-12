	import java.util.ArrayList;

/**
 * This object emulates our SOM.
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class Map {
	/**
	 * Each self organizing map consists of NxN nodes, an input vector, a radius
	 * (sigma) value, a rate (n) , J and T values and train and test error.
	 */
	public Node[][] matrix;
	public double[] inputs;
	int T;
	private double J, sigma, n, n0, sigma0;
	public double train_error = 0, test_error = 0;

	/**
	 * Simple map constructor, we need the dimensions of the map, the number of
	 * inputs (thus the size of the vector), the total number of iterations we want
	 * to run and the learning rate we'll use.
	 * 
	 * @param dimension      the size of our map
	 * @param numberOfInputs size of input vector
	 * @param maxIterations  how many epochs we'll run
	 * @param learningRate   learning rate of the map
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

	/**
	 * This method calculates the neighborhood of the winner.
	 * 
	 * @param winner      The winner node
	 * @param random_node A node of the map
	 * @return used in weight updates
	 */
	public double neighborhoodFunction(Node winner, Node random_node) {
		return Math.exp(-(Tools.Euclidean(winner, random_node)) / (2 * Math.pow(sigma, 2)));

	}

	/**
	 * This method updates the learning rate of the map.
	 * 
	 * @param currentIteration the iteration we are currently in
	 */
	public void updateRate(int currentIteration) {
		n = n0 * Math.exp(-currentIteration / T);
	}

	/**
	 * This method updates the radius of the map.
	 * 
	 * @param currentIteration the iteration we are currently in
	 */
	public void updateSigma(int currentIteration) {
		sigma = sigma0 * Math.exp(-currentIteration / J);
	}

	/**
	 * This method is used to print some data for our map.
	 */
	public void printDetails() {
		System.out.println("T: " + T + " J: " + J + " Rate: " + n + " Radius: " + sigma);
	}

	/**
	 * This method is used to update the weights of each node.
	 * 
	 * @param winner The winner node that decides how each node's weights change
	 */
	public void updateWeights(Node winner) {
		for (Node[] s : matrix)
			for (Node node : s) {
				double h = neighborhoodFunction(winner, node);
				for (int i = 0; i < inputs.length; i++)
					node.weights[i] = node.weights[i] + this.n * h * (inputs[i] - node.weights[i]);

			}

	}

	/**
	 * This method adds a minimum distance to a sum in order to calculate the train
	 * error.
	 * 
	 * @param distance Each epochs minimum distance
	 */
	public void addTrainError(double distance) {
		addErrorRun(distance, "train");
	}

	/**
	 * This method adds a minimum distance to a sum in order to calculate the test
	 * error.
	 * 
	 * @param distance Each epochs minimum distance
	 */
	public void addTestError(double distance) {
		addErrorRun(distance, "test");
	}

	/**
	 * This method actually adds the minimum distance to each error train/test.
	 * 
	 * @param distance Each epochs minimum distance
	 * @param state    Flag to determine which error we want to update
	 */
	private void addErrorRun(double distance, String state) {
		if (state.equals("train")) {
			train_error += distance*distance;
		} else if (state.equals("test")) {
			test_error += distance*distance;
		} else {
			System.out.println("Wrong Inputs");
			System.exit(0);
		}

	}

	/**
	 * This method calculates the training error.
	 * 
	 * @param dataSetSize The size of the training set
	 * @return Training error
	 */
	public double getTrainningError(int dataSetSize) {
		return train_error  / dataSetSize;

	}

	/**
	 * This method calculates the testing error.
	 * 
	 * @param dataSetSize The size of the training set
	 * @return Testing error
	 */
	public double getTestingError(int dataSetSize) {
		return  test_error / dataSetSize;

	}

	/**
	 * This method updates the input vector for each epoch.
	 * 
	 * @param new_input The new input vector
	 */
	public void enterNewInput(double new_input[]) {
		for (int i = 0; i < inputs.length; i++)
			inputs[i] = new_input[i];
	}

	/**
	 * This method emulates the LVQ function. We check each training input and
	 * update the winner weights.
	 * 
	 * @param train_inputs  An arraylist of the input vectors
	 * @param train_outputs An arraylist of expected outputs
	 */
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

	/**
	 * This method is used to find the winner node for a input vector.
	 * 
	 * @return Winner Node
	 */
	public Node findWinner() {
		double distance = Integer.MAX_VALUE;
		int min_placeX = -1, min_placeY = -1;
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++) {
				double temp = 0;
				for (int a = 0; a < inputs.length; a++) {
					temp += Math.pow(inputs[a] - matrix[i][j].weights[a], 2);
				}
				
				if (temp <= distance) {
					min_placeX = i;
					min_placeY = j;
					distance = temp;
				}
			}
		matrix[min_placeX][min_placeY].setDistance(distance);
		return matrix[min_placeX][min_placeY];
	}

	/**
	 * This method is used to reset the training and testing errors.
	 */
	public void resetErrors() {
		test_error = 0;
		train_error = 0;
	}

}
