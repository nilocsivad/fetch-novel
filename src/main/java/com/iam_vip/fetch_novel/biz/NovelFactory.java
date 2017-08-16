/**
 * 
 */
package com.iam_vip.fetch_novel.biz;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.iam_vip.fetch_novel.biz.children._1KanShu;
import com.iam_vip.fetch_novel.biz.children._23wx;
import com.iam_vip.fetch_novel.biz.children._23wx_cc;
import com.iam_vip.fetch_novel.biz.children._bxwx;

/**
 * @author Colin
 */
public final class NovelFactory {

	// <novel-children>
	private static final Map<String, Class<?>> MAP = new HashMap<>();


	static {
		MAP.put("http://www.23wx.com", _23wx.class);
		MAP.put("http://www.23wx.cc", _23wx_cc.class);
		MAP.put("http://www.1kanshu.cc", _1KanShu.class);
		MAP.put("http://www.bxwx.org", _bxwx.class);
	}
	// </novel-children>

	/**
	 * 
	 */
	private NovelFactory() {
	}


	private static File folder;

	public static int novel_group_length = 50;


	public static void setFolder(String folder) {

		NovelFactory.folder = new File(folder);

		if (!NovelFactory.folder.exists())
			NovelFactory.folder.mkdirs();
	}
	
	private static String prefix = "";

	public static Novel createNovel(String url, String folder) throws Exception {

		url = url.trim();

		Class<?> cls = getInstance(url);
		Novel instance = (Novel) cls.newInstance();

		instance.url = url;
		instance.outFile = new File(NovelFactory.folder, folder);
		instance.outFile.mkdirs();
		instance.base_url = prefix;
		return instance;
	}

	private static Class<?> getInstance(String url) {
		for (Map.Entry<String, Class<?>> itm : MAP.entrySet()) {
			if (url.startsWith(itm.getKey())) {
				//prefix = url.substring(0, url.lastIndexOf("/") + 1);
				prefix = itm.getKey();
				return itm.getValue();
			}
		}
		return null;
	}

}
