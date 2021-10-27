public class Node {
	private int x, y;
	private double[] weights;

	public Node(int x, int y, int numberOfInputs) {
		this.x = x;
		this.y = y;
		weights = new double[numberOfInputs];
		for (int i = 0; i < numberOfInputs; i++)
			weights[i] = Tools.generateRandomWeights();

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public void printNode() {
		System.out.println("============================================================");
		System.out.println("Place: ( " + x + " , " + y + " )");
		System.out.println("Weights: ");
		for (int i = 0; i < weights.length; i++)
			System.out.print(weights[i] + " ");
		System.out.println("\n============================================================");
	}

	
}
