package application;

public class Edge {
	public Node<CityNode> destination;
	int cost;
	int difficulty;
	
	public Edge(Node<CityNode> destination,int cost,int difficulty) {
		this.destination = destination;
		this.cost = cost;
		this.difficulty = difficulty;
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

	public int getCost(int cost) {
		return cost;
	}
	
}
