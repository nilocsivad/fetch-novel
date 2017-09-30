/**
 * 
 */
package com.iam_vip.v3.fn.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iam_vip.v3.fn.IHtmlParser;

/**
 * @author Colin
 */
public class ClassParser implements IHtmlParser {

	/**
	 * /// class="" ///
	 */
	protected String what;

	/**
	 * /// ///
	 */
	protected int index = 0;

	public ClassParser(String what, int index) {
		this.what = what;
		this.index = index;
	}

	@Override
	public Element doParse(JsoupParse parser, Element element) {
		Elements elements = element.getElementsByClass(this.what);
		if (elements == null || elements.size() == 0 || this.index >= elements.size()) {
			System.out.println("Wrong with " + this.what + "-" + this.index);
		}
		return parser.execute(elements.get(this.index));
	}

}
