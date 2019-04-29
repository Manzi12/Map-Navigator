package application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class MainController {
	@FXML private ImageView imageView;
	@FXML private Slider zoomSlider;
	@FXML private Button minZoom;
	@FXML private Button maxZoom;
	@FXML private MenuItem pin_info;
	@FXML private MenuButton map_pin1,map_pin2;
	@FXML private ScrollPane mapScrollpane;
	@FXML private ChoiceBox<String> sourcelist = new ChoiceBox<String>();
	@FXML private ChoiceBox<String> destinationlist = new ChoiceBox<String>();
	@FXML private ChoiceBox<String> waypointList = new ChoiceBox<String>();
	@FXML private ChoiceBox<String> cityavoidance = new ChoiceBox<String>();
	@FXML private Pane mypane;
	Group zoomGroup;
	private CityNode [] city = new CityNode[38]; 
	@SuppressWarnings("unchecked")
	private Node<CityNode> [] nodeList = new Node[38];
	private Dijkstra dijkstra = new Dijkstra();
	private ArrayList<String> allcity = new ArrayList<>();
	private List<Node<CityNode>> path;
	List<List<Node<CityNode>>> allPaths;

	
	/**
	 * this is a function that initilize the varibales and our database to use
	 * for this project
	 */
	public void initialize() {

		zoomSlider.setMin(0.5);
		zoomSlider.setMax(1.5);
		zoomSlider.setValue(1.0);
		zoomSlider.valueProperty().addListener((o,oldValue,newValue) -> zoom((Double) newValue));

		Group contentGroup = new Group();
		zoomGroup = new Group();
		contentGroup.getChildren().add(zoomGroup);
		zoomGroup.getChildren().add(mapScrollpane.getContent());
		mapScrollpane.setContent(contentGroup);
		map_pin1.setVisible(false);
		map_pin2.setVisible(false);

		sourcelist.getItems().addAll("Bear Island","Winterfell","White harbor","The Crag","Riverrun","Gulltown"
				,"The Twins","Castle black","Lotus Port","Pentos","Braavos","Sunspear","Highgarden","Kings Landing",
				"Castle Rock","Borrowtown","Sarnath","Tyrosh","Gragonstone","Moat Cailin","Pyke","Faircastle","Lannisport","The Eyrie",
				"Harrenhal","Storms End","The Deadfort","Vaes Khadork","Selhorys","The Sorrows","Ar Noy","Qohor","Myr","Choyan Drohe","Norvos",
				"Lorath","Oldtown");


		destinationlist.getItems().addAll("Bear Island","Winterfell","White harbor","The Crag","Riverrun","Gulltown"
				,"The Twins","Castle black","Lotus Port","Pentos","Braavos","Sunspear","Highgarden","Kings Landing",
				"Castle Rock","Borrowtown","Sarnath","Tyrosh","Gragonstone","Moat Cailin","Pyke","Faircastle","Lannisport","The Eyrie",
				"Harrenhal","Storms End","The Deadfort","Vaes Khadork","Selhorys","The Sorrows","Ar Noy","Qohor","Myr","Choyan Drohe","Norvos",
				"Lorath","Oldtown");



		sourcelist.getSelectionModel().selectedItemProperty().addListener((o,oldValue,newValue) -> {
			if(newValue != null) {
				for(int i = 0;i<city.length;i++) {
					if(city[i].getCityName().equals(newValue)) {
						map_pin1.setLayoutX(city[i].getX() -24); 
						map_pin1.setLayoutY(city[i].getY() -60); 
						map_pin1.setVisible(true);
						break;
					}

				}
			}

		});


		destinationlist.getSelectionModel().selectedItemProperty().addListener((o,oldValue,newValue) -> {
			if(newValue != null) {
				for(int i = 0;i<city.length;i++) {
					if(city[i].getCityName().equals(newValue)) {
						map_pin2.setLayoutX(city[i].getX() -24);
						map_pin2.setLayoutY(city[i].getY() -60);
						//System.out.println("***********");
						map_pin2.setVisible(true);
						break;
					}

				}
			}

		});

		System.out.println(" \n Mypane Children: " + mypane.getChildren().size());
		main();
	}

	/**
	 * this is is like a database to us,it has all the nodes and the citynode and they have 
	 * been initilized there and called in the initilize function 
	 */
	public void main() {
		CityNode a = new CityNode("Bear Island",650,640);
		CityNode b = new CityNode("Winterfell",837,850);
		CityNode c = new CityNode("White harbor",935,1050);
		CityNode d = new CityNode("The Crag",560,1510);
		CityNode e = new CityNode("Riverrun",765,1485);
		CityNode f = new CityNode("Gulltown",1210,1430);
		CityNode g = new CityNode("The Twins",790,1320);
		CityNode h = new CityNode("Castle black",975,530);
		CityNode i = new CityNode("Lotus Port",1330,2760);
		CityNode j = new CityNode("Pentos",1460,1690);
		CityNode k = new CityNode("Braavos",1450,1260);
		CityNode l = new CityNode("Sunspear",1210,2200);
		CityNode m = new CityNode("Highgarden",643,1940);
		CityNode n = new CityNode("Kings Landing",990,1710);
		CityNode o = new CityNode("Castle Rock",580,1590);
		CityNode p = new CityNode("Borrowtown",670,1040);
		CityNode q = new CityNode("Sarnath",2460,1730);
		CityNode r = new CityNode("Tyrosh",1350,1990);
		CityNode s = new CityNode("Gragonstone",1190,1590);
		CityNode t = new CityNode("Moat Cailin",835,1100);
		CityNode u = new CityNode("Pyke",535,1415);
		CityNode v = new CityNode("Faircastle",513,1548);
		CityNode w = new CityNode("Lannisport",535,1650);
		CityNode x = new CityNode("The Eyrie",1040,1390);
		CityNode y = new CityNode("Harrenhal",910,1540);
		CityNode z = new CityNode("Storms End",1130,1880);
		CityNode aa = new CityNode("The Deadfort",1040,810);
		CityNode bb = new CityNode("Oldtown",520,2080);
		CityNode cc = new CityNode("Lorath",1650,1310);
		CityNode dd = new CityNode("Norvos",1720,1540);
		CityNode ee = new CityNode("Choyan Drohe",1600,1660);
		CityNode ff = new CityNode("Ny Sar",1750,1740);
		CityNode gg = new CityNode("Myr",1550,1960);
		CityNode hh = new CityNode("Qohor",1970,1690);
		CityNode ii = new CityNode("Ar Noy",1870,1780);
		CityNode jj = new CityNode("The Sorrows",1800,1940);
		CityNode kk = new CityNode("Selhorys",1830,2070);
		CityNode ll = new CityNode("Vaes Khadork",2190,1680);


		city[0] = a;
		city[1] = b;
		city[2] = c;
		city[3] = d;
		city[4] = e;
		city[5] = f;
		city[6] = g;
		city[7] = h;
		city[8] = i;
		city[9] = j;
		city[10] = k;
		city[11] = l;
		city[12] = m;
		city[13] = n;
		city[14] = o;
		city[15] = p;
		city[16] = q;
		city[17] = r;
		city[18] = s;
		city[19] = t;
		city[20] = u;
		city[21] = v;
		city[22] = w;
		city[23] = x;
		city[24] = y;
		city[25] = z;
		city[26] = aa;
		city[27] = bb;
		city[28] = cc;
		city[29] = dd;
		city[30] = ee;
		city[31] = ff;
		city[32] = gg;
		city[33] = hh;
		city[34] = ii;
		city[35] = jj;
		city[36] = kk;
		city[37] = ll;


		Node<CityNode> a1 = new Node<>(a);
		Node<CityNode> b1 = new Node<>(b);
		Node<CityNode> c1 = new Node<>(c);
		Node<CityNode> d1 = new Node<>(d);
		Node<CityNode> e1 = new Node<>(e);
		Node<CityNode> f1 = new Node<>(f);
		Node<CityNode> g1 = new Node<>(g);
		Node<CityNode> h1 = new Node<>(h);
		Node<CityNode> i1 = new Node<>(i);
		Node<CityNode> j1 = new Node<>(j);
		Node<CityNode> k1 = new Node<>(k);
		Node<CityNode> l1 = new Node<>(l);
		Node<CityNode> m1 = new Node<>(m);
		Node<CityNode> n1 = new Node<>(n);
		Node<CityNode> o1 = new Node<>(o);
		Node<CityNode> p1 = new Node<>(p);
		Node<CityNode> q1 = new Node<>(q);
		Node<CityNode> r1 = new Node<>(r);
		Node<CityNode> s1 = new Node<>(s);
		Node<CityNode> t1 = new Node<>(t);
		Node<CityNode> u1 = new Node<>(u);
		Node<CityNode> v1 = new Node<>(v);
		Node<CityNode> w1 = new Node<>(w);
		Node<CityNode> x1 = new Node<>(x);
		Node<CityNode> y1 = new Node<>(y);
		Node<CityNode> z1 = new Node<>(z);
		Node<CityNode> aa1 = new Node<>(aa);
		Node<CityNode> bb1 = new Node<>(bb);
		Node<CityNode> cc1 = new Node<>(cc);
		Node<CityNode> dd1 = new Node<>(dd);
		Node<CityNode> ee1 = new Node<>(ee);
		Node<CityNode> ff1 = new Node<>(ff);
		Node<CityNode> gg1 = new Node<>(gg);
		Node<CityNode> hh1 = new Node<>(hh);
		Node<CityNode> ii1 = new Node<>(ii);
		Node<CityNode> jj1 = new Node<>(jj);
		Node<CityNode> kk1 = new Node<>(kk);
		Node<CityNode> ll1 = new Node<>(ll);


		nodeList[0] = a1;
		nodeList[1] = b1;
		nodeList[2] = c1;
		nodeList[3] = d1;
		nodeList[4] = e1;
		nodeList[5] = f1;
		nodeList[6] = g1;
		nodeList[7] = h1;
		nodeList[8] = i1;
		nodeList[9] = j1;
		nodeList[10] = k1;
		nodeList[11] = l1;
		nodeList[12] = m1;
		nodeList[13] = n1;
		nodeList[14] = o1;
		nodeList[15] = p1;
		nodeList[16] = q1;
		nodeList[17] = r1;
		nodeList[18] = s1;
		nodeList[19] = t1;
		nodeList[20] = u1;
		nodeList[21] = v1;
		nodeList[22] = w1;
		nodeList[23] = x1;
		nodeList[24] = y1;
		nodeList[25] = z1;
		nodeList[26] = aa1;
		nodeList[27] = bb1;
		nodeList[28] = cc1;
		nodeList[29] = dd1;
		nodeList[30] = ee1;
		nodeList[31] = ff1;
		nodeList[32] = gg1;
		nodeList[33] = hh1;
		nodeList[34] = ii1;
		nodeList[35] = jj1;
		nodeList[36] = kk1;
		nodeList[37] = ll1;

		a1.connectToNodeUndirected(h1, 55, 1,10);
		a1.connectToNodeUndirected(aa1, 60, 1,10);
		a1.connectToNodeUndirected(b1, 50, 1,10);
		h1.connectToNodeUndirected(aa1, 30, 3,30);
		h1.connectToNodeUndirected(b1, 30, 3,30);
		aa1.connectToNodeUndirected(b1, 15, 1,10);
		aa1.connectToNodeUndirected(c1, 20, 1,10);
		b1.connectToNodeUndirected(c1, 20, 3,30);
		b1.connectToNodeUndirected(p1, 20, 5,50);
		p1.connectToNodeUndirected(t1, 15, 3,30);
		p1.connectToNodeUndirected(c1, 20, 1,10);
		c1.connectToNodeUndirected(t1, 10, 5,50);
		t1.connectToNodeUndirected(g1, 20, 3,30);
		g1.connectToNodeUndirected(u1, 50, 5,50);
		g1.connectToNodeUndirected(x1, 40, 5,50);
		g1.connectToNodeUndirected(e1, 25, 5,50);
		g1.connectToNodeUndirected(y1, 20, 1,10);
		u1.connectToNodeUndirected(d1, 95, 5,50);
		u1.connectToNodeUndirected(v1, 50, 5,50);
		d1.connectToNodeUndirected(e1, 20, 3,30);
		e1.connectToNodeUndirected(v1, 40, 1,10);
		e1.connectToNodeUndirected(y1, 20, 1,10);
		e1.connectToNodeUndirected(m1, 40, 3,30);
		x1.connectToNodeUndirected(f1, 30, 1,10);
		x1.connectToNodeUndirected(s1, 50, 5,50);
		y1.connectToNodeUndirected(n1, 30, 1,10);
		e1.connectToNodeUndirected(w1, 30, 5,50);
		v1.connectToNodeUndirected(w1, 30, 5,50);
		w1.connectToNodeUndirected(m1, 35, 5,50);
		m1.connectToNodeUndirected(n1, 35, 5,50);
		n1.connectToNodeUndirected(z1, 25, 5,50);
		s1.connectToNodeUndirected(z1, 50, 1,50);
		s1.connectToNodeUndirected(x1, 30, 3,30);
		n1.connectToNodeUndirected(s1, 30, 3, 20);
		y1.connectToNodeUndirected(s1, 25, 2,20);
		z1.connectToNodeUndirected(r1, 40, 1,10);
		m1.connectToNodeUndirected(bb1, 20, 1,10);
		r1.connectToNodeUndirected(l1, 60, 5,50);
		r1.connectToNodeUndirected(gg1, 40, 1,10);
		j1.connectToNodeUndirected(z1, 50, 3,30);
		r1.connectToNodeUndirected(gg1, 40, 2,20);
		cc1.connectToNodeUndirected(dd1, 50, 4,40);
		dd1.connectToNodeUndirected(ff1, 40, 3,30);
		gg1.connectToNodeUndirected(kk1, 30, 2,20);
		hh1.connectToNodeUndirected(ii1, 30, 2,20);
		gg1.connectToNodeUndirected(jj1, 20, 1,10);
		kk1.connectToNodeUndirected(ll1, 60, 4,40);
		ee1.connectToNodeUndirected(ff1, 20, 1,10);
		j1.connectToNodeUndirected(ee1, 10, 1,10);
		dd1.connectToNodeUndirected(ii1, 30, 3,30);
		jj1.connectToNodeUndirected(ll1, 40, 3,30);
		jj1.connectToNodeUndirected(kk1, 20, 1,10);
		ll1.connectToNodeUndirected(q1, 20, 1,10);
		hh1.connectToNodeUndirected(ll1, 30, 2,20);
		k1.connectToNodeUndirected(cc1, 50, 3,30);
		dd1.connectToNodeUndirected(hh1, 40, 3,30);
		ee1.connectToNodeUndirected(dd1, 40, 3,30);
		ii1.connectToNodeUndirected(ll1, 30, 2,20);
		q1.connectToNodeUndirected(jj1, 40, 3,30);
		j1.connectToNodeUndirected(gg1, 40, 2,20);
		e1.connectToNodeUndirected(n1, 30, 1,10);
		s1.connectToNodeUndirected(j1, 50, 3,30);
		f1.connectToNodeUndirected(k1, 50, 3,30);
		j1.connectToNodeUndirected(f1, 50, 3,30);





		System.out.println("Recursive depth first trans");
		System.out.println("-----------------------------");
		traverseGraphDepthFirst(b1,null,0);
		System.out.print("-----------------------------------------");

	}



	/**
	 * this function provides a node and the cityNode and the list for the fuction
	 * of generatin the single route
	 */
	public void generatingSinglePath() {
		if(mypane.getChildren().size()>3)
			mypane.getChildren().remove(3, mypane.getChildren().size());
		CityNode s = new CityNode("",0,0);
		Node<CityNode> d = new Node<>();
		String str ="";



		for(int i = 0;i<nodeList.length;i++) {
			if(sourcelist.getSelectionModel().getSelectedItem().equals(nodeList[i].town.getCityName())) {
				d = nodeList[i];
			}
		}

		System.out.println("Coming from : " + d.town.getCityName());

		for(int i = 0;i<city.length;i++) {
			if(destinationlist.getSelectionModel().getSelectedItem().equals(city[i].getCityName())) {
				s = city[i];
			}
		}

		System.out.println("Going to : " + s.getCityName());
		System.out.println("---------------------------------------");		
		System.out.println("below is the path your looking for \n");

		List<Node<CityNode>> path =  findPathDepthFirst(d,null,s);


		if( path != null) {
			for(Node<CityNode> route : path) {
				System.out.println(route.town.getCityName() + " ->");				
			}
		}

		if( path != null) {
			for(int i=0;i<path.size()-1;i++) {
				drawPath(path.get(i).town.getX(),path.get(i).town.getY(),path.get(i+1).town.getX(),path.get(i+1).town.getY());
			}
		}

		int counter = 1;
		allPaths = findAllPathsDepthFirst(d,null,s);
		for(List<Node<CityNode>> p : allPaths) {
			counter++;
			for(Node<CityNode> city : p) { 
				str = city.town.getCityName();
				System.out.println(str + " ->");
				allcity.add(str);	
			}
		}
		System.out.println("the pathList size is " + counter);
		System.out.println("the boss size is " + allcity.size());
		HashSet<String> waypoint = new HashSet<>();
		//ArrayList<String> waypoint = new ArrayList<>();
		for(int i = 0;i<allcity.size();i++) {
			waypoint .add(allcity.get(i));
		}
		waypointList.getItems().clear();
		waypointList.getItems().addAll(waypoint);
		cityavoidance.getItems().clear();
		cityavoidance.getItems().addAll(waypoint);
	

	}


	/**
	 * this function provides a node and the cityNode and the list for the fuction
	 * of generatin the multiple route
	 */
	public void generatingMultplePaths() {
		if(mypane.getChildren().size()>3)
			mypane.getChildren().remove(3, mypane.getChildren().size());
		CityNode s = new CityNode("",0,0);
		Node<CityNode> d = new Node<>();
		String str = "";
		//int counter = 0;

		for(int i = 0;i<nodeList.length;i++) {
			if(sourcelist.getSelectionModel().getSelectedItem().equals(nodeList[i].town.getCityName())) {
				d = nodeList[i];
			}
		}

		System.out.println("Coming from : " + d.town.getCityName());

		for(int i = 0;i<city.length;i++) {
			if(destinationlist.getSelectionModel().getSelectedItem().equals(city[i].getCityName())) {
				s = city[i];
			}
		}

		System.out.println("Going to : " + s.getCityName());
		System.out.println("---------------------------------------");		
		System.out.println("below is the path your looking for \n");


		allPaths = findAllPathsDepthFirst(d,null,s);


		int counter = 1;
		for(List<Node<CityNode>> p : allPaths) {
			System.out.println("\nPath  " + (counter++)+ "\n----------");
			for(Node<CityNode> city : p) { 
				str = city.town.getCityName();
				System.out.println(str + " ->");
			}

			if( p != null) {
				for(int i=0;i<p.size()-1;i++) {
					drawPath(p.get(i).town.getX(),p.get(i).town.getY(),p.get(i+1).town.getX(),p.get(i+1).town.getY());
				}
			}
		}

	}



	/**
	 * this is a huge function that deals with getting the easiest path /route 
	 * from the multiple number of route by calculating the average of the
	 * difficulty values and the one with the least average is considered to 
	 * be the easiest and pretty much the safest
	 */

	public void easiestPath() {
		CityNode s = new CityNode("",0,0);
		Node<CityNode> d = new Node<>();
		ArrayList<Double> averages = new ArrayList<>();
		String str = "";
		int diff = 0;
		int sum = 0;
		int diffValue = 0;
		double average = 0;
		double min = 0;


		for(int i = 0;i<nodeList.length;i++) {
			if(sourcelist.getSelectionModel().getSelectedItem().equals(nodeList[i].town.getCityName())) {
				d = nodeList[i];
			}
		}

		System.out.println("Coming from : " + d.town.getCityName());

		for(int i = 0;i<city.length;i++) {
			if(destinationlist.getSelectionModel().getSelectedItem().equals(city[i].getCityName())) {
				s = city[i];
			}
		}

		System.out.println("Going to : " + s.getCityName());
		System.out.println("---------------------------------------");		
		System.out.println("below is the path your looking for \n");

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);

		List<List<Node<CityNode>>> allPaths = findAllPathsDepthFirst(d,null,s);
		int counter = 1;
		for(List<Node<CityNode>> p : allPaths) {
			ArrayList<Integer> difflist = new ArrayList<>();
			System.out.println("\nPath  " + (counter++)+ "\n----------");

			for(Node<CityNode> city : p) { 
				for(int i = 0;i<city.linklist.size();i++) {
					diff = city.linklist.get(i).getDifficulty();
					sum =diff; // there is a bug when i remove this sum the program crashes 
				}

				difflist.add(diff);
				for(int i =0;i<difflist.size()-1;i++) {
					diffValue = difflist.get(i);
					sum+=diffValue;
					average = ((double)sum/p.size());
					average = Double.valueOf(df.format(average));
				}

				str = city.town.getCityName();
				System.out.println(str + " -> " + "difficulty : " + diffValue);

			}

			averages.add(average);

			double max = 0;

			for(int i = 0;i<averages.size()-1;i++) {
				if(max<averages.get(i)) {
					max = averages.get(i);
				}
			}

			min = max;

			for(int i = 0;i<averages.size()-1;i++) {
				if(min>averages.get(i)) {
					min = averages.get(i);
				}
			}



			System.out.println("------------------------------------------------------");
			System.out.println("| "+"total nodes : " + difflist.size() + " sum of difficuty : " + sum + " average : " + average + "| ");
			System.out.println("------------------------------------------------------");

		}
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		System.out.println("the Easiest Route is the least average: " + min);
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	}
	
	
	/**
	 * well the safest work exactily like the easiest,it calculates the total safe 
	 * interger assigned to the edge and adds them all then gets the averages for all 
	 * of the path and then,checks the least average and that is considered as a safest route
	 */
	public void safestRoute() {
		CityNode s = new CityNode("",0,0);
		Node<CityNode> d = new Node<>();
		ArrayList<Double> averages = new ArrayList<>();
		String str = "";
		int safe = 0;
		int sum = 0;
		int safeValue = 0;
		double average = 0;
		double min = 0;


		for(int i = 0;i<nodeList.length;i++) {
			if(sourcelist.getSelectionModel().getSelectedItem().equals(nodeList[i].town.getCityName())) {
				d = nodeList[i];
			}
		}

		System.out.println("Coming from : " + d.town.getCityName());

		for(int i = 0;i<city.length;i++) {
			if(destinationlist.getSelectionModel().getSelectedItem().equals(city[i].getCityName())) {
				s = city[i];
			}
		}

		System.out.println("Going to : " + s.getCityName());
		System.out.println("---------------------------------------");		
		System.out.println("below is the path your looking for \n");

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);

		List<List<Node<CityNode>>> allPaths = findAllPathsDepthFirst(d,null,s);
		int counter = 1;
		for(List<Node<CityNode>> p : allPaths) {
			ArrayList<Integer> safelist = new ArrayList<>();
			System.out.println("\nPath  " + (counter++)+ "\n----------");

			for(Node<CityNode> city : p) { 
				for(int i = 0;i<city.linklist.size();i++) {
					safe = city.linklist.get(i).getSafe();
					sum =safe; // there is a bug when i remove this sum the program crashes 
				}

				safelist.add(safe);
				for(int i =0;i<safelist.size()-1;i++) {
					safeValue = safelist.get(i);
					sum+=safeValue;
					average = ((double)sum/p.size());
					average = Double.valueOf(df.format(average));
				}

				str = city.town.getCityName();
				System.out.println(str + " -> " + "Safe : " + safeValue);

			}

			averages.add(average);

			double max = 0;

			for(int i = 0;i<averages.size()-1;i++) {
				if(max<averages.get(i)) {
					max = averages.get(i);
				}
			}

			min = max;

			for(int i = 0;i<averages.size()-1;i++) {
				if(min>averages.get(i)) {
					min = averages.get(i);
				}
			}



			System.out.println("------------------------------------------------------");
			System.out.println("| "+"total nodes : " + safelist.size() + " sum of difficuty : " + sum + " average : " + average + "| ");
			System.out.println("------------------------------------------------------");

		}
		System.out.println("##########################################");
		System.out.println("the Safest Route is the least average: " + min);
		System.out.println("##########################################");
	}


	/**
	 * well after generating aroute,the city avoidance choicebox on the javafx get populated 
	 * by all the possible cities that one might go through to get to his destination so that if 
	 * you decide to avoid a city from that list should be valid
	 */
	public void cityAvoidance() {
		if(mypane.getChildren().size()>3)
			mypane.getChildren().remove(3, mypane.getChildren().size());
		String cityToAvoid = "";
		cityToAvoid = cityavoidance.getSelectionModel().getSelectedItem();
		List<Node<CityNode>> newPath = new ArrayList<>();
		newPath = null;
		boolean flag = true;
		System.out.println("\n\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		System.out.println(cityToAvoid);


		for(int i= 0;i<allPaths.size()-1;i++) {
			flag =true;
			for(int k =0;k<allPaths.get(i).size()-1;k++) {
				if(allPaths.get(i).get(k).town.getCityName().equals(cityToAvoid)) {
					flag=false;
					break;
				}
			}
			if(flag) {
				newPath = allPaths.get(i);
				break;

			}
		}

		System.out.println();
		System.out.println();

		if(newPath==null)
			newPath = path;
		System.out.println(newPath.size());
		for(int i= 0;i<newPath.size()-1;i++)
			System.out.println(newPath.get(i).town.getCityName());


		if( newPath != null) {
			for(int i=0;i<newPath.size()-1;i++) {
				drawPath(newPath.get(i).town.getX(),newPath.get(i).town.getY(),newPath.get(i+1).town.getX(),newPath.get(i+1).town.getY());
			}
		}

		System.out.println("\n\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	}


	
	/**
	 * well after generating aroute,the city avoidance choicebox on the javafx get populated 
	 * by all the possible cities that one might go through to get to his destination so that if 
	 * you decide to go through acity from that list should be valid
	 */
	public void waypointFuction() {
		if(mypane.getChildren().size()>3)
			mypane.getChildren().remove(3, mypane.getChildren().size());
		String waypointCity = "";
		waypointCity = waypointList.getSelectionModel().getSelectedItem();
		List<Node<CityNode>> newPath = new ArrayList<>();
		newPath = null;
		boolean flag = false;
		System.out.println("\n\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		System.out.println(waypointCity);
		System.out.println();
		System.out.println();

		for(int i= 0;i<allPaths.size()-1;i++) {
			flag =false;
			for(int k =0;k<allPaths.get(i).size()-1;k++) {
				if(allPaths.get(i).get(k).town.getCityName().equals(waypointCity)) {
					flag=true;
					break;
				}
			}
			if(flag) {
				newPath = allPaths.get(i);
				break;

			}
		}

		System.out.println();
		System.out.println();

		if(newPath==null)
			newPath = path;
		System.out.println(newPath.size());
		for(int i= 0;i<newPath.size()-1;i++)
			System.out.println(newPath.get(i).town.getCityName());


		if( newPath != null) {
			for(int i=0;i<newPath.size()-1;i++) {
				drawPath(newPath.get(i).town.getX(),newPath.get(i).town.getY(),newPath.get(i+1).town.getX(),newPath.get(i+1).town.getY());
			}
		}

		System.out.println("\n\nxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
	}
	
	
	/**
	 * well this function takes them coordinates and uses them to draw the lines or edges/links 
	 * between cites
	 * @param fromX source x coordinate
	 * @param fromY source y coordinate
	 * @param toX destination x coordinate
	 * @param toY destination y coordinate
	 */
	public void drawPath(double fromX, double fromY, double toX, double toY) {
		Line line = new Line(fromX,fromY,toX,toY);
		line.setStroke(Color.RED);
		line.setStrokeWidth(5);
		line.getStrokeDashArray().setAll(25d, 20d, 5d, 20d);
		double maxOffset = line.getStrokeDashArray().stream().reduce(0d, (a, b) -> a + b);

		Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(line.strokeDashOffsetProperty(), 0, Interpolator.LINEAR)),
				new KeyFrame(Duration.seconds(2), new KeyValue(line.strokeDashOffsetProperty(), maxOffset, Interpolator.LINEAR)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setRate(-10);
		timeline.play();
		mypane.getChildren().add(line);
	}



	/**
	 * this function calculates the shortest path using the cost of the path 
	 * so pretty its looking for the cheapest path
	 */

	public void shortest() {
		if(mypane.getChildren().size()>3)
			mypane.getChildren().remove(3, mypane.getChildren().size());
		CityNode s = new CityNode("",0,0);
		Node<CityNode> d = new Node<>();


		for(int i = 0;i<nodeList.length;i++) {
			if(sourcelist.getSelectionModel().getSelectedItem().equals(nodeList[i].town.getCityName())) {
				d = nodeList[i];
			}
		}

		System.out.println("Coming from : " + d.town.getCityName());

		for(int i = 0;i<city.length;i++) {
			if(destinationlist.getSelectionModel().getSelectedItem().equals(city[i].getCityName())) {
				s = city[i];
			}
		}

		System.out.println("Going to : " + s.getCityName());
		System.out.println("---------------------------------------");		
		System.out.println("below is the path your looking for \n");

		CostedPath costedPath =  dijkstra.findCheapestPathDijkstra(d, s);

		for(Node<CityNode> n : costedPath.pathList) {
			System.out.println(n.town.getCityName());

		}

		if( costedPath.pathList != null) {
			for(int i=0;i<costedPath.pathList.size()-1;i++) {
				drawPath(costedPath.pathList.get(i).town.getX(),costedPath.pathList.get(i).town.getY(),costedPath.pathList.get(i+1).town.getX(),costedPath.pathList.get(i+1).town.getY());
			}
		}
		System.out.println("\nThe total Path cost is :" + costedPath.pathCost);
	}


	/**
	 * this is a function for an event of zooming in when you click on the 
	 * minus sign in the javafx file
	 * @param event
	 */
	public void zoomIn(ActionEvent event) {
		double SliderVal = zoomSlider.getValue();
		zoomSlider.setValue(SliderVal += 0.1);
	}

	/**
	 * this is a function for zooming out when you click on the plus sign
	 * in the javafx file
	 * @param event
	 */
	public void zoomOut(ActionEvent event) {
		double SliderVal = zoomSlider.getValue();
		zoomSlider.setValue(SliderVal + -0.1);
	}

	/**
	 * zoom fuction 
	 * @param ScaleValue
	 */
	public void zoom(double ScaleValue) {
		double scrollH = mapScrollpane.getHvalue();
		double scrollV = mapScrollpane.getVvalue();
		zoomGroup.setScaleX(ScaleValue);
		zoomGroup.setScaleY(ScaleValue);
		mapScrollpane.setHvalue(scrollH);
		mapScrollpane.setVvalue(scrollV);
	}

	/**
	 * pin movemoent source pin
	 */
	public void pinMove() {
		imageView.setOnMouseClicked(e ->{
			System.out.println("X : "+ e.getX() + "Y : " + e.getY());

			map_pin1.setLayoutX(e.getX()-24);
			map_pin1.setLayoutY(e.getY()-60);

			System.out.println("Pin X : "+ map_pin1.getLayoutX() + "Pin Y : " + map_pin1.getLayoutY());
		});

	}

	/**
	 * pinmovement destinationpin
	 */
	public void pinMove2() {
		imageView.setOnMouseClicked(e ->{
			System.out.println("X : "+ e.getX() + "Y : " + e.getY());

			map_pin2.setLayoutX(e.getX()-24);
			map_pin2.setLayoutY(e.getY()-60);
		});

	}

	
	/**
	 * well this is a function that is called to traverse the routes 
	 * @param from
	 * @param encountered
	 * @param totalCost
	 */
	public void traverseGraphDepthFirst(Node<CityNode> from,List<Node<CityNode>> encountered,int totalCost) {
		System.out.println(from.town.cityName);
		if(encountered==null) encountered = new ArrayList<>();
		encountered.add(from);

		Collections.sort(from.linklist,(h1,j1)->h1.cost-j1.cost);

		for(Edge a : from.linklist)
			if(!encountered.contains(a.destination)) traverseGraphDepthFirst(a.destination,encountered,totalCost+a.cost);
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
