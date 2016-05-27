/**
 * 
 */
package com.iam_vip.v2.fn.site;

import java.io.IOException;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Colin
 *
 */
public interface _site {

	String[] UserAgent = { "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:46.0) Gecko/20100101 Firefox/46.0", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/7.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)" };

	default Document getDoc(String url) throws IOException {
		int idx = new Random().nextInt(UserAgent.length);
		String agent = UserAgent[idx];
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
