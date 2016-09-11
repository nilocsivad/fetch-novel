package com.iam_vip.v2.fn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iam_vip.v2.fn.site._site;
import com.iam_vip.v2.fn.site.item._136book;
import com.iam_vip.v2.fn.site.item._23wx;
import com.iam_vip.v2.fn.site.item.mianhuatang;

/**
 * Hello world!
 */
public class FetchNovelApp {

	/**
	 * 
	 */
	public FetchNovelApp() {
	}

	static Map<String, Class<?>> map = new HashMap<>();

	static {
		map.put(_23wx.PREFIX, _23wx.class);
		map.put(_136book.PREFIX, _136book.class);
		map.put(mianhuatang.PREFIX, mianhuatang.class);
	}

	static _site getSite(String url) throws InstantiationException, IllegalAccessException {
		for (Map.Entry<String, Class<?>> itm : map.entrySet()) {
			if (url.startsWith(itm.getKey())) {
				return (_site) itm.getValue().newInstance();
			}
		}
		return null;
	}

	static void write(String url, _site instance, BufferedWriter writer, int count) throws Exception {
		try {
			Document doc = instance.getDoc(url);
			System.out.println(doc.title() + " --- " + url);
			String line = "\r\n\r\n\r\n------------------------------------\r\n---   " + doc.title() + " ---\r\n--- " + url + " ---\r\n------------------------------------\r\n\r\n";
			String html = instance.getDocHtml(doc);
			writer.write(line);
			writer.write(html + "\r\n");
			writer.flush();
		}
		catch (Exception ex) {
			System.err.println("error with " + url);
			ex.printStackTrace();
			if (count <= 5) {
				Thread.sleep(10000);
				write(url, instance, writer, ++count);
			}
		}
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		int group = 100, add = 100000, start = 0;

		String url = "";



		_site instance = getSite(url);
		if (instance == null)
			return;

		String folder = (System.getProperty("os.name").contains("Windows")) ? "D:\\novel" : (System.getProperty("user.home") + "/novel/");

		Document doc = instance.getDoc(url);
		String title = instance.getName(doc);

		File _fol = new File(folder, title);
		if (!_fol.exists())
			_fol.mkdirs();

		Elements elements = instance.get(doc);

		BufferedWriter writer = null;
		File txtFile = null;

		for (int i = start, x = 0, l = elements.size(); l > i; ++i, ++x) {
			Element ele = elements.get(i);
			String href = ele.attr("href");

			if (x % group == 0) {
				if (x >= group && writer != null)
					writer.close();

				txtFile = new File(_fol, title + "." + (add + x + 1) + "-" + (add + x + group) + ".txt");
				writer = new BufferedWriter(new FileWriter(txtFile));
			}

			String toURL = url + href;
			if (href.startsWith("http:")) {
				toURL = href;
			}
			write(toURL, instance, writer, 1);

			Thread.sleep(50);
		}

	}

}
