package Crawler.manga;

import Crawler.framework.Task;
import Crawler.framework.PauseCrawlerException;

import java.util.ArrayList;

public class EchoTask extends Task {

	String text;

	public EchoTask(String text) {
		this.text = text;
	}
	public String toString() {
		return "EchoTask " + this.text;
	}

	public ArrayList<Task> process() throws PauseCrawlerException {
		System.out.println(text);
		return new ArrayList<Task>();
	}

	public static class EchoTaskConstructor implements Task.Constructor {
		public Task construct(String[] args) {
			return new EchoTask(args[0]);
		}
	}
}
