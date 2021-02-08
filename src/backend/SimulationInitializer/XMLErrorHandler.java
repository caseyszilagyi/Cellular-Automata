package backend.SimulationInitializer;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * https://docs.oracle.com/javase/tutorial/jaxp/dom/readingXML.html
 */
public class XMLErrorHandler implements ErrorHandler {


  XMLErrorHandler() {
  }

  private String getParseExceptionInfo(SAXParseException spe) {
    String systemId = spe.getSystemId();
    if (systemId == null) {
      systemId = "null";
    }

    String info = "URI=" + systemId + " Line=" + spe.getLineNumber() +
        ": " + spe.getMessage();
    return info;
  }

  public void warning(SAXParseException spe) throws SAXException {
    //This line was changed from a printwriter to the system.
    System.out.println("Warning: " + getParseExceptionInfo(spe));
  }

  public void error(SAXParseException spe) throws SAXException {
    String message = "Error: " + getParseExceptionInfo(spe);
    throw new SAXException(message);
  }

  public void fatalError(SAXParseException spe) throws SAXException {
    String message = "Fatal Error: " + getParseExceptionInfo(spe);
    throw new SAXException(message);
  }
}
