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
public class _6lk_net extends _23wx_cc {

	public static final String PREFIX = "http://www.6lk.net/";

	/**
	 * 
	 */
	public _6lk_net() {
	}

	@Override
	public String getName(Document doc) {
		return doc.getElementsByClass("booktitle").get(0).getElementsByTag("h1").get(0).text();
	}

	@Override
	public Elements get(Document doc) {
		return doc.getElementById("readerlist").getElementsByTag("a");
	}

	@Override
	public String next(Document doc) {
		try {
			Elements tags = doc.getElementsByClass("jumptop").get(0).getElementsByTag("a");
			if (tags.size() == 3) {
				return tags.get(2).absUrl("href");
			}
		} catch (Exception e) {
		}
		return super.next(doc);
	}

}
