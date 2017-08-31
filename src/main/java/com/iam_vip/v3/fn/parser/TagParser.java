/**
 * 
 */
package com.iam_vip.v3.fn.parser;

import org.jsoup.nodes.Element;

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
		return parser.execute(element.getElementsByTag(this.what).get(this.index));
	}

}
