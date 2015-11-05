package com.iam_vip.fetch_novel;

import com.iam_vip.fetch_novel.biz.NovelFactory;

/**
 * Hello world!
 */
public class FetchNovelApp {

	/**
	 * 
	 */
	public FetchNovelApp() {
	}

	public static void main(String[] args) throws Exception {

		NovelFactory.setFolder("D:\\fetch-novel\\");

		NovelFactory.createNovel("http://www.***.com/html/***.html", "***.txt").fetch();

	}

}




















