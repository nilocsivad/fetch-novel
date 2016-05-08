/**
 * 
 */
package com.iam_vip.fetch_novel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Colin
 */
public class ReadNovelTxt2Html {

	/**
	 * 
	 */
	public ReadNovelTxt2Html() {
	}

	private int start;
	private int len;
	private FileWriter writer;
	private Document templateDoc;

	@Before
	public void initData() throws IOException {

		len = 10 * 2 + 1; // 10

	}

	private void write2Html(File f, String txt) throws IOException {

		File pf = f.getParentFile();
		File hf = new File(pf, (start++) + ".html");
		writer = new FileWriter(hf);

		Document doc = templateDoc.clone();

		Elements next = doc.getElementsByClass("next");
		next.attr("href", String.format("./%s", start + ".html"));

		Elements previous = doc.getElementsByClass("previous");
		previous.attr("href", String.format("./%s", (start - 2) + ".html"));

		doc.getElementById("wrap-box");
		Element box = doc.getElementById("wrap-box");
		box.append(txt);
		writer.write(doc.outerHtml());

		writer.close();
	}

	@Test
	public void read() throws IOException {

		String pf = "";

		/** include this folder **/
		this.parseTxt(pf);

		/** all children folder **/
		// File[] folders = new File(pf).listFiles(f -> {
		// return f.isDirectory();
		// });
		// for (File folder : folders) {
		// // this.deleteFilter( folder );
		// this.parseTxt(folder);
		// }
	}

	public void parseTxt(String folder) throws IOException {

		this.parseTxt(new File(folder));
	}

	public void deleteFilter(File folder) {

		File[] fs = folder.listFiles(f -> {
			return f.isFile() && f.getName().endsWith("html");
		});

		for (File f : fs) {
			f.delete();
		}
	}

	public void parseTxt(File folder) throws IOException {

		File[] fs = folder.listFiles(f -> {
			return f.isFile() && f.getName().endsWith("txt");
		});

		start = 100000;
		start += folder.listFiles(f -> {
			return f.isFile() && f.getName().endsWith("html");
		}).length + 1;

		InputStream input = this.getClass().getResourceAsStream("/com/iam_vip/fetch_novel/template.html");
		templateDoc = Jsoup.parse(input, "UTF-8", "");

		for (File f : fs) {

			System.out.println(f.getAbsolutePath());

			StringBuffer buf = new StringBuffer(32);

			BufferedReader reader = new BufferedReader(new FileReader(f));

			int c = 0;

			String line = null;
			while ((line = reader.readLine()) != null) {

				if (line.equals("------------------------------------"))
					c++;

				if (c > 0 && c % len == 0) {
					c = 1; // line.equals(
							// "------------------------------------" )
					this.write2Html(f, buf.toString());
					buf = new StringBuffer(64);
				}

				buf.append(String.format("<p class='txt-line'> %s </p>\r\n", line));
				line = null;
			}

			this.write2Html(f, buf.toString());
			reader.close();

		}

	}

}
