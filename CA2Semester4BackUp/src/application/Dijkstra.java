package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dijkstra {
	public  CostedPath findCheapestPathDijkstra(Node<CityNode> startNode, CityNode lookingfor){
		CostedPath cp=new CostedPath(); 
		List<Node<CityNode>> encountered=new ArrayList<>(), unencountered=new ArrayList<>(); 
		startNode.nodeValue=0; 
		unencountered.add(startNode); 
		Node<CityNode> currentNode;
		do{ 
			currentNode=unencountered.remove(0); 
			encountered.add(currentNode); 
			if(currentNode.town.equals(lookingfor)){ 
				cp.pathList.add(currentNode);
				cp.pathCost=currentNode.nodeValue; 
				while(currentNode!=startNode) {
					boolean foundPrevPathNode=false; 
					for(Node<CityNode> n : encountered) { 
						for(Edge e : n.linklist) 
							if(e.destination==currentNode && currentNode.nodeValue-e.cost==n.nodeValue){ 
								
								cp.pathList.add(0,n); 
								currentNode=n; 
								foundPrevPathNode=true; 
								break; 
							}
						if(foundPrevPathNode) break; 
					}
				}
				
				for(Node<?> n : encountered) n.nodeValue=Integer.MAX_VALUE;
				for(Node<?> n : unencountered) n.nodeValue=Integer.MAX_VALUE;
				return cp; 
			}
			
			for(Edge e : currentNode.linklist) 
				if(!encountered.contains(e.destination)) { 
					e.destination.nodeValue=Integer.min(e.destination.nodeValue, (int) (currentNode.nodeValue+e.cost)); 
					
					unencountered.add(e.destination);
				}
			Collections.sort(unencountered,(n1,n2)->n1.nodeValue-n2.nodeValue); 
		}while(!unencountered.isEmpty());
		return null; 
	}

}
