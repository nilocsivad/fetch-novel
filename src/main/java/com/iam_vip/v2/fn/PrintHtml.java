/**
 * 
 */
package com.iam_vip.v2.fn;

import java.io.IOException;

import org.jsoup.Jsoup;

/**
 * @author Colin
 *
 */
public class PrintHtml {

	/**
	 * 
	 */
	public PrintHtml() {
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String url = "";

		String html = Jsoup.connect(url).timeout(1000 * 60 * 3).get().html();
		System.out.println(html);

	}

}
