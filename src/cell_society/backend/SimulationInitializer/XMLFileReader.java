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
import org.xml.sax.SAXException;

/**
 * Used to take in an XML file and parse through it in order to get the information needed to begin
 * a simulation
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

  /**
   * Setting the simulation type in order to double check that the proper XML file was read in
   * @param userSimulationType
   */
  public void setSimulationType(String userSimulationType){
    simulationType = userSimulationType;
  }

  /**
   * Gets the simulation type
   * @return The simulation type
   */
  public String getSimulationType(){
    return simulationType;
  }


  /**
   * Getting all the parameters that are needed in a map format to run the simulation
   * @param dataFile The XML file
   * @return The map that links the specific parameters to their values
   * @throws XMLErrorHandler Error that isi thrown if the file is not valid
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

  /**
   * Gets all of the parameters related to the cell's behavior in map form. Assumes valid file
   * because this method is called after the previous one
   * @param dataFile The XML file
   * @return The map linking all of the behaviors to their values
   */
  public Map getCellBehavior(String dataFile){
    NodeList list = getRootElement(new File(dataFile)).getElementsByTagName("parameters").item(0).getChildNodes();
    Map<String, String> results = new HashMap<>();
    for (int i = 0; i<list.getLength(); i++) {
      if(list.item(i) instanceof Element){
        Node attribute = list.item(i).getAttributes().item(0);
        results.put(attribute.getNodeName(), attribute.getNodeValue());
      }
    }
    return results;
  }

  public Map getCellCodes(String dataFile){
    NodeList list = getRootElement(new File(dataFile)).getElementsByTagName("codes").item(0).getChildNodes();
    Map<String, String> results = new HashMap<>();
    for (int i = 0; i<list.getLength(); i++) {
      if(list.item(i) instanceof Element){
        Node attribute = list.item(i).getAttributes().item(0);
        results.put(attribute.getNodeName(), attribute.getNodeValue());
      }
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
