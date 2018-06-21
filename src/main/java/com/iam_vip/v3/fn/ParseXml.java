/**
 * 
 */
package com.iam_vip.v3.fn;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.iam_vip.v3.fn.parser.ClassParser;
import com.iam_vip.v3.fn.parser.IDParser;
import com.iam_vip.v3.fn.parser.TagParser;

/**
 * @author Colin
 */
public final class ParseXml {

	private Document document;

	public ParseXml(String sourceXML) throws DocumentException {
		File xmlFile = new File(sourceXML);
		System.out.println("Load XML configure file " + xmlFile.getAbsolutePath());
		this.document = new SAXReader().read(xmlFile);
	}



	/**
	 * /// ///
	 * 
	 * @param sourceXML
	 * @param elementName
	 */
	public String getHtmlParsers(String elementName, String itemName, String attr) throws DocumentException {
		return this.document.getRootElement().element(elementName).element(itemName).attributeValue(attr);
	}



	/**
	 * /// ///
	 * 
	 * @param sourceXML
	 * @param elementName
	 */
	public List<IHtmlParser> getHtmlParsers(String elementName, String itemName) throws DocumentException {

		List<Element> elements = this.document.getRootElement().element(elementName).element(itemName).elements();

		List<IHtmlParser> parsers = new ArrayList<>(elements.size() + 1);
		for (Element $0 : elements) {
			Attribute what = $0.attribute("what");
			Attribute index = $0.attribute("index");
			switch ($0.getName()) {
			case "id":
				parsers.add(new IDParser(what.getText()));
				break;
			case "class":
				parsers.add(new ClassParser(what.getText(), Integer.parseInt(index.getText())));
				break;
			case "tag":
				parsers.add(new TagParser(what.getText(), Integer.parseInt(index.getText())));
				break;
			default:
				break;
			}
		}
		return parsers;

	}

}
