/**
 * 
 */
package com.iam_vip.v2.fn.site;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Colin
 *
 */
public interface _site {

	default Document getDoc(String url) throws IOException {
		return Jsoup.connect(url).timeout(1000 * 60 * 3).get();
	}

	String getName(Document doc);

	default String getDocHtml(String url) throws IOException {
		return getDoc(url).html();
	}

	String getDocHtml(Document doc);

	default Elements get(String url) throws IOException {
		return get(getDoc(url));
	}

	Elements get(Document doc);

}
