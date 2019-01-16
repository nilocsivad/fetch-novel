/**
 * 
 */
package com.iam_vip.v2.fn.site;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.iam_vip.IBrowserUserAgent;

/**
 * @author Colin
 *
 */
public interface NovelSite extends IBrowserUserAgent {

	default Document getDoc(String url) throws IOException {
		int idx = new Random().nextInt(ArrUserAgent.length);
		String agent = ArrUserAgent[idx];
		return Jsoup.connect(url).header("Referer", url).header("User-Agent", agent).timeout(1000 * 60 * 3).get();
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
