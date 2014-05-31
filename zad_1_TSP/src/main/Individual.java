package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Individual {

	ArrayList<City> individualList ;
	int[] citiesTab;
	double totalIndividualDistance = 0;

	public Individual(ArrayList<City> list) {
		createIndividual(list);
	}
	
	public Individual() {
		individualList = new ArrayList<City>();
	}
	public int[] createIndividual(int size) {

		Random rgen = new Random();
		citiesTab = new int[size];

		for (int i = 0; i < size; i++) {
			citiesTab[i] = i + 1;
		}
		// shuffle
		for (int i = 0; i < citiesTab.length; i++) {
			int randomPosition = rgen.nextInt(citiesTab.length);
			int temp = citiesTab[i];
			citiesTab[i] = citiesTab[randomPosition];
			citiesTab[randomPosition] = temp;
		}
		for (int i = 0; i < size; i++) {
			System.out.println(citiesTab[i]);
		}
		System.out.println(citiesTab.getClass());
		return citiesTab;
	}

	public void createIndividual(ArrayList<City> list) {
		individualList = new ArrayList<>();
		int index = 0;
		for(City city : list) {
			individualList.add(new City(list.get(index).getCityName(), list.get(index).getCityX(), list.get(index).getCityY() ) );
			index++;
		}
		
		Collections.shuffle(individualList);
		/*for (City city : individualList) {
			System.out.println(city.toString());
		}*/
		
//		for(City city : list) { System.out.println(city.toString()); }
	}
	
	public void creatveIndividualChild(Individual parent) {
		individualList = new ArrayList<>();
		for(int i = 0; i < parent.getIndividualList().size(); i++) {
			individualList.add(new City());
		}
	}
 
	public void countTotalIndividualDistance() {
		for (int i = 0; i < individualList.size() - 1; i++) {
			totalIndividualDistance += individualList.get(i).distanceToCity(individualList.get(i+1));
		}
		totalIndividualDistance += individualList.get(individualList.size() - 1).distanceToCity(individualList.get(0));
//		System.out.println("Calkowita droga wynosi: " + totalIndividualDistance);
	}
	
	public double getTotalIndividualDistance() {
		return totalIndividualDistance;
	}
	
	public ArrayList<City> getIndividualList() {
		return individualList;
	}
}
