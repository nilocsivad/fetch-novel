/**
 * 
 */
package com.iam_vip.chain.item;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.iam_vip.chain.ChainNovel;

/**
 * @author Colin
 */
public class _23wx_com extends ChainNovel {

	public static final String PREFIX = "http://www.23wx.com/";

	/**
	 * 
	 */
	public _23wx_com() {
	}

	@Override
	protected Element getNext(Document doc) {
		return doc.getElementById("amain").getElementsMatchingOwnText("下一页").get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see com.iam_vip.chain.ChainNovel#getContent(org.jsoup.nodes.Document)
	 */
	@Override
	protected String getContent(Document doc) {

		StringBuffer buf = new StringBuffer();
		buf.append("------------------------------------\r\n");
		buf.append("--- " + doc.title() + " ---\r\n");
		buf.append("--- " + doc.baseUri() + " ---\r\n");
		buf.append("------------------------------------\r\n\r\n");

		Element novel = doc.getElementById("contents");
		buf.append(novel.html().replace("&nbsp;", "").replace("<br>", "").replace("<br/>", "").replace("<br />", ""));

		return buf.toString();
	}

}
