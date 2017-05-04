/**
 * 
 */
package com.iam_vip.v2.fn.site.item;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iam_vip.v2.fn.site._site;

/**
 * @author Colin
 *
 */
public class _23us_la implements _site {

	public static final String PREFIX = "http://www.23us.la/";

	/**
	 * 
	 */
	public _23us_la() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iam_vip.v2.fn.site._site#getName(org.jsoup.nodes.Document)
	 */
	@Override
	public String getName(Document doc) {
		return doc.getElementsByClass("btitle").get(0).getElementsByTag("span").first().text();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iam_vip.v2.fn.site._site#get(org.jsoup.nodes.Document)
	 */
	@Override
	public Elements get(Document doc) {
		return doc.getElementsByClass("chapterlist").get(0).getElementsByTag("a");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iam_vip.v2.fn.site._site#getDocHtml(org.jsoup.nodes.Document)
	 */
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
