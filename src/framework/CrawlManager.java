package Crawler.framework;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class CrawlManager {

	ArrayDeque<Task> tasks;
	TaskLoader loader;
	public CrawlManager(TaskLoader loader) {
		this.loader = loader;
		this.tasks = this.loader.getTasks();
	}
	// dirty stuff, I'll fix it later
	public void begin() throws Throwable {
		while (!tasks.isEmpty()) {
			Task task = tasks.getFirst();
			ArrayList<Task> newTasks;

			try {
				newTasks = task.process();
			}
			catch (PauseCrawlerException pce) {
				// serialize and save all existing tasks, including current task
				boolean success = this.loader.saveTasks(tasks);
				if (!success) {
					System.out.println("Error - Failed to save tasks");
				}
				throw pce.getCause();
			}

			tasks.pop();
			for (Task x : newTasks) {
				tasks.push(x);
			}
		}
	}
}
