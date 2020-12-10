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

public class EgileaBilatu2 {

    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("liburuak.xml"));

        String isbnz;
        isbnz = "012";
        String egilea = "";

        NodeList liburuNodoak = document.getElementsByTagName("liburu");

        for (int i = 0; i < liburuNodoak.getLength(); i++) {
            Node nodoa = liburuNodoak.item(i);
            Element elemLiburua = (Element) nodoa;
            
            if (elemLiburua.getAttribute("isbn").equals(isbnz)) {
                
                NodeList liburuNodoarenSemeak = nodoa.getChildNodes();
                
                for (int a = 0; a < liburuNodoarenSemeak.getLength(); a++) {
                    
                    Node semea = liburuNodoarenSemeak.item(a);
                    
                    if (semea.getNodeName() == "egilea") {
                        egilea = semea.getTextContent();
                        System.out.println(egilea);
                    }

                }
            }

        }

    }
}
