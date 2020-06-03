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
public class zhuaji_org implements NovelSite {

	public static final String PREFIX = "www.zhuaji.org/";

	/**
	 * 
	 */
	public zhuaji_org() {
	}

	@Override
	public String getName(Document doc) {
		return doc.getElementsByClass("mulu_bookinfo").get(0).getElementsByTag("h1").get(0).text();
	}

	@Override
	public Elements get(Document doc) {
		return doc.getElementById("mulu").getElementsByTag("a");
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
