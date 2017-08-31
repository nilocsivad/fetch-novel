/**
 * 
 */
package com.iam_vip.v3.fn;

import org.jsoup.nodes.Element;

import com.iam_vip.v3.fn.parser.JsoupParse;

/**
 * @author Colin
 */
public interface IHtmlParser {

	Element doParse(JsoupParse parser, Element element);

}
