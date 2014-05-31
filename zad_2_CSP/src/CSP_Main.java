
public class CSP_Main {

	public static void main(String[] args) {

		// String path = "d0/10_3_5_3.mpp";
		String[] paths = new String[] {
				"d0/10_3_5_3.mpp","d0/10_5_8_5.mpp", "d0/10_7_10_7.mpp", 
				"d0/15_3_5_3.mpp", "d0/15_6_10_6.mpp", "d0/15_9_12_9.mpp",
				"imopse_d6/100_20_23_9_D1.mpp", "imopse_d6/100_10_27_9_D2.mpp",
				"imopse_d6/100_5_20_9_D3.mpp", "imopse_d6/200_40_130_9_D4.mpp",
				"imopse_d6/200_20_150_9_D5.mpp","imopse_d6/200_10_135_9_D6.mpp" };
//		Project project = new Project(paths[4]);
		GreedyAlgorithm gAlgorithm = new GreedyAlgorithm();
//		gAlgorithm.CO(project);
//		gAlgorithm.DO(project);
//		project.evaluateProject();
		System.out.println();
		
		for (int i = 0; i < paths.length; i++) {
			Project project = new Project(paths[i]);
			gAlgorithm.CO(project);
			project.evaluateProject();
			System.out.println();
		project.fm.writeFile(project.file, "wyniki/CO/" +  paths[i] + ".xml");
		} 
	}
}