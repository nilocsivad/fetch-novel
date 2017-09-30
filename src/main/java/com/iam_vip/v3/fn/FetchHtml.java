/**
 * 
 */
package com.iam_vip.v3.fn;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.iam_vip.IBrowserUserAgent;
import com.iam_vip.v3.fn.parser.JsoupParse;

/**
 * @author Colin
 */
public class FetchHtml implements IBrowserUserAgent {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {


		String whatURL = "";
		String toFolder = "D:\\Novel\\", sourceXML = "D:\\Novel\\html_document.xml";
		int iGroupCount = 100;





		/// jar包代码 ///
		{
			whatURL = JOptionPane.showInputDialog("Input URL please!");
			if (whatURL == null || whatURL.trim().equals("")) {
				System.exit(0);
			}

			toFolder = JOptionPane.showInputDialog("Input a path please!", toFolder);
			iGroupCount = Integer.parseInt(JOptionPane.showInputDialog("Input a group number please!", iGroupCount));

			String jarWholePath = FetchHtml.class.getProtectionDomain().getCodeSource().getLocation().getFile();
			jarWholePath = java.net.URLDecoder.decode(jarWholePath, "UTF-8");
			String parentPath = "";
			if (jarWholePath.endsWith("./")) {
				parentPath = new File(jarWholePath.substring(0, jarWholePath.length() - 2)).getAbsolutePath();
			} else {
				parentPath = new File(jarWholePath).getParentFile().getAbsolutePath();
				System.out.println(">>>2>>> " + parentPath);
			}
			sourceXML = new File(parentPath, "html_document.xml").getAbsolutePath();
		}


		


		int endIndex = whatURL.indexOf("/", 7);
		String elementName = whatURL.substring(0, endIndex).replace(":", "_").replace("/", "").replace(".", "_");


		/// XML -> Parser ///
		ParseXml parseXml = new ParseXml(sourceXML);
		List<IHtmlParser> NameParsers = parseXml.getHtmlParsers(elementName, "name");
		List<IHtmlParser> LinkParsers = parseXml.getHtmlParsers(elementName, "link");
		List<IHtmlParser> TextParsers = parseXml.getHtmlParsers(elementName, "text");


		JsoupParse jsoupParse = JsoupParse.getInstance();

		/// Links URL -> Document ///
		Document document = getDoc(whatURL);
		if (document == null)
			throw new RuntimeException("Error: " + whatURL);
		System.out.println(document.title() + " " + whatURL);


		Element bodyNode = document.getElementsByTag("body").first();
		String name = jsoupParse.getHtmlText(NameParsers, bodyNode);
		System.out.println(">>> " + name);
		List<String> links = jsoupParse.getHtmlLinks(LinkParsers, bodyNode);
		System.out.println(">>> " + links.size());

		File folder = new File(toFolder, name);
		if (folder.exists() == false)
			folder.mkdirs();



		FileWriter writer = null;
		int iCount = 0;
		for (String $0 : links) {

			document = getDoc($0);
			if (document == null) {
				System.err.println("Error: " + $0);
				continue;
			}

			String title = document.title(), href = document.location();

			/// HTML -> Element ///
			Element element = document.getElementsByTag("body").first();
			String htmlText = null;

			try {
				htmlText = jsoupParse.getHtmlText(TextParsers, element).replace("&nbsp;", "")
						.replace("<br />", "\r\n\r\n").replace("<br/>", "\r\n\r\n").replace("<br>", "\r\n\r\n")
						.replace("</p>", "</p>\r\n\r\n").replace("</div>", "</div>\r\n\r\n");
			} catch (Exception e) {
				System.err.println("Error get from " + href);
				continue;
			}

			System.out.println(title + " " + href);

			if (iCount == 0 || iCount % iGroupCount == 0)
				writer = new FileWriter(new File(folder, name + "_" + System.currentTimeMillis() + ".txt"));

			writer.write("\r\n" + IBrowserUserAgent.START + "\r\n");
			writer.write("--- " + title + " ---\r\n");
			writer.write("--- " + href + " ---\r\n\r\n");
			writer.write(htmlText);
			writer.write("\r\n\r\n\r\n");
			iCount++;

		}

	}






	private static Document getDoc(String url) {
		return getDoc(url, 0);
	}

	private static Document getDoc(String url, int iCount) {

		iCount++;
		int idx = new Random().nextInt(ArrUserAgent.length);
		String agent = ArrUserAgent[idx];

		try {
			return Jsoup.connect(url).header("Referer", url).header("User-Agent", agent).timeout(1000 * 60 * 3).get();
		} catch (IOException e) {

			if (iCount >= 3) {
				return null;
			}
			return getDoc(url);

		}
	}




}
