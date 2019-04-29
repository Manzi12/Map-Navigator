package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CostedPath {
	
	public int pathCost=0;
	public List<Node<CityNode>> pathList=new ArrayList<>();


	public static  CostedPath searchGraphDepthFirstCheapestPath(Node<CityNode> from, List<Node<CityNode>> encountered,
			int totalCost, CityNode lookingfor){
		if(from.town.equals(lookingfor)){
			CostedPath cp=new CostedPath(); 
			cp.pathList.add(from); 
			cp.pathCost=totalCost; 
			return cp; 
		}
		if(encountered==null) encountered=new ArrayList<>(); 
		encountered.add(from);
		List<CostedPath> allPaths=new ArrayList<>(); 
		for(Edge e : from.linklist) 
			if(!encountered.contains(e.destination)) 
			{

				CostedPath temp=searchGraphDepthFirstCheapestPath(e.destination,new ArrayList<>(encountered),
						(int) (totalCost+e.cost),lookingfor);
				if(temp==null) continue; 
				temp.pathList.add(0,from); 
				allPaths.add(temp); 
				
			}


		return allPaths.isEmpty() ? null : Collections.min(allPaths, (p1,p2)->p1.pathCost-p2.pathCost);
	}

}

