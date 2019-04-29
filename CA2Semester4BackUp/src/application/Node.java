package application;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("hiding")
public class Node<CityNode> {
	public CityNode town;
	public int nodeValue = Integer.MAX_VALUE;
	
	
	public List<Edge> linklist = new ArrayList<>();
	public List<Node<CityNode>> cityList = new ArrayList<>();
	
	/**
	 * Node Constructor that takes in town of type cityNode
	 * @param town
	 */
	public Node(CityNode town) {
		this.town = town;
	}
	
	/**
	 * empty constructor
	 */
	public Node() {
		
	}
	
	
	/**
	 * fuction to connect nodes in directed direction,it takes in Node,cost,
	 * difficulty and safety information
	 * @param destNode
	 * @param cost
	 * @param difficulty
	 * @param safe
	 */
	@SuppressWarnings("unchecked")
	public void connectToNodeDirected(Node<CityNode> destNode,int cost,int difficulty,int safe) {
		linklist.add(new Edge( (Node<application.CityNode>) destNode, cost, difficulty, safe));
		cityList.add(destNode);
	}
	
	/**
	 * fuction to connect nodes in undirected direction,it takes in Node,cost,
	 * difficulty and safety information
	 * @param destNode
	 * @param cost
	 * @param difficulty
	 * @param safe
	 */
	@SuppressWarnings("unchecked")
	public void connectToNodeUndirected(Node<CityNode> destNode,int cost,int difficulty,int safe) {
		linklist.add(new Edge ((Node<application.CityNode>) destNode, cost,difficulty, safe));
		destNode.linklist.add(new Edge ((Node<application.CityNode>) this, cost,difficulty, safe));
		cityList.add(destNode);
	}
	

}
