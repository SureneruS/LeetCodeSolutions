import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

record Project(int capital, int profit) {
}

class Solution {
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        List<Project> projects = new ArrayList<>();

        for (int i = 0; i < profits.length; i++) {
            projects.add(new Project(capital[i], profits[i]));
        }

        projects.sort(Comparator.comparing(Project::capital));

        PriorityQueue<Project> projectsQueue = new PriorityQueue<>(Comparator.comparing(Project::profit).reversed());

        int maximizedCapital = w;
        int projectCursor = 0;
        while (k-- > 0) {
            while (projectCursor < projects.size() && projects.get(projectCursor).capital() <= maximizedCapital) {
                projectsQueue.offer(projects.get(projectCursor++));
            }

            Project nextProject = projectsQueue.poll();
            if (nextProject == null) {
                return maximizedCapital;
            }
            
            maximizedCapital += nextProject.profit();
        }

        return maximizedCapital;
    }
}