/**
 * 
 */
package com.iam_vip.v3.fn.parser;

import org.jsoup.nodes.Element;

import com.iam_vip.v3.fn.IHtmlParser;

/**
 * @author Colin
 */
public class IDParser implements IHtmlParser {

	/**
	 * /// id="" ///
	 */
	private String what;

	public IDParser(String what) {
		this.what = what;
	}

	@Override
	public Element doParse(JsoupParse parser, Element element) {
		return parser.execute(element.getElementById(this.what));
	}

}
