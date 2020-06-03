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
import com.iam_vip.v2.fn.site.NovelSite;
import com.iam_vip.v2.fn.site.item._00ksw_org;
import com.iam_vip.v2.fn.site.item._136book;
import com.iam_vip.v2.fn.site.item._23us;
import com.iam_vip.v2.fn.site.item._23us_cc;
import com.iam_vip.v2.fn.site.item._23us_la;
import com.iam_vip.v2.fn.site.item._23wx;
import com.iam_vip.v2.fn.site.item._23wx_cc;
import com.iam_vip.v2.fn.site.item._80txt;
import com.iam_vip.v2.fn.site.item.biqudao;
import com.iam_vip.v2.fn.site.item.biquge_cm;
import com.iam_vip.v2.fn.site.item.biquge_info;
import com.iam_vip.v2.fn.site.item.biquge_tw;
import com.iam_vip.v2.fn.site.item.biqugegebook;
import com.iam_vip.v2.fn.site.item.biquke;
import com.iam_vip.v2.fn.site.item.biqule;
import com.iam_vip.v2.fn.site.item.booktxt_net;
import com.iam_vip.v2.fn.site.item.dingdiann;
import com.iam_vip.v2.fn.site.item.mianhuatang_la;
import com.iam_vip.v2.fn.site.item.sbkk8;
import com.iam_vip.v2.fn.site.item.tszww;
import com.iam_vip.v2.fn.site.item.x23us;
import com.iam_vip.v2.fn.site.item.xbiquge6;
import com.iam_vip.v2.fn.site.item.xbiquge_la;
import com.iam_vip.v2.fn.site.item.xs_la;
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

	private static Map<String, Class<?>> MAP = new HashMap<>();

	static {
		MAP.put("www.00ksw.org/", _00ksw_org.class);
		MAP.put("www.136book.com/", _136book.class);
		MAP.put("www.23us.cc/", _23us_cc.class);
		MAP.put("www.23us.la/", _23us_la.class);
		MAP.put("www.23us.com/", _23us.class);
		MAP.put("www.23wx.cc/", _23wx_cc.class);
		MAP.put("www.23wx.com/", _23wx.class);
		MAP.put("www.80txt.com/", _80txt.class);
		MAP.put("www.biqudao.com/", biqudao.class);
		MAP.put("www.biquge.cm/", biquge_cm.class);
		MAP.put("www.biquge.info/", biquge_info.class);
		MAP.put("www.biquge.tw/", biquge_tw.class);
		MAP.put("www.biqugebook.com/", biqugegebook.class);
		MAP.put("www.biquke.com/", biquke.class);
		MAP.put("www.biqule.com/", biqule.class);
		MAP.put("www.booktxt.net/", booktxt_net.class);
		MAP.put("www.dingdiann.com/", dingdiann.class);
		MAP.put("www.mianhuatang.la/", mianhuatang_la.class);
		MAP.put("www.sbkk8.cn/", sbkk8.class);
		MAP.put("www.tszww.com/", tszww.class);
		MAP.put("www.x23us.com/", x23us.class);
		MAP.put("www.xbiquge.la/", xbiquge_la.class);
		MAP.put("www.xbiquge6.com/", xbiquge6.class);
		MAP.put("www.xs.la/", xs_la.class);
		MAP.put("www.xxbiquge.com/", xxbiquge.class);
		MAP.put("www.zhuaji.org/", zhuaji_org.class);
	}

	public static void main(String[] args) throws Exception {
		
		///var aaas = $("#list").find("a");
		///for ( var i = 0, l = aaas.length; i < l; ++i ) {
		///	var a = aaas[i];
		///	if ( $(a).text().indexOf("") >= 0 ) {
		///		console.log(i);
		///	}
		///};

		doFetch("");
		
		doFetch("", 0);
		
		// BufferedReader reader = new BufferedReader(new
		// FileReader("D:/output.txt"));
		// String line = null;
		// while((line = reader.readLine()) != null) {
		// final String tmp = line.trim();
		// if (tmp.startsWith("http")) {
		// new Thread() {
		// public void run() {
		// try {
		// doFetch(tmp);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }.start();
		// }
		// line = null;
		// }
		// reader.close();

	}
	
	
	

	private static NovelSite getSite(String url) throws InstantiationException, IllegalAccessException {
		for (Map.Entry<String, Class<?>> itm : MAP.entrySet()) {
			if (url.startsWith("http://" + itm.getKey()) || url.startsWith("https://" + itm.getKey())) {
				return (NovelSite) itm.getValue().newInstance();
			}
		}
		return null;
	}

	private static void write(String url, NovelSite instance, BufferedWriter writer, int count) throws Exception {
		try {
			Document doc = instance.getDoc(url);

			System.out.printf("%s %n", doc.title());

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

	public static void doFetch(String url) throws Exception {
		doFetch(url, 0);
	}
	
	public static void doFetch(String url, int start) throws Exception {

		if (!url.startsWith("http:") && !url.startsWith("https:")) {
			System.err.println("\"" + url + "\"" + " is not a complete URL address.");
			return;
		}
		
		System.out.println("Load \"" + url + "\"");

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
