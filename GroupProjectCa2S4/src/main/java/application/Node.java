package application;

import java.util.ArrayList;
import java.util.List;

public class Node<CityNode> {
	public CityNode town;
	public int nodeValue = Integer.MAX_VALUE;
	
	
	public List<Edge> linklist = new ArrayList<>();
	public List<Node<CityNode>> cityList = new ArrayList<>();
	
	public Node(CityNode town) {
		this.town = town;
	}
	
	
	public Node() {
		
	}
	
	@SuppressWarnings("unchecked")
	public void connectToNodeDirected(Node<CityNode> destNode,int cost,int difficulty) {
		linklist.add(new Edge( (Node<application.CityNode>) destNode, cost, difficulty));
		cityList.add(destNode);
	}
	
	@SuppressWarnings("unchecked")
	public void connectToNodeUndirected(Node<CityNode> destNode,int cost,int difficulty) {
		linklist.add(new Edge ((Node<application.CityNode>) destNode, cost,difficulty));
		destNode.linklist.add(new Edge ((Node<application.CityNode>) this, cost,difficulty));
		cityList.add(destNode);
	}

//	public List<Node<T>> getCityList() {
//		return cityList;
//	}
//
//	public void setCityList(List<Node<T>> cityList) {
//		this.cityList = cityList;
//	}
	
//	public void traverseGraphDepthFirst(Node<?> from,List<Node<?>> encountered) {
//		System.out.println(from.town);
//		if(encountered==null) encountered = new ArrayList<>();
//		encountered.add(from);
//		for(Node<?> cityList : from.cityList)
//			if(!encountered.contains(cityList)) traverseGraphDepthFirst(cityList,encountered);
//	}
	
//	public T Node<?> searchGraphNodeDepthFirst(Node<?> from,List<Node<?>> encountered,T lookingFor){
//		if(from.town.equals(lookingFor)) return from;
//		if(encountered==null) encountered = new ArrayList<>();
//		encountered.add(from);
//		for(Node<?> cityList : from.cityList)
//			if(!encountered.contains(cityList)) {
//			Node<?> result = searchGraphNodeDepthFirst(cityList,encountered,lookingFor);
//		if(result!=null) return result;
//	}
//		return lookingFor;
//	}
	
	

}
