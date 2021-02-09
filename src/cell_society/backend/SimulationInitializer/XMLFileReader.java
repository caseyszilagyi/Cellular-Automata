package cell_society.backend.SimulationInitializer;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;

/** Used to take in an XML file and parse through it in order to get the information needed to
 * begin a simulation
 *
 * Still need to come back and add an error handling part, but for now, it will assume good data
 * @author Casey Szliagyii
 */
public class XMLFileReader {

  private ErrorHandler errorHandler = new XMLErrorHandler();
  private DocumentBuilder db;
  private Document doc;

  private String fileName;

  /**
   * Constructor only takes a filename, because that is all that is needed. This assumes that all XML
   * files that are designed for the simulation have the proper tags.
   * @param userFile the file name
   */
  public XMLFileReader(String userFile){
    fileName = userFile;
  }


  /**
   * Parses the file with the established DocumentBuilder
   * @throws Exception if anything goes wrong while parsing
   */
  public void parseFile() throws Exception {
    doc = db.parse(new File(fileName));
  }


  /**
   * Makes the document builder that will be used to parse through all XML files
   * @throws ParserConfigurationException
   */
  private void makeDocumentBuilder() throws ParserConfigurationException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    db = dbf.newDocumentBuilder();
    db.setErrorHandler(errorHandler);
  }

}
