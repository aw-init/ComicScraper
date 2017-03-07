package Crawler.manga;

import Crawler.framework.Task;
import Crawler.framework.PauseCrawlerException;

import java.util.ArrayList;
import java.util.Arrays;

import java.lang.Exception;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTask extends Task {

	String input;
	String output;

	public ZipTask(String volume, String zip) {
		this.input = volume;
		this.output = zip;
	}
	public String toString() {
		return "ZipTask " + this.input + " " + this.output;
	}

	public ArrayList<Task> process() throws PauseCrawlerException {
		try {
			File destination = new File(output);

			/* stream stacking bullshit */
			ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(
					new FileOutputStream(destination)));

			File incoming = new File(input);
			this.writeFileToZip(incoming, "", out);
			out.close();

			return new ArrayList<Task>();
		}
		catch (Exception exc) {
			throw new PauseCrawlerException(exc);
		}
	}

	private void writeFileToZip(File path, String prefix, ZipOutputStream out) throws IOException {
		String zipDest = prefix + path.getName();

		if (path.isDirectory()) {
			zipDest += "/";

			File[] contents = path.listFiles();
			//Arrays.sort(contents);
			for (File child : contents) {
				writeFileToZip(child, zipDest, out);
			}
		}
		else {
			int BufferSize = 2048;
			byte data[] = new byte[BufferSize];

			BufferedImage img = ImageIO.read(path);
			

			BufferedInputStream fileInput = new BufferedInputStream(
				new FileInputStream(path), BufferSize);

			ZipEntry entry = new ZipEntry(path.toString());
			out.putNextEntry(entry);

			ImageIO.write(img, "jpg", out);
		}
	}

	public static class ZipTaskConstructor implements Task.Constructor {
		public Task construct(String[] args) {
			return new ZipTask(args[0], args[1]);
		}
	}
}
