/**
 * 
 */
package com.iam_vip.v3.fn.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iam_vip.v3.fn.IHtmlParser;

/**
 * @author Colin
 */
public final class JsoupParse {

	private List<IHtmlParser> parsers;
	private int indexOf = 0;

	private JsoupParse() {
	}

	private static final class JsoupParseSingleton {
		private static final JsoupParse INSTANCE = new JsoupParse();
	}

	public static final JsoupParse getInstance() {
		return JsoupParseSingleton.INSTANCE;
	}





	public String getHtmlText(List<IHtmlParser> parsers, Element element) {
		
		this.parsers = parsers;
		this.indexOf = 0;
		
		return this.execute(element).html();
	}

	Element execute(Element element) {
		if (this.parsers.size() <= indexOf) {
			return element;
		}
		return this.parsers.get(indexOf++).doParse(this, element);

	}







	public List<String> getHtmlLinks(List<IHtmlParser> parsers, Element element) {
		
		this.parsers = parsers;
		this.indexOf = 0;
		
		Elements tags = this.execute(element).getElementsByTag("a");

		List<String> list = new ArrayList<>(tags.size() + 1);
		for (Element $0 : tags) {
			list.add($0.absUrl("href"));
		}
		return list;
	}











}
