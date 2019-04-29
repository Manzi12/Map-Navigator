package application;

public class Edge {
	public Node<CityNode> destination;
	int cost;
	int difficulty;
	int safe;
	
	/**
	 * constructor for Edge that takes below variables
	 * @param destination
	 * @param cost
	 * @param difficulty
	 * @param safe
	 */
	public Edge(Node<CityNode> destination,int cost,int difficulty,int safe) {
		this.destination = destination;
		this.cost = cost;
		this.difficulty = difficulty;
		this.safe = safe;
	}

	
	/**
	 * setters and getters for Node destination,cost,difficulty,safe
	 * @return
	 */
	public Node<CityNode> getDestination() {
		return destination;
	}

	public void setDestination(Node<CityNode> destination) {
		this.destination = destination;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getSafe() {
		return safe;
	}

	public void setSafe(int safe) {
		this.safe = safe;
	}
	
	
	
}
