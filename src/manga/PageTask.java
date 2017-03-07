package Crawler.manga;

import Crawler.framework.Task;
import Crawler.framework.PauseCrawlerException;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.URL;
import java.net.URLConnection;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.lang.Exception;
import java.io.IOException;
import java.io.File;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PageTask extends Task {
	public static String IMAGE_EXTENSION = ".png";
	public static int PADDING_WIDTH = 5;
	public static String USER_AGENT = "Mozilla/5.0 (X11; Linux i686; rv:45.0) Gecko/20100101 Firefox/45.0";

	String destination;
	String url;

	public PageTask(String destination, String url) {
		this.destination = destination;
		this.url = url;
	}

	public String toString() {
		return "PageTask " + this.destination + " " + this.url;
	}
	public ArrayList<Task> process() throws PauseCrawlerException {
		try {
			Document page = loadPage(this.url);

			/* Get the url of the image */
			Element img = page.select("img#img").first();
			String imgUrl = img.attr("src");

			/* save the file to the correct path */
			URL imgLocation = new URL(imgUrl);
			URLConnection imgConnection = imgLocation.openConnection();
			imgConnection.setRequestProperty("User-Agent", USER_AGENT);
			BufferedImage image = ImageIO.read(imgConnection.getInputStream());

			File imgDestination = new File(this.destination);
			ImageIO.write(image, "png", imgDestination);
			
			/* this task has no more subtasks */
			ArrayList<Task> newTasks = new ArrayList<Task>();
			return newTasks;	
		}
		catch (Exception exc) {
			throw new PauseCrawlerException(exc);
		}
	}

	public static class PageTaskConstructor implements Task.Constructor {
		public Task construct(String[] args) {
			return new PageTask(args[0], args[1]);
		}
	}
}
