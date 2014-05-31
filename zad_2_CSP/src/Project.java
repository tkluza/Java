import java.util.List;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Resource;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import core.SkillsUtilities;
import core.conflicts.ConflictFixer;
import core.eval.Eval;
import core.skills.Qualification;

public class Project {

	ProjectFile file;
	FileManager fm = new FileManager();
	String path;

	public Project(String path) {
		this.path = path;
		file = fm.readFile(path);

	}

	public Project() {

	}
	
	public void removeResourceAssignment() {
		for (Task task : file.getAllTasks())
		{
			task.getResourceAssignments().clear();
		}
	}

	public void printProjects() {
		for (Task task : file.getAllTasks()) {
			System.out.println("Zadanie: " + task.getName());
		}
	}

	public void listHierarchy() {
		for (Task task : file.getChildTasks()) {
			System.out.println("Zadanie: " + task.getName());
			listHierarchy(task, " ");
		}
		System.out.println();
	}

	private void listHierarchy(Task task, String indent) {
		for (Task child : task.getChildTasks()) {
			System.out.println(indent + "Zadanie: " + child.getName());
			listHierarchy(child, indent + " ");
		}
	}

	public void showResourceAssignment() {
		for (ResourceAssignment assignment : file.getAllResourceAssignments()) {
			Task task = assignment.getTask();
			String taskName;
			if (task == null)
				taskName = "(puste zadanie)";
			else
				taskName = task.getName();
			Resource resource = assignment.getResource();
			String resourceName;
			if (resource == null)
				resourceName = "(pusty zasob)";
			else
				resourceName = resource.getName();
			System.out.println("Przydzial: Zadanie=" + taskName + " Zasob="
					+ resourceName);
		}
	}

	public void showResourceAssignmentAfterTasks() {
		for (Task task : file.getAllTasks()) {
			System.out.println("Przydzialy do zadania " + task.getName() + ":");
			for (ResourceAssignment assignment : task.getResourceAssignments()) {
				Resource resource = assignment.getResource();
				String resourceName;
				if (resource == null)
					resourceName = "(pusty zasob)";
				else
					resourceName = resource.getName();
				System.out.println(" " + resourceName);
			}
		}
	}

	public void showResourceAssignmentAfterResources(ProjectFile file) {
		for (Resource resource : file.getAllResources()) {
			System.out.println("Przydzialy dla zasobu " + resource.getName()
					+ ":");
			for (ResourceAssignment assignment : resource.getTaskAssignments()) {
				Task task = assignment.getTask();
				System.out.println(" " + task.getName());
			}
		}
	}

	public void ResourceAssignment(Task t) {
		Qualification qual1 = SkillsUtilities.strToQualification(t.getText(1));
		List<Resource> resources = SkillsUtilities
				.resourcesMatchingQualification(qual1, file.getAllResources());
		for (Resource resource : resources) {
			System.out.println("zadanie " + t.getName() + "zasob " + resource.getName());
		}
		Resource r = resources.get((int) (Math.random() * resources.size()));	
		ResourceAssignment ra = t.addResourceAssignment(r);
		ra.setStart(t.getStart());
		ra.setWork(t.getDuration());
		ra.setRemainingWork(ra.getWork());
		ra.setCost(ra.getWork().getDuration() * r.getStandardRate().getAmount());
	}
	//assign resource to the task
	public void assignResourceToTask(Resource r, Task t) {
		ResourceAssignment ra = t.addResourceAssignment(r);
		ra.setStart(t.getStart());
		ra.setWork(t.getDuration());
		ra.setRemainingWork(ra.getWork());
		ra.setCost(ra.getWork().getDuration()
				* r.getStandardRate().getAmount());
		ConflictFixer.pack(file);
		ConflictFixer.fixConflicts(file);
	}
	
	public void evaluateProject() {
		
		System.out.println("Dla projektu wejœciowego " + path);
		System.out.println("Czas trwania projektu: " + Eval.getProjectDuration(file));
		System.out.println("Koszt realizacji projektu: " + Eval.getProjectCost(file));
	}
}
