/**
 * This object represents the nodes used in our SOM.
 * 
 * 
 * @author Michail Panagiotis Bofos
 *
 */
public class Node {
	/**
	 * Each node contains a (x,y) set, an array of weights depending on the number
	 * of inputs, a label (for the class we want to assign) and a distance value
	 * from the input.
	 */
	private int x, y;
	public double[] weights;
	private char label;
	private double distance;

	/**
	 * Simple node constructor, we want the x,y point and the number of inputs we
	 * are going to use.
	 * 
	 * @param x              X coordinate
	 * @param y              Y coordinate
	 * @param numberOfInputs We have to look our dataset
	 */
	public Node(int x, int y, int numberOfInputs) {
		this.x = x;
		this.y = y;
		weights = new double[numberOfInputs];
		for (int i = 0; i < numberOfInputs; i++)
			weights[i] = Tools.generateRandomWeights();

	}

	/**
	 * This method returns the X coordinate.
	 * 
	 * @return X coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * This method changes the X coordinate.
	 * 
	 * @param x new X coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * This method returns the Y coordinate.
	 * 
	 * @return Y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * This method changes the Y coordinate.
	 * 
	 * @param x new Y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * This method fetches the label of the node.
	 * 
	 * @return the node's label
	 */
	public char getLabel() {
		return label;
	}

	/**
	 * This method changes the node's label
	 * 
	 * @param label the label to set
	 */
	public void setLabel(char label) {
		this.label = label;
	}

	/**
	 * This method fetches the calculated distance from the input vector.
	 * 
	 * @return the distance from the input vector
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * This method sets the calculated distance from the input vector
	 * 
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Typical equals method based on the XY coordinates.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return x == other.x && y == other.y;
	}

}
