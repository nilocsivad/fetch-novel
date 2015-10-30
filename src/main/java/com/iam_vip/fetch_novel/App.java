package com.iam_vip.fetch_novel;

import com.iam_vip.fetch_novel.biz.NovelFactory;

/**
 * Hello world!
 */
public class App {

	public static void main(String[] args) throws Exception {

		NovelFactory.createNovel("http://www.23wx.com/html/42/42377/18783648.html", "D:\\完美世界.txt").fetch();

	}

}
