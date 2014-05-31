package main;

import java.util.ArrayList;

public class Population {

	ArrayList<Individual> populationList = new ArrayList<>();
	double best;
	double average;
	double worst;
	int bestCityName;

	public Population() {

	}

	public void createFirstPopulation(Individual individual, int populationSize) {
		for (int i = 0; i < populationSize; i++) {
			populationList.add(new Individual(individual.getIndividualList()));
		}
	}

	public ArrayList<Individual> getPopulationList() {
		return populationList;
	}

	public void evaluatePopulation() {
		double distance;
		for (Individual individual : populationList) {
			//individual.totalIndividualDistance = 0;
			individual.countTotalIndividualDistance();
			
			distance = individual.getTotalIndividualDistance();
			average += distance;
			if (best == 0) {
				best = distance;
			} else {
				if (distance <= best) {
				best = distance;
				}
			}
			if (distance >= worst) {
				worst = distance;
			}
		}
		average = average / populationList.size();
		/*
		 * System.out.println("best: " + best); System.out.println("worst: " +
		 * worst); System.out.println("average: " + average);
		 */
	}

	public Individual getBestInPopulation() {

		double distance = 0;
		double bestPop = worst;
		int index = 0;
		for (int i = 0; i < populationList.size(); i++) {
			distance = populationList.get(i).getTotalIndividualDistance();
			if (distance <= bestPop) {
				bestPop = distance;
				index = i;
			}
		}
		// System.out.println(index +" "+
		// populationList.get(index).getTotalIndividualDistance());
		return populationList.get(index);
	}

	public double getBestTour() {
		return best;
	}

	public double getWorstTour() {
		return worst;
	}

	public double getAverageTour() {
		return average;
	}

	public void printPopulation() {
		int i = 0;
		for (Individual individual : populationList) {
			System.out.println("To populacja numer " + (i + 1)
					+ " a droga wynosi "
					+ individual.getTotalIndividualDistance());
			for (City city : populationList.get(i).individualList) {
				System.out.println(city.toString());
			}
			i++;
		}

	}
}
