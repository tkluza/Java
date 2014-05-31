package main;

import java.util.ArrayList;

public class GreedyAlgorithm {

	public double beGreedy(ArrayList<City> list) {
		int[] indexTab = new int[list.size()];
		double totalDistance = 0;
		int acc = 0;
		double tempBest = 10000000;
		City temp = null;
		int index = 0;
		for (int i = 0; i < list.size(); i++) {
			if(i == 0)
				indexTab[i] = -1;
			else
				indexTab[i] = i;
		}
		while (acc < indexTab.length) {
			if(tempBest != 10000000) {
				totalDistance += tempBest;
			}			
			temp = list.get(index);
			tempBest = 1000000;
			for (int i = 0; i < indexTab.length; i++) {
				if (indexTab[i] != -1) {		
					if (temp.distanceToCity(list.get(i)) <= tempBest) {
						index = i;
						tempBest = temp.distanceToCity(list.get(i));
						
					}
				}
			}
			acc++;
			indexTab[index] = -1;
		}
		totalDistance += temp.distanceToCity(list.get(0));
		System.out.println("Wynik algorytmu zach³annaego: " + totalDistance);
		return totalDistance;
	}
}
