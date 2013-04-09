package arc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

 
/*The configutarion.xml file is located in WEB-INF/ */

public class ConfigurationParser{

	String configurationFile = "configuration.xml";

	private Document loadDocument(String fileName) throws 
		ParserConfigurationException, 
		SAXException, 
		IOException {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(fileName));
		return document;
	}

	public boolean requiresLDAP(String path){

		Document document = loadDocument(path);

		NodeList requirements = getRequirements(document);
		Boolean result = false;
		Node node = null;

		for(int i=0;i<requirements.getLength();i++){  
			node = requirements.item(i);  

			if(node.getTextContent().compareTo("ldap") == 0){
				result = true;
				System.out.println("Requires LDAP connection");
				break;
			}

			System.out.println(nodo.getTextContent());  
		}  	
		return result;
	}
	
	public NodeList getRequirements(Document document){

		NodeList requirements = document.getElementsByTagName("requirements");

		return requirements;

	}

}
