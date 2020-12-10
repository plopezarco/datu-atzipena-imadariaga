package xml;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EgileaBilatu {

    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("liburuak.xml"));

        String izenburua;
        izenburua = "Harry Potter";
        String idazlea = "";
        boolean aurkitu = false;
 

        NodeList liburuNodoak = document.getElementsByTagName("liburu");

        for (int i = 0; i < liburuNodoak.getLength(); i++) {
            Node nodoa = liburuNodoak.item(i);
            NodeList liburuNodoarenSemeak = nodoa.getChildNodes();

            for (int j = 0; j < liburuNodoarenSemeak.getLength(); j++) {
                Node semea = liburuNodoarenSemeak.item(j);
                if (semea.getNodeName() == "izenburua") {
                    String content = semea.getTextContent();
                    if(izenburua.equals(content)){
                        aurkitu = true;
                    }
                    
                }
                if (semea.getNodeName() == "egilea") {
                     idazlea = semea.getTextContent();
                     if(aurkitu){
                         System.out.println(idazlea);
                         aurkitu = false;
                         
                     }
                   
                }

            }
           

        }

    }
}
