package gui.Functions;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
public class ReportsGenerator {


	public  void xmlReport(String strFileName,String strScenarioName,String strStatus,String strTimestamp)
	{
		
		 try {

				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				//testsuite root elements
				Document doc = docBuilder.newDocument();
				Element testsuite = doc.createElement("testsuite");
				doc.appendChild(testsuite);
				
				Attr rootAttr = doc.createAttribute("name");
				rootAttr.setValue(strScenarioName);
				testsuite.setAttributeNode(rootAttr);
				
				rootAttr = doc.createAttribute("hostname");
				rootAttr.setValue("");
				testsuite.setAttributeNode(rootAttr);
				
			    rootAttr = doc.createAttribute("tests");
				rootAttr.setValue("1");
				testsuite.setAttributeNode(rootAttr);
				
				 rootAttr = doc.createAttribute("failures");
				rootAttr.setValue(strStatus);
				testsuite.setAttributeNode(rootAttr);
				
				rootAttr = doc.createAttribute("timestamp");
				rootAttr.setValue(strTimestamp);
				testsuite.setAttributeNode(rootAttr);
				
				rootAttr = doc.createAttribute("time");
				rootAttr.setValue("");
				testsuite.setAttributeNode(rootAttr);
				
				rootAttr = doc.createAttribute("errors");
				rootAttr.setValue("");
				testsuite.setAttributeNode(rootAttr);
				
				
				// testcase elements
				Element testcase = doc.createElement("testcase");
				testsuite.appendChild(testcase);

				// set attribute to staff element
				Attr attr = doc.createAttribute("name");
				attr.setValue(strScenarioName);
				testcase.setAttributeNode(attr);
				
				attr = doc.createAttribute("time");
				attr.setValue("");
				testcase.setAttributeNode(attr);
				
				attr = doc.createAttribute("classname");
				attr.setValue(strScenarioName);
				testcase.setAttributeNode(attr);
				
				
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(strFileName+".xml"));

				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);

				transformer.transform(source, result);

				System.out.println("File saved!");

			  } catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();
			  }

	}

}
