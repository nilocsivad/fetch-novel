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
public class _23wx implements NovelSite {

	public static final String PREFIX = "www.23wx.com/";

	/**
	 * 
	 */
	public _23wx() {
	}

	@Override
	public String getName(Document doc) {
		return doc.getElementsByClass("fr").get(0).parent().getElementsByTag("a").last().text();
	}

	@Override
	public Elements get(Document doc) {
		return doc.getElementById("at").getElementsByTag("a");
	}

	@Override
	public String getDocHtml(Document doc) {
		Element novel = doc.getElementById("contents");
		StringBuffer buffer = new StringBuffer(novel.html());
		buffer = new StringBuffer(buffer.toString().replace("&nbsp;", ""));
		buffer = new StringBuffer(buffer.toString().replace("<br />", ""));
		buffer = new StringBuffer(buffer.toString().replace("<br/>", ""));
		buffer = new StringBuffer(buffer.toString().replace("<br>", ""));
		return buffer.toString();
	}

}
