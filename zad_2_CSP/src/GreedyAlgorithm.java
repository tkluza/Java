import java.util.List;

import net.sf.mpxj.Resource;
import net.sf.mpxj.Task;
import core.ResourceAssignmentUtilities;
import core.SkillsUtilities;
import core.eval.Eval;
import core.skills.Qualification;

public class GreedyAlgorithm {

	public void CO(Project project) {
//		System.out.println("Algorytm zach³anny - optymalizacja ze wzglêdu na koszt");
		for (Task t : ResourceAssignmentUtilities
				.getTasksWithoutAssignments(project.file)) {
			Qualification qual1 = SkillsUtilities.strToQualification(t
					.getText(1));
			List<Resource> resources = SkillsUtilities
					.resourcesMatchingQualification(qual1,
							project.file.getAllResources());
			
/*			for(Resource resource : resources) {
					System.out.println(resource.getName() + " " + resource.getStandardRate().getAmount());
					
			}*/
			Resource r = resources.get(0);
			for(Resource resource : resources) {
				if(resource.getStandardRate().getAmount() <= r.getStandardRate().getAmount())
					r = resource;
			}
			project.assignResourceToTask(r, t);
		}
	}
	
	public void DO(Project project) {
//		System.out.println("Algorytm zach³anny - optymalizacja ze wzglêdu na czas");
		for (Task task : ResourceAssignmentUtilities.getTasksWithoutAssignments(project.file))
		{
			Qualification qual1 = SkillsUtilities.strToQualification(task.getText(1));
			List<Resource> resources = SkillsUtilities.resourcesMatchingQualification(qual1, project.file.getAllResources());
			project.assignResourceToTask(DO_resourceChoice(resources, task, project), task);

		}
	}
	
	public Resource DO_resourceChoice(List<Resource> resources, Task task, Project project)
	{
		Resource res = null;
		int i = 0;
		double val = Double.MAX_VALUE;
		for (Resource r : resources)
		{
			
			project.assignResourceToTask(r, task);
			if (Eval.getProjectDuration(project.file) < val)
			{
				val = Eval.getProjectDuration(project.file);
				res = r;
			}
			task.getResourceAssignments().get(0).remove();
			i++;
		}
		if (res == null)
		{
			System.out.println();
		}
		return res;
	}
}
