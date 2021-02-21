package cell_society.backend.simulation_initializer;

import cell_society.controller.ErrorHandler;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
  private File currentFile;

  public static final String ERROR_MESSAGE = "The file presented is not an XML file of the correct game type";
  public final List<String> DATA_FIELDS = List.of(
      "title",
      "author",
      "description"
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
   *
   * @param userSimulationType
   */
  public void setSimulationType(String userSimulationType) {
    simulationType = userSimulationType;
  }

  /**
   * Gets the simulation type
   *
   * @return The simulation type
   */
  public String getSimulationType() {
    return simulationType;
  }

  /**
   * Set the file used to run the simulation
   * @param fileName The filename
   */
  public void setFile(String fileName){
    currentFile = new File(fileName);
  }

  /**
   * Getting all the parameters that are needed in a map format to run the simulation
   *
   * @return The map that links the specific parameters to their values
   * @throws XMLErrorHandler Error that is thrown if the file is not valid
   */
  public Map getSimulationParameters(){
    Element root = getRootElement(currentFile);
    if (!isValidFile(root, simulationType)) {
      throw new ErrorHandler("WrongType");
    }
    // read data associated with the fields given by the object
    Map<String, String> results = new HashMap<>();
    for (String field : DATA_FIELDS) {
      results.put(field, getTextValue(root, field));
    }

    return results;
  }

  // gets the details of a grid or patch and returns it. Throws an error if a core grid specification is missing
  private GridOrPatchDetails getGridOrPatchDetails(Element gridOrPatch){
    Element root = getRootElement(currentFile);
    try {
      int rows = Integer.parseInt(getTextValue(root, "rows"));
      int cols = Integer.parseInt(getTextValue(root, "columns"));
      String type = getTextValue(gridOrPatch, "type");
      String grid = getTextValue(gridOrPatch, "gridState");
      Map<String, String> codes = getSubAttributeMap("codes", gridOrPatch);
      Map<String, String> decoder = getSubAttributeMap("decoder", gridOrPatch);
      Map<String, String> parameters = getSubAttributeMap("parameters", gridOrPatch);
      Map<String, String> probabilities = getSubAttributeMap("randomProbs", gridOrPatch);
      GridOrPatchDetails details = new GridOrPatchDetails(type, cols, rows, grid, parameters, codes, decoder, probabilities);
      return details;
    } catch(Exception e){
      throw new ErrorHandler("CoreGridSpecification");
    }
  }

  /**
   * Gets the details of the simulation grid
   * @return The grid details
   * @throws XMLErrorHandler
   */
  public GridOrPatchDetails getGridDetails() {
    Element root = getRootElement(currentFile);
    Element current = (Element) ((Element) root.getElementsByTagName("gridInfo").item(0)).getElementsByTagName("grid").item(0);
    return getGridOrPatchDetails(current);
  }

  /**
   * Gets a set of all the patches
   * @return The set of patches
   */
  public Set getPatchDetails(){
    Element root = getRootElement(currentFile);
    NodeList current = ((Element) root.getElementsByTagName("gridInfo").item(0)).getElementsByTagName("patch");
    Set<GridOrPatchDetails> allPatches = new HashSet<>();
    for(int i = 0; i<current.getLength(); i++){
      allPatches.add(getGridOrPatchDetails((Element) current.item(i)));
    }
    return allPatches;
  }


  /**
   * Gets a hashmap of all of the attributes and their assigned values inside a given element
   *
   * @param userAttribute the element name
   */
  public Map getSubAttributeMap(String userAttribute, Element element) {
    NodeList list = element.getElementsByTagName(userAttribute).item(0)
        .getChildNodes();

    Map<String, String> results = new HashMap<>();
    for (int i = 0; i < list.getLength(); i++) {
      if (list.item(i) instanceof Element) {
        Node attribute = list.item(i).getAttributes().item(0);
        results.put(attribute.getNodeName(), attribute.getNodeValue());
      }
    }
    return results;
  }

  /**
   * Gets a hashmap of all of the attributes and their assigned values inside a given element
   *
   * @param userAttribute the element name
   */
  public Map getAttributeMap(String userAttribute) {
    NodeList list;
    try{
      list = getNodeListByTagName(userAttribute);
    } catch(Exception e){
      throw new ErrorHandler("CoreSpecificationError");
    }

    Map<String, String> results = new HashMap<>();
    for (int i = 0; i < list.getLength(); i++) {
      if (list.item(i) instanceof Element) {
        Node attribute = list.item(i).getAttributes().item(0);
        results.put(attribute.getNodeName(), attribute.getNodeValue());
      }
    }
    return results;
  }

  /**
   * Gets the NodeList corresponding to a certain tag name. Throws an exception if the tag
   * name doesn't exist
   * @param userAttribute The tag name
   * @return The NodeList
   */
  public NodeList getNodeListByTagName(String userAttribute) {
    return getRootElement(currentFile).getElementsByTagName(userAttribute).item(0)
        .getChildNodes();
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
