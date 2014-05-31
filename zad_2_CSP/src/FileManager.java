import java.io.IOException;

import core.ProjectCloner;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mspdi.MSPDIWriter;


public class FileManager {

		public ProjectFile readFile(String path) {
			ProjectFile project = null;
			try {
				
				project = ProjectCloner.createBaseProject(new MPPReader().read(path), false);
				
			} catch (MPXJException e) {

				System.out.println("b³¹d wczytywania pliku");
			}
			return project;
		}
		
		public void writeFile(ProjectFile project, String path) {
			try {
				new MSPDIWriter().write(project, path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("b³¹d zapisu pliku");
			}
		}
}
