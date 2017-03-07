package Crawler.framework;

import java.lang.RuntimeException;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.HashMap;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;
import java.io.FileNotFoundException;

public abstract class TaskLoader {

	String path;
	HashMap<String, Task.Constructor> tasks;

	public TaskLoader(String listPath) {
		this.path = listPath;
		this.tasks = new HashMap<String, Task.Constructor>();
	}

	public void put(String name, Task.Constructor constr) {
		this.tasks.put(name, constr);
	}

	public ArrayDeque<Task> getTasks() {
		ArrayDeque<Task> init = new ArrayDeque<Task>();
		try(BufferedReader reader = new BufferedReader(new FileReader(this.path))) {
			String line = reader.readLine();

			while (line != null && line.length() > 0) {
				String[] parts = line.split("\\s+");
				String task = parts[0];
				String[] args = Arrays.copyOfRange(parts, 1, parts.length);

				if (tasks.containsKey(task)) {
					Task.Constructor constr = tasks.get(task);
					init.addLast(constr.construct(args));
				}
				else {
					throw new RuntimeException("Unknown task " + task);
				}

				line = reader.readLine();
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			return new ArrayDeque<Task>();
		}
		return init;
	}
	public boolean saveTasks(ArrayDeque<Task> taskList) {
		try {
			PrintWriter writer = new PrintWriter(this.path, "UTF-8");
			for (Task tk : taskList) {
				writer.println(tk.toString());
			}
			writer.close();
			return true;
		}
		catch (IOException ioe) {
			System.out.println("Fatal Error - IOException interrupted task recovery.");
			ioe.printStackTrace();
			return false;
		}
	}
}
