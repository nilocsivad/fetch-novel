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
public class sbkk8 implements NovelSite {

	public static final String PREFIX = "www.sbkk8.cn/";

	/**
	 * 
	 */
	public sbkk8() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.iam_vip.v2.fn.site._site#getName(org.jsoup.nodes.Document)
	 */
	@Override
	public String getName(Document doc) {
		return doc.getElementsByClass("myPlace").get(0).getElementsByTag("a").get(2).text();
	}

	/*
	 * (non-Javadoc)
	 * @see com.iam_vip.v2.fn.site._site#get(org.jsoup.nodes.Document)
	 */
	@Override
	public Elements get(Document doc) {
		Elements eles = doc.getElementsByClass("leftList");
		return eles.get(0).getElementsByTag("a");
	}

	/*
	 * (non-Javadoc)
	 * @see com.iam_vip.v2.fn.site._site#getDocHtml(org.jsoup.nodes.Document)
	 */
	@Override
	public String getDocHtml(Document doc) {
		Element novel = doc.getElementsByClass("f_article").get(0);
		novel.getElementsByTag("script").remove();
		novel.getElementsByTag("div").remove();
		StringBuffer buffer = new StringBuffer(novel.html());
		buffer = new StringBuffer(buffer.toString().replace("<p>", ""));
		buffer = new StringBuffer(buffer.toString().replace("</p>", "\r\n\r\n"));
		return buffer.toString();
	}

}
