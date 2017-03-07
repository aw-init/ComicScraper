package Crawler.framework;

import java.util.ArrayList;


import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

import java.net.SocketTimeoutException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.HttpStatusException;
import org.jsoup.UnsupportedMimeTypeException;

public abstract class Task {
	protected String UserAgent = "Random User Agent";
	public abstract ArrayList<Task> process() throws PauseCrawlerException;
	public abstract String toString();

	public Document loadPage(String url) throws MalformedURLException, IOException {
		Connection conn = Jsoup.connect(url).userAgent(UserAgent);
		try {
			return conn.get();
		}
		catch (UnsupportedMimeTypeException exc) {
			throw new IOException(exc);
		}
		catch (SocketTimeoutException exc) {
			throw new IOException(exc);
		}
		catch (HttpStatusException exc) {
			throw new IOException(exc);
		}	
	}

	public interface Constructor {
		public abstract Task construct(String[] args);
	}
}
