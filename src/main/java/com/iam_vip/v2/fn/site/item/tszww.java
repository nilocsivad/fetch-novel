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
public class tszww implements _site {

	public static final String PREFIX = "http://www.tszww.com/";

	/**
	 * 
	 */
	public tszww() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iam_vip.v2.fn.site._site#getName(org.jsoup.nodes.Document)
	 */
	@Override
	public String getName(Document doc) {
		return doc.getElementsByClass("content").get(2).getElementsByTag("h1").get(0).text();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iam_vip.v2.fn.site._site#get(org.jsoup.nodes.Document)
	 */
	@Override
	public Elements get(Document doc) {
		return doc.getElementsByClass("dirbox").get(0).getElementsByTag("a");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iam_vip.v2.fn.site._site#getDocHtml(org.jsoup.nodes.Document)
	 */
	@Override
	public String getDocHtml(Document doc) {
		Element novel = doc.getElementById("table_container");
		StringBuffer buffer = new StringBuffer(novel.html());
		buffer = new StringBuffer(buffer.toString().replace("&nbsp;", ""));
		buffer = new StringBuffer(buffer.toString().replace("<br />", "<p>"));
		buffer = new StringBuffer(buffer.toString().replace("<br/>", "<p>"));
		buffer = new StringBuffer(buffer.toString().replace("<br>", "\r\n<p>"));
		return buffer.toString();
	}

}
