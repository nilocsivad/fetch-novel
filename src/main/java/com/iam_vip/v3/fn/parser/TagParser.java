/**
 * 
 */
package com.iam_vip.v3.fn.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator
 *
 */
public class TagParser extends ClassParser {

	public TagParser(String what, int index) {
		super(what, index);
	}

	@Override
	public Element doParse(JsoupParse parser, Element element) {
		Elements elements = element.getElementsByTag(this.what);
		if (elements == null || elements.size() == 0 || this.index >= elements.size()) {
			System.out.println("Wrong with " + this.what + "-" + this.index);
		}
		return parser.execute(elements.get(this.index));
	}

}
