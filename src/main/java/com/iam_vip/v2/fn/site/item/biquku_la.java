package com.iam_vip.v2.fn.site.item;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class biquku_la extends _23us_cc {

	public static final String PREFIX = "http://www.biquku.la/";

	public biquku_la() {
	}

	@Override
	public String getName(Document doc) {
		return doc.getElementById("info").getElementsByTag("h1").get(0).text();
	}

	@Override
	public Elements get(Document doc) {
		return doc.getElementsByClass("list").get(0).getElementsByTag("a");
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

	@Override
	public String next(Document doc) {
		Elements tags = doc.getElementsByClass("bottem2").get(0).getElementsByTag("a");
		StringBuilder buf = new StringBuilder();
		tags.forEach(t -> {
			if (t.text().indexOf("下一章") >= 0) {
				buf.append(t.absUrl("href"));
			}
		});
		return buf.toString();
	}

}
