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
public class _00ksw_org implements NovelSite {

	public static final String PREFIX = "http://www.00ksw.org/";

	/**
	 * 
	 */
	public _00ksw_org() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iam_vip.v2.fn.site._site#getName(org.jsoup.nodes.Document)
	 */
	@Override
	public String getName(Document doc) {
		return doc.getElementById("info").getElementsByTag("h1").get(0).text();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iam_vip.v2.fn.site._site#get(org.jsoup.nodes.Document)
	 */
	@Override
	public Elements get(Document doc) {
		return doc.getElementById("list").getElementsByTag("a");
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
