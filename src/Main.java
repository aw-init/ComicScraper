package Crawler;

import Crawler.framework.CrawlManager;
import Crawler.manga.MangaTaskLoader;

public class Main {
	static String TaskCachePath = "tasks.txt";

	public static void main(String[] argv) throws Throwable {
		MangaTaskLoader loader = new MangaTaskLoader(TaskCachePath);
		CrawlManager mgr = new CrawlManager(loader);
		mgr.begin();

	}

}
