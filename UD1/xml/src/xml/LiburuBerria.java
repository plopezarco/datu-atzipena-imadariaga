package xml;

import java.io.File;
import java.io.IOException;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class LiburuBerria {

    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("liburuak.xml"));

        //Creamos el elemento liburu con su atributo
        Element elemLiburu = document.createElement("liburu");
        elemLiburu.setAttribute("isbn", "014");
        //Creamos el elemento izenburu y egile
        Element elemIzenburu = document.createElement("izenburua");
        Element elemEgile = document.createElement("egile");
        //Creamos el texto para el elemento izenburu y egile
        Text textIzenburu = document.createTextNode("Vredaman");
        Text textEgile = document.createTextNode("Unai Elorriaga");
        //Metemos el libro en el arbol
        document.getDocumentElement().appendChild(elemLiburu);
        //Metemos el elemento izenburu en el libro
        elemLiburu.appendChild(elemIzenburu);
        //Metemos el texto en el elemento izenburu
        elemIzenburu.appendChild(textIzenburu);
        //Lo mismo pero con el egile
        elemLiburu.appendChild(elemEgile);
        elemEgile.appendChild(textEgile);

        System.out.println("Liburu kopurua: " + document.getElementsByTagName("liburu").getLength());

        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new java.io.File("Liburuak4.xml"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);

    }
}
