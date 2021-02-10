package cell_society.backend.SimulationInitializer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * Used to take in an XML file and parse through it in order to get the information needed to begin
 * a simulation
 * <p>
 * Still need to come back and add an error handling part, but for now, it will assume good data
 *
 * @author Casey Szilagyi
 */
public class XMLFileReader {

  private final DocumentBuilder DOCUMENT_BUILDER;
  private String simulationType;


  public static final String ERROR_MESSAGE = "The file presented is not an XML file of the correct game type";
  public final List<String> DATA_FIELDS = List.of(
      "title",
      "author",
      "description",
      "rows",
      "columns",
      "grid"
  );

  /**
   * Constructor only takes a filename, because that is all that is needed. This assumes that all
   * XML files that are designed for the simulation have the proper tags.
   */
  public XMLFileReader() {
    DOCUMENT_BUILDER = getDocumentBuilder();
  }

  public void setSimulationType(String userSimulationType){
    simulationType = userSimulationType;
  }


  /**
   * Get data contained in this XML file as an object
   */
  public Map getSimulationParameters(String dataFile) throws XMLErrorHandler {
    Element root = getRootElement(new File(dataFile));
    if (!isValidFile(root, simulationType)) {
      throw new XMLErrorHandler(ERROR_MESSAGE, simulationType);
    }
    // read data associated with the fields given by the object
    Map<String, String> results = new HashMap<>();
    for (String field : DATA_FIELDS) {
      results.put(field, getTextValue(root, field));
    }

    return results;
  }

  public Map getCellBehavior(String dataFile) throws XMLErrorHandler {
    NodeList list = getRootElement(new File(dataFile)).getElementsByTagName("parameters");
    Map<String, String> results = new HashMap<>();
    for (int i = 0; i<list.getLength(); i++) {
      Node curr = list.item(i);
      results.put(curr.getNodeName(), curr.getNodeValue());
    }
    return results;
  }


  // returns if this is a valid XML file for the specified object type. The attribute of the first
  // tag needs to be the same as the type of game that is given
  private boolean isValidFile(Element root, String type) {
    return root.getAttribute("type").equals(type);
  }

  // get value of Element's attribute
  private String getAttribute(Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }

  // get value of Element's text
  private String getTextValue(Element e, String tagName) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    } else {
      // FIXME: empty string or exception? In some cases it may be an error to not find any text
      return "";
    }
  }


  // get root element of an XML file
  private Element getRootElement(File xmlFile) throws XMLErrorHandler {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    } catch (SAXException | IOException e) {
      throw new XMLErrorHandler(e);
    }
  }


  // boilerplate code needed to make a documentBuilder
  private DocumentBuilder getDocumentBuilder() throws XMLErrorHandler {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLErrorHandler(e);
    }
  }

}