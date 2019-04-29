package application;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class testCAGroup {
	private CityNode [] city = new CityNode[6]; 
	@SuppressWarnings("unchecked")
	private Node<CityNode> [] nodeList = new Node[6];
	//Node<CityNode> k;
	
	@Before
	public void setUp() {
		CityNode a = new CityNode("Bear Island",650,640);
		CityNode b = new CityNode("Winterfell",837,850);
		CityNode c = new CityNode("White harbor",935,1050);
		CityNode d = new CityNode("The Crag",560,1510);
		CityNode e = new CityNode("Riverrun",765,1485);
		CityNode f = new CityNode("Gulltown",1210,1430);
		
		city[0] = a;
		city[1] = b;
		city[2] = c;
		city[3] = d;
		city[4] = e;
		city[5] = f;
		
		
		Node<CityNode> a1 = new Node<>(a);
		Node<CityNode> b1 = new Node<>(b);
		Node<CityNode> c1 = new Node<>(c);
		Node<CityNode> d1 = new Node<>(d);
		Node<CityNode> e1 = new Node<>(e);
		Node<CityNode> f1 = new Node<>(f);
		
		nodeList[0] = a1;
		nodeList[1] = b1;
		nodeList[2] = c1;
		nodeList[3] = d1;
		nodeList[4] = e1;
		nodeList[5] = f1;
		
		a1.connectToNodeUndirected(b1, 50, 1,10);
		b1.connectToNodeUndirected(c1, 20, 3,30);
		d1.connectToNodeUndirected(e1, 20, 3,30);
		
		
		
	}


	@Test
	public void testNumberOfNodes() {
		assertEquals(6,nodeList.length);
	}
	
	@Test
	public void testNumberOfFalseNodes() {
		assertNotEquals(5,nodeList.length);
	}
	
	@Test
	public void testNumberOfCityNodes() {
		assertEquals(6,city.length);
	}
	
	@Test
	public void testNumberOfFalseCityNodes() {
		assertNotEquals(7,city.length);
	}
	
		
//	@Test
//	public void testNodeCities() {
//		assertEquals(.town.getCityName().equals("Bear Island"),"Bear Island");
//	}

}
