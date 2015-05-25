package cn.peon.core.kit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * 
 * 本类是专门解析XML文件的，主要用于为系统读取自己的配置文件时提供最方便的解析操作
 * 
 * @author HX
 * 
 * 
 */
public class XmlReaderUtil {
	/**
	 * 
	 * 得到某节点下某个属性的值
	 * 
	 * @param element
	 *            要获取属性的节点
	 * 
	 * @param attributeName
	 *            要取值的属性名称
	 * 
	 * @return 要获取的属性的值
	 * 
	 * @author HX_2010-01-12
	 */
	public static String getAttribute(Element element, String attributeName) {
		return element.getAttribute(attributeName);
	}
	/**
	 * 
	 * 获取指定节点下的文本
	 * 
	 * @param element
	 *            要获取文本的节点
	 * 
	 * @return 指定节点下的文本
	 * 
	 * @author HX_2010-01-12
	 */
	public static String getText(Element element) {
		return element.getFirstChild().getNodeValue();
	}
	/**
	 * 
	 * 解析某个xml文件，并在内存中创建DOM树
	 * 
	 * @param xmlFile
	 *            要解析的XML文件
	 * 
	 * @return 解析某个配置文件后的Document
	 * 
	 * @throws Exception
	 *             xml文件不存在
	 */
	public static Document parse(String xmlFile) throws Exception {
		return parse(new File(xmlFile));
	}
	/**
	 * 
	 * 获得某节点下的某个子节点（指定子节点名称，和某个属性的值）<br>
	 * 
	 * 即获取parentElement下名字叫childName，并且属性attributeName的值为attributeValue的子结点
	 * 
	 * @param parentElement
	 *            要获取子节点的那个父节点
	 * 
	 * @param childName
	 *            要获取的子节点名称
	 * 
	 * @param attributeName
	 *            要指定的属性名称
	 * 
	 * @param attributeValue
	 *            要指定的属性的值
	 * 
	 * @return 符合条件的子节点
	 * 
	 * @throws Exception
	 *             子结点不存在或有多个符合条件的子节点
	 * 
	 * @author HX_2008-12-01
	 */
	public static Element getChildElement(Element parentElement,
			String childName, String attributeName, String attributeValue)
			throws Exception {
		NodeList list = getChildElements(parentElement, childName);
		int count = 0;
		Element curElement = null;
		for (int i = 0; i < list.getLength(); i++) {
			Element child = (Element) list.item(i);
			String value = child.getAttribute(attributeName);
			if (true == value.equals(attributeValue)) {
				curElement = child;
				count++;
			}
		}
		if (0 == count) {
			throw new Exception("找不到个符合条件的子节点:"+parentElement.getNodeName()+">>"+childName);
		} else if (1 < count) {
			throw new Exception("找不到个符合条件的子节点:"+parentElement.getNodeName()+">>"+childName);
		}
		return curElement;
	}
	/**
	 * 
	 * 得到某节点下的某个子节点（通过指定子节点名称）<br>
	 * 
	 * 即获取parentElement下名字叫childName的子节点
	 * 
	 * @param parentElement
	 *            要获取子节点的父节点
	 * 
	 * @param childName
	 *            要获取的子节点名称
	 * 
	 * @return 符合条件的子节点
	 * 
	 * @throws Exception
	 *             找不到符合条件的子结点或找到多个符合条件的子节点
	 */
	public static Element getChildElement(Element parentElement,
			String childName) throws Exception {
		NodeList list = getChildElements(parentElement, childName);
		Element curElement = null;
		if (1 == list.getLength()) {
			curElement = (Element) list.item(0);
		} else if (0 == list.getLength()) {
//			LOG.error("找不到个符合条件的子节点:"+parentElement.getNodeName()+">>"+childName);
			return curElement;
		} else {
//			LOG.error("找不到个符合条件的子节点:"+parentElement.getNodeName()+">>"+childName);
			return curElement;
		}
		return curElement;
	}
	public static NodeList getChildElements(Element parentElement,
			String childName) {
		NodeList list = parentElement.getElementsByTagName(childName);
		return list;
	}
	public static Document parse(File file) throws ParserConfigurationException, SAXException, IOException {
		return parse(new FileInputStream(file));
	}
	public static Document parse(FileInputStream fileInputStream) throws SAXException, IOException, ParserConfigurationException {
		// 绑定XML文件，建造DOM树
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document domTree = db.parse(fileInputStream);
		return domTree;
	}
}