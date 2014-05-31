package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TSP_Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileManager fm = new FileManager();
		ArrayList<City> citiesList = fm.readCitiesFile("test/29.txt");
/*		fm.showCitiesList(citiesList);
		Population population = new Population();
		population.createFirstPopulation(new Individual(citiesList), 10);
		population.evaluatePopulation();
		population.getBestInPopulation();
		population.printPopulation();
		}*/
/*		Individual test1 = new Individual(citiesList);
		Individual test2 = new Individual(citiesList);
		test2.individualList.get(0).cityName = test1.individualList.get(0).getCityName();
		for(City city : test2.individualList) {
			System.out.println(city);
		}
		System.out.println();
		test2.individualList.get(0).cityName = 2000;
		for(City city : test2.individualList) {
			System.out.println(city);
		}
		System.out.println();
		test2.individualList.get(0).cityName = 23213;
		for(City city : test2.individualList) {
			System.out.println(city);
		}
		System.out.println();
		for(City city : citiesList) {
			System.out.println(city);
		}*/
		
		GeneticAlgorithm gm = new GeneticAlgorithm();
		int pop = 100;
		int gen = 10000;
		double crossP = 0.4;
		double mutP = 0.1;
		int tournament = 5;
		String filePath =  "wyniki/" + 29 + " Pop " + pop + " Gen  " + gen + " CrossP " + crossP + " MutP " + mutP + " T " + tournament  + ".png";
		gm.startGeneticAlgorithm(pop, gen, crossP, mutP, tournament, "test/29.txt");
		GreedyAlgorithm gA = new GreedyAlgorithm();
		double greedySummary = gA.beGreedy(citiesList);		
		fm.createChart(gm.summary, greedySummary, filePath);
		
/*		Individual test1 = new Individual(citiesList);
		Individual test2 = new Individual(citiesList);
		gm.crossover(test1, test2);*/

	}	
}


