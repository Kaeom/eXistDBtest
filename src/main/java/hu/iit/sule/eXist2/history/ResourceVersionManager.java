package hu.iit.sule.eXist2.history;

import hu.iit.sule.eXist2.util.Util;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import hu.iit.sule.eXist2.history.model.eXistVersionModel;
import org.xmldb.api.base.XMLDBException;

public class ResourceVersionManager {

    private static final String DB = "/db/";//root collection
    private String uri = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private String adminUser = "admin";
    private String adminPass = "admin";
    private static Util util = new Util();

    public ResourceVersionManager(String uri, String adminUser, String adminPass) {
        this.uri = uri;
        this.adminUser = adminUser;
        this.adminPass = adminPass;
    }

    public ArrayList<eXistVersionModel> getResourceHistory(String resource) throws Exception {
        return ReadHistoryXML(getHistoryFromDB(resource));
    }

    public void setVersioningToCollection(String coll){
        //if coll == null => all collection versioning has been enabled
        coll = "";
        String query="xquery version \"3.1\";\n" +
                "let $doc := doc(\"/db/system/config/db/" + coll + "collection.xconf\")\n" +
                "return update insert \n" +
                "    <trigger class=\"org.exist.versioning.VersioningTrigger\">\n" +
                "    <parameter name=\"overwrite\" value=\"yes\"/>\n" +
                "    </trigger>\n" +
                "into $doc//triggers";
        //add trigger to collection collection.xconf file

    }

    public void removeVersioninFromCollection(String coll){
        coll = "";
        String query="xquery version \"3.1\";\n" +
                "let $doc := doc(\"/db/system/config/db/" + coll + "collection.xconf\")\n" +
                "return update delete \n" +
                "    <trigger class=\"org.exist.versioning.VersioningTrigger\">\n" +
                "    <parameter name=\"overwrite\" value=\"yes\"/>\n" +
                "    </trigger>\n" +
                "into $doc//triggers";
        //remove versioning trigger from collection.xconf file
    }

    /**
     *
      * @param history get resource versions data as string
     * @return resource all version data in eXistVersionModel POJO
     */
    private ArrayList<eXistVersionModel> ReadHistoryXML(String history) {
        ArrayList<eXistVersionModel> evmList = new ArrayList<>();
        eXistVersionModel evm;
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(history.getBytes(StandardCharsets.UTF_8));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(input);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("v:revision");

            System.out.println("Doc name: " + doc.getElementsByTagName("v:document").item(0).getTextContent());

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);;

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int rev = Integer.parseInt(((Element) nNode).getAttribute("rev"));
                    String vData = eElement.getElementsByTagName("v:date").item(0).getTextContent();
                    String vUser = eElement.getElementsByTagName("v:user").item(0).getTextContent();

                    evm = null;
                    evm = new eXistVersionModel(rev,vData,vUser);
                    evmList.add(evm);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return evmList;
    }

    /**
     *
     * @param resourceUrl resource full path
     * @return resource versioning data
     * @throws Exception
     */
    private String getHistoryFromDB(String resourceUrl) throws Exception {
        //util.initDatabaseDriver("org.exist.xmldb.DatabaseImpl");
        Collection collection = null;
        String history = "";
        try {
            collection = DatabaseManager.getCollection(uri + DB,adminUser,adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace v=\"http://exist-db.org/versioning\";\n" +
                    "v:history(doc(\""+ resourceUrl +"\"))";
            history = util.execXQuery(query, collection);
        } catch (XMLDBException e) {
            System.out.println("Get History exception: " + e.getMessage());
        }
        return history;
    }

    /**     *
     * @param resourceUrl resource full path
     * @param version resource version number
     */

    public void getChanges(String resourceUrl ,int version){
        Collection collection = null;
        String result = "";
        try{
            collection = DatabaseManager.getCollection(uri+DB,adminUser, adminPass);
            String query = "xquery version \"3.1\";\n" +
                    "import module namespace v=\"http://exist-db.org/versioning\";\n" +
                    "let $doc := doc(\"/db/system/versions"+ resourceUrl + "." + version +"\")\n" +
                    "return $doc";
            System.out.println(query);
            result = util.execXQuery(query,collection);
            convertStringToDocument(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param xmlStr convertible text
     * @return document object converted from text
     * @throws IOException
     */
    private Document convertStringToDocument(String xmlStr) throws IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc = null;
        try
        {
            builder = factory.newDocumentBuilder();
            doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        xmlPrettier(doc);
        return null;
    }

    /**
     *
      * @param document document from create pretty output
     * @throws IOException
     */
    private void xmlPrettier(Document document) throws IOException {
        OutputFormat format = new OutputFormat(document); //document is an instance of org.w3c.dom.Document
        format.setLineWidth(65);
        format.setIndenting(true);
        format.setIndent(2);
        Writer out = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(out, format);
        serializer.serialize(document);

        String formattedXML = out.toString();
        System.out.println("formatted out:" + formattedXML);
    }
}
