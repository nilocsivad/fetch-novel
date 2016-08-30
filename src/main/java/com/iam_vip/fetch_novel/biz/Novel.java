/**
 * 
 */
package com.iam_vip.fetch_novel.biz;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.iam_vip.fetch_novel.c.IBrowserUserAgent;

/**
 * @author Colin
 */
public abstract class Novel implements IBrowserUserAgent {


	protected static final String BASE_URI = "http://www.baidu.com";
	private static final String WRAP_LINE = "\r\n";

	protected static final int TIMEOUT = 1000 * 60 * 1 * 1; // 1 minute
	protected static final int novel_group_length = NovelFactory.novel_group_length;


	/**
	 * 
	 */
	protected Novel() {
	}




	protected String url = "";
	protected String base_url;

	protected File outFile;

	private FileWriter writer;

	protected String templateHTML;


	public abstract int getWrapLine();

	public abstract void fetch() throws Exception;

	public abstract void fetch(int start) throws Exception;

	protected void setTemplate(String template) {

		if (templateHTML == null) {
			templateHTML = template;
		}
	}

	public abstract void fetch2html() throws Exception;

	protected void newWriter(String file) throws Exception {

		File parent = outFile.isDirectory() ? outFile : outFile.getParentFile();
		writer = new FileWriter(new File(parent, file));
	}

	protected void write(String text) throws Exception {

		if (writer == null)
			writer = new FileWriter(outFile, true);

		writer.write(text);

		for (int k = 0, l = this.getWrapLine(); l > k; ++k)
			writer.write(WRAP_LINE);

		writer.flush();
	}

	protected String getUserAgent() {

		int index = new Random().nextInt(ArrUserAgent.length);
		return ArrUserAgent[index];
	}

	protected Document doc() throws IOException {

		Connection connection = Jsoup.connect(url).timeout(TIMEOUT).header("User-Agent", getUserAgent());
		if (connection.execute().statusCode() != 200)
			return null;
		return connection.get();
	}

	protected void end() throws IOException {

		this.end(false);
	}

	protected void end(boolean isFromEx) throws IOException {

		if (!isFromEx) {
			writer.flush();
			writer.close();
		}
	}

	protected void calcBaseUrl(String href) {

		if (null == base_url || "".equals(base_url)) {

			String[] arr = href.split("/");
			String[] urlArr = url.split("/");

			StringBuffer buf = new StringBuffer(url.length());
			for (int m = 0, l = urlArr.length - arr.length; l > m; ++m) {
				buf.append(urlArr[m]);
				buf.append("/");
			}

			base_url = buf.toString();
		}
	}

	protected String url2file(String url) {

		url = url.substring(url.indexOf(base_url) + base_url.length());

		url = url.replace("/", "");
		url = url.replace(":", "");
		url = url.replace("*", "");
		url = url.replace("?", "");
		url = url.replace("\"", "");
		url = url.replace("'", "");
		url = url.replace(",", "");
		url = url.replace("|", "");
		url = url.replace("<", "");
		url = url.replace(">", "");
		url = url.replace("&", "");
		url = url.replace("^", "");
		url = url.replace("%", "");
		url = url.replace("$", "");
		url = url.replace("#", "");
		url = url.replace("@", "");
		url = url.replace("!", "");
		url = url.replace("`", "");
		url = url.replace("~", "");

		return url;
	}

}
