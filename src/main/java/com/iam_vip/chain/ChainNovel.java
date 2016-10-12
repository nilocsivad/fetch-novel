/**
 * 
 */
package com.iam_vip.chain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.iam_vip.IBrowserUserAgent;
import com.iam_vip.chain.item._23wx_com;

/**
 * @author Colin
 */
public abstract class ChainNovel implements IBrowserUserAgent {

	protected static final int TIMEOUT = 1000 * 60 * 1 * 1; // 1 minute

	private static final Map<String, Class<?>> MAP = new HashMap<>();

	static {
		MAP.put(_23wx_com.PREFIX, _23wx_com.class);
	}

	/**
	 * 
	 */
	protected ChainNovel() {
	}

	private static String URL;

	public static ChainNovel getInstance(String url) throws InstantiationException, IllegalAccessException {

		Class<?> cls = null;

		for (String itm : MAP.keySet()) {
			if (url.startsWith(itm)) {
				cls = MAP.get(itm);
				break;
			}
		}

		if (cls != null) {
			URL = url;
			ChainNovel instance = (ChainNovel) cls.newInstance();
			return instance;
		}
		else {
			throw new RuntimeException("Can't find implement for " + url);
		}

	}


	private File folder;

	private void setFolder(String folder) {
		this.folder = new File(folder);
		if (!this.folder.exists())
			this.folder.mkdirs();
	}

	public void start(String toFolder) throws IOException {
		this.start(30, toFolder);
	}

	public void start(int len, String toFolder) throws IOException {
		boolean isWin = System.getProperty("os.name").contains("Windows");
		String folder = isWin ? "D:\\Novel" : System.getProperty("user.home") + "/Novel/";
		this.start(folder, len, toFolder);
	}

	protected abstract String getContent(Document doc);

	public void start(String parentFolder, int len, String toFolder) throws IOException {

		this.setFolder(parentFolder + File.separator + toFolder);

		int counter = 1;

		Document doc = this.doc(URL);
		FileWriter writer = null;

		do {

			System.out.println("--> " + doc.baseUri() + " ==> " + doc.title());

			String content = this.getContent(doc);

			if (counter % len == 1) {
				File txtFile = new File(this.folder, (System.currentTimeMillis() / 1000) + ".txt");
				writer = new FileWriter(txtFile);
			}

			writer.write(content + "\r\n\r\n\r\n\r\n");
			writer.flush();

			if (counter % len == 0) {
				writer.close();
				counter = 0;
			}
			counter++;

		}
		while ((doc = this.next(doc)) != null);

	}

	protected String getUserAgent() {
		int index = new Random().nextInt(ArrUserAgent.length);
		return ArrUserAgent[index];
	}

	protected Document doc(final String url) throws IOException {
		Connection connection = Jsoup.connect(url).timeout(TIMEOUT).header("User-Agent", getUserAgent());
		if (connection.execute().statusCode() != 200)
			return null;
		return connection.get();
	}

	/**
	 * 下一页 DOM 对象
	 */
	protected abstract Element getNext(Document doc);

	protected Document next(Document doc) throws IOException {
		Element element = getNext(doc);
		String url = element.absUrl("href");
		return doc(url);
	}

}
