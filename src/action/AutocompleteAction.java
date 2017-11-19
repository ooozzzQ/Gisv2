package action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.Car;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import service.AutocompleteServiceInterface;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

public class AutocompleteAction extends ActionSupport {
	private HttpServletResponse response;
	private HttpServletRequest request;
	private String wordtext;
	private AutocompleteServiceInterface autocompleteServiceInterface;

	public AutocompleteAction(HttpServletResponse response, HttpServletRequest request, String wordtext, AutocompleteServiceInterface autocompleteServiceInterface) {
		super();
		this.response = response;
		this.request = request;
		this.wordtext = wordtext;
		this.autocompleteServiceInterface = autocompleteServiceInterface;
	}
	
	public AutocompleteAction() {
		
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getWordtext() {
		return wordtext;
	}

	public void setWordtext(String wordtext) {
		this.wordtext = wordtext;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public AutocompleteServiceInterface getAutocompleteServiceInterface() {
		return autocompleteServiceInterface;
	}

	public void setAutocompleteServiceInterface(AutocompleteServiceInterface autocompleteServiceInterface) {
		this.autocompleteServiceInterface = autocompleteServiceInterface;
	}

	public void autoComplete() {
		String wordTextTemp;
		try {
			wordTextTemp = URLDecoder.decode(wordtext.trim(), "UTF-8"); 
			if ("" != wordTextTemp && wordTextTemp != null) {
				String datas = getXml(wordTextTemp);
				String resposeString = new String(datas.getBytes("GBK"), "ISO-8859-1");
				//System.out.println("进来了resposeString==" + resposeString);
				response = ServletActionContext.getResponse();
				//response.setCharacterEncoding("GB2312");
				PrintWriter writer = response.getWriter();
				writer.print(resposeString);
				writer.flush();
				writer.close();
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 获得匹配的字母，返回List类型
	 * 
	 * @return
	 */
	private  List<String> getResultList(String userword ) {
		return autocompleteServiceInterface.getCarIdList(userword);
	}

	/**
	 * 返回结果的XML字符串
	 * 
	 * @return
	 */
	private  String getXml(String userword) {
		List<String> list = getResultList(userword);
		Iterator<String> iterator = list.iterator();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		Document document = null;
		try {
			db = dbf.newDocumentBuilder();
			document = db.newDocument();
			// 创建根节点
			Element root = document.createElement("words");
			// 根节点添加到dom树上
			document.appendChild(root);
			while (iterator.hasNext()) {
				String content = iterator.next();
				// 创建文本节点
				Text word = document.createTextNode(content);
				// 创建word节点
				Element child = document.createElement("word");
				child.appendChild(word);
				root.appendChild(child);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return doc2Xml(document);
	}

	private static String doc2Xml(Document document) {
		// 创建一个DOM转换器
		String docStr = null;
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		ByteArrayOutputStream outputStream = null;
		try {
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			outputStream = new ByteArrayOutputStream();
			
			transformer.transform(new DOMSource(document), new StreamResult(outputStream));
			docStr = outputStream.toString("GB2312");
			return docStr;
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return docStr;
	}

}
