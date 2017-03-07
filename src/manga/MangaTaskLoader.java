package Crawler.manga;

import Crawler.framework.TaskLoader;

public class MangaTaskLoader extends TaskLoader {
	public MangaTaskLoader(String path) {
		super(path);
		put("VolumeTask", new VolumeTask.VolumeTaskConstructor());
		put("ChapterTask", new ChapterTask.ChapterTaskConstructor());
		put("PageTask", new PageTask.PageTaskConstructor());
		put("ZipTask", new ZipTask.ZipTaskConstructor());
		put("EchoTask", new EchoTask.EchoTaskConstructor());
	}
}
