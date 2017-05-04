/**
 * 
 */
package com.iam_vip.v2.fn;

import java.io.IOException;
import java.net.URL;

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

		// String html = Jsoup.connect(url).timeout(1000 * 60 * 3).get().html();
		String html = Jsoup.parse(new URL(url).openStream(), "gbk", url).html();
		System.out.println(html);

	}

}
