package main;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class GeneticAlgorithm {
	
	Population population;
	ArrayList<Integer> randomTournamentList;
	ArrayList<Integer> randomMutationList;
	double[][] summary;
	public GeneticAlgorithm() {

	}
	
	public void startGeneticAlgorithm(int populationSize, int generation, double crossoverProbability, double mutationProbabiltity,int tournamentSize, String filePath) {
		int generationAcc = 0;
		boolean crossed;
		boolean mutated;
		FileManager fm = new FileManager();
		ArrayList<City> citiesList = fm.readCitiesFile(filePath);
		Individual firstIndividual = new Individual(citiesList);
		Individual child = null;
		Individual child2 = null;
		Individual parent1;
		Individual parent2;
		Population firstPopulation = new Population();
		population = firstPopulation;
		Population oldPop;
		summary = new double[generation][4];
		firstPopulation.createFirstPopulation(firstIndividual, populationSize);
		
		//creating another generation
		while(generationAcc <= generation - 1) {
			population.evaluatePopulation();
			summary[generationAcc][0] = population.getBestTour();
			summary[generationAcc][1] = population.getAverageTour();
			summary[generationAcc][2] = population.getWorstTour();
			System.out.println("Pokolenie numer: " + (generationAcc + 1) + " best: " + summary[generationAcc][0]  + " average: " + summary[generationAcc][1] + " worst " + summary[generationAcc][2]);
			Individual tempBest = new Individual();
			tempBest = population.getBestInPopulation();
//			System.out.println(tempBest.getTotalIndividualDistance());
			//creating new population using genethic algorithm's methods
			oldPop = population;
			population = new Population();
			population.populationList.add(tempBest);
			while(population.populationList.size() <= populationSize ) {
				crossed = false;
				mutated = false;
				parent1 = selectionFromTournament(oldPop, populationSize, tournamentSize);
				parent2 = selectionFromTournament(oldPop, populationSize, tournamentSize);
				if(Math.random() <= crossoverProbability) {
					child = crossover(parent1, parent2);
					child2 = crossover(parent2, parent1);
					crossed = true;
				}
				if(crossed) {
					if(Math.random() <= mutationProbabiltity) {
						child = mutation(child);
						child2 = mutation(child2);
						mutated = true;
/*						System.out.println("po krzyzowaniu i mutowaniu");
						for(City city : child.individualList) {
							System.out.println(city) ;
						}
						System.out.println("droga " + child.totalIndividualDistance);
						System.out.println();*/
						population.populationList.add(child);
						population.populationList.add(child2);
					} else {
/*						System.out.println("po krzyzowaniu");
						for(City city : child.individualList) {
							System.out.println(city) ;
						}
						child.countTotalIndividualDistance();
						System.out.println("droga " + child.totalIndividualDistance);*/
						population.populationList.add(child);
						population.populationList.add(child2);
					}
				}
				else {
					if(Math.random() <= mutationProbabiltity) {
						parent1 = mutation(parent1);
						parent2 = mutation(parent2);
						mutated = true;
						Individual temp1 = new Individual();
						Individual temp2 = new Individual();
						for (int i = 0; i < parent1.individualList.size(); i++) {
							temp1.individualList.add(new City(parent1.individualList.get(i).cityName, parent1.individualList.get(i).x, parent1.individualList.get(i).y)) ;
							temp2.individualList.add(new City(parent2.individualList.get(i).cityName, parent2.individualList.get(i).x, parent2.individualList.get(i).y)) ;
						}
						population.populationList.add(temp1);
						population.populationList.add(temp2);
					} else {
						Individual temp1 = new Individual();
						Individual temp2 = new Individual();
						for (int i = 0; i < parent1.individualList.size(); i++) {
							temp1.individualList.add(new City(parent1.individualList.get(i).cityName, parent1.individualList.get(i).x, parent1.individualList.get(i).y)) ;
							temp2.individualList.add(new City(parent2.individualList.get(i).cityName, parent2.individualList.get(i).x, parent2.individualList.get(i).y)) ;
						}
						population.populationList.add(temp1);
						population.populationList.add(temp2);
					}
				}
			}
			generationAcc++;
		}
	}
	
	public Individual crossover(Individual parent1, Individual parent2) {
		int firstPoint;
		int secondPoint;
		int temp;
		Individual child = new Individual();
		child.creatveIndividualChild(parent1);
//		System.out.println(child.getIndividualList().size());

		firstPoint = secondPoint = temp = 0;
		boolean bool;
		Random rand = new Random();
		while (firstPoint == secondPoint) {
			firstPoint = rand.nextInt(parent1.individualList.size());
			secondPoint = rand.nextInt(parent1.individualList.size());
		}
		if (firstPoint > secondPoint) {
			temp = firstPoint;
			firstPoint = secondPoint;
			secondPoint = temp;
		}
//		System.out.println(firstPoint + " " + secondPoint);

		// copying selected cities to child
		int[] parent2IndexTable = new int[secondPoint - firstPoint + 1];
		int index = 0;
		for (int i = firstPoint; i <= secondPoint; i++) {
			child.individualList.get(i).cityName = parent1.individualList
					.get(i).cityName;
			child.individualList.get(i).x = parent1.individualList.get(i).x;
			child.individualList.get(i).y = parent1.individualList.get(i).y;
		}
/*			if (child.individualList.get(i).cityName != parent2.individualList
					.get(i).cityName) {
				parent2Tab[index] = parent2.individualList.get(i).cityName;
				parent1Tab[index] = parent1.individualList.get(i).cityName;
				parent2IndexTable[index] = i;
				System.out.println(parent2Tab[index] + " parent2 " + i);
				System.out.println(parent1Tab[index] + " parent1 \n");
				index++;
			}
		}*/

		// swapping missing cities
		/*for (int i = 0; i < parent2Tab.length; i++) {
			index = -1;
			temp = parent1Tab[i];
			boolean alreadyExist = false;
			for (City city : child.individualList) {
				if (city.cityName == parent2Tab[i])
					alreadyExist = true;
			}
			if (!alreadyExist) {

				for (City city : parent2.individualList) {
					index++;
					if (city.cityName == temp) {
						break;
					}
				}
				if (child.individualList.get(index).cityName == 0) {
					child.individualList.get(index).cityName = parent2.individualList
							.get(parent2IndexTable[i]).cityName;
					child.individualList.get(index).x = parent2.individualList
							.get(parent2IndexTable[i]).x;
					child.individualList.get(index).y = parent2.individualList
							.get(parent2IndexTable[i]).y;
				} else {
					temp = child.individualList.get(index).getCityName();
					index = -1;
					for (City city : parent2.individualList) {
						index++;
						if (city.cityName == temp) {
							break;
						}
					}
					child.individualList.get(index).cityName = parent2.individualList
							.get(index).cityName;
					child.individualList.get(index).x = parent2.individualList
							.get(index).x;
					child.individualList.get(index).y = parent2.individualList
							.get(index).y;
				}
			} else {*/
/*				int acc ;
				int firstFreeIndex = child.individualList.size() - 1;
				for (int p = 0; p < parent2.individualList.size(); p++) {
					acc = 0;
					System.out.println(parent2.individualList.get(p).cityName);
					for (int c = 0 ; c < child.individualList.size() ; c++) {
						if (parent2.individualList.get(p).cityName != child.individualList.get(c).cityName ) {
							acc++;
						}
					}
					if (acc == child.individualList.size()) {
						for (int c = 0 ; c < child.individualList.size() ; c++) {
							if(child.individualList.get(c).cityName == 0) {
								if(c <= firstFreeIndex) {
									firstFreeIndex = c;
								}								
							}
						}
						child.individualList.get(firstFreeIndex).cityName = parent2.individualList.get(p).cityName;
						child.individualList.get(firstFreeIndex).x = parent2.individualList.get(p).x;
						child.individualList.get(firstFreeIndex).y = parent2.individualList.get(p).y;
						firstFreeIndex = child.individualList.size() - 1;
				}
			}*/
		int acc ;
		int firstFreeIndex = secondPoint + 1;
		boolean end = true;
		for (int p = secondPoint + 1; p < parent2.individualList.size(); p++) {
			acc = 0;
			for (int c = 0  ; c < child.individualList.size() ; c++) {
				if (parent2.individualList.get(p).cityName != child.individualList.get(c).cityName ) {
					acc++;
				}
			}
			if (acc == child.individualList.size()) {
				for (int c = secondPoint + 1 ; c < child.individualList.size() ; c++) {
					if(child.individualList.get(c).cityName == 0) {
						if(c <= firstFreeIndex ) {
							firstFreeIndex = c;
						}								
					}
				}
				if (firstFreeIndex == -1) {
					for (int c = 0 ; c < child.individualList.size() ; c++) {
						if(child.individualList.get(c).cityName == 0) {
							if(c <= firstFreeIndex || firstFreeIndex == -1) {
								firstFreeIndex = c;
							}								
						}
					}
				}
				child.individualList.get(firstFreeIndex).cityName = parent2.individualList.get(p).cityName;
				child.individualList.get(firstFreeIndex).x = parent2.individualList.get(p).x;
				child.individualList.get(firstFreeIndex).y = parent2.individualList.get(p).y;
				firstFreeIndex = -1;
		}
			if (p == parent2.individualList.size() - 1) {
				end = true;
			}
			else 
				end = false;
	}
		if (end) {
			
		
			for (int p = 0; p < parent2.individualList.size(); p++) {
				acc = 0;
				for (int c = 0 ; c < child.individualList.size() ; c++) {
					if (parent2.individualList.get(p).cityName != child.individualList.get(c).cityName ) {
						acc++;
					}
				}
				if (acc == child.individualList.size()) {
					for (int c = 0 ; c < child.individualList.size() ; c++) {
						if(child.individualList.get(c).cityName == 0) {
							if(c <= firstFreeIndex) {
								firstFreeIndex = c;
							}								
						}
					}
					if (firstFreeIndex == -1) {
						for (int c = 0 ; c < child.individualList.size() ; c++) {
							if(child.individualList.get(c).cityName == 0) {
								if(c <= firstFreeIndex || firstFreeIndex == -1) {
									firstFreeIndex = c;
								}								
							}
						}
					}
					child.individualList.get(firstFreeIndex).cityName = parent2.individualList.get(p).cityName;
					child.individualList.get(firstFreeIndex).x = parent2.individualList.get(p).x;
					child.individualList.get(firstFreeIndex).y = parent2.individualList.get(p).y;
					firstFreeIndex = - 1;
				}
			}
		}
		
	
					

/*		for (City city : child.individualList) {
			index++;
			if (city.cityName == 0) {
				city.cityName = parent2.individualList.get(index).cityName;
				city.x = parent2.individualList.get(index).x;
				city.y = parent2.individualList.get(index).y;
			}
		}*/

/*		for (City city : parent1.individualList) {
			System.out.println(city);
		}
		System.out.println();
		for (City city : parent2.individualList) {
			System.out.println(city);
		}
		System.out.println();
		for (City city : child.individualList) {
			System.out.println(city);
		}*/
		return child;
	}
	
	public Individual mutation(Individual child)  {
		int temp = 0;
		boolean bool;
		randomMutationList = new ArrayList<>();
		ArrayList<City> tempList = new ArrayList<>();
		Random rand = new Random();
		int mutationSize = 0;
		while(mutationSize == 0 || mutationSize == 1) {
			mutationSize = rand.nextInt(child.individualList.size());
		}
		for (int i = 0; i <= mutationSize; i++) {
			bool = true;
			while (bool) {
				temp = rand.nextInt(child.individualList.size());
				if (!randomMutationList.contains(temp)) {
					randomMutationList.add(temp);
					bool = false;
				}
			}
		}
		int v;
		int acc = 0;
		for(int n : randomMutationList) {
			v = randomMutationList.get(acc);
			tempList.add(new City(child.individualList.get(v).cityName, child.individualList.get(v).x, child.individualList.get(v).y));
			acc++;
		}
		Collections.shuffle(tempList);

		v = 0;
		acc = 0;
		for(int i = 0; i < randomMutationList.size(); i++) {
			v = randomMutationList.get(i);
			child.individualList.get(v).cityName = tempList.get(i).cityName;
			child.individualList.get(v).x = tempList.get(i).x;
			child.individualList.get(v).y = tempList.get(i).y;
		}
		tempList.clear();
		return child;
	}
	
	public void createRandomTournamentList(int populationSize,
			int tournamentSize) {
		int temp = 0;
		boolean bool;
		randomTournamentList = new ArrayList<>();
		Random rand = new Random();
		for (int i = 0; i < tournamentSize; i++) {
			bool = true;
			while(bool) {
				temp = rand.nextInt(populationSize);
				if (!randomTournamentList.contains(temp)) {
					randomTournamentList.add(temp);
					bool = false;
				}
			}
		}
	}

	public Individual selectionFromTournament(Population population, int populationSize,
			int tournamentSize) {
		createRandomTournamentList(populationSize, tournamentSize);
		Individual best = population.populationList.get(0);
		for (int n : randomTournamentList) {
			if (population.populationList.get(n).getTotalIndividualDistance() <= best
					.getTotalIndividualDistance()) {
				best = population.populationList.get(n);
			}
//			System.out.println("Droga "
//					+ n
//					+ " wynosi "
//					+ population.populationList.get(n)
//							.getTotalIndividualDistance());
		}
//		System.out.println("Droga najlepszego z turnieju: "
//				+ best.totalIndividualDistance + "\n");
		return best;
	}
}

