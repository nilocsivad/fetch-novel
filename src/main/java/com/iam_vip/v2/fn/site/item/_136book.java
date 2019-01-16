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
public class _136book implements NovelSite {

	public static final String PREFIX = "http://www.136book.com/";

	/**
	 * 
	 */
	public _136book() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.iam_vip.v2.fn.site._site#getName(org.jsoup.nodes.Document)
	 */
	@Override
	public String getName(Document doc) {
		return doc.getElementsByClass("cont_title").get(0).getElementsByTag("h1").text();
	}

	/*
	 * (non-Javadoc)
	 * @see com.iam_vip.v2.fn.site._site#get(org.jsoup.nodes.Document)
	 */
	@Override
	public Elements get(Document doc) {
		Elements eles = doc.getElementsByClass("box1");
		return eles.get(eles.size() - 1).getElementsByTag("a");
	}

	/*
	 * (non-Javadoc)
	 * @see com.iam_vip.v2.fn.site._site#getDocHtml(org.jsoup.nodes.Document)
	 */
	@Override
	public String getDocHtml(Document doc) {
		Element novel = doc.getElementById("content");
		novel.getElementsByTag("center").remove();
		novel.getElementsByTag("div").remove();
		StringBuffer buffer = new StringBuffer(novel.html());
		buffer = new StringBuffer(buffer.toString().replace("<p>", ""));
		buffer = new StringBuffer(buffer.toString().replace("</p>", "\r\n\r\n"));
		return buffer.toString();
	}

}
