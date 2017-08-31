/**
 * 
 */
package com.iam_vip.v3.fn.parser;

import org.jsoup.nodes.Element;

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
		return parser.execute(element.getElementsByClass(this.what).get(this.index));
	}

}
