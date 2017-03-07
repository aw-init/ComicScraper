package Crawler.manga;

import Crawler.framework.Task;
import Crawler.framework.PauseCrawlerException;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.lang.Exception;
import java.io.IOException;

public class LyricPageTask extends Task {
	// https://genius.com/{number}
	String url;
	public LyricPageTask(String url) {
		this.url = url;
	}

	public String toString() {
		return "LyricPageTask " + url + "\n";
	}

	public ArrayList<Task> process() throws PauseCrawlerException {
		try {
			Document page = loadPage(this.url);

			Element headers = page.select("div.song_header-primary_info").first();

			String song_title = headers.select(".song_header-primary_info-title").first().text();
			System.out.println("Title " + song_title);

			HashMap<String, String> artists = new HashMap<String, String>();

			Element primary = headers.select("a.song_header-primary_info-primary_artist").first();
			artists.put(primary.text(), primary.attr("href"));
			System.out.println("Artist " + primary.text());

			Elements additional = headers.select("expandable-list[template=additional_artists] a");
			for (Element artist : additional) {
				String name = artist.text();
				String url = artist.attr("href");
				artists.put(name, url);
				System.out.println("Artist " + name);
			}
			
			Element lyrics_elem = page.select("lyrics").first();
			String lyrics = lyrics_elem.text();

			Elements annotation_elems = lyrics_elem.select("a.referent");
			System.out.println(annotation_elems.first().attr("href"));
		}
		catch (Exception exc) {
			throw new PauseCrawlerException(exc);
		}

		return new ArrayList<Task>();
	}
}
