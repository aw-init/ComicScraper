package Crawler.manga;

import Crawler.framework.Task;
import Crawler.framework.PauseCrawlerException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.nio.file.Paths;
import java.nio.file.Files;

import java.net.URL;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.lang.Exception;
import java.io.IOException;

public class VolumeTask extends Task {
	// http://www.mangareader.net/{name}
	String url;
	String filePath;
	String name;
	public VolumeTask(String destination, String name) {
		this.name = name;
		this.url = "http://www.mangareader.net/" + name;
		this.filePath = destination;
	}

	public String toString() {
		return "VolumeTask " + filePath + " " + name;
	}

	public ArrayList<Task> process() throws PauseCrawlerException {
		try {
			Document page = loadPage(this.url);
			URL domain = new URL("http://www.mangareader.net/");

			/* get the elements containing the important information */
			Elements chapters = page.select("#chapterlist tr td a");

			ArrayList<Task> newTasks = new ArrayList<Task>();
			Pattern chapterNumber = Pattern.compile("\\d+");
			int failureId = 10000;

			for (Element chapter : chapters) {

				/* get url of the chapter */
				URL urlResolution = new URL(domain, chapter.attr("href"));
				String chapterUrl = urlResolution.toString();

				/* use regex to parse out chapter number from the element text */
				Matcher getInt = chapterNumber.matcher(chapter.text());
				boolean success = getInt.find();

				/* assign unique default if not found */
				String chapterId;
				if (success) {
					chapterId = getInt.group(getInt.groupCount());
				}
				else {
					chapterId = Integer.toString(failureId++);
				}

				/* generate new directory name from chapter number */
				String destination = Paths.get(this.filePath, chapterId).toString();

				/* create a new task to put into the queue */
				newTasks.add(new ChapterTask(destination, chapterUrl));
			}

			/* create the directory for the project */
			Files.createDirectories(Paths.get(this.filePath));
			return newTasks;
		}
		catch (Exception exc) {
			throw new PauseCrawlerException(exc);
		}
	}

	public static class VolumeTaskConstructor implements Task.Constructor {
		public Task construct(String[] args) {
			return new VolumeTask(args[0], args[1]);
		}
	}
}
