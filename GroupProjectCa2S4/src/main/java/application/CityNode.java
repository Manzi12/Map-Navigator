package application;

public class CityNode {
	public String cityName;
	private int x;
	private int y;
	
	
	public CityNode(String cityName,int x,int y) {
		this.cityName = cityName;
		this.x = x;
		this.y = y;
	}
	

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	

}

