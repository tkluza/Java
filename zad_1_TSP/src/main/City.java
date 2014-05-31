package main;

public class City {

	double x;
	double y; 
	int cityName;
	
	public City() {
		
	}

	public City(int i) {
		this.cityName = i;
		this.x = (int) (Math.random() * 100);
		this.y = (int) (Math.random() * 100);

	}

	public City(int cityName, double x, double y) {
		this.cityName = cityName;
		this.x = x;
		this.y = y;
	}

	public int getCityName() {
		return this.cityName;
	}

	public double getCityX() {
		return this.x;
	}

	public double getCityY() {
		return this.y;
	}
	
	public void setCityName(int cityName) {
		this.cityName = cityName;
	}
	
	public void setCityX(double x) {
		this.x = x;
	}
	
	public void setCityY(double y) {
		this.y = y;
	}

	public String toString() {
		return getCityName() + " " + getCityX() + " " + getCityY();
	}

	public double distanceToCity(City city) {
		double xDistance = Math.abs(city.getCityX() - this.getCityX());
		double yDistance = Math.abs(city.getCityY() - this.getCityY());
		return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
	}

}
