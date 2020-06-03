package com.iam_vip.v2.fn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iam_vip.IBrowserUserAgent;
import com.iam_vip.v2.fn.site.NovelSite;
import com.iam_vip.v2.fn.site.item._00ksw_org;
import com.iam_vip.v2.fn.site.item._136book;
import com.iam_vip.v2.fn.site.item._23us;
import com.iam_vip.v2.fn.site.item._23us_cc;
import com.iam_vip.v2.fn.site.item._23us_la;
import com.iam_vip.v2.fn.site.item._23wx;
import com.iam_vip.v2.fn.site.item._23wx_cc;
import com.iam_vip.v2.fn.site.item._6lk_net;
import com.iam_vip.v2.fn.site.item._80txt;
import com.iam_vip.v2.fn.site.item.biquge_tw;
import com.iam_vip.v2.fn.site.item.biqugegebook;
import com.iam_vip.v2.fn.site.item.biquku_la;
import com.iam_vip.v2.fn.site.item.biqule;
import com.iam_vip.v2.fn.site.item.mianhuatang_la;
import com.iam_vip.v2.fn.site.item.sbkk8;
import com.iam_vip.v2.fn.site.item.tszww;
import com.iam_vip.v2.fn.site.item.xbiquge6;
import com.iam_vip.v2.fn.site.item.xxbiquge;
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

	
	
	public static void main(String[] args) throws Exception {
		/// 全部 ///
		doFetch(""); 
		/// 某链之后 ///
		doChainFetch(new File(""), "");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	private static void doChainFetch(File dir, String link) throws Exception {
		if (!dir.exists() || link == null || "".equals(link.trim())) {
			return;
		}
		NovelSite instance = getSite(link);
		if (instance == null) {
			return;
		}
		String next = link;
		BufferedWriter writer = null;
		int count = 0;
		int perSize = 100;
		do {
			Document doc = instance.getDoc(next);
			if (count++ % perSize == 0) {
				checkWriter(writer);
				writer = newWriter(dir, count);
			}
			write(doc, instance, writer, 1);
			next = instance.next(doc);
		} while (next != null && next.startsWith("http"));
		writer.close();
	}
	private static void checkWriter(BufferedWriter writer) throws IOException {
		if (writer != null) {
			writer.close();
		}
	}
	private static BufferedWriter newWriter(File dir, int count) throws IOException {
		String name = String.format("file-%04d.txt", (count / 100) + 1);
		return new BufferedWriter(new FileWriter(new File(dir, name)));
	}















	static Map<String, Class<?>> map = new HashMap<>();

	static {
		map.put(_00ksw_org.PREFIX, _00ksw_org.class);
		map.put(_136book.PREFIX, _136book.class);
		map.put(_23us_cc.PREFIX, _23us_cc.class);
		map.put(_23us_la.PREFIX, _23us_la.class);
		map.put(_23us.PREFIX, _23us.class);
		map.put(_23wx_cc.PREFIX, _23wx_cc.class);
		map.put(_23wx.PREFIX, _23wx.class);
		map.put(_6lk_net.PREFIX, _6lk_net.class);
		map.put(_80txt.PREFIX, _80txt.class);
		map.put(_00ksw_org.PREFIX, _00ksw_org.class);
		map.put(biquge_tw.PREFIX, biquge_tw.class);
		map.put(xbiquge6.PREFIX, xbiquge6.class);
		map.put(xxbiquge.PREFIX, xxbiquge.class);
		map.put(biqugegebook.PREFIX, biqugegebook.class);
		map.put(biquku_la.PREFIX, biquku_la.class);
		map.put(biqule.PREFIX, biqule.class);
		map.put(mianhuatang_la.PREFIX, mianhuatang_la.class);
		map.put(sbkk8.PREFIX, sbkk8.class);
		map.put(tszww.PREFIX, tszww.class);
		map.put(zhuaji_org.PREFIX, zhuaji_org.class);
	}
	
	
	

	private static NovelSite getSite(String url) throws InstantiationException, IllegalAccessException {
		for (Map.Entry<String, Class<?>> itm : map.entrySet()) {
			if (url.startsWith("http://" + itm.getKey()) || url.startsWith("https://" + itm.getKey())) {
				return (NovelSite) itm.getValue().newInstance();
			}
		}
		return null;
	}
	
	static void write(String url, NovelSite instance, BufferedWriter writer, int count) throws Exception {
		Document doc = instance.getDoc(url);
		write(doc, instance, writer, count);
	}

	static void write(Document doc, NovelSite instance, BufferedWriter writer, int count) throws Exception {
		try {
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
			System.err.println("error with " + doc.location());
			ex.printStackTrace();
			if (count <= 5) {
				Thread.sleep(10000);
				write(doc.location(), instance, writer, ++count);
			}
		}
	}

	public static void doFetch(String url) throws Exception {
		doFetch(url, 0);
	}
	
	public static void doFetch(String url, int start) throws Exception {
		if (!url.startsWith("http:") && !url.startsWith("https:")) {
			System.err.println("\"" + url + "\"" + " is not a complete URL address.");
			return;
		}
		int group = 100, add = 100000;
		NovelSite instance = getSite(url);
		if (instance == null) {
			System.err.println("\"" + url + "\"" + ", there is no parser for this URL.");
			return;
		}

		String folder = (System.getProperty("os.name").contains("Windows")) ? "D:\\novel" : (System.getProperty("user.home") + "/novel/");
		Document doc = instance.getDoc(url);
		String title = instance.getName(doc);
		File dir = new File(folder, title);
		if (!dir.exists())
			dir.mkdirs();
		System.out.println("Create directory: " + dir.getAbsolutePath());

		Elements elements = instance.get(doc);
		System.out.println("There are " + elements.size() + " articles.");

		BufferedWriter writer = null;
		File txtFile = null;
		for (int i = start, x = 0, l = elements.size(); l > i; ++i, ++x) {
			Element ele = elements.get(i);
			String href = ele.absUrl("href");
			if (x % group == 0) {
				if (x >= group && writer != null)
					writer.close();
				txtFile = new File(dir, title + "." + (add + x + 1) + "-" + (add + x + group) + ".txt");
				writer = new BufferedWriter(new FileWriter(txtFile));
			}
			System.out.printf("%s %4d/%d ==> ", href, i, l);
			write(href, instance, writer, 1);
			Thread.sleep(50);
		}

	}

}
