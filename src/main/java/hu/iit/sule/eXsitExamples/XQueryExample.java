package hu.iit.sule.eXsitExamples;

import org.xmldb.api.base.*;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.*;
import org.exist.xmldb.EXistResource;
public class XQueryExample {

    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private static String collection = "/db/videos/";
    private static String xQuery = "xquery version \"3.1\";\n" +
            "\n" +
            "import module namespace file=\"http://exist-db.org/xquery/file\";\n" +
            "\n" +
            "let $result_file := \"C:\\Result\\result.xml\"\n" +
            "let $doc := doc(\"/db/videos/videos.xml\")//videos/video\n" +
            "for $x in $doc\n" +
            "where $x/year = 1997\n" +
            "return file:serialize($x, $result_file, '',true())";
    /**
     * args[0] Should be the name of the collection to access
     * args[1] Should be the XQuery to execute
     */
    public static void main(String args[]) throws Exception {

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        try {
            col = DatabaseManager.getCollection(URI + collection,"admin","admin");
            XQueryService xqs = (XQueryService) col.getService("XQueryService","1.0");
            xqs.setProperty("indent", "yes");

            CompiledExpression compiled = xqs.compile(xQuery);
            ResourceSet result = xqs.execute(compiled);
            ResourceIterator i = result.getIterator();
            Resource res = null;
            while(i.hasMoreResources()) {
                try {
                    res = i.nextResource();
                    System.out.println(res.getContent());
                } finally {
                    //dont forget to cleanup resources
                    try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
                }
            }
        } finally {
            //dont forget to cleanup
            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
    }
}