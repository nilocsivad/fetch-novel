/**
 * 
 */
package com.iam_vip.fetch_novel.biz;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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

	protected String url;
	protected String base_url;

	String path;

	private FileWriter writer;

	public abstract int getWrapLine();

	public abstract void fetch() throws Exception;

	protected void write(String text) throws Exception {

		if (writer == null)
			writer = new FileWriter(path, true);

		writer.write(text);

		for (int k = 0, l = this.getWrapLine(); l > k; ++k)
			writer.write(WRAP_LINE);

		writer.flush();
	}

	protected String getUserAgent() {
		int index = new Random().nextInt(ArrUserAgent.length);
		return ArrUserAgent[index];
	}

	protected void end() throws IOException {
		writer.flush();
		writer.close();
	}

	protected void calcBaseUrl(String href) {

		if (base_url == null && href.startsWith("http")) {

			String[] arr = href.split("/");
			String[] urlArr = url.split("/");

			StringBuffer buf = new StringBuffer(url.length());
			for (int m = 0, l = urlArr.length - arr.length; l > m; ++m) {
				buf.append(urlArr[m]);
				buf.append("/");
			}

			base_url = buf.toString();
		} else {
			base_url = "";
		}
	}

}
