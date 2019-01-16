/**
 * 
 */
package com.iam_vip.v2.fn.site.item;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iam_vip.v2.fn.site.NovelSite;

/**
 * @author Colin
 *
 */
public class _23us_cc implements NovelSite {

	public static final String PREFIX = "www.23us.cc/";

	/**
	 * 
	 */
	public _23us_cc() {
	}

	@Override
	public String getName(Document doc) {
		return doc.getElementsByClass("btitle").get(0).getElementsByTag("span").first().text();
	}

	@Override
	public Elements get(Document doc) {
		return doc.getElementsByClass("chapterlist").get(0).getElementsByTag("a");
	}

	@Override
	public String getDocHtml(Document doc) {
		Element novel = doc.getElementById("content");
		StringBuffer buffer = new StringBuffer(novel.html());
		buffer = new StringBuffer(buffer.toString().replace("&nbsp;", ""));
		buffer = new StringBuffer(buffer.toString().replace("<br />", ""));
		buffer = new StringBuffer(buffer.toString().replace("<br/>", ""));
		buffer = new StringBuffer(buffer.toString().replace("<br>", ""));
		return buffer.toString();
	}

}
