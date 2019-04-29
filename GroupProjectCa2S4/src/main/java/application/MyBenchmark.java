/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.RunnerException;

@Measurement(iterations = 5,time=1)
@Warmup(iterations = 5,time=1)
@Fork(value=1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)

public class MyBenchmark {
	private CityNode city [] = new CityNode[9];
	@SuppressWarnings("unchecked")
	private Node<CityNode> nodeList [] = new Node[9];
	@Setup
	public void initilize() {
		CityNode a = new CityNode("Bear Island",640,640);
		CityNode b = new CityNode("Winterfell",820,850);
		CityNode c = new CityNode("White harbor",930,1050);
		CityNode d = new CityNode("The Crag",550,1510);
		CityNode e = new CityNode("Riverrun",750,1490);
		CityNode f = new CityNode("Gulltown",1210,1430);
		CityNode g = new CityNode("The Twins",790,1320);
		CityNode h = new CityNode("Castle black",960,530);
		CityNode i = new CityNode("Lotus Port",1330,2760);

		city[0] = a;
		city[1] = b;
		city[2] = c;
		city[3] = d;
		city[4] = e;
		city[5] = f;
		city[6] = g;
		city[7] = h;
		city[8] = i;

		Node<CityNode> a1 = new Node<>(a);
		Node<CityNode> b1 = new Node<>(b);
		Node<CityNode> c1 = new Node<>(c);
		Node<CityNode> d1 = new Node<>(d);
		Node<CityNode> e1 = new Node<>(e);
		Node<CityNode> f1 = new Node<>(f);
		Node<CityNode> g1 = new Node<>(g);
		Node<CityNode> h1 = new Node<>(h);
		Node<CityNode> i1 = new Node<>(i);

		nodeList[0] = a1;
		nodeList[1] = b1;
		nodeList[2] = c1;
		nodeList[3] = d1;
		nodeList[4] = e1;
		nodeList[5] = f1;
		nodeList[6] = g1;
		nodeList[7] = h1;
		nodeList[8] = i1;



	}

	public static void main(String[]args) throws RunnerException, IOException{
		Main.main(args);
	}

	@Benchmark
	public void findingPathDepthFirst() {
		Node<CityNode> d = new Node<CityNode>();
		CityNode s = new CityNode("Lotus Port",1330,2760);

		for(int i= 0;i<nodeList.length;i++) {
			d = nodeList[0];
		}
		List<Node<CityNode>> path = findPathDepthFirst(d,null,s);
		if( path != null) {
			for(Node<CityNode> route : path) {
				//drawPath(d,path,s);
				System.out.println(route.town.getCityName() + " ->");
			}
		}

	}

	public static  List<Node<CityNode>> findPathDepthFirst(Node<CityNode> from, List<Node<CityNode>> encountered, CityNode lookingfor){
		List<Node<CityNode>> result;
		if(from.town.equals(lookingfor)) { //Found it
			result=new ArrayList<>(); //Create new list to store the path info (any List implementation could be used)
			result.add(from); //Add the current node as the only/last entry in the path list
			return result; //Return the path list
		}

		if(encountered==null) 
			encountered=new ArrayList<>(); //First node so create new (empty) encountered list
		encountered.add(from);

		//System.out.println("encounter: " + encountered.size());
		//System.out.println("1: " + from.cityList.size());

		for(Node<CityNode> adjNode : from.cityList) {
			//System.out.println(adjNode.town.getCityName());
			if(!encountered.contains(adjNode)) {
				result=findPathDepthFirst(adjNode,encountered,lookingfor);
				if(result!=null) { //Result of the last recursive call contains a path to the solution node
					result.add(0,from); //Add the current node to the front of the path list
					return result; //Return the path list
				}
			}
		}
		return null;
	}

	public void traverseGraphDepthFirst(Node<CityNode> from,List<Node<CityNode>> encountered,int totalCost) {
		System.out.println(from.town.cityName);
		if(encountered==null) encountered = new ArrayList<>();
		encountered.add(from);

		Collections.sort(from.linklist,(h1,j1)->h1.cost-j1.cost);

		for(Edge a : from.linklist)
			if(!encountered.contains(a.destination)) traverseGraphDepthFirst(a.destination,encountered,totalCost+a.cost);
	}


	@Benchmark
	public void testTraverseGraphDepthFirst() {
		int counter =1;
		Node<CityNode> b1 = new Node<CityNode>();
		if(counter==nodeList.length) {
		for(int i =0;i<nodeList.length;i++) {
			b1 = nodeList[i];
			traverseGraphDepthFirst(b1,null,0);
			counter++;
				break;
			}
		}
	}


	@Benchmark
	public void testFindAllPathsDepthFirst() {
		Node<CityNode> d = new Node<CityNode>();
		CityNode s = new CityNode("Lotus Port",1330,2760);
		List<Node<CityNode>> encountered = null;

		for(int i= 0;i<nodeList.length;i++) {
			d = nodeList[0];
		}

		findAllPathsDepthFirst(d,encountered,s);

	}

	public static List<List<Node<CityNode>>> findAllPathsDepthFirst(Node<CityNode> from, List<Node<CityNode>> encountered, CityNode lookingfor){
		List<List<Node<CityNode>>> result=null, temp2;
		if(from.town.equals(lookingfor)) { //Found it
			List<Node<CityNode>> temp=new ArrayList<>(); //Create new single solution path list
			temp.add(from); //Add current node to the new single path list
			result=new ArrayList<>(); //Create new "list of lists" to store path permutations
			result.add(temp); //Add the new single path list to the path permutations list
			return result; //Return the path permutations list
		}
		if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
		encountered.add(from); //Add current node to encountered list
		for(Node<CityNode> adjNode : from.cityList){
			if(!encountered.contains(adjNode)) {
				temp2=findAllPathsDepthFirst(adjNode,new ArrayList<>(encountered),lookingfor); //Use clone of encountered list
				//for recursive call!
				if(temp2!=null) { //Result of the recursive call contains one or more paths to the solution node
					for(List<Node<CityNode>> x : temp2) //For each partial path list returned
						x.add(0,from); //Add the current node to the front of each path list
					if(result==null) result=temp2; //If this is the first set of solution paths found use it as the result
					else result.addAll(temp2); //Otherwise append them to the previously found paths
				}
			}
		}
		return result;
	}
}



