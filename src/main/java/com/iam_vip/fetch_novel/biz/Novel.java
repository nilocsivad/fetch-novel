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
 *
 */
public abstract class Novel implements IBrowserUserAgent {

	private static final String WRAP_LINE = "\r\n";
	protected static final int TIMEOUT = 1000 * 60 * 1 * 1; // 1 minute

	/**
	 * 
	 */
	public Novel() {
		// TODO Auto-generated constructor stub
	}

	protected String url = "";
	protected String base_url = "";

	File txtFile;

	private FileWriter writer;

	public abstract int getWrapLine();

	public abstract void fetch() throws Exception;

	protected void write(String text) throws Exception {

		if (writer == null)
			writer = new FileWriter(txtFile, true);

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
		writer.flush();
		writer.close();
	}

	protected void calcBaseUrl(String href) {

		if ("".equals(base_url) && !href.startsWith("http")) {

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

}
