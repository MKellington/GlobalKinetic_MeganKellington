package gui.Functions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
public class XMLreportEditor {
	
	
	public void editXmlEdit(String filePath,String strScenarioName)
	{
		  File xmlFile = new File(filePath);
	      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder dBuilder;
	
	      
	        try {
	            dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = dBuilder.parse(xmlFile);
	            doc.getDocumentElement().normalize();
	            
	            //update attribute value
	            updateAttributeValue(doc,strScenarioName);
	            
	            
	            /*
	            //update Element value
	            updateElementValue(doc);
	            
	            //delete element
	            deleteElement(doc);
	            
	            //add new element
	            addElement(doc);
	            */
	            //write the updated document to file or console
	            doc.getDocumentElement().normalize();
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            DOMSource source = new DOMSource(doc);
	            StreamResult result = new StreamResult(new File("strScenarioName.xml"));
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            transformer.transform(source, result);
	            System.out.println("XML file updated successfully");
	            
	        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
	            e1.printStackTrace();
	        }
	}
	
	
	
	
	private static void addElement(Document doc) {
        NodeList employees = doc.getElementsByTagName("Employee");
        Element emp = null;
        
        //loop for each employee
        for(int i=0; i<employees.getLength();i++){
            emp = (Element) employees.item(i);
            Element salaryElement = doc.createElement("salary");
            salaryElement.appendChild(doc.createTextNode("10000"));
            emp.appendChild(salaryElement);
        }
    }

	
    private static void deleteElement(Document doc) {
        NodeList employees = doc.getElementsByTagName("Employee");
        Element emp = null;
        //loop for each employee
        for(int i=0; i<employees.getLength();i++){
            emp = (Element) employees.item(i);
            Node genderNode = emp.getElementsByTagName("gender").item(0);
            emp.removeChild(genderNode);
        }
        
    }

    private static void updateElementValue(Document doc) {
        NodeList employees = doc.getElementsByTagName("Employee");
        Element emp = null;
        //loop for each employee
        for(int i=0; i<employees.getLength();i++){
            emp = (Element) employees.item(i);
            Node name = emp.getElementsByTagName("name").item(0).getFirstChild();
            name.setNodeValue(name.getNodeValue().toUpperCase());
        }
    }

    private static void updateAttributeValue(Document doc,String strTestName) {
       
    	
    	NodeList testsuite = doc.getElementsByTagName("name");
        Element testsuiteElement = null;
        
       
        
        
        //loop for each testsuite
        for(int i=0; i<testsuite.getLength();i++){
        	
        
        	testsuiteElement = (Element) testsuite.item(i);
            
           //String gender = testsuiteElement.getElementsByTagName("gender").item(0).getFirstChild().getNodeValue();
            
            testsuiteElement.setAttribute("name", strTestName);
          
            
         /*   if( testsuiteElement.getElementsByTagName("name").item(i).getFirstChild().getNodeValue().equalsIgnoreCase("nameatttibute"))
            {
            	
            	testsuiteElement.setAttribute("id", "M"+testsuiteElement.getAttribute("id"))
            }
            
            if(gender.equalsIgnoreCase("male")){
            	
            	
                //prefix id attribute with M
            	testsuiteElement.setAttribute("id", "M"+testsuiteElement.getAttribute("id"));
            }else{
                //prefix id attribute with F
            	testsuiteElement.setAttribute("id", "F"+testsuiteElement.getAttribute("id"));
            }*/
        }
    }


}