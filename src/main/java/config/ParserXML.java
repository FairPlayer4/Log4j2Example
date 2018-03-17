package config;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class ParserXML
{
    static Document doc;

    static File log4jXml;

    static {
        log4jXml = new File("log4j2.xml");
        if (!log4jXml.exists()) {
            log4jXml = new File("target/classes/log4j2.xml");
        }
    }

    private static void loadLog4jXml()
    {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(log4jXml);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            //NodeList nList = doc.getElementsByTagName("staff");

            System.out.println("----------------------------");

            /*for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("Staff id : " + eElement.getAttribute("id"));
                    System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
                    System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
                    System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
                    System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());

                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addFileAppender(String appenderName, String fileName, boolean append)
    {
        loadLog4jXml();
        Element appender = doc.createElement("RandomAccessFile");
        appender.setAttribute("name", appenderName);
        appender.setAttribute("fileName", fileName);
        appender.setAttribute("immediateFlush", "false");
        appender.setAttribute("append", String.valueOf(append));
        Element patternLayout = doc.createElement("PatternLayout");
        Element pattern = doc.createElement("pattern");
        pattern.setTextContent("[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n");
        patternLayout.appendChild(pattern);
        appender.appendChild(patternLayout);
        Node appenders = doc.getElementsByTagName("Appenders").item(0);
        appenders.appendChild(appender);

        saveDoc();
        /*
        <RandomAccessFile name="RandomAccessFileAppenderError" fileName="Logs/ErrorLog.log" immediateFlush="false" append="true">
            <PatternLayout>
                <pattern>
                    [%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n
                </pattern>
            </PatternLayout>
        </RandomAccessFile>*/

    }

    private static void saveDoc()
    {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(log4jXml);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
