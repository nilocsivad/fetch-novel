/**
 * 
 */
package com.iam_vip.v2.fn.site.item;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Colin
 *
 */
public class biqule extends _23wx_cc {

	public static final String PREFIX = "www.biqule.com/";

	/**
	 * 
	 */
	public biqule() {
	}

	@Override
	public Elements get(Document doc) {
		return doc.getElementsByClass("article-list").get(0).getElementsByTag("dl").get(0).getElementsByTag("a");
	}

}
