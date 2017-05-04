package com.iam_vip.v2.fn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iam_vip.IBrowserUserAgent;
import com.iam_vip.v2.fn.site._site;
import com.iam_vip.v2.fn.site.item._136book;
import com.iam_vip.v2.fn.site.item._23us;
import com.iam_vip.v2.fn.site.item._23us_cc;
import com.iam_vip.v2.fn.site.item._23us_la;
import com.iam_vip.v2.fn.site.item._23wx;
import com.iam_vip.v2.fn.site.item._23wx_cc;
import com.iam_vip.v2.fn.site.item._80txt;
import com.iam_vip.v2.fn.site.item.biquge_tw;
import com.iam_vip.v2.fn.site.item.biqugegebook;
import com.iam_vip.v2.fn.site.item.biqule;
import com.iam_vip.v2.fn.site.item.mianhuatang;
import com.iam_vip.v2.fn.site.item.sbkk8;
import com.iam_vip.v2.fn.site.item.zhuaji_org;

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
		map.put(_136book.PREFIX, _136book.class);
		map.put(_23us_cc.PREFIX, _23us_cc.class);
		map.put(_23us_la.PREFIX, _23us_la.class);
		map.put(_23us.PREFIX, _23us.class);
		map.put(_23wx_cc.PREFIX, _23wx_cc.class);
		map.put(_23wx.PREFIX, _23wx.class);
		map.put(_80txt.PREFIX, _80txt.class);
		map.put(biquge_tw.PREFIX, biquge_tw.class);
		map.put(biqugegebook.PREFIX, biqugegebook.class);
		map.put(biqule.PREFIX, biqule.class);
		map.put(mianhuatang.PREFIX, mianhuatang.class);
		map.put(sbkk8.PREFIX, sbkk8.class);
		map.put(zhuaji_org.PREFIX, zhuaji_org.class);
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

			System.out.println("--> " + doc.baseUri() + " ==> " + doc.title());


			StringBuffer buf = new StringBuffer();
			buf.append(IBrowserUserAgent.START + "\r\n");
			buf.append("--- " + doc.title() + " ---\r\n");
			buf.append("--- " + doc.baseUri() + " ---\r\n\r\n");
			String line = buf.toString();

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

	public static void main(String[] args) throws Exception {

		String[] urls = { 
				"", 
				"", 
				"" };
		for (String url : urls) {
			doFetch(url);
		}

	}

	public static void doFetch(String url) throws Exception {

		if (!url.startsWith("http:")) {
			return;
		}

		int group = 100, add = 100000, start = 0;

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
			String href = ele.absUrl("href");

			if (x % group == 0) {
				if (x >= group && writer != null)
					writer.close();

				txtFile = new File(_fol, title + "." + (add + x + 1) + "-" + (add + x + group) + ".txt");
				writer = new BufferedWriter(new FileWriter(txtFile));
			}

			write(href, instance, writer, 1);

			Thread.sleep(50);
		}

	}

}
