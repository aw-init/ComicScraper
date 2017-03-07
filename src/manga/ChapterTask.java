package Crawler.manga;

import Crawler.framework.Task;
import Crawler.framework.PauseCrawlerException;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.URL;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.lang.Exception;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

public class ChapterTask extends Task {
	String url;
	String destination;

	public ChapterTask(String dest, String url) {
		this.url = url;
		this.destination = dest;
	}

	public String toString() {
		return "ChapterTask " + this.destination + " " + this.url;
	}
	public ArrayList<Task> process() throws PauseCrawlerException {
		try {
			Document page = loadPage(this.url);

			/* get the elements containing the important information */
			Elements mangaPages = page.select("#pageMenu option");

			URL domain = new URL("http://www.mangareader.net");
			ArrayList<Task> newTasks = new ArrayList<Task>();


			for (Element mangaPage : mangaPages) {
				/* get url to the page */
				URL urlResolution = new URL(domain, mangaPage.attr("value"));
				String pageUrl = urlResolution.toString();

				/* get filename for the page to be stored */
				String pageNumber = mangaPage.text();
				while (pageNumber.length() < PageTask.PADDING_WIDTH) {
					pageNumber = "0" + pageNumber;
				}
				String destination = Paths.get(this.destination,
					pageNumber+ PageTask.IMAGE_EXTENSION).toString();


				newTasks.add(new PageTask(destination, pageUrl));
			}

			/* create the directory for the chapter */
			Files.createDirectories(Paths.get(this.destination));
			return newTasks;	
		}
		catch (Exception exc) {
			throw new PauseCrawlerException(exc);
		}
	}

	public static class ChapterTaskConstructor implements Task.Constructor {
		public Task construct(String[] args) {
			return new ChapterTask(args[0], args[1]);
		}
	}
}
