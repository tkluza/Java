package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class FileManager {

	public ArrayList<City> readCitiesFile(String path) {

		int cityName = -1;
		double cityX = -1;
		double cityY = -1;

		File file = new File(path);
		Scanner in = null;

		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("nie znaleziono pliku");
		}
		ArrayList<City> citiesList = new ArrayList<City>();
		while (in.hasNext()) {
			cityName = in.nextInt();

			// problem with in.nextDouble(); dont know why
			cityX = Double.parseDouble(in.next());
			cityY = Double.parseDouble(in.next());
			if ((cityName != -1) && (cityX != -1) && (cityY != -1)) {
				citiesList.add(new City(cityName, cityX, cityY));
				cityName = -1;
				cityX = cityY = -1;
			}

		}
		in.close();
		return citiesList;
	}

	public void saveFile(double[][] summaryTab, String filePath) {

		String[][] summaryStringTab = new String [summaryTab.length][summaryTab[0].length + 1];
		for (int i = 0; i < summaryStringTab.length; i++) {
			for (int j = 0; j < summaryStringTab[i].length; j++) {
				summaryStringTab[i][j] = Double.toString(summaryTab[i][j]);
			}
		}

		try {
			FileWriter writer = new FileWriter(filePath);
			for (int i = 0; i < summaryTab.length; i++) {
				for (int j = 0; j < summaryTab[i].length; j++) {
					writer.append(summaryStringTab[i][j]);
					if(j == 0 || j == 1)
						writer.append(" , ");
				}
				writer.append("\n");
			}

			// generate whatever data you want
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createChart(double[][] summary,double greedySummary, String filePath) {
		try {
 
            /* Step - 1: Define the data for the line chart  */
            DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
            for(int i = 0; i<summary.length; i++){
            	 line_chart_dataset.addValue(greedySummary, "greedy", Integer.toString(i));
            	 line_chart_dataset.addValue(summary[i][0], "the best", Integer.toString(i));
            	 line_chart_dataset.addValue(summary[i][1], "average", Integer.toString(i));
            	 line_chart_dataset.addValue(summary[i][2], "the worst", Integer.toString(i));
           
            }
            
            
            /* Step -2:Define the JFreeChart object to create line chart */
            JFreeChart lineChartObject = ChartFactory.createLineChart("GENERATION ALGORITHM - TSP","generation","Total distance",line_chart_dataset,PlotOrientation.VERTICAL,true,true,false);                                     
            /* Step -3 : Write line chart to a file */               
             int width=1100; /* Width of the image */
             int height=480; /* Height of the image */                
             File lineChart=new File(filePath);              
             ChartUtilities.saveChartAsPNG(lineChart,lineChartObject,width,height); 
     }
     catch (Exception i)
     {
         System.out.println(i);
     }
	}
	
	public void showCitiesList(ArrayList<City> list) {
		// Collections.shuffle(list);
		for (City city : list) {
			System.out.println(city.toString());
		}
	}
}
