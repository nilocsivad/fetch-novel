/**
 * 
 */
package com.iam_vip.fetch_novel.biz.children;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.iam_vip.fetch_novel.biz.Novel;

/**
 * @author Colin
 */
public class _23wx extends Novel {

	private static final int WRAP_LINE = 2;
	private static final String END_SUFFIX = "/index.html";

	/**
	 * 
	 */
	public _23wx() {
	}

	@Override
	public int getWrapLine() {
		return WRAP_LINE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iam_vip.fetch_novel.biz.Novel#fetch()
	 */
	@Override
	public void fetch() throws IOException {

		try {

			do {

				Document document = super.doc();
				System.out.println(document.title() + "   " + super.url);
				super.write("\r\n------------------------------------\r\n---   " + document.title() + " ---\r\n--- " + super.url + " ---\r\n------------------------------------\r\n\r\n");

				Element novel = document.getElementById("contents");
				StringBuffer buffer = new StringBuffer(novel.html());
				buffer = new StringBuffer(buffer.toString().replace("&nbsp;", ""));
				buffer = new StringBuffer(buffer.toString().replace("<br />", ""));
				buffer = new StringBuffer(buffer.toString().replace("<br/>", ""));
				buffer = new StringBuffer(buffer.toString().replace("<br>", ""));
				super.write(buffer.toString());

				Element next = document.getElementById("amain").getElementsMatchingOwnText("下一页").get(0);
				String href = next.attr("href");
				super.calcBaseUrl(href);
				super.url = super.base_url + href;

			} while (!super.url.endsWith(END_SUFFIX));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.end();
		}
	}

}
