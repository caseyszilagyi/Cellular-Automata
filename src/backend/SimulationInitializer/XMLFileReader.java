package backend.SimulationInitializer;

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

  ErrorHandler errorHandler = new XMLErrorHandler();
  Document doc;

  private String fileName;

  /**
   * Constructor only takes a filename, because that is all that is needed. This assumes that all XML
   * files that are designed for the simulation have the proper tags.
   * @param userFile the file name
   */
  public XMLFileReader(String userFile){
    fileName = userFile;
  }


  public void makeFactoryAndParse() throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    db.setErrorHandler(errorHandler);
    //May need to make this namespace aware in the future if it is deemed necessary
    doc = db.parse(new File(fileName));
  }

}
